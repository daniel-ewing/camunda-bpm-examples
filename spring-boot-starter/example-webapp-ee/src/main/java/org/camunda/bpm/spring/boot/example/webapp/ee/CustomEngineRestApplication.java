package org.camunda.bpm.spring.boot.example.webapp.ee;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.camunda.bpm.engine.rest.mapper.JacksonConfigurator;
import org.camunda.bpm.webapp.impl.engine.EngineRestApplication;

import java.util.Set;

public class CustomEngineRestApplication extends EngineRestApplication {

    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> classes = super.getClasses();
        classes.removeIf(c -> c == JacksonConfigurator.class);
        classes.add(CustomJacksonConfigurator.class);
        return classes;
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