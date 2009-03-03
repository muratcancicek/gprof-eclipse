/*
 * =======================================================================
 * ============================ gprof-eclipse ============================
 * =======================================================================
 * 
 * 
 * File: GProfPreferencePage.java
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

import org.eclipse.core.runtime.Preferences;
import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.jface.preference.FileFieldEditor;
import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.jface.util.PropertyChangeEvent;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

public class GProfPreferencePage extends FieldEditorPreferencePage implements IWorkbenchPreferencePage
{
    /** Holds the field that is used to select the location of the gprof executable. */
    FileFieldEditor gprofCommandField = null;
    
    /**
     * @see FieldEditorPreferencePage#
     */
    protected void createFieldEditors()
    {
        // Make sure that the default values have been set for the plugin.
        GProfLaunchPlugin.getDefault().setDefaults();
        
        // Create the field and add it to the page.
        gprofCommandField = new FileFieldEditor(GProfLaunchPlugin.GPROF_COMMAND, "gprof Command:", getFieldEditorParent());
        addField(gprofCommandField);
        
        // Set the listener for the field and update the field before display.
        IPropertyChangeListener listener = new IPropertyChangeListener()
        {
            public void propertyChange(PropertyChangeEvent event)
            {
                GProfPreferencePage.this.updateApplyButton();
            }
        };
        gprofCommandField.setPropertyChangeListener(listener);
        updateFields();
    }
    
    /**
     * @see org.eclipse.jface.preference.PreferencePage#performApply()
     */
    protected void performApply()
    {
        this.performOk();
    }

    /**
     * @see FieldEditorPreferencePage#performOk()
     */
    public boolean performOk()
    {
        Preferences preferences = GProfLaunchPlugin.getDefault().getPluginPreferences();
        preferences.setValue(GProfLaunchPlugin.GPROF_COMMAND, gprofCommandField.getStringValue().trim());
        return super.performOk();
    }
    
    /**
     * @see IWorkbenchPreferencePage#init(IWorkbench)
     */
    public void init(IWorkbench workbench)
    {
        /* stub function */
    }
    
    /**
     * @see org.eclipse.jface.preference.FieldEditorPreferencePage#performDefaults()
     */
    protected void performDefaults()
    {
        GProfLaunchPlugin.getDefault().setDefaults();
        updateFields();
    }
    
    /**
     * Updates the preferences page's fields.
     */
    protected void updateFields()
    {
        Preferences preferences = GProfLaunchPlugin.getDefault().getPluginPreferences();
        gprofCommandField.setStringValue(preferences.getString(GProfLaunchPlugin.GPROF_COMMAND));
    }
    
    /**
     * @see org.eclipse.jface.preference.PreferencePage#isValid()
     */
    public boolean isValid()
    {
        return gprofCommandField.isValid();
    }
}
