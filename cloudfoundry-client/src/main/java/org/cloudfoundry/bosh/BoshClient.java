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

package org.cloudfoundry.bosh;

import org.cloudfoundry.bosh.deployments.Deployments;
import org.cloudfoundry.bosh.info.Info;
import org.cloudfoundry.bosh.releases.Releases;
import org.cloudfoundry.bosh.stemcells.Stemcells;
import org.cloudfoundry.bosh.tasks.Tasks;

/**
 * Main entry point to the BOSH API
 */
public interface BoshClient {

    /**
     * Main entry point to the BOSH Deployments API
     */
    Deployments deployments();

    /**
     * Main entry point to the BOSH Info API
     */
    Info info();

    /**
     * Main entry point to the BOSH Releases API
     */
    Releases releases();

    /**
     * Main entry point to the BOSH Stemcells API
     */
    Stemcells stemcells();

    /**
     * Main entry point to the BOSH Tasks API
     */
    Tasks tasks();

}
