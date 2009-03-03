/*
 * =======================================================================
 * ============================ gprof-eclipse ============================
 * =======================================================================
 * 
 * 
 * File: GProfLaunchPlugin.java
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

import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.Plugin;
import org.eclipse.core.runtime.Preferences;
import org.osgi.framework.BundleContext;

/**
 * The activator class controls the plug-in life cycle
 */
public class GProfLaunchPlugin extends Plugin
{    
    /** The preferences element that holds the path to the gprof executable. */
    public static final String GPROF_COMMAND = "gprof_command";
    
    /** The default value for the GPROF_COMMAND preferences element (for Windows). */
    private static final String GPROF_COMMAND_DEFAULT_VALUE_WINDOWS = "C:/MinGW/bin/gprof.exe";
    
    /** The default value for the GPROF_COMMAND preferences element (for Linux). */
    private static final String GPROF_COMMAND_DEFAULT_VALUE_LINUX = "/usr/bin/gprof";
    
    /** Holds the plugin identifier. */
    public static final String PLUGIN_ID = "org.eclipse.cdt.gprof.launch";
    
    /** Holds the shared instance of the plugin. */
    private static GProfLaunchPlugin plugin;
    
    /**
     * Constructs the plugin.
     */
    public GProfLaunchPlugin()
    {
        /* stub function */
    }
    
    /**
     * @see org.eclipse.core.runtime.Plugins#start(org.osgi.framework.BundleContext)
     */
    public void start(BundleContext context) throws Exception
    {
        super.start(context);
        plugin = this;
    }
    
    /**
     * @see org.eclipse.core.runtime.Plugin#stop(org.osgi.framework.BundleContext)
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
    public static GProfLaunchPlugin getDefault()
    {
        return plugin;
    }
    
    /**
     * Sets the default values for the plugin's preferences.
     */
    public void setDefaults()
    {
        Preferences preferences = this.getPluginPreferences();
        String osString = Platform.getOS();
        // Only support Windows and Linux for now (assume everything non Windows is Linux).
        if (osString.equals(Platform.OS_WIN32))
        {
            preferences.setDefault(GProfLaunchPlugin.GPROF_COMMAND, GProfLaunchPlugin.GPROF_COMMAND_DEFAULT_VALUE_WINDOWS);
        }
        else
        {
            preferences.setDefault(GProfLaunchPlugin.GPROF_COMMAND, GProfLaunchPlugin.GPROF_COMMAND_DEFAULT_VALUE_LINUX);
        }
    }
}
