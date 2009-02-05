/*
 * =======================================
 * ============ gprof-eclipse ============
 * =======================================
 * 
 * File: ProfiledSubroutine.java
 * 
 * ---------------------------------------
 * 
 * Last changed:
 * $Revision$
 * $Author$
 * $Date$
 */

package profiled;

/**
 * Represents a subroutine of a function.
 * 
 * This class represents a function that is called by a given function that we are interested in.
 * 
 * @author chrisculy
 * 
 */
class ProfiledSubroutine
{
	/** Holds the ID of the caller. */
	private int id;
	
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
		/* stub function */
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
