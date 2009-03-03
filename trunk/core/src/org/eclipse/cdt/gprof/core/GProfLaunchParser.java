/*
 * =======================================================================
 * ============================ gprof-eclipse ============================
 * =======================================================================
 * 
 * 
 * File: GProfLaunchParser.java
 * 
 * 
 * -----------------------------------------------------------------------
 * Copyright (c) 2009 Chris Culy and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * + Chris Culy - initial API and implementation
 * -----------------------------------------------------------------------
 * 
 * 
 * Last changed:
 * $Revision$
 * $Author$
 * $Date$
 */

package org.eclipse.cdt.gprof.core;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Scanner;

import org.eclipse.cdt.gprof.core.profiled.ProfiledCaller;
import org.eclipse.cdt.gprof.core.profiled.ProfiledFunction;
import org.eclipse.cdt.gprof.core.profiled.ProfiledSubroutine;
import org.eclipse.cdt.gprof.core.profiled.ProfilerReport;
import org.eclipse.cdt.gprof.launch.IGProfLaunchParser;

/**
 * Parses the gprof output into a profiler report.
 */
public class GProfLaunchParser implements IGProfLaunchParser
{
    /**
     * Represents the range of a statistic within a given line to be parsed.
     */
    private static final class StatRange
    {
        /** The starting index of the statistic. */
        public int start;
        
        /** The ending index of the statistic. */
        public int end;
        
        /**
         * Retrieves whether the given stat range overlaps this stat range.
         * 
         * @param statRange The stat range to compare.
         * @return <code>true</code> if the stat ranges overlap;<code>false</code> otherwise.
         */
        public boolean overlaps(StatRange statRange)
        {
            return (this.start <= statRange.end && this.end >= statRange.start)
                || (statRange.start <= this.end && statRange.end >= this.start);
        }
    }
    
    /** Holds the scanner used to parse the gprof output. */
    private Scanner scanner;
    
    /** Holds the state of the parser. */
    private int state = PARSE_STATE_NONE;
    
    /** Holds the profiler report that the parser populates. */
    private ProfilerReport report = new ProfilerReport();
    
    /** Holds the statistic ranges for the flat profile stats. */
    private StatRange[] flatProfileStatRanges = new StatRange[PARSE_FLAT_PROFILE_STAT_COUNT];
    
    /** Holds the statistic ranges for the call graph stats. */
    private StatRange[] callGraphStatRanges = new StatRange[PARSE_CALL_GRAPH_STAT_COUNT];
    
    // ======================= Parser State Constants ====================== //
    
    private static final int PARSE_STATE_NONE = 0x0001;
    
    private static final int PARSE_STATE_FLAT_PROFILE = 0x0002;
    
    private static final int PARSE_STATE_FLAT_PROFILE_STATS = 0x0004;
    
    private static final int PARSE_STATE_FLAT_PROFILE_STATS_END = 0x0008;
    
    private static final int PARSE_STATE_CALL_GRAPH = 0x0010;
    
    private static final int PARSE_STATE_CALL_GRAPH_STATS_PROFILED_CALLER = 0x0020;
    
    private static final int PARSE_STATE_CALL_GRAPH_STATS_PROFILED_SUBROUTINE = 0x0040;
    
    // ======================= Parser State Constants ====================== //
    
    // ====================== General Parser Constants ===================== //
    
    private static final int PARSE_FLAT_PROFILE_STAT_COUNT = 7;
    
    private static final int PARSE_CALL_GRAPH_STAT_COUNT = 6;
    
    private static final String PARSE_STR_FLAG_CHARACTER = "";
    
    private static final String[] PARSE_STR_FLAG = { PARSE_STR_FLAG_CHARACTER };
    
    private static final String[] PARSE_STR_FLAT_PROFILE = { "Flat", "profile:" };
    
    private static final String[] PARSE_STR_FLAT_PROFILE_STATS = { "time", "seconds", "seconds", "calls", "ms/call", "ms/call", "name" };
    
    private static final String[] PARSE_STR_CALL_GRAPH = { "Call", "graph" };
    
    private static final String[] PARSE_STR_CALL_GRAPH_STATS = { "index", "%", "time", "self", "children", "called", "name" };
    
    private static final String PARSE_STR_CALL_GRAPH_FLAG_LINE_START = "------------------";
    
    private static final String PARSE_CG_SPONTANEOUS_ENTRY = "<spontaneous>";
    
    private static final String PARSE_CALL_COUNT_SEPARATOR = "/";
    
    private static final String PARSE_CALL_COUNTS_SEPARATOR = "+";
    
    private static final String PARSE_ID_START_STR = "[";
    
    private static final String PARSE_ID_END_STR = "]";
    
    private static final Object PARSE_PERCENT_STR = "%";
    
    private static final String PARSE_EMPTY_STRING = "";
    
    private static final String PARSE_SPACE_STR = " ";
    
    private static final String PARSE_WHITESPACE_REGEX = "\\s";
    
    private static final String PARSE_WHITESPACES_REGEX = "\\s+";
    
    private static final String PARSE_TRAILING_WHITESPACE_REGEX = "\\s+$";
    
    // ====================== General Parser Constants ===================== //
    
    /**
     * Parses the gprof output piped in on the given input stream.
     * 
     * @param input The gprof output as an input stream.
     */
    public void parse(InputStream input)
    {
        // Create a Scanner for the input stream and start parsing.
        scanner = new Scanner(input);
        
        if (scanner != null)
        {
            // Create a list of profiled functions to be filled.
            ArrayList<ProfiledFunction> functions = new ArrayList<ProfiledFunction>();
            
            // Create a list of profiled callers and profiled subroutines.
            ArrayList<ProfiledCaller> callers = new ArrayList<ProfiledCaller>();
            ArrayList<ProfiledSubroutine> subroutines = new ArrayList<ProfiledSubroutine>();
            
            // Create a ProfiledFunction object to point to the call graph function currently being parsed.
            ProfiledFunction currentCallGraphFunction = new ProfiledFunction();
            
            // Perform the actual parsing of the gprof output.
            while (scanner.hasNextLine())
            {
                // Get the current line and split it into tokens by whitespace.
                String line = scanner.nextLine();
                String[] tokens = getTokens(line);
                
                // Depending on the state, only check for appropriate inputs.
                if (this.isInState(PARSE_STATE_NONE))
                {
                    // Update the state if appropriate.
                    if (stringArraysMatch(GProfLaunchParser.PARSE_STR_FLAT_PROFILE, tokens))
                    {
                        this.setState(PARSE_STATE_FLAT_PROFILE);
                    }
                }
                else if (this.isInState(PARSE_STATE_FLAT_PROFILE))
                {
                    if (stringArraysMatch(GProfLaunchParser.PARSE_STR_FLAT_PROFILE_STATS, tokens))
                    {
                        // Calculate the statistic ranges (used to parse the actual stats later on).
                        String currentLine = line;
                        int parsePosition = 0;
                        int parseStatCount = 0;
                        while (parseStatCount < PARSE_FLAT_PROFILE_STAT_COUNT)
                        {
                            StatRange statRange = getStatRange(currentLine, parsePosition);
                            if (statRange != null)
                            {
                                this.flatProfileStatRanges[parseStatCount] = statRange;
                                parsePosition = statRange.end + 1;
                                parseStatCount++;
                            }
                            else
                            {
                                break;
                            }
                        }
                        
                        // Update the state.
                        this.setState(PARSE_STATE_FLAT_PROFILE_STATS);
                    }
                }
                else if (this.isInState(PARSE_STATE_FLAT_PROFILE_STATS))
                {
                    // Update the state if appropriate.
                    if (stringArraysMatch(GProfLaunchParser.PARSE_STR_FLAG, tokens))
                    {
                        this.setState(GProfLaunchParser.PARSE_STATE_FLAT_PROFILE_STATS_END);
                        continue;
                    }
                    
                    // Parse the function's statistics.
                    ProfiledFunction function = new ProfiledFunction();
                    int parsePosition = 0;
                    while (parsePosition < line.trim().length())
                    {
                        StatRange statRange = getStatRange(line, parsePosition);
                        if (statRange != null)
                        {
                            parseFPFunctionStat(function, line, statRange);
                            parsePosition = statRange.end + 1;
                        }
                    }
                    
                    // Add the function to the list of functions.
                    functions.add(function);
                }
                else if (this.isInState(PARSE_STATE_FLAT_PROFILE_STATS_END))
                {
                    // Update the state if appropriate.
                    if (stringArraysMatch(GProfLaunchParser.PARSE_STR_CALL_GRAPH, tokens))
                    {
                        this.setState(PARSE_STATE_CALL_GRAPH);
                    }
                }
                else if (this.isInState(PARSE_STATE_CALL_GRAPH))
                {
                    if (stringArraysMatch(GProfLaunchParser.PARSE_STR_CALL_GRAPH_STATS, tokens))
                    {
                        // Calculate the statistic ranges (used to parse the actual stats later on).
                        String currentLine = line;
                        int parsePosition = 0;
                        int parseStatCount = 0;
                        while (parseStatCount < PARSE_CALL_GRAPH_STAT_COUNT)
                        {
                            StatRange statRange = getStatRange(currentLine, parsePosition);
                            if (statRange != null)
                            {
                                this.callGraphStatRanges[parseStatCount] = statRange;
                                parsePosition = statRange.end + 1;
                                parseStatCount++;
                            }
                            else
                            {
                                break;
                            }
                        }
                        
                        // Update the state.
                        this.setState(PARSE_STATE_CALL_GRAPH_STATS_PROFILED_CALLER);
                    }
                }
                else if (this.isInState(PARSE_STATE_CALL_GRAPH_STATS_PROFILED_CALLER))
                {
                    // Check if we are done parsing.
                    if (stringArraysMatch(GProfLaunchParser.PARSE_STR_FLAG, tokens))
                    {
                        // We're done, so stop parsing.
                        break;
                    }
                    
                    // Ignore '<spontaneous>' entries in the call graph.
                    if (line.trim().equals(PARSE_CG_SPONTANEOUS_ENTRY))
                    {
                        continue;
                    }
                    // Parse the caller's statistics.
                    else if (!line.startsWith(GProfLaunchParser.PARSE_ID_START_STR))
                    {
                        ProfiledCaller caller = new ProfiledCaller();
                        int parsePosition = 0;
                        String lineWithNoTrailingWhitespace = line.replaceAll(GProfLaunchParser.PARSE_TRAILING_WHITESPACE_REGEX,
                            GProfLaunchParser.PARSE_EMPTY_STRING);
                        while (parsePosition < lineWithNoTrailingWhitespace.length())
                        {
                            StatRange statRange = getStatRange(line, parsePosition);
                            if (statRange != null)
                            {
                                parseCGCallerStat(caller, line, statRange);
                                parsePosition = statRange.end + 1;
                            }
                        }
                        
                        // If the corresponding function does not exist, add it.
                        ProfiledFunction function = getProfiledFunctionByName(functions, caller.GetName());
                        if (function == null)
                        {
                            function = new ProfiledFunction();
                            function.SetID(caller.GetID());
                            function.SetName(caller.GetName());
                            functions.add(function);
                        }
                        
                        // Add the caller to the list of callers.
                        callers.add(caller);
                    }
                    // Parse the function's statistics.
                    else
                    {
                        ProfiledFunction function = new ProfiledFunction();
                        int parsePosition = 0;
                        while (parsePosition < line.trim().length())
                        {
                            StatRange statRange = getStatRange(line, parsePosition);
                            if (statRange != null)
                            {
                                parseCGFunctionStat(function, line, statRange);
                                parsePosition = statRange.end + 1;
                            }
                        }
                        
                        // If the function already exists, update it-otherwise add it.
                        ProfiledFunction existingFunction = getProfiledFunctionByName(functions, function.GetName());
                        if (existingFunction != null)
                        {
                            // Only update the fields that were just parsed.
                            existingFunction.SetPercentTotalTime(function.GetPercentTotalTime());
                            existingFunction.SetSelfTime(function.GetSelfTime());
                            existingFunction.SetSubroutineTime(function.GetSubroutineTime());
                            existingFunction.SetCallCount(function.GetCallCount());
                            existingFunction.SetRecursiveCallCount(function.GetRecursiveCallCount());
                            existingFunction.SetID(function.GetID());
                            existingFunction.SetName(function.GetName());
                            
                            // Set this so that when we use it below it is the correct object.
                            function = existingFunction;
                        }
                        else
                        {
                            functions.add(function);
                        }
                        
                        // Add the callers if there are any and then clear the array.
                        if (callers.size() > 0)
                        {
                            function.SetCallers(callers);
                            callers = new ArrayList<ProfiledCaller>();
                        }
                        
                        // Set this function as the current call graph function.
                        currentCallGraphFunction = function;
                        
                        // Update the state.
                        this.setState(GProfLaunchParser.PARSE_STATE_CALL_GRAPH_STATS_PROFILED_SUBROUTINE);
                    }
                }
                else if (this.isInState(PARSE_STATE_CALL_GRAPH_STATS_PROFILED_SUBROUTINE))
                {
                    // Check if we are done parsing subroutines for the current function.
                    if (tokens.length == 1 && tokens[0].startsWith(GProfLaunchParser.PARSE_STR_CALL_GRAPH_FLAG_LINE_START))
                    {
                        // Set the subroutines on the current call graph function (if there are any) and clear the subroutines array.
                        if (subroutines.size() > 0)
                        {
                            currentCallGraphFunction.SetSubroutines(subroutines);
                            subroutines = new ArrayList<ProfiledSubroutine>();
                        }
                        
                        // We are done with this profiled function and its callers and subroutines, so go back to the caller state.
                        this.setState(PARSE_STATE_CALL_GRAPH_STATS_PROFILED_CALLER);
                    }
                    // Parse the profiled subroutine's statistics.
                    else
                    {
                        ProfiledSubroutine subroutine = new ProfiledSubroutine();
                        int parsePosition = 0;
                        while (parsePosition < line.trim().length())
                        {
                            StatRange statRange = getStatRange(line, parsePosition);
                            if (statRange != null)
                            {
                                parseCGSubroutineStat(subroutine, line, statRange);
                                parsePosition = statRange.end + 1;
                            }
                        }
                        
                        // Add the subroutine to the list of subroutines.
                        subroutines.add(subroutine);
                    }
                }
            }
            
            // Store the list of constructed profiled functions in the profiled report.
            this.report.SetFunctions(functions);
        }
    }
    
    /**
     * Parses the given region of the line specified by the stat range as a flat profile function statistic and sets it for the given
     * function.
     * 
     * @param function The function to parse the statistic for.
     * @param line The line to parse.
     * @param statRange The statistic range to parse.
     */
    private void parseFPFunctionStat(ProfiledFunction function, String line, StatRange statRange)
    {
        // Get the string that holds the actual statistic value to be parsed.
        String statString = line.substring(statRange.start, statRange.end + 1);
        
        // Determine the correct statistic to be parsed.
        if (statRange.overlaps(flatProfileStatRanges[0]))
        {
            if (function.GetPercentTotalSelfTime() == -1f)
            {
                // Percent Total Self Time.
                function.SetPercentTotalSelfTime(Float.parseFloat(statString));
            }
            else
            {
                // Total Time.
                function.SetTotalTime(Float.parseFloat(statString));
            }
        }
        else if (statRange.overlaps(flatProfileStatRanges[1]))
        {
            if (function.GetTotalTime() == -1f)
            {
                // Total Time.
                function.SetTotalTime(Float.parseFloat(statString));
            }
            else
            {
                // Self Time.
                function.SetSelfTime(Float.parseFloat(statString));
            }
        }
        else if (statRange.overlaps(flatProfileStatRanges[2]))
        {
            if (function.GetSelfTime() == -1f)
            {
                // Self Time.
                function.SetSelfTime(Float.parseFloat(statString));
            }
            else
            {
                // Call Count.
                function.SetCallCount(Float.parseFloat(statString));
            }
        }
        else if (statRange.overlaps(flatProfileStatRanges[3]))
        {
            if (function.GetCallCount() == -1f)
            {
                // Call Count.
                function.SetCallCount(Float.parseFloat(statString));
            }
            else
            {
                // Self MS Per Call.
                function.SetSelfMSPerCall(Float.parseFloat(statString));
            }
        }
        else if (statRange.overlaps(flatProfileStatRanges[4]))
        {
            if (function.GetSelfMSPerCall() == -1f)
            {
                // Self MS Per Call.
                function.SetSelfMSPerCall(Float.parseFloat(statString));
            }
            else
            {
                // Total MS Per Call.
                function.SetTotalMSPerCall(Float.parseFloat(statString));
            }
        }
        else if (statRange.overlaps(flatProfileStatRanges[5]))
        {
            if (function.GetTotalMSPerCall() == -1f)
            {
                // Total MS Per Call.
                function.SetTotalMSPerCall(Float.parseFloat(statString));
            }
            else
            {
                // Name.
                if (function.GetName() != null && !function.GetName().isEmpty())
                {
                    function.SetName(function.GetName() + GProfLaunchParser.PARSE_SPACE_STR + statString);
                }
                else
                {
                    function.SetName(statString);
                }
            }
        }
        else if (statRange.overlaps(flatProfileStatRanges[6]) || statRange.start >= flatProfileStatRanges[6].end)
        {
            // Name.
            if (function.GetName() != null && !function.GetName().isEmpty())
            {
                function.SetName(function.GetName() + GProfLaunchParser.PARSE_SPACE_STR + statString);
            }
            else
            {
                function.SetName(statString);
            }
        }
    }
    
    /**
     * Parses the given region of the line specified by the stat range as a call graph function statistic and sets it for the given
     * function.
     * 
     * @param function The function to parse the statistic for.
     * @param line The line to parse.
     * @param statRange The statistic range to parse.
     */
    private void parseCGFunctionStat(ProfiledFunction function, String line, StatRange statRange)
    {
        // Get the string that holds the actual statistic value to be parsed.
        String statString = line.substring(statRange.start, statRange.end + 1);
        
        // Determine the correct statistic to be parsed.
        if (statRange.overlaps(callGraphStatRanges[0]))
        {
            if (function.GetID() == -1)
            {
                // ID.
                function.SetID(getID(statString));
            }
            else
            {
                // Percent Total Time.
                function.SetPercentTotalTime(Float.parseFloat(statString));
            }
        }
        else if (statRange.overlaps(callGraphStatRanges[1]))
        {
            if (function.GetPercentTotalTime() == -1f)
            {
                // Percent Total Time.
                function.SetPercentTotalTime(Float.parseFloat(statString));
            }
            else
            {
                // Since self time is also parsed in the flat profile, only set it if it isn't already set.
                if (function.GetSelfTime() == -1f)
                {
                    // Self Time.
                    function.SetSelfTime(Float.parseFloat(statString));
                }
            }
        }
        else if (statRange.overlaps(callGraphStatRanges[2]))
        {
            if (function.GetSelfTime() == -1f)
            {
                // Self Time.
                function.SetSelfTime(Float.parseFloat(statString));
            }
            else
            {
                // Subroutine Time.
                function.SetSubroutineTime(Float.parseFloat(statString));
            }
        }
        else if (statRange.overlaps(callGraphStatRanges[3]))
        {
            if (function.GetSubroutineTime() == -1f)
            {
                // Subroutine Time.
                function.SetSubroutineTime(Float.parseFloat(statString));
            }
            else
            {
                // Call Counts.
                int[] callCounts = getCGCallCounts(statString);
                if (function.GetCallCount() == -1)
                {
                    function.SetCallCount(callCounts[0]);
                }
                if (callCounts.length == 2)
                {
                    function.SetRecursiveCallCount(callCounts[1]);
                }
            }
        }
        else if (statRange.overlaps(callGraphStatRanges[4]))
        {
            if (function.GetCallCount() == -1 || function.GetRecursiveCallCount() == -1)
            {
                // Call Counts.
                int[] callCounts = getCGCallCounts(statString);
                if (function.GetCallCount() == -1)
                {
                    function.SetCallCount(callCounts[0]);
                }
                if (callCounts.length == 2)
                {
                    function.SetRecursiveCallCount(callCounts[1]);
                }
            }
            else
            {
                // Name.
                if (function.GetName() != null && !function.GetName().isEmpty())
                {
                    function.SetName(function.GetName() + GProfLaunchParser.PARSE_SPACE_STR + statString);
                }
                else
                {
                    function.SetName(statString);
                }
            }
        }
        else if (statRange.overlaps(callGraphStatRanges[5]) || statRange.start > callGraphStatRanges[5].start)
        {
            // Ignore the ID at the end of the line since it was parsed at the beginning of the line.
            if (statString.startsWith(GProfLaunchParser.PARSE_ID_START_STR))
            {
                return;
            }
            // Name.
            else if (function.GetName() != null && !function.GetName().isEmpty())
            {
                function.SetName(function.GetName() + GProfLaunchParser.PARSE_SPACE_STR + statString);
            }
            else
            {
                function.SetName(statString);
            }
        }
    }
    
    /**
     * Parses the given region of the line specified by the stat range as a caller statistic and sets it for the given function.
     * 
     * @param caller The caller to parse the statistic for.
     * @param line The line to parse.
     * @param statRange The statistic range to parse.
     */
    private void parseCGCallerStat(ProfiledCaller caller, String line, StatRange statRange)
    {
        // Get the string that holds the actual statistic value to be parsed.
        String statString = line.substring(statRange.start, statRange.end + 1);
        
        // Determine the correct statistic to be parsed.
        if (statRange.overlaps(callGraphStatRanges[2]))
        {
            if (caller.GetTimeInCalledSelf() == -1f)
            {
                // Time In Called Self.
                caller.SetTimeInCalledSelf(Float.parseFloat(statString));
            }
            else
            {
                // Time In Called Subroutines.
                caller.SetTimeInCalledSubroutines(Float.parseFloat(statString));
            }
        }
        else if (statRange.overlaps(callGraphStatRanges[3]))
        {
            if (caller.GetTimeInCalledSubroutines() == -1f)
            {
                // Time In Called Subroutines.
                caller.SetTimeInCalledSubroutines(Float.parseFloat(statString));
            }
            else
            {
                // Call Count.
                caller.SetCallCount(getCGCallCount(statString));
            }
        }
        else if (statRange.overlaps(callGraphStatRanges[4]))
        {
            if (caller.GetCallCount() == -1)
            {
                // Call Counts.
                caller.SetCallCount(getCGCallCount(statString));
            }
            else
            {
                // Name.
                if (caller.GetName() != null && !caller.GetName().isEmpty())
                {
                    caller.SetName(caller.GetName() + GProfLaunchParser.PARSE_SPACE_STR + statString);
                }
                else
                {
                    caller.SetName(statString);
                }
            }
        }
        else if (statRange.overlaps(callGraphStatRanges[5]) || statRange.start >= callGraphStatRanges[5].end)
        {
            // ID.
            if (statString.startsWith(GProfLaunchParser.PARSE_ID_START_STR) && caller.GetID() == -1)
            {
                caller.SetID(getID(statString));
            }
            // Name.
            else if (caller.GetName() != null && !caller.GetName().isEmpty())
            {
                caller.SetName(caller.GetName() + GProfLaunchParser.PARSE_SPACE_STR + statString);
            }
            else
            {
                caller.SetName(statString);
            }
        }
    }
    
    /**
     * Parses the given region of the line specified by the stat range as a subroutine statistic and sets it for the given function.
     * 
     * @param subroutine The subroutine to parse the statistic for.
     * @param line The line to parse.
     * @param statRange The statistic range to parse.
     */
    private void parseCGSubroutineStat(ProfiledSubroutine subroutine, String line, StatRange statRange)
    {
        // Get the string that holds the actual statistic value to be parsed.
        String statString = line.substring(statRange.start, statRange.end + 1);
        
        // Determine the correct statistic to be parsed.
        if (statRange.overlaps(callGraphStatRanges[2]))
        {
            if (subroutine.GetTimeInSubroutineSelf() == -1f)
            {
                // Time In Subroutine Self.
                subroutine.SetTimeInSubroutineSelf(Float.parseFloat(statString));
            }
            else
            {
                // Time In Subroutine Subroutines.
                subroutine.SetTimeInSubroutineSubroutines(Float.parseFloat(statString));
            }
        }
        else if (statRange.overlaps(callGraphStatRanges[3]))
        {
            if (subroutine.GetTimeInSubroutineSubroutines() == -1f)
            {
                // Time In Subroutine Subroutines.
                subroutine.SetTimeInSubroutineSubroutines(Float.parseFloat(statString));
            }
            else
            {
                // Call Count.
                subroutine.SetCallCount(getCGCallCount(statString));
            }
        }
        else if (statRange.overlaps(callGraphStatRanges[4]))
        {
            if (subroutine.GetCallCount() == -1)
            {
                // Call Counts.
                subroutine.SetCallCount(getCGCallCount(statString));
            }
            else
            {
                // Name.
                if (subroutine.GetName() != null && !subroutine.GetName().isEmpty())
                {
                    subroutine.SetName(subroutine.GetName() + GProfLaunchParser.PARSE_SPACE_STR + statString);
                }
                else
                {
                    subroutine.SetName(statString);
                }
            }
        }
        else if (statRange.overlaps(callGraphStatRanges[5]) || statRange.start >= callGraphStatRanges[5].end)
        {
            // ID.
            if (statString.startsWith(GProfLaunchParser.PARSE_ID_START_STR) && subroutine.GetID() == -1)
            {
                subroutine.SetID(getID(statString));
            }
            // Name.
            else if (subroutine.GetName() != null && !subroutine.GetName().isEmpty())
            {
                subroutine.SetName(subroutine.GetName() + GProfLaunchParser.PARSE_SPACE_STR + statString);
            }
            else
            {
                subroutine.SetName(statString);
            }
        }
    }
    
    /**
     * Retrieves whether the parser is in the given state.
     * 
     * @param state The state to check.
     * @return <code>true</code> if the parser is in the given state;<code>false</code> otherwise.
     */
    private boolean isInState(int state)
    {
        return this.state == state;
    }
    
    /**
     * Sets the state for the parser.
     * 
     * @param state The new state for the parser.
     */
    private void setState(int state)
    {
        this.state = state;
    }
    
    /**
     * Retrieves the number of times a caller called the current function or a subroutine was called by the current function given the parse
     * string for the 'called' column in the call graph section.
     * 
     * @param parseString The string to be parsed.
     * @return The call count.
     */
    private static int getCGCallCount(String parseString)
    {
        int index = parseString.indexOf(GProfLaunchParser.PARSE_CALL_COUNT_SEPARATOR);
        if (index != -1)
        {
            return Integer.parseInt(parseString.substring(0, index));
        }
        return Integer.parseInt(parseString);
    }
    
    /**
     * Retrieves the call counts for the given parse string.
     * 
     * @param parseString The parse string.
     * @return The call counts in the array form : { nonrecursive call count, recursive call count } if there are recursive calls; {
     *         nonrecursive call count } otherwise.
     */
    private static int[] getCGCallCounts(String parseString)
    {
        int index = parseString.indexOf(GProfLaunchParser.PARSE_CALL_COUNTS_SEPARATOR);
        if (index != -1)
        {
            return new int[] { Integer.parseInt(parseString.substring(0, index)), Integer.parseInt(parseString.substring(index + 1)) };
        }
        else
        {
            return new int[] { Integer.parseInt(parseString) };
        }
    }
    
    /**
     * Retrieves the ID of the function given the parse string of the form '[ID#]'.
     * 
     * @param parseString The parse string.
     * @return The ID of the function.
     */
    private static int getID(String parseString)
    {
        int startIndex = parseString.indexOf(GProfLaunchParser.PARSE_ID_START_STR);
        int endIndex = parseString.indexOf(GProfLaunchParser.PARSE_ID_END_STR);
        if (startIndex != -1 && endIndex != -1)
        {
            return Integer.parseInt(parseString.substring(startIndex + 1, endIndex));
        }
        return -1;
    }
    
    /**
     * Retrieves the function by name.
     * 
     * @param functions The list of functions to search.
     * @param name The name of the function to retrieve.
     * @return The function with the given name or <code>null</code> if the function cannot be found.
     */
    private static ProfiledFunction getProfiledFunctionByName(ArrayList<ProfiledFunction> functions, String name)
    {
        if (name != null)
        {
            for (int i = 0; i < functions.size(); i++)
            {
                if (name.equals(functions.get(i).GetName()))
                {
                    return functions.get(i);
                }
            }
        }
        return null;
    }
    
    /**
     * Retrieves the first stat range for the given string starting from the given parsing position. This is essentially the start and end
     * indices of the first token of the given string.
     * 
     * @param string The string to search.
     * @param parsePosition The position in the string to start parsing from.
     * @return The statistic range for the given string or <code>null</code> if none is found.
     */
    private static StatRange getStatRange(String string, int parsePosition)
    {
        // Get the starting index (the first non whitespace character).
        int startIndex = -1;
        for (int i = parsePosition; i < string.length(); i++)
        {
            // Check if the current character is not a whitespace character.
            // (Special case for the "percent time" statistic in the call graph, we ignore
            // the '%' character since we want the start and end indices to be for the string 'time'.)
            if (!string.substring(i, i + 1).matches(GProfLaunchParser.PARSE_WHITESPACE_REGEX)
                && !string.substring(i, i + 1).equals(GProfLaunchParser.PARSE_PERCENT_STR))
            {
                startIndex = i;
                break;
            }
        }
        
        // Check if the start of the column header exists.
        if (startIndex != -1)
        {
            // Get the ending index (search for the first whitespace character and return the preceding position).
            for (int i = startIndex; i < string.length(); i++)
            {
                // Check if the current character is not a whitespace character.
                if (string.substring(i, i + 1).matches(GProfLaunchParser.PARSE_WHITESPACE_REGEX))
                {
                    StatRange statRange = new StatRange();
                    statRange.start = startIndex;
                    statRange.end = i - 1;
                    return statRange;
                }
            }
            
            // In this case, the statistic column header goes to the end of the line.
            StatRange statRange = new StatRange();
            statRange.start = startIndex;
            statRange.end = string.length() - 1;
            return statRange;
        }
        return null;
    }
    
    /**
     * Takes the given line and returns a proper set of tokens.
     * 
     * @param line The line to parse into tokens.
     * @return The proper set of tokens.
     */
    private String[] getTokens(String line)
    {
        String[] strings = line.split(GProfLaunchParser.PARSE_WHITESPACES_REGEX);
        // Check if the array of strings is null.
        if (strings == null)
        {
            return null;
        }
        
        // Check for the special flag character case.
        if (line.equals(PARSE_STR_FLAG_CHARACTER))
        {
            return new String[] { line };
        }
        
        // Go through and remove all empty strings.
        ArrayList<String> stringList = new ArrayList<String>();
        for (int i = 0; i < strings.length; i++)
        {
            if (strings[i].length() > 0)
            {
                stringList.add(strings[i]);
            }
        }
        
        return stringList.toArray(new String[stringList.size()]);
    }
    
    /**
     * Retrieves whether the two arrays of Strings match.
     * 
     * @param arrayOne The first array.
     * @param arrayTwo The second array.
     * @return <code>true</code> if the two arrays match;<code>false</code> otherwise.
     */
    private static boolean stringArraysMatch(String[] arrayOne, String[] arrayTwo)
    {
        // Make sure the two arrays have the same length.
        if (arrayOne.length != arrayTwo.length)
        {
            return false;
        }
        
        // Go through the two and make sure all the elements match.
        for (int i = 0; i < arrayOne.length; i++)
        {
            if (!arrayOne[i].equals(arrayTwo[i]))
            {
                return false;
            }
        }
        return true;
    }
}
