/*
 * =======================================================================
 * ============================ gprof-eclipse ============================
 * =======================================================================
 * 
 * 
 * File: GProfLaunchConfigurationTab.java
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

import org.eclipse.cdt.debug.core.ICDTLaunchConfigurationConstants;
import org.eclipse.cdt.launch.internal.ui.WorkingDirectoryBlock;
import org.eclipse.cdt.launch.ui.CArgumentsTab;
import org.eclipse.debug.core.ILaunchConfigurationWorkingCopy;

/**
 * Arguments and working directory tab with custom working directory behavior.
 * 
 * @see CArgumentsTab
 */
public class GProfArgumentsTab extends CArgumentsTab
{
    /**
     * Applies the value for the working directory directly from the corresponding text box.
     * The super class sets the working directory to null if 'Use default' is checked.
     * (We do this because when 'Use default' is checked the text box is populated properly.)
     */
    class GProfWorkingDirectoryBlock extends WorkingDirectoryBlock
    {
        /**
         * @see WorkingDirectoryBlock#performApply(ILaunchConfigurationWorkingCopy)
         */
        public void performApply(ILaunchConfigurationWorkingCopy configuration)
        {
            configuration.setAttribute(ICDTLaunchConfigurationConstants.ATTR_WORKING_DIRECTORY, getAttributeValueFrom(fWorkingDirText));
        }
    }
    
    /**
     * Constructs the tab with the custom working directory block.
     * 
     * @see GProfWorkingDirectoryBlock
     */
    public GProfArgumentsTab()
    {
        super();
        
        // Set our custom working directory block.
        this.fWorkingDirectoryBlock = new GProfWorkingDirectoryBlock();
    }
}
