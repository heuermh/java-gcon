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

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.Map;

import com.github.heuermh.gcon.GenomeConnector;
import com.github.heuermh.gcon.GenomeConnectorClient;

import com.github.heuermh.personalgenome.client.PersonalGenomeClient;

/**
 * 23andMe implementation of the genome connector APIs.
 */
final class TtamGenomeConnector implements GenomeConnector {

    @Override
    public final GenomeConnectorClient createClient(final Map<String, String> context) {
        checkNotNull(context);

        // todo:  requires guice and scribe OAuthService
        PersonalGenomeClient client = null;
        return new TtamGenomeConnectorClient(client);
    }
}
