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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.eq;
import static org.mockito.Mockito.when;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.net.URI;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.google.common.collect.ImmutableList;

import com.github.heuermh.gcon.GenomeConnectorClient;
import com.github.heuermh.gcon.AbstractGenomeConnectorClientTest;
import com.github.heuermh.gcon.GenomeConnectorFile;
import com.github.heuermh.gcon.GenomeConnectorFileSet;

import org.junit.Before;
import org.junit.Test;

import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import org.jclouds.blobstore.BlobStore;
import org.jclouds.blobstore.BlobStoreContext;
import org.jclouds.blobstore.InputStreamMap;
import org.jclouds.blobstore.domain.BlobMetadata;
import org.jclouds.blobstore.domain.PageSet;
import org.jclouds.blobstore.domain.StorageMetadata;
import org.jclouds.blobstore.domain.StorageType;
import org.jclouds.blobstore.domain.internal.PageSetImpl;
import org.jclouds.blobstore.domain.internal.StorageMetadataImpl;
import org.jclouds.blobstore.options.ListContainerOptions;
import org.jclouds.domain.Location;

/**
 * Unit test for BlobStoreGenomeConnectorClient.
 */
public final class BlobStoreGenomeConnectorClientTest extends AbstractGenomeConnectorClientTest {

    @Mock
    private BlobMetadata blobMetadata;
    @Mock
    private BlobStore blobStore;
    @Mock
    private BlobStoreContext context;
    @Mock
    private InputStreamMap inputStreamMap;
    @Mock
    private Location location;

    @Override
    protected GenomeConnectorClient createClient() {
        return new BlobStoreGenomeConnectorClient("container", context);
    }

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        super.setUp();
    }

    @Test(expected=NullPointerException.class)
    public void testConstructorNullContainer() {
        new BlobStoreGenomeConnectorClient(null, context);
    }

    @Test(expected=NullPointerException.class)
    public void testConstructorNullContext() {
        new BlobStoreGenomeConnectorClient("container", null);
    }

    @Test
    public void testGet() {
        when(context.createInputStreamMap(eq("container"))).thenReturn(inputStreamMap);
        when(inputStreamMap.get(eq("resource"))).thenReturn(new ByteArrayInputStream(new byte[0]));

        InputStream inputStream = null;
        try {
            inputStream = client.get("resource");
            assertNotNull(inputStream);
        }
        finally {
            try {
                inputStream.close();
            }
            catch (Exception e) {
                // ignore
            }
        }
    }

    @Test
    public void testGetResourceNotFound() {
        when(context.createInputStreamMap(eq("container"))).thenReturn(inputStreamMap);
        assertNull(client.get("resource-not-found"));
    }

    @Test
    public void testPut() {
        when(context.createInputStreamMap(eq("container"))).thenReturn(inputStreamMap);
        client.put("resource", new ByteArrayInputStream(new byte[0]));
    }

    @Test
    public void testPutGetRoundTrip() throws Exception {
        InputStreamMap testInputStreamMap = new TestInputStreamMap();
        when(context.createInputStreamMap(eq("container"))).thenReturn(testInputStreamMap);

        byte[] bytes = new byte[1];
        bytes[0] = 42;

        client.put("resource", new ByteArrayInputStream(bytes));
        InputStream inputStream = null;
        try {
            inputStream = client.get("resource");
            assertNotNull(inputStream);

            inputStream.read(bytes, 0, 1);
            assertEquals(42, bytes[0]);
        }
        finally {
            try {
                inputStream.close();
            }
            catch (Exception e) {
                // ignore
            }
        }        
    }

    /**
     * Test implementation of InputStreamMap.
     */
    private static class TestInputStreamMap extends HashMap<String, InputStream> implements InputStreamMap {

        @Override
        public Iterable<? extends StorageMetadata> list() {
            return null;
        }

        @Override
        public InputStream putString(final String key, final String value) {
            return null;
        }

        @Override
        public InputStream putFile(final String key, final File value) {
            return null;
        }

        @Override
        public InputStream putBytes(final String key, final byte[] value) {
            return null;
        }

        @Override
        public void putAllStrings(final Map<? extends String, ? extends String> map) {
            // empty
        }

        @Override
        public void putAllBytes(final Map<? extends String, ? extends byte[]> map) {
            // empty
        }

        @Override
        public void putAllFiles(final Map<? extends String, ? extends File> map) {
            // empty
        }
    }

    /*
    @Test
    public void testListContainerDoesNotExist() {
        when(context.getBlobStore()).thenReturn(blobStore);
        when(blobStore.containerExists(anyString())).thenReturn(false);

        int count = 0;
        for (GenomeConnectorFileSet fileSet : client.list()) {
            count++;
        }
        assertEquals(0, count);
    }

    @Test
    public void testListSingleFile() throws Exception {
        when(context.getBlobStore()).thenReturn(blobStore);
        when(blobStore.containerExists(anyString())).thenReturn(true);

        Map<String, String> empty = Collections.emptyMap();
        StorageMetadata storageMetadata = new StorageMetadataImpl(StorageType.BLOB, "id", "name", location, new URI("http://localhost/name"), "eTag", new Date(), new Date(), empty);
        final PageSet<StorageMetadata> pageSet = new PageSetImpl(ImmutableList.of(storageMetadata), null);

        // answer req'd since thenReturn fails to covert wildcard type
        when(blobStore.list(anyString())).thenAnswer(new Answer() {
                @Override
                public Object answer(final InvocationOnMock invocation) {
                    return pageSet;
                }
            });

        int count = 0;
        for (GenomeConnectorFileSet fileSet : client.list()) {
            assertNotNull(fileSet);
            count++;
        }
        assertEquals(1, count);
    }

    @Test
    public void testListMultipleFiles() throws Exception {
        when(context.getBlobStore()).thenReturn(blobStore);
        when(blobStore.containerExists(anyString())).thenReturn(true);

        Map<String, String> empty = Collections.emptyMap();
        StorageMetadata foo = new StorageMetadataImpl(StorageType.BLOB, "id0", "foo", location, new URI("http://localhost/foo"), "eTag", new Date(), new Date(), empty);
        StorageMetadata bar = new StorageMetadataImpl(StorageType.BLOB, "id1", "bar", location, new URI("http://localhost/bar"), "eTag", new Date(), new Date(), empty);
        StorageMetadata baz = new StorageMetadataImpl(StorageType.BLOB, "id2", "baz", location, new URI("http://localhost/baz"), "eTag", new Date(), new Date(), empty);
        final PageSet<StorageMetadata> pageSet = new PageSetImpl(ImmutableList.of(foo, bar, baz), null);
        when(blobStore.list(anyString())).thenAnswer(new Answer() {
                @Override
                public Object answer(final InvocationOnMock invocation) {
                    return pageSet;
                }
            });

        int count = 0;
        for (GenomeConnectorFileSet fileSet : client.list()) {
            assertNotNull(fileSet);
            count++;
        }
        assertEquals(3, count);
    }

    @Test
    public void testListAdditionalListCall() throws Exception {
        when(context.getBlobStore()).thenReturn(blobStore);
        when(blobStore.containerExists(anyString())).thenReturn(true);

        Map<String, String> empty = Collections.emptyMap();
        StorageMetadata foo = new StorageMetadataImpl(StorageType.BLOB, "id0", "foo", location, new URI("http://localhost/foo"), "eTag", new Date(), new Date(), empty);
        StorageMetadata bar = new StorageMetadataImpl(StorageType.BLOB, "id1", "bar", location, new URI("http://localhost/bar"), "eTag", new Date(), new Date(), empty);
        final PageSet<StorageMetadata> pageSet = new PageSetImpl(ImmutableList.of(foo), "marker");
        final PageSet<StorageMetadata> additionalPageSet = new PageSetImpl(ImmutableList.of(bar), null);
        when(blobStore.list(anyString())).thenAnswer(new Answer() {
                @Override
                public Object answer(final InvocationOnMock invocation) {
                    return pageSet;
                }
            });
        when(blobStore.list(anyString(), any(ListContainerOptions.class))).thenAnswer(new Answer() {
                @Override
                public Object answer(final InvocationOnMock invocation) {
                    return additionalPageSet;
                }
            });

        int count = 0;
        for (GenomeConnectorFileSet fileSet : client.list()) {
            assertNotNull(fileSet);
            count++;
        }
        assertEquals(2, count);
    }

    @Test(expected=NullPointerException.class)
    public void testMetaNullFile() {
        client.meta(null);
    }

    @Test
    public void testMetaBlobDoesNotExist() {
        when(context.getBlobStore()).thenReturn(blobStore);
        when(blobStore.blobExists(anyString(), anyString())).thenReturn(false);
        assertNull(client.meta(new GenomeConnectorFile() {}));
    }

    @Test
    public void testMeta() {
        when(context.getBlobStore()).thenReturn(blobStore);
        when(blobStore.blobExists(anyString(), anyString())).thenReturn(true);
        when(blobStore.blobMetadata(anyString(), anyString())).thenReturn(blobMetadata);        
        assertNotNull(client.meta(new GenomeConnectorFile() {}));
    }
    */
}