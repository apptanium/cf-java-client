/*
 * Copyright 2013-2017 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.cloudfoundry.reactor.bosh.stemcells;

import org.cloudfoundry.bosh.stemcells.ListStemcellsRequest;
import org.cloudfoundry.bosh.stemcells.ListStemcellsResponse;
import org.cloudfoundry.bosh.stemcells.Stemcells;
import org.cloudfoundry.reactor.ConnectionContext;
import org.cloudfoundry.reactor.TokenProvider;
import org.cloudfoundry.reactor.bosh.AbstractBoshOperations;
import reactor.core.publisher.Mono;

/**
 * The Reactor-based implementation of {@link Stemcells}
 */
public final class ReactorStemcells extends AbstractBoshOperations implements Stemcells {

    /**
     * Creates an instance
     *
     * @param connectionContext the {@link ConnectionContext} to use when communicating with the server
     * @param root              the root URI of the server.  Typically something like {@code https://api.run.pivotal.io}.
     * @param tokenProvider     the {@link TokenProvider} to use when communicating with the server
     */
    public ReactorStemcells(ConnectionContext connectionContext, Mono<String> root, TokenProvider tokenProvider) {
        super(connectionContext, root, tokenProvider);
    }

    @Override
    public Mono<ListStemcellsResponse> list(ListStemcellsRequest request) {
        return get(request, ListStemcellsResponse.class, builder -> builder.pathSegment("stemcells"));
    }

}
