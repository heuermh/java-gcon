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

/**
 * Genome connector client.
 */
public interface GenomeConnectorClient {

    /**
     * List the directories available to this genome connector client.
     *
     * @return zero or more directories available to this genome connector client
     */
    Iterable<GenomeConnectorDirectory> listDirectories();

    /**
     * List the files available to this genome connector client under the root directory.
     *
     * @return zero or more files available to this genome connector client under the root directory
     */
    Iterable<GenomeConnectorFile> listFiles();

    /**
     * List the files available to this genome connector client under the specified directory.
     *
     * @param directory directory, must not be null
     * @return zero or more files available to this genome connector client under the specified directory
     */
    Iterable<GenomeConnectorFile> listFiles(GenomeConnectorDirectory directory);

    /**
     * Get the specified file.
     *
     * @param file file to get, must not be null
     */
    void getFile(GenomeConnectorFile file);

    /**
     * Get the specified file as an input stream.
     *
     * @param file file to get, must not be null
     * @return the specified file as an input stream
     */
    InputStream getFileAsStream(GenomeConnectorFile file);

    /**
     * Put the specified file.
     */
    void putFile();
}