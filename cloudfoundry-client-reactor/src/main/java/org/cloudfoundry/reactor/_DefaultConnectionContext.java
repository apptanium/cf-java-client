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

package org.cloudfoundry.reactor;

import org.cloudfoundry.reactor.util.JsonCodec;
import org.cloudfoundry.reactor.util.NetworkLogging;
import org.immutables.value.Value;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;

import java.util.Map;

/**
 * The default implementation of the {@link ConnectionContext} interface.  This is the implementation that should be used for most non-testing cases.
 */
@Value.Immutable
abstract class _DefaultConnectionContext extends AbstractConnectionContext {

    _DefaultConnectionContext() {
        super(443, "cloudfoundry-client");
    }

    @Override
    public Mono<String> getRoot(String key) {
        return getInfo()
            .map(info -> normalize(UriComponentsBuilder.fromUriString(info.get(key)), getScheme()))
            .doOnNext(components -> trust(components, getSslCertificateTruster()))
            .map(UriComponents::toUriString)
            .cache();
    }

    @SuppressWarnings("unchecked")
    @Value.Derived
    Mono<Map<String, String>> getInfo() {
        return getRoot()
            .map(uri -> UriComponentsBuilder.fromUriString(uri).pathSegment("v2", "info").build().toUriString())
            .then(uri -> getHttpClient()
                .get(uri)
                .doOnSubscribe(NetworkLogging.get(uri))
                .transform(NetworkLogging.response(uri)))
            .transform(JsonCodec.decode(getObjectMapper(), Map.class))
            .map(m -> (Map<String, String>) m)
            .cache();
    }

}
