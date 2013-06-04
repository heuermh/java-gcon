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

import java.util.Map;

import com.github.heuermh.gcon.GenomeConnector;
import com.github.heuermh.gcon.GenomeConnectorClient;

import com.illumina.basespace.BaseSpaceConfiguration;
import com.illumina.basespace.BaseSpaceSession;
import com.illumina.basespace.BaseSpaceSessionManager;

/**
 * BaseSpace implementation of the genome connector APIs.
 */
final class BaseSpaceGenomeConnector implements GenomeConnector {

    @Override
    public final GenomeConnectorClient createClient(final Map<String, String> context) {
        BaseSpaceConfiguration config = new BaseSpaceConfiguration() {
                // todo:  use values from context

                @Override
                public String getVersion() {
                    return "v1pre3"; // The current version of the API
                }

                @Override
                public String getApiRootUri() {
                    return "https://api.basespace.illumina.com"; // The API server's URI
                }

                @Override
                public String getAccessTokenUriFragment() {
                    return "/oauthv2/token"; // The API call to request an Access Token
                }

                @Override
                public String getAuthorizationUriFragment() {
                    return "/oauthv2/deviceauthorization";  // The API call to request a device authorization code
                }

                @Override
                public String getAuthorizationScope() {
                    return "browse global"; // The scope specified earlier
                }

                @Override
                public String getProxyHost() {
                    return null;
                }

                @Override
                public int getProxyPort() {
                    return 0;
                }

                @Override
                public String getAccessToken() {
                    return null; // This token is null for apps that do not have a permanent access token
                }

                @Override
                public String getClientId() {
                    return "client id"; // Our client id from My Apps
                }

                @Override
                public String getClientSecret() {
                    return "client secret"; // Our client secret from My Apps
                }

                @Override
                public int getReadTimeout() {
                    return -1;
                }

                @Override
                public int getConnectionTimeout() {
                    return -1;
                }
            };

        BaseSpaceSession session = BaseSpaceSessionManager.instance().requestSession(config);
        return new BaseSpaceGenomeConnectorClient(session);
    }
}