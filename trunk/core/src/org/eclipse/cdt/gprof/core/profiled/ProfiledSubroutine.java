/*
 * =======================================================================
 * ============================ gprof-eclipse ============================
 * =======================================================================
 * 
 * 
 * File: ProfiledSubroutine.java
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

/**
 * Represents a subroutine of a function.
 * 
 * This class represents a function that is called by a given function that we are interested in.
 * 
 * @author chrisculy
 * 
 */
public class ProfiledSubroutine
{
	/** Holds the ID of the subroutine. */
	private int id;
	
	/** Holds the name of the subroutine. */
    private String name;
	
	/**
	 * Holds the number of times the subroutine was called by the given function.
	 */
	private int callCount;
	
	/**
	 * Holds the amount of time spent in the subroutine itself when called by the given function.
	 */
	private float timeInSubroutineSelf;
	
	/**
	 * Holds the amount of time spent in the subroutine's subroutines when called by the given function.
	 */
	private float timeInSubroutineSubroutines;
	
	/**
	 * Constructor.
	 */
	public ProfiledSubroutine()
	{
	    // Set all variables to invalid "flag" variables.
        // This object should be parsed into, not initialized properly by default.
        this.id = -1;
        this.name = null;
        this.callCount = -1;
        this.timeInSubroutineSelf = -1f;
        this.timeInSubroutineSubroutines = -1f;
	}
	
	/**
	 * Retrieves the subroutine's ID.
	 * 
	 * @return The subroutine's ID.
	 */
	public int GetID()
	{
		return this.id;
	}
	
	/**
     * Retrieves the profiled subroutine's name.
     * 
     * @return The name.
     */
    public String GetName()
    {
        return this.name;
    }
	
	/**
	 * Retrieves the number of times the subroutine was called by the given function.
	 * 
	 * @return The subroutine's call count.
	 */
	public int GetCallCount()
	{
		return this.callCount;
	}
	
	/**
	 * Retrieves the amount of time spent in the subroutine itself when called from the given function.
	 * 
	 * @return The time spent in the subroutine.
	 */
	public float GetTimeInSubroutineSelf()
	{
		return this.timeInSubroutineSelf;
	}
	
	/**
	 * Retrieves the amount of time spent in the subroutine's subroutines when called from the given function.
	 * 
	 * @return The time spent in the subroutine's subroutines.
	 */
	public float GetTimeInSubroutineSubroutines()
	{
		return this.timeInSubroutineSubroutines;
	}
	
	/**
	 * Sets the subroutine's ID.
	 * 
	 * @param id The subroutine's ID.
	 */
	public void SetID(int id)
	{
		this.id = id;
	}
	
	/**
     * Sets the profiled subroutine's name.
     * 
     * @param name The name.
     */
    public void SetName(String name)
    {
        this.name = name;
    }
	
	/**
	 * Sets the number of times the subroutine was called by the given function.
	 * 
	 * @param callCount The subroutine's call count.
	 */
	public void SetCallCount(int callCount)
	{
		this.callCount = callCount;
	}
	
	/**
	 * Sets the amount of time spent in the subroutine itself when called from the given function.
	 * 
	 * @param timeInSubroutineSelf The time spent in the subroutine.
	 */
	public void SetTimeInSubroutineSelf(float timeInSubroutineSelf)
	{
		this.timeInSubroutineSelf = timeInSubroutineSelf;
	}
	
	/**
	 * Sets the amount of time spent in the subroutine's subroutines when called from the given function.
	 * 
	 * @param timeInSubroutineSubroutines The time spent in the subroutine's subroutines.
	 */
	public void SetTimeInSubroutineSubroutines(float timeInSubroutineSubroutines)
	{
		this.timeInSubroutineSubroutines = timeInSubroutineSubroutines;
	}
}
