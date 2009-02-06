/*
 * =======================================
 * ============ gprof-eclipse ============
 * =======================================
 * 
 * File: ProfiledFunction.java
 * 
 * ---------------------------------------
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
class ProfiledFunction
{
	// ---- Profiled Function Statistics Constants ---- //
	
	private static final int STAT_PERCENT_TOTAL_TIME_INDEX = 0;
	
	private static final int STAT_SELF_TIME_INDEX = 1;
	
	private static final int STAT_SUBROUTINE_TIME_INDEX = 2;
	
	private static final int STAT_TOTAL_TIME_INDEX = 3;
	
	private static final int STAT_CALL_COUNT_INDEX = 4;
	
	private static final int STAT_RECURSIVE_CALL_COUNT_INDEX = 5;
	
	private static final int STAT_SELF_MS_PER_CALL_INDEX = 6;
	
	private static final int STAT_TOTAL_MS_PER_CALL_INDEX = 7;
	
	private static final int STAT_COUNT = 8;
	
	// ---- Profiled Function Statistics Constants ---- //
	
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
		/* stub function */
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
	 * Retrieves the percent of total program time that was spent in the profiled function.
	 * 
	 * @return The percent of total time spent in the profiled function.
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
	public float GetCallCount()
	{
		return this.stats[STAT_CALL_COUNT_INDEX];
	}
	
	/**
	 * Retrieves the number of times the profiled function was called recursively.
	 * 
	 * @return The number of times the profiled function was called recursively.
	 */
	public float GetRecursiveCallCount()
	{
		return this.stats[STAT_RECURSIVE_CALL_COUNT_INDEX];
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
	 * Sets the percent of total program time that was spent in the profiled function.
	 * 
	 * @param percentTotalTime The percent of total time spent in the profiled function.
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
	public void SetRecursiveCallCount(float recursiveCallCount)
	{
		this.stats[STAT_RECURSIVE_CALL_COUNT_INDEX] = recursiveCallCount;
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
