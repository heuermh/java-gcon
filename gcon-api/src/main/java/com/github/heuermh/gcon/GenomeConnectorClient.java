/*

    java-gcon  Genome connector APIs.
    Copyright (c) 2013 held jointly by the individual authors.

    This library is free software; you can redistribute it and/or modify it
    under the terms of the GNU Lesser General Public License as published
    by the Free Software Foundation; either version 3 of the License, or (at
    your option) any later version.

    This library is distributed in the hope that it will be useful, but WITHOUT
    ANY WARRANTY; with out even the implied warranty of MERCHANTABILITY or
    FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Lesser General Public
    License for more details.

    You should have received a copy of the GNU Lesser General Public License
    along with this library;  if not, write to the Free Software Foundation,
    Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307  USA.

    > http://www.fsf.org/licensing/licenses/lgpl.html
    > http://www.opensource.org/licenses/lgpl-license.php

*/
package com.github.heuermh.gcon;

import java.net.URL;


/**
 * Genome connector client.
 * 
 * 
 */
public interface GenomeConnectorClient {

    /**
     * Give the root file of this genome connector
     *
     * @return the root file of the genome connector
     */
    GenomeConnectorFile root();

    
    /**
     * Give the GCon file represented by the URL
     * 
     * @param url file to fetch
     * @return GConFile
     */
    GenomeConnectorFile file(URL url);
    
    
    
    /**
     * Put the specified file.
     */
    void put();
}