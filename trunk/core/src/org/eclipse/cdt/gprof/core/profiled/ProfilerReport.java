/*
 * =======================================
 * ============ gprof-eclipse ============
 * =======================================
 * 
 * File: ProfiledReport.java
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
 * Represents a full report from gprof for a given run of a program.
 * 
 * @author chrisculy
 */
public class ProfilerReport
{
	/** Holds all the profiled functions. */
	private ArrayList<ProfiledFunction> functions;
	
	/**
	 * Constructor.
	 */
	public ProfilerReport()
	{
		/* stub function */
	}
	
	/**
	 * Retrieves the profiler report's set of profiled functions
	 * 
	 * @return The profiler report's set of profiled functions.
	 */
	public ArrayList<ProfiledFunction> GetFunctions()
	{
		return this.functions;
	}
	
	/**
	 * Sets the profiler report's set of profiled functions
	 * 
	 * @param functions The profiler report's set of profiled functions.
	 */
	public void SetFunctions(ArrayList<ProfiledFunction> functions)
	{
		this.functions = functions;
	}
}
