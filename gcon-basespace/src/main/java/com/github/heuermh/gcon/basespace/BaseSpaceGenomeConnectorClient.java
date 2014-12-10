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
package com.github.heuermh.gcon.basespace;

import static com.google.common.base.Preconditions.checkNotNull;

import java.io.InputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

import com.google.common.collect.ImmutableList;
import com.google.common.io.Files;

import com.github.heuermh.gcon.GenomeConnectorClient;
import com.github.heuermh.gcon.GenomeConnectorFile;
import com.github.heuermh.gcon.GenomeConnectorFileMetadata;
import com.github.heuermh.gcon.GenomeConnectorFileSet;
import com.github.heuermh.gcon.GenomeConnectorClient;

import com.illumina.basespace.ApiClient;

import com.illumina.basespace.entity.File;

import com.illumina.basespace.response.GetFileResponse;

/**
 * BaseSpace implementation of a genome connector client.
 */
final class BaseSpaceGenomeConnectorClient implements GenomeConnectorClient {
    private final ApiClient client;

    //@Inject
    BaseSpaceGenomeConnectorClient(final ApiClient client) {
        checkNotNull(client);
        this.client = client;
    }


    @Override
    public InputStream get(final String name) {
        checkNotNull(name);
        GetFileResponse getFileResponse = client.getFile(name);
        if (getFileResponse == null) {
            return null;
        }
        File file = getFileResponse.get();
        return client.getFileInputStream(file);
    }

    @Override
    public void put(final String name, final InputStream inputStream) {
        checkNotNull(name);
        checkNotNull(inputStream);
        // cannot find BaseSpace upload APIs in java sdk
    }

    /*
    @Override
    public Iterable<GenomeConnectorFileSet> list() {
        return ImmutableList.of();
    }

    @Override
    public Iterable<GenomeConnectorFile> list(final GenomeConnectorFileSet fileSet)  {
        checkNotNull(fileSet);
        return ImmutableList.of();
    }

    @Override
    public GenomeConnectorFileMetadata meta(final GenomeConnectorFile file) {
        checkNotNull(file);
        return null;
    }

    @Override
    public InputStream get(final GenomeConnectorFile file) {
        checkNotNull(file);

        // todo: getFile is keyed by id, not uri
        return client.getFileInputStream(client.getFile(file.toString()));
    }

    @Override
    public void get(final GenomeConnectorFile file, final Path path) {
        checkNotNull(file);
        checkNotNull(path);

        InputStream inputStream = null;
        try {
            inputStream = get(file);

            // write as byte stream
            Files.asByteSink(path.toFile()).writeFrom(inputStream);
        }
        catch (IOException e) {
            // rethrow?
        }
        finally {
            try {
                inputStream.close();
            }
            catch (IOException e) {
                // ignore
            }
        }
    }

    @Override
    public void put() {
    }
    */
}