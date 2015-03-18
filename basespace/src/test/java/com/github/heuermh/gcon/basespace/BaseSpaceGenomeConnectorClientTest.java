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
package com.github.heuermh.gcon.basespace;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.eq;
import static org.mockito.Mockito.when;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import com.github.heuermh.gcon.AbstractGenomeConnectorClientTest;
import com.github.heuermh.gcon.GenomeConnectorClient;

import com.illumina.basespace.ApiClient;

import com.illumina.basespace.entity.File;

import com.illumina.basespace.response.GetFileResponse;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

/**
 * Unit test for BaseSpaceGenomeConnectorClient.
 */
public final class BaseSpaceGenomeConnectorClientTest extends AbstractGenomeConnectorClientTest {

    @Mock
    private ApiClient apiClient;
    @Mock
    private GetFileResponse getFileResponse;
    @Mock
    private File file;


    @Override
    protected GenomeConnectorClient createClient() {
        return new BaseSpaceGenomeConnectorClient(apiClient);
    }

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        super.setUp();
    }

    @Test(expected=NullPointerException.class)
    public void testConstructorNullSession() {
        new BaseSpaceGenomeConnectorClient(null);
    }

    @Test
    public void testGet() throws Exception {
        when(apiClient.getFile(eq("resource"))).thenReturn(getFileResponse);
        when(getFileResponse.get()).thenReturn(file);
        when(apiClient.getFileInputStream(eq(file))).thenReturn(new ByteArrayInputStream(new byte[0]));

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
    public void testGetResourceNotFound() throws Exception {
        // todo:  confirm that null reponse is ok for these
        when(apiClient.getFile(eq("resource"))).thenReturn(null);
        when(apiClient.getFileInputStream(null)).thenReturn(null);
        assertNull(client.get("resource-not-found"));
    }

    @Test
    @Ignore // todo:  not supported until put method is implemented
    public void testPut() {
        client.put("resource", new ByteArrayInputStream(new byte[0]));
    }

    @Test
    @Ignore // todo:  not supported until put method is implemented
    public void testPutGetRoundTrip() throws Exception {
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
}
