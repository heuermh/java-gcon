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
package com.github.heuermh.gcon.ttam;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import com.github.heuermh.gcon.AbstractGenomeConnectorClientTest;
import com.github.heuermh.gcon.GenomeConnectorClient;

import com.github.heuermh.personalgenome.client.PersonalGenomeClient;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

/**
 * Unit test for TtamGenomeConnectorClient.
 */
public final class TtamGenomeConnectorClientTest extends AbstractGenomeConnectorClientTest {

    @Mock
    private PersonalGenomeClient personalGenomeClient;

    @Override
    protected GenomeConnectorClient createClient() {
        return new TtamGenomeConnectorClient(personalGenomeClient);
    }

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        super.setUp();
    }

    @Test(expected=NullPointerException.class)
    public void testConstructorNullClient() {
        new TtamGenomeConnectorClient(null);
    }

    @Test
    @Ignore // todo:  determine what format to return
    public void testGet() throws Exception {
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
        assertNull(client.get("resource-not-found"));
    }

    @Test(expected=UnsupportedOperationException.class)
    public void testPutOperationNotSupported() {
        client.put("resource", new ByteArrayInputStream(new byte[0]));
    }
}
