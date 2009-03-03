/*
 * =======================================================================
 * ============================ gprof-eclipse ============================
 * =======================================================================
 * 
 * 
 * File: IGProfLaunchParser.java
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

import java.io.InputStream;

/**
 * The interface that must be implemented to receive output from GProf during profiling.
 */
public interface IGProfLaunchParser
{
    /**
     * Used to parse the output from gprof received through the given input stream.
     * 
     * @param input The output from gprof as an input stream.
     */
    public void parse(InputStream input);
}
