package org.camunda.bpm.spring.boot.example.webapp.ee;

import org.camunda.bpm.spring.boot.starter.property.CamundaBpmProperties;
import org.glassfish.jersey.servlet.ServletContainer;
import org.springframework.boot.autoconfigure.web.servlet.JerseyApplicationPath;
import org.springframework.boot.web.servlet.ServletContextInitializer;
import org.springframework.core.annotation.Order;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration.Dynamic;

import static java.util.Collections.singletonMap;
import static org.glassfish.jersey.servlet.ServletProperties.JAXRS_APPLICATION_CLASS;

@Order(0)
public class CustomCamundaBpmRestInitializer implements ServletContextInitializer {

    private JerseyApplicationPath applicationPath;

    public CustomCamundaBpmRestInitializer(JerseyApplicationPath applicationPath) {
        this.applicationPath = applicationPath;
    }

    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {

        /*
         * Custom registration of the engine-rest REST API JAX-RS application with our
         * customized ObjectMapper. See CamundaBpmRestInitializer for comparison.
         */
        Dynamic registration = servletContext.addServlet("ENGINE REST APIs", ServletContainer.class);
        registration.addMapping(applicationPath.getUrlMapping());
        registration.setInitParameters(singletonMap(JAXRS_APPLICATION_CLASS, CustomEngineRestApplication.class.getName()));
    }

}