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

import java.io.InputStream;
import java.net.URL;
import java.nio.file.Path;

/**
 * Genome connector file.
 */
public interface GenomeConnectorFile {
    // empty
	
	
	/**
     * Get contents of the file as an input stream.
     *
     * @param file file to get, must not be null
     * @return the specified file as an input stream
     */
    InputStream stream();
    
    
    /**
     * Get locator for the file content
     */
    URL url();
    
    /**
     * Get the specified file and write it to the specified local path.
     *
     * @param file file to get, must not be null
     * @param path local path to write the specified file to
     */
    void download(Path path);


    
    
    
    /**
     * Return extended metadata for the specified file, if any.
     *
     * @param file file, must not be null
     * @return extended metadata for the specified file, or null if no such metadata exists
     */
    GenomeConnectorFileMetadata meta();

	
}