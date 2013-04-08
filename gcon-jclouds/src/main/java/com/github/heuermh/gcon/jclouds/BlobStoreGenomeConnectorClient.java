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
package com.github.heuermh.gcon.jclouds;

import static com.github.heuermh.gcon.jclouds.BlobStoreUtils.createFileMetadata;
import static com.github.heuermh.gcon.jclouds.BlobStoreUtils.createFileSet;
import static com.google.common.base.Preconditions.checkNotNull;
import static org.jclouds.blobstore.options.ListContainerOptions.Builder.afterMarker;

import java.io.InputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;

import com.google.common.collect.Lists;
import com.google.common.collect.ImmutableList;
import com.google.common.io.Files;

import com.github.heuermh.gcon.GenomeConnectorClient;
import com.github.heuermh.gcon.GenomeConnectorFile;
import com.github.heuermh.gcon.GenomeConnectorFileMetadata;
import com.github.heuermh.gcon.GenomeConnectorFileSet;

import org.jclouds.blobstore.BlobStore;
import org.jclouds.blobstore.BlobStoreContext;
import org.jclouds.blobstore.domain.BlobMetadata;
import org.jclouds.blobstore.domain.PageSet;
import org.jclouds.blobstore.domain.StorageMetadata;

/**
 * jclouds BlobStore-based implementation of a genome connector client.
 */
final class BlobStoreGenomeConnectorClient implements GenomeConnectorClient {
    private final String container;
    private final BlobStoreContext context;

    //@Inject
    BlobStoreGenomeConnectorClient(final String container, final BlobStoreContext context) {
        checkNotNull(container);
        checkNotNull(context);
        this.container = container;
        this.context = context;
    }


    @Override
    public Iterable<GenomeConnectorFileSet> list() {
        BlobStore store = context.getBlobStore();
        if (!store.containerExists(container)) {
            return ImmutableList.of();
        }
        List<GenomeConnectorFileSet> fileSets = Lists.newLinkedList();
        PageSet<? extends StorageMetadata> pageSet = store.list(container);
        for (StorageMetadata storageMetadata : pageSet) {
            fileSets.add(createFileSet(storageMetadata));
        }

        String marker = pageSet.getNextMarker();
        while (marker != null) {
            PageSet<? extends StorageMetadata> additionalPageSet = store.list(container, afterMarker(marker));
            for (StorageMetadata storageMetadata : pageSet) {
                fileSets.add(createFileSet(storageMetadata));
            }
            marker = additionalPageSet.getNextMarker();
        }
        return fileSets;
    }

    @Override
    public Iterable<GenomeConnectorFile> list(final GenomeConnectorFileSet fileSet)  {
        checkNotNull(fileSet);
        return null;
    }

    @Override
    public GenomeConnectorFileMetadata meta(final GenomeConnectorFile file) {
        checkNotNull(file);
        BlobStore store = context.getBlobStore();
        if (!store.blobExists(container, file.toString())) {
            return null;
        }
        BlobMetadata metadata = store.blobMetadata(container, file.toString());
        return createFileMetadata(file, metadata);
    }

    @Override
    public InputStream get(final GenomeConnectorFile file) {
        checkNotNull(file);

        // caller is responsible for closing input stream
        return context.createInputStreamMap(container).get(file.toString());
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
}