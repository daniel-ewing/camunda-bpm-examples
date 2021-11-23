/*
 * Copyright Camunda Services GmbH and/or licensed to Camunda Services GmbH
 * under one or more contributor license agreements. See the NOTICE file
 * distributed with this work for additional information regarding copyright
 * ownership. Camunda licenses this file to you under the Apache License,
 * Version 2.0; you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.camunda.bpm.spring.boot.example.webapp.ee;

import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.spring.boot.starter.annotation.EnableProcessApplication;
import org.camunda.bpm.spring.boot.starter.event.PostDeployEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.servlet.JerseyApplicationPath;
import org.springframework.context.annotation.Bean;
import org.springframework.context.event.EventListener;

@SpringBootApplication
@EnableProcessApplication
public class EnterpriseRestExampleApplication {
  private final String processKey = "java-8-date";
  private final static Logger LOGGER = LoggerFactory.getLogger(EnterpriseRestExampleApplication.class);

  @Autowired
  private RuntimeService runtimeService;

  @Autowired
  private JerseyApplicationPath applicationPath;

  @Bean
  public CustomCamundaBpmRestInitializer getCustomInitializer() {
    return new CustomCamundaBpmRestInitializer(applicationPath);
  }

  @EventListener
  public void onPostDeploy(PostDeployEvent event) {
    ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(processKey);
    LOGGER.info("Process {} started with processInstanceId {}", processKey, processInstance.getProcessInstanceId());
  }

  public static void main(String... args) {
    SpringApplication.run(EnterpriseRestExampleApplication.class, args);
  }

}
