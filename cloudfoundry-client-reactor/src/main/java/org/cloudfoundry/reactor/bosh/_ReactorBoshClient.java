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

package org.cloudfoundry.reactor.bosh;

import org.cloudfoundry.Nullable;
import org.cloudfoundry.bosh.BoshClient;
import org.cloudfoundry.bosh.deployments.Deployments;
import org.cloudfoundry.bosh.info.Info;
import org.cloudfoundry.bosh.releases.Releases;
import org.cloudfoundry.bosh.stemcells.Stemcells;
import org.cloudfoundry.bosh.tasks.Tasks;
import org.cloudfoundry.reactor.ConnectionContext;
import org.cloudfoundry.reactor.TokenProvider;
import org.cloudfoundry.reactor.bosh.deployments.ReactorDeployments;
import org.cloudfoundry.reactor.bosh.info.ReactorInfo;
import org.cloudfoundry.reactor.bosh.releases.ReactorReleases;
import org.cloudfoundry.reactor.bosh.stemcells.ReactorStemcells;
import org.cloudfoundry.reactor.bosh.tasks.ReactorTasks;
import org.immutables.value.Value;
import reactor.core.publisher.Mono;

/**
 * The Reactor-based implementation of {@link BoshClient}
 */
@Value.Immutable
abstract class _ReactorBoshClient implements BoshClient {

    @Override
    @Value.Derived
    public Deployments deployments() {
        return new ReactorDeployments(getConnectionContext(), getRoot(), getTokenProvider());
    }

    @Override
    @Value.Derived
    public Info info() {
        return new ReactorInfo(getConnectionContext(), getRoot(), getTokenProvider());
    }

    @Override
    @Value.Derived
    public Releases releases() {
        return new ReactorReleases(getConnectionContext(), getRoot(), getTokenProvider());
    }

    @Override
    @Value.Derived
    public Stemcells stemcells() {
        return new ReactorStemcells(getConnectionContext(), getRoot(), getTokenProvider());
    }

    @Override
    @Value.Derived
    public Tasks tasks() {
        return new ReactorTasks(getConnectionContext(), getRoot(), getTokenProvider());
    }

    @Nullable
    abstract ConnectionContext getConnectionContext();

    @Value.Default
    Mono<String> getRoot() {
        return getConnectionContext().getRoot();
    }

    abstract TokenProvider getTokenProvider();

}
