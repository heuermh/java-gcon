/*

    java-gcon  Genome connector APIs.
    Copyright (c) 2013-2015 held jointly by the individual authors.

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
package com.github.heuermh.gcon.jclouds;

import com.github.heuermh.gcon.GenomeConnectorFile;
import com.github.heuermh.gcon.GenomeConnectorFileMetadata;
import com.github.heuermh.gcon.GenomeConnectorFileSet;

import org.jclouds.blobstore.domain.BlobMetadata;
import org.jclouds.blobstore.domain.StorageMetadata;

/**
 * Static utility methods.
 */
final class BlobStoreUtils {

    static GenomeConnectorFileSet createFileSet(final StorageMetadata storageMetadata) {
        // creationDate, eTag, lastModified, location, name, providerId, type, uri, userMetadata
         return new GenomeConnectorFileSet() {};
    }

    static GenomeConnectorFileMetadata createFileMetadata(final GenomeConnectorFile file, final BlobMetadata blobMetadata) {
        // container, creationDate, eTag, lastModified, location, providerId, publicUri, type, uri, userMetadata
        // contentDisposition, contentEncoding, contentLanguage, contentLength, contentMD5, contentType, expires
        return new GenomeConnectorFileMetadata() {};
    }
}
