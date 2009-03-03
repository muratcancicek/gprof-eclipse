/*
 * =======================================================================
 * ============================ gprof-eclipse ============================
 * =======================================================================
 * 
 * 
 * File: GProfPreferencesInitializer.java
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

import org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer;

/**
 * Initializes the default preferences for the launch plugin.
 */
public class GProfPreferencesInitializer extends AbstractPreferenceInitializer
{
    /**
     * @see AbstractPreferenceInitializer#initializeDefaultPreferences()
     */
    public void initializeDefaultPreferences()
    {
        GProfLaunchPlugin.getDefault().setDefaults();
    }
}
