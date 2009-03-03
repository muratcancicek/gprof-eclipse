/*
 * =======================================================================
 * ============================ gprof-eclipse ============================
 * =======================================================================
 * 
 * 
 * File: GProfLaunchConfigurationDelegate.java
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

import org.eclipse.cdt.launch.AbstractCLaunchDelegate;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.Platform;
import org.eclipse.debug.core.ILaunch;
import org.eclipse.debug.core.ILaunchConfiguration;

/**
 * The launch configuration for profiling with gprof.
 */
public class GProfLaunchConfigurationDelegate extends AbstractCLaunchDelegate
{
    /**
     * @see AbstractCLaunchDelegate#launch(ILaunchConfiguration, String, ILaunch, IProgressMonitor)
     */
    public void launch(ILaunchConfiguration config, String mode, ILaunch launch, IProgressMonitor monitor) throws CoreException
    {
        // Try to the get the working directory from the config.
        String workingDirectory = null;
        if (this.getWorkingDirectoryPath(config) != null)
        {
            workingDirectory = this.getWorkingDirectoryPath(config).toString();
        }
        // Otherwise, we set it to a fallback value.
        else
        {
            workingDirectory = System.getProperty("user.dir");
        }
        
        // Create the profiling session.
        GProfSession session = new GProfSession(config, workingDirectory);
        IGProfLaunchParser parser = null;
        
        // Get the IO connection from the extension registry.
        IExtensionRegistry registry = Platform.getExtensionRegistry();
        IConfigurationElement[] extensions = registry.getConfigurationElementsFor("org.eclipse.cdt.gprof.launch");
        
        // We only support having one IO connection.
        if (extensions.length > 0)
        {
            parser = (IGProfLaunchParser) extensions[0].createExecutableExtension("class");
        }
        
        // Run the session with the given IO connection.
        session.run(parser);
    }
    
    /**
     * @see AbstractCLaunchDelegate#getPluginID()
     */
    protected String getPluginID()
    {
        return GProfLaunchPlugin.PLUGIN_ID;
    }
}
