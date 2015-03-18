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
package com.github.heuermh.gcon;

import static org.junit.Assert.assertNotNull;

import java.io.ByteArrayInputStream;

import org.junit.Before;
import org.junit.Test;

/**
 * Abstract unit test for implementations of GenomeConnectorClient.
 */
public abstract class AbstractGenomeConnectorClientTest {
    protected GenomeConnectorClient client;

    /**
     * Create and return a new instance of an implementation of GenomeConnectorClient to test.
     *
     * @return a new instance of an implementation of GenomeConnectorClient to test
     */
    protected abstract GenomeConnectorClient createClient();

    @Before
    public void setUp() {
        client = createClient();
    }

    @Test
    public void testCreateClient() {
        assertNotNull(client);
    }

    @Test(expected=NullPointerException.class)
    public void testGetNullName() throws Exception {
        client.get(null);
    }

    @Test(expected=NullPointerException.class)
    public void testPutNullName() {
        client.put(null, new ByteArrayInputStream(new byte[0]));
    }

    @Test(expected=NullPointerException.class)
    public void testPutNullInputStream() {
        client.put("name", null);
    }
}
