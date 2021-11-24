package org.camunda.bpm.spring.boot.example.webapp.ee;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.camunda.bpm.engine.rest.impl.CamundaRestResources;
import org.camunda.bpm.engine.rest.mapper.JacksonConfigurator;
import org.camunda.bpm.spring.boot.starter.rest.CamundaJerseyResourceConfig;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.springframework.context.annotation.Configuration;

import java.util.Set;

@Configuration
public class CustomEngineRestApplication extends CamundaJerseyResourceConfig {

    protected void registerCamundaRestResources() {
        Set<Class<?>> classes = CamundaRestResources.getConfigurationClasses();
        classes.removeIf(clazz -> clazz == JacksonConfigurator.class);
        classes.add(CustomJacksonConfigurator.class);
        this.registerClasses(classes);

        this.registerClasses(CamundaRestResources.getResourceClasses());
        this.register(JacksonFeature.class);
    }

    private static class CustomJacksonConfigurator extends JacksonConfigurator {

        @Override
        public ObjectMapper getContext(Class<?> clazz) {
            ObjectMapper objectMapper = super.getContext(clazz);
            objectMapper.registerModule(new JavaTimeModule());

            return objectMapper;
        }
    }
}