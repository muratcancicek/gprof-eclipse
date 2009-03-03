/*
 * =======================================================================
 * ============================ gprof-eclipse ============================
 * =======================================================================
 * 
 * 
 * File: GProfSession.java
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

package org.eclipse.cdt.gprof.launch;

import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;

import org.eclipse.cdt.debug.core.ICDTLaunchConfigurationConstants;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.Preferences;
import org.eclipse.core.runtime.Status;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.ui.console.ConsolePlugin;
import org.eclipse.ui.console.IConsole;
import org.eclipse.ui.console.IConsoleManager;
import org.eclipse.ui.console.MessageConsole;
import org.eclipse.ui.console.MessageConsoleStream;

/**
 * Represents the gprof profiling session to be run.
 */
public class GProfSession
{
    // =================== GProf Session String Constants ================== //
    
    static final String GPROF_CONSOLE_NAME = "GProf Profiling";
    
    static final String GPROF_GPROF_COMMAND_STR = "gprof_command";
    
    static final String GPROF_UNIVERSAL_PATH_SEPARATOR = "/";
    
    static final String GPROF_DEFAULT_OUTPUT_FILE = "gmon.out";
    
    static final String GPROF_DEFAULT_PROJECT_EXECUTABLE = "a.out";
    
    static final String GPROF_EMPTY_STRING = "";
    
    private static final String GPROF_NOT_VERBOSE = "-b";
    
    // =================== GProf Session String Constants ================== //
    
    /** Holds the full path to the gprof executable. */
    private String gprofCommand = GPROF_EMPTY_STRING;
    
    /** Holds the full path to the project executable. */
    private String projectExecutable = GPROF_EMPTY_STRING;
    
    /** Holds the full path the project's gprof output file (i.e. gmon.out). */
    private String projectGProfOutputFile = GPROF_EMPTY_STRING;
    
    /**
     * Constructs the session using the given configuration and working directory.
     * 
     * @param config The launch configuration.
     * @param workingDirectory The working directory to run gprof in.
     */
    public GProfSession(ILaunchConfiguration config, String workingDirectory)
    {
        try
        {
            // Get the full path to the gprof command.
            Preferences preferences = GProfLaunchPlugin.getDefault().getPluginPreferences();
            this.gprofCommand = preferences.getString(GProfLaunchPlugin.GPROF_COMMAND);
            
            // Compose the full path to the executable to be launched (and profiled).
            String programName = config.getAttribute(ICDTLaunchConfigurationConstants.ATTR_PROGRAM_NAME, GPROF_EMPTY_STRING);
            String programArguments = config.getAttribute(ICDTLaunchConfigurationConstants.ATTR_PROGRAM_ARGUMENTS, GPROF_EMPTY_STRING);
            
            if (!workingDirectory.endsWith(GPROF_UNIVERSAL_PATH_SEPARATOR))
            {
                workingDirectory += GPROF_UNIVERSAL_PATH_SEPARATOR;
            }
            this.projectExecutable = workingDirectory + programName + " " + programArguments;
            this.projectGProfOutputFile = workingDirectory + GPROF_DEFAULT_OUTPUT_FILE;
            
        }
        catch (CoreException e)
        {
            e.printStackTrace();
        }
    }
    
    /**
     * Runs the gprof profiling session.
     * 
     * @param parser The parser used to parse the gprof profiling output.
     * @throws CoreException When the complete gprof profiling command (including the project executable) fails to execute.
     */
    public void run(IGProfLaunchParser parser) throws CoreException
    {
        try
        {
            // Get the workspace full path and then compose the commands to run.
            String[] command = new String[] { this.gprofCommand, GProfSession.GPROF_NOT_VERBOSE, this.projectExecutable,
                this.projectGProfOutputFile };
            
            // Execute the gprof profiling command.
            Process process = Runtime.getRuntime().exec(command);
            
            // Process any errors right away (write them to the Console).
            InputStream errorStream = process.getErrorStream();
            Scanner errorScanner = new Scanner(errorStream);
            MessageConsole console = getConsole(GPROF_CONSOLE_NAME);
            MessageConsoleStream out = console.newMessageStream();
            if (errorStream.available() > 0)
            {
                while (errorScanner.hasNextLine())
                {
                    out.println(errorScanner.nextLine());
                }
            }
            
            // Start the parser.
            parser.parse(process.getInputStream());
        }
        catch (IOException e)
        {
            Status status = new Status(Status.ERROR, GProfLaunchPlugin.PLUGIN_ID, "The command \'" + this.gprofCommand
                + "\' could not be run.\n" + e.getLocalizedMessage());
            throw new CoreException(status);
        }
    }
    
    /**
     * Retrieves the console with the given name, creating one if it does not already exist.
     * 
     * @param name The name of the console to retrieve.
     * @return The console.
     */
    private MessageConsole getConsole(String name)
    {
        ConsolePlugin consolePlugin = ConsolePlugin.getDefault();
        IConsoleManager consoleManager = consolePlugin.getConsoleManager();
        IConsole[] consoles = consoleManager.getConsoles();
        for (int i = 0; i < consoles.length; i++)
        {
            if (name.equals(consoles[i].getName()))
            {
                return (MessageConsole) consoles[i];
            }
        }
        MessageConsole console = new MessageConsole(name, null);
        consoleManager.addConsoles(new IConsole[] { console });
        return console;
    }
}
