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
package com.github.heuermh.gcon.basespace.examples;

import static com.google.common.base.Preconditions.checkNotNull;

import java.io.InputStream;

import java.util.Map;

import java.util.concurrent.Callable;

import com.google.common.collect.ImmutableMap;

import com.google.common.io.ByteStreams;

import com.github.heuermh.gcon.GenomeConnector;
import com.github.heuermh.gcon.GenomeConnectorClient;

import com.github.heuermh.gcon.basespace.BaseSpaceGenomeConnector;

/**
 * Examples for the BaseSpace implementation of the genome connector APIs.
 */
public final class BaseSpaceExample implements Callable<Integer> {
    private final String clientId;
    private final String clientSecret;
    private final String name;


    /**
     * Create a new BaseSpace example with the specified name.
     *
     * @param clientId client id, must not be null
     * @param clientSecret client secret, must not be null
     * @param name name, must not be null
     */
    public BaseSpaceExample(final String clientId, final String clientSecret, final String name) {
        checkNotNull(clientId);
        checkNotNull(clientSecret);
        checkNotNull(name);
        this.clientId = clientId;
        this.clientSecret = clientSecret;
        this.name = name;
    }


    @Override
    public Integer call() throws Exception {
        GenomeConnector genomeConnector = new BaseSpaceGenomeConnector();
        Map<String, String> context = ImmutableMap.<String, String>builder().put("clientId", clientId).put("clientSecret", clientSecret).build();
        GenomeConnectorClient client = genomeConnector.createClient(context);
        InputStream inputStream = client.get(name);
        ByteStreams.copy(inputStream, System.out);
        return 0;
    }


    /**
     * Main.
     *
     * @param args command line arguments
     */
    public static void main(final String[] args) {
        if (args.length != 3) {
            System.err.println("usage:\njava BaseSpaceExample [client_id] [client_secret] [name]");
            System.exit(-1);
        }
        try {
            System.exit(new BaseSpaceExample(args[0], args[1], args[2]).call());
        }
        catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
    }
}
