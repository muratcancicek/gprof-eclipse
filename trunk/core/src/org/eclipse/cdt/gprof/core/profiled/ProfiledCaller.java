/*
 * =======================================================================
 * ============================ gprof-eclipse ============================
 * =======================================================================
 * 
 * 
 * File: ProfiledCaller.java
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
 * Represents a caller of a function.
 * 
 * This class represents a function that is a caller of a given profiled function that we are interested in.
 * 
 * @author chrisculy
 */
public class ProfiledCaller
{
	/** Holds the ID of the caller. */
	private int id;
	
	/** Holds the name of the caller. */
	private String name;
	
	/** Holds the number of times the caller called the given function. */
	private int callCount;
	
	/** Holds the amount of time spent in the given function itself. */
	private float timeInCalledSelf;
	
	/** Holds the amount of time spent in the given function's subroutines. */
	private float timeInCalledSubroutines;
	
	/**
	 * Constructor.
	 */
	public ProfiledCaller()
	{
	    // Set all variables to invalid "flag" variables.
        // This object should be parsed into, not initialized properly by default.
        this.id = -1;
        this.name = null;
        this.callCount = -1;
        this.timeInCalledSelf = -1f;
        this.timeInCalledSubroutines = -1f;
	}
	
	/**
	 * Retrieves the caller's ID.
	 * 
	 * @return The caller's ID.
	 */
	public int GetID()
	{
		return this.id;
	}
	
	/**
     * Retrieves the profiled caller's name.
     * 
     * @return The name.
     */
    public String GetName()
    {
        return this.name;
    }
	
	/**
	 * Retrieves the number of times the caller called the given function.
	 * 
	 * @return The caller's call count.
	 */
	public int GetCallCount()
	{
		return this.callCount;
	}
	
	/**
	 * Retrieves the amount of time spent in the given function itself.
	 * 
	 * @return The time spent in the called function.
	 */
	public float GetTimeInCalledSelf()
	{
		return this.timeInCalledSelf;
	}
	
	/**
	 * Retrieves the amount of time spent in the given function's subroutines.
	 * 
	 * @return The time spent in the called function's subroutines.
	 */
	public float GetTimeInCalledSubroutines()
	{
		return this.timeInCalledSubroutines;
	}
	
	/**
	 * Sets the caller's ID.
	 * 
	 * @param id The caller's ID.
	 */
	public void SetID(int id)
	{
		this.id = id;
	}
	
	/**
     * Sets the profiled caller's name.
     * 
     * @param name The name.
     */
    public void SetName(String name)
    {
        this.name = name;
    }
	
	/**
	 * Sets the number of times the caller called the given function.
	 * 
	 * @param callCount The caller's call count.
	 */
	public void SetCallCount(int callCount)
	{
		this.callCount = callCount;
	}
	
	/**
	 * Sets the amount of time spent in the given function itself.
	 * 
	 * @param timeInCalledSelf The time spent in the called function.
	 */
	public void SetTimeInCalledSelf(float timeInCalledSelf)
	{
		this.timeInCalledSelf = timeInCalledSelf;
	}
	
	/**
	 * Sets the amount of time spent in the given function's subroutines.
	 * 
	 * @param timeInCalledSubroutines The time spent in the called function's subroutines.
	 */
	public void SetTimeInCalledSubroutines(float timeInCalledSubroutines)
	{
		this.timeInCalledSubroutines = timeInCalledSubroutines;
	}
}
