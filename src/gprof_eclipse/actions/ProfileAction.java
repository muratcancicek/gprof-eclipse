/*
 * =======================================
 * ============ gprof-eclipse ============
 * =======================================
 * 
 * File: ProfileAction.java
 * 
 * ---------------------------------------
 * 
 * Last changed:
 * $Revision$
 * $Author$
 * $Date$
 */

package gprof_eclipse.actions;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;
import org.eclipse.jface.dialogs.MessageDialog;

/**
 * Workbench "Run With Profiling" action.
 * 
 * @see IWorkbenchWindowActionDelegate
 */
public class ProfileAction implements IWorkbenchWindowActionDelegate
{
	private IWorkbenchWindow window;
	
	/**
	 * Constructor.
	 */
	public ProfileAction()
	{
		/* stub function */
	}
	
	/**
	 * Execute the action.
	 * 
	 * @see IWorkbenchWindowActionDelegate#run
	 */
	public void run(IAction action)
	{
		action.setText("Run With Profiling");
		action.setToolTipText("Run With Profiling");
		MessageDialog.openInformation(window.getShell(), "gprof-eclipse", "Running profiler...");
	}
	
	/**
	 * TODO: Implement (decide if this is needed).
	 * 
	 * @see IWorkbenchWindowActionDelegate#selectionChanged
	 */
	public void selectionChanged(IAction action, ISelection selection)
	{
		/* stub function */
	}
	
	/**
	 * Clean up after the action.
	 * 
	 * @see IWorkbenchWindowActionDelegate#dispose
	 */
	public void dispose()
	{
		/* stub function */
	}
	
	/**
	 * Initialize the action.
	 * 
	 * @see IWorkbenchWindowActionDelegate#init
	 */
	public void init(IWorkbenchWindow window)
	{
		this.window = window;
	}
}
