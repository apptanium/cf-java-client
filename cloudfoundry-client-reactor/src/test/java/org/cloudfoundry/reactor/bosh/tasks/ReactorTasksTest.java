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

package org.cloudfoundry.reactor.bosh.tasks;

import org.cloudfoundry.bosh.tasks.GetTaskOutputRequest;
import org.cloudfoundry.bosh.tasks.GetTaskRequest;
import org.cloudfoundry.bosh.tasks.GetTaskResponse;
import org.cloudfoundry.bosh.tasks.ListTasksRequest;
import org.cloudfoundry.bosh.tasks.ListTasksResponse;
import org.cloudfoundry.bosh.tasks.State;
import org.cloudfoundry.bosh.tasks.Task;
import org.cloudfoundry.bosh.tasks.Type;
import org.cloudfoundry.reactor.InteractionContext;
import org.cloudfoundry.reactor.TestRequest;
import org.cloudfoundry.reactor.TestResponse;
import org.cloudfoundry.reactor.bosh.AbstractBoshApiTest;
import org.junit.Test;
import reactor.test.StepVerifier;

import java.time.Duration;

import static io.netty.handler.codec.http.HttpMethod.GET;
import static io.netty.handler.codec.http.HttpResponseStatus.OK;

public final class ReactorTasksTest extends AbstractBoshApiTest {

    private final ReactorTasks tasks = new ReactorTasks(CONNECTION_CONTEXT, this.root, TOKEN_PROVIDER);

    @Test
    public void get() {
        mockRequest(InteractionContext.builder()
            .request(TestRequest.builder()
                .method(GET).path("/tasks/1180")
                .build())
            .response(TestResponse.builder()
                .status(OK)
                .payload("fixtures/bosh/tasks/GET_{id}_response.json")
                .build())
            .build());

        this.tasks
            .get(GetTaskRequest.builder()
                .taskId(1180)
                .build())
            .as(StepVerifier::create)
            .expectNext(GetTaskResponse.builder()
                .id(1180)
                .state(State.PROCESSING)
                .description("run errand acceptance_tests from deployment cf-warden")
                .timestamp(1447033291)
                .user("admin")
                .build())
            .expectComplete()
            .verify(Duration.ofSeconds(5));
    }

    @Test
    public void getOutput() {
        mockRequest(InteractionContext.builder()
            .request(TestRequest.builder()
                .method(GET).path("/tasks/1180/output?type=debug")
                .build())
            .response(TestResponse.builder()
                .status(OK)
                .payload("fixtures/bosh/tasks/GET_{id}_output_response.txt")
                .build())
            .build());

        this.tasks
            .getOutput(GetTaskOutputRequest.builder()
                .taskId(1180)
                .type(Type.DEBUG)
                .build())
            .as(StepVerifier::create)
            .expectNext("D, [2015-11-09 02:19:36 #32545] [] DEBUG -- DirectorJobRunner: RECEIVED: director.37d8c089-853e-458c-8535-195085b4b7ed.459b05ae-8b69-4679-b2d5-b34e5fef2dcc " +
                "{\"value\":{\"agent_task_id\":\"c9f5b328-0656-41f1-631c-e17151be1e18\",\"state\":\"running\"}}\n" +
                "D, [2015-11-09 02:19:36 #32545] [task:1180] DEBUG -- DirectorJobRunner: (0.000441s) SELECT NULL\n" +
                "D, [2015-11-09 02:19:36 #32545] [task:1180] DEBUG -- DirectorJobRunner: (0.000317s) SELECT * FROM \"tasks\" WHERE \"id\" = 1180\n")
            .expectComplete()
            .verify(Duration.ofSeconds(5));
    }

    @Test
    public void list() {
        mockRequest(InteractionContext.builder()
            .request(TestRequest.builder()
                .method(GET).path("/tasks")
                .build())
            .response(TestResponse.builder()
                .status(OK)
                .payload("fixtures/bosh/tasks/GET_response.json")
                .build())
            .build());

        this.tasks
            .list(ListTasksRequest.builder()
                .build())
            .as(StepVerifier::create)
            .expectNext(ListTasksResponse.builder()
                .task(Task.builder()
                    .id(1180)
                    .state(State.PROCESSING)
                    .description("run errand acceptance_tests from deployment cf-warden")
                    .timestamp(1447033291)
                    .user("admin")
                    .build())
                .task(Task.builder()
                    .id(1179)
                    .state(State.DONE)
                    .description("scan and fix")
                    .timestamp(1447031334)
                    .result("scan and fix complete")
                    .user("admin")
                    .build())
                .task(Task.builder()
                    .id(1178)
                    .state(State.DONE)
                    .description("scan and fix")
                    .timestamp(1447031334)
                    .result("scan and fix complete")
                    .user("admin")
                    .build())
                .build())
            .expectComplete()
            .verify(Duration.ofSeconds(5));
    }

    @Test
    public void listDeployment() {
        mockRequest(InteractionContext.builder()
            .request(TestRequest.builder()
                .method(GET).path("/tasks?deployment=cf-warden")
                .build())
            .response(TestResponse.builder()
                .status(OK)
                .payload("fixtures/bosh/tasks/GET_?state_response.json")
                .build())
            .build());

        this.tasks
            .list(ListTasksRequest.builder()
                .deployment("cf-warden")
                .build())
            .as(StepVerifier::create)
            .expectNext(ListTasksResponse.builder()
                .task(Task.builder()
                    .id(1180)
                    .state(State.PROCESSING)
                    .description("run errand acceptance_tests from deployment cf-warden")
                    .timestamp(1447033291)
                    .user("admin")
                    .build())
                .build())
            .expectComplete()
            .verify(Duration.ofSeconds(5));
    }

    @Test
    public void listState() {
        mockRequest(InteractionContext.builder()
            .request(TestRequest.builder()
                .method(GET).path("/tasks?state=queued,processing,cancelling")
                .build())
            .response(TestResponse.builder()
                .status(OK)
                .payload("fixtures/bosh/tasks/GET_?state_response.json")
                .build())
            .build());

        this.tasks
            .list(ListTasksRequest.builder()
                .state(State.QUEUED)
                .state(State.PROCESSING)
                .state(State.CANCELLING)
                .build())
            .as(StepVerifier::create)
            .expectNext(ListTasksResponse.builder()
                .task(Task.builder()
                    .id(1180)
                    .state(State.PROCESSING)
                    .description("run errand acceptance_tests from deployment cf-warden")
                    .timestamp(1447033291)
                    .user("admin")
                    .build())
                .build())
            .expectComplete()
            .verify(Duration.ofSeconds(5));
    }

}
