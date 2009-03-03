/*
 * =======================================================================
 * ============================ gprof-eclipse ============================
 * =======================================================================
 * 
 * 
 * File: GProfLaunchConfigurationTabGroup.java
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

import org.eclipse.cdt.launch.ui.CMainTab;
import org.eclipse.debug.ui.AbstractLaunchConfigurationTabGroup;
import org.eclipse.debug.ui.CommonTab;
import org.eclipse.debug.ui.ILaunchConfigurationDialog;
import org.eclipse.debug.ui.ILaunchConfigurationTab;

/**
 * Holds the tab group used in the launch dialog for launching an executable with gprof profiling.
 */
public class GProfLaunchConfigurationTabGroup extends AbstractLaunchConfigurationTabGroup
{
    /**
     * @see AbstractLaunchConfigurationTabGroup#createTabs(ILaunchConfigurationDialog, String)
     */
    public void createTabs(ILaunchConfigurationDialog dialog, String mode)
    {
        ILaunchConfigurationTab[] tabs = new ILaunchConfigurationTab[] { new CMainTab(), new GProfArgumentsTab(), new CommonTab() };
        setTabs(tabs);
    }
}
