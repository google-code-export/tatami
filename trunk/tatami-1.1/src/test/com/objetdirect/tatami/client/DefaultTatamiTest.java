/*
 * Tatami: 
 * Copyright (C) 2007 Objet Direct
 * Copyright (C) 2007 France Telecom
 * Contact: tatami@googlegroups.com
 * 
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or any later version.
 * 
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307
 * USA
 *
 * Authors:  Vianney Grassaud
 * Initial developer(s): Vianney Grassaud
 * Contributor(s):
 */
package com.objetdirect.tatami.client;

import com.google.gwt.junit.client.GWTTestCase;


/**
 * Class defining the unit tests of the Tatami components. 
 * The class defines the module to uese for the tests. To 
 * write the test of the components to assure that the name of the module is the good one.
 * @author Vianney
 *
 */
public class DefaultTatamiTest extends GWTTestCase {

	/**
	 * Returns the name of the module to use.
	 * @return "com.objetdirect.tatami.Tatami"
	 */
	public String getModuleName() {
		return "com.objetdirect.tatami.Tatami";
	}
	
	/**
	 * A default test. Checks only if the <code>getModuleName()</code> is not <code>null</code>
	 */
	public void test() {
		assertNotNull(getModuleName());
	}
	

     


}//end of class
