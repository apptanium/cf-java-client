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

package org.cloudfoundry.bosh.info;

import reactor.core.publisher.Mono;

/**
 * Main entry point to the BOSH Info API
 */
public interface Info {

    /**
     * Makes the <a href="https://bosh.io/docs/director-api-v1.html#info">Get Info</a> request
     *
     * @param request the Get Info request
     * @return the response from the Get Info request
     */
    Mono<GetInfoResponse> get(GetInfoRequest request);

}
