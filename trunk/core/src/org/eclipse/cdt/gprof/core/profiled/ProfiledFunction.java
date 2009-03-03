/*
 * =======================================================================
 * ============================ gprof-eclipse ============================
 * =======================================================================
 * 
 * 
 * File: ProfiledFunction.java
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

package org.eclipse.cdt.gprof.core.profiled;

import java.util.ArrayList;

/**
 * Represents a function that has been profiled.
 * 
 * @author chrisculy
 */
public class ProfiledFunction
{
	// =============== Profiled Function Statistics Constants ============== //
	
    private static final int STAT_PERCENT_TOTAL_SELF_TIME_INDEX = 0;
    
	private static final int STAT_PERCENT_TOTAL_TIME_INDEX = 1;
	
	private static final int STAT_SELF_TIME_INDEX = 2;
	
	private static final int STAT_SUBROUTINE_TIME_INDEX = 3;
	
	private static final int STAT_TOTAL_TIME_INDEX = 4;
	
	private static final int STAT_CALL_COUNT_INDEX = 5;
	
	private static final int STAT_RECURSIVE_CALL_COUNT_INDEX = 6;
	
	private static final int STAT_SELF_MS_PER_CALL_INDEX = 7;
	
	private static final int STAT_TOTAL_MS_PER_CALL_INDEX = 8;
	
	private static final int STAT_COUNT = 9;
	
	// =============== Profiled Function Statistics Constants ============== //
	
	/** Holds the ID of the function. */
	private int id;
	
	/** The name of the profiled function. */
	private String name;
	
	/** Holds the various statistics of the profiled function. */
	private float[] stats = new float[STAT_COUNT];
	
	/** Holds the callers of this function. */
	private ArrayList<ProfiledCaller> callers;
	
	/** Holds the subroutines of this function. */
	private ArrayList<ProfiledSubroutine> subroutines;
	
	/**
	 * Constructor.
	 */
	public ProfiledFunction()
	{
		// Set all variables to invalid "flag" variables.
	    // This object should be parsed into, not initialized properly by default.
	    this.id = -1;
	    this.name = null;
	    for (int i = 0; i < this.stats.length; i++)
        {
	        this.stats[i] = -1f;
        }
	    this.callers = null;
	    this.subroutines = null;
	}
	
	/**
	 * Retrieves the profiled function's ID.
	 * 
	 * @return The ID.
	 */
	public int GetID()
	{
		return this.id;
	}
	
	/**
	 * Retrieves the profiled function's name.
	 * 
	 * @return The name.
	 */
	public String GetName()
	{
		return this.name;
	}
	
	/**
     * Retrieves the percent of total program time that was spent in the profiled function itself.
     * 
     * @return The percent of total time spent in the profiled function itself.
     */
    public float GetPercentTotalSelfTime()
    {
        return this.stats[STAT_PERCENT_TOTAL_SELF_TIME_INDEX];
    }
	
	/**
	 * Retrieves the percent of total program time that was spent in the profiled function and its subroutines.
	 * 
	 * @return The percent of total time spent in the profiled function and its subroutines.
	 */
	public float GetPercentTotalTime()
	{
		return this.stats[STAT_PERCENT_TOTAL_TIME_INDEX];
	}
	
	/**
	 * Retrieves the amount of time spent in the profiled function itself.
	 * 
	 * @return The amount of time spent in the profiled function itself.
	 */
	public float GetSelfTime()
	{
		return this.stats[STAT_SELF_TIME_INDEX];
	}
	
	/**
	 * Retrieves the amount of time spent in the profiled function's subroutines.
	 * 
	 * @return The amount of time spent in the profiled function's subroutines.
	 */
	public float GetSubroutineTime()
	{
		return this.stats[STAT_SUBROUTINE_TIME_INDEX];
	}
	
	/**
	 * Retrieves the total amount of time spent in the profiled function (includes subroutines).
	 * 
	 * @return The total amount of time spent in the profiled function.
	 */
	public float GetTotalTime()
	{
		return this.stats[STAT_TOTAL_TIME_INDEX];
	}
	
	/**
	 * Retrieves the number of times the profiled function was called.
	 * 
	 * @return The number of times the profiled function was called.
	 */
	public int GetCallCount()
	{
		return (int)this.stats[STAT_CALL_COUNT_INDEX];
	}
	
	/**
	 * Retrieves the number of times the profiled function was called recursively.
	 * 
	 * @return The number of times the profiled function was called recursively.
	 */
	public int GetRecursiveCallCount()
	{
		return (int)this.stats[STAT_RECURSIVE_CALL_COUNT_INDEX];
	}
	
	/**
	 * Retrieves the average number of milliseconds spent in the profiled function itself per call.
	 * 
	 * @return The average number of milliseconds spent in the profiled function itself per call.
	 */
	public float GetSelfMSPerCall()
	{
		return this.stats[STAT_SELF_MS_PER_CALL_INDEX];
	}
	
	/**
	 * Retrieves the average number of milliseconds spent in the profiled function and its subroutines per call.
	 * 
	 * @return The average number of milliseconds spent in the profiled function and its subroutines per call.
	 */
	public float GetTotalMSPerCall()
	{
		return this.stats[STAT_TOTAL_MS_PER_CALL_INDEX];
	}
	
	/**
	 * Retrieves the callers of the profiled functions.
	 * 
	 * @return The callers of the profiled functions.
	 */
	public ArrayList<ProfiledCaller> GetCallers()
	{
		return this.callers;
	}
	
	/**
	 * Retrieves the subroutines of the profiled function.
	 * 
	 * @return The subroutines of the profiled function.
	 */
	public ArrayList<ProfiledSubroutine> GetSubroutines()
	{
		return this.subroutines;
	}
	
	/**
	 * Sets the profiled function's ID.
	 * 
	 * @param id The ID.
	 */
	public void SetID(int id)
	{
		this.id = id;
	}
	
	/**
	 * Sets the profiled function's name.
	 * 
	 * @param name The name.
	 */
	public void SetName(String name)
	{
		this.name = name;
	}
	
	/**
     * Sets the percent of total program time that was spent in the profiled function itself.
     * 
     * @param percentTotalSelfTime The percent of total time spent in the profiled function itself.
     */
    public void SetPercentTotalSelfTime(float percentTotalSelfTime)
    {
        this.stats[STAT_PERCENT_TOTAL_SELF_TIME_INDEX] = percentTotalSelfTime;
    }
	
	/**
	 * Sets the percent of total program time that was spent in the profiled function and all its subroutines.
	 * 
	 * @param percentTotalTime The percent of total time spent in the profiled function and all its subroutines.
	 */
	public void SetPercentTotalTime(float percentTotalTime)
	{
		this.stats[STAT_PERCENT_TOTAL_TIME_INDEX] = percentTotalTime;
	}
	
	/**
	 * Sets the amount of time spent in the profiled function itself.
	 * 
	 * @param selfTime The amount of time spent in the profiled function itself.
	 */
	public void SetSelfTime(float selfTime)
	{
		this.stats[STAT_SELF_TIME_INDEX] = selfTime;
	}
	
	/**
	 * Sets the amount of time spent in the profiled function's subroutines.
	 * 
	 * @param subroutineTime The amount of time spent in the profiled function's subroutines.
	 */
	public void SetSubroutineTime(float subroutineTime)
	{
		this.stats[STAT_SUBROUTINE_TIME_INDEX] = subroutineTime;
	}
	
	/**
	 * Sets the total amount of time spent in the profiled function (includes subroutines).
	 * 
	 * @param totalTime The total amount of time spent in the profiled function.
	 */
	public void SetTotalTime(float totalTime)
	{
		this.stats[STAT_TOTAL_TIME_INDEX] = totalTime;
	}
	
	/**
	 * Sets the number of times the profiled function was called.
	 * 
	 * @param callCount The number of times the profiled function was called.
	 */
	public void SetCallCount(float callCount)
	{
		this.stats[STAT_CALL_COUNT_INDEX] = callCount;
	}
	
	/**
	 * Sets the number of times the profiled function was called recursively.
	 * 
	 * @param recursiveCallCount The number of times the profiled function was called recursively.
	 */
	public void SetRecursiveCallCount(int recursiveCallCount)
	{
		this.stats[STAT_RECURSIVE_CALL_COUNT_INDEX] = (float)recursiveCallCount;
	}
	
	/**
	 * Sets the average number of milliseconds spent in the profiled function itself per call.
	 * 
	 * @param selfMSPerCall The average number of milliseconds spent in the profiled function itself per call.
	 */
	public void SetSelfMSPerCall(float selfMSPerCall)
	{
		this.stats[STAT_SELF_MS_PER_CALL_INDEX] = selfMSPerCall;
	}
	
	/**
	 * Sets the average number of milliseconds spent in the profiled function and its subroutines per call.
	 * 
	 * @param totalMSPerCall The average number of milliseconds spent in the profiled function and its subroutines per call.
	 */
	public void SetTotalMSPerCall(float totalMSPerCall)
	{
		this.stats[STAT_TOTAL_MS_PER_CALL_INDEX] = totalMSPerCall;
	}
	
	/**
	 * Sets the callers of the profiled functions.
	 * 
	 * @param callers The callers of the profiled functions.
	 */
	public void SetCallers(ArrayList<ProfiledCaller> callers)
	{
		this.callers = callers;
	}
	
	/**
	 * Sets the subroutines of the profiled function.
	 * 
	 * @param subroutines The subroutines of the profiled function.
	 */
	public void SetSubroutines(ArrayList<ProfiledSubroutine> subroutines)
	{
		this.subroutines = subroutines;
	}
}
