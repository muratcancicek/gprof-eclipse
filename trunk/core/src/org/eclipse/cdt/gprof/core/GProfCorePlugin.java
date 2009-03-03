/*
 * =======================================================================
 * ============================ gprof-eclipse ============================
 * =======================================================================
 * 
 * 
 * File: GProfCorePlugin.java
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

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

/**
 * The core gprof plugin.
 */
public class GProfCorePlugin extends AbstractUIPlugin
{
    /** Holds the plugin identifier. */
	public static final String PLUGIN_ID = "org.eclipse.cdt.gprof.core";
	
	/** Holds the shared instance of the plugin. */
	private static GProfCorePlugin plugin;
	
	/**
	 * Constructs the plugin.
	 */
	public GProfCorePlugin()
	{
	    /* stub function */
	}
	
	/**
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#start(org.osgi.framework.BundleContext)
	 */
	public void start(BundleContext context) throws Exception
	{
		super.start(context);
		plugin = this;
	}
	
	/**
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#stop(org.osgi.framework.BundleContext)
	 */
	public void stop(BundleContext context) throws Exception
	{
		plugin = null;
		super.stop(context);
	}
	
	/**
	 * Retrieves the default (shared) instance of the plugin.
	 * 
	 * @return The default (shared) instance.
	 */
	public static GProfCorePlugin getDefault()
	{
		return plugin;
	}
	
	/**
	 * Retrieves an image descriptor for the image file at the given plug-in relative path.
	 * 
	 * @param path The path to the image.
	 * @return The image descriptor.
	 * @see AbstractUIPlugin#imageDescriptorFromPlugin(String, String)
	 */
	public static ImageDescriptor getImageDescriptor(String path)
	{
		return AbstractUIPlugin.imageDescriptorFromPlugin(PLUGIN_ID, path);
	}
}
