/*
 * =======================================================================
 * ============================ gprof-eclipse ============================
 * =======================================================================
 * 
 * 
 * File: ProfilingView.java
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

package org.eclipse.cdt.gprof.core.views;

import org.eclipse.cdt.gprof.core.profiled.ProfilerReport;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.ViewPart;


/**
 * Displays the profiler results.
 */

public class ProfilingView extends ViewPart
{
	/** Holds the profiler's report. */
	private ProfilerReport report;
	
	/** Holds the actual graphical view. */
	private TableViewer viewer;
	
	/**
	 * Provides the profiler report to the view in a displayable format.
	 * 
	 * @author chrisculy
	 */
	class ViewContentProvider implements IStructuredContentProvider
	{
		public void inputChanged(Viewer v, Object oldInput, Object newInput)
		{
			/* stub function */
		}
		
		public void dispose()
		{
			/* stub function */
		}
		
		public Object[] getElements(Object parent)
		{
			return new String[] { "One", "Two", "Three" };
		}
	}
	
	class ViewLabelProvider extends LabelProvider implements ITableLabelProvider
	{
		public String getColumnText(Object obj, int index)
		{
			return this.getText(obj);
		}
		
		public Image getColumnImage(Object obj, int index)
		{
			return this.getImage(obj);
		}
		
		public Image getImage(Object obj)
		{
			return PlatformUI.getWorkbench().getSharedImages().getImage(ISharedImages.IMG_OBJ_ELEMENT);
		}
	}
	
	/**
	 * Constructor.
	 */
	public ProfilingView()
	{
		/* stub function */
	}
	
	/**
	 * Retrieves the profiler report object.
	 * 
	 * @return The profiler report.
	 */
	ProfilerReport getProfilerReport()
	{
	    return this.report;
	}
	
	/**
	 * Create the viewer.
	 */
	public void createPartControl(Composite parent)
	{
		this.viewer = new TableViewer(parent, SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL);
		this.viewer.setContentProvider(new ViewContentProvider());
		this.viewer.setLabelProvider(new ViewLabelProvider());
		this.viewer.setInput(this.getViewSite());
		
		// Create the help context id for the viewer's control
		PlatformUI.getWorkbench().getHelpSystem().setHelp(this.viewer.getControl(), "org.eclipse.cdt.gprof.core.viewer");
	}
	
	/**
	 * Passing the focus request to the viewer's control.
	 */
	public void setFocus()
	{
		this.viewer.getControl().setFocus();
	}
}
