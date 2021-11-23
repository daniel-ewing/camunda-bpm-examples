package org.camunda.bpm.spring.boot.example.webapp.ee;

import org.camunda.bpm.spring.boot.starter.property.CamundaBpmProperties;
import org.glassfish.jersey.servlet.ServletContainer;
import org.springframework.boot.web.servlet.ServletContextInitializer;
import org.springframework.core.annotation.Order;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration.Dynamic;

import static java.util.Collections.singletonMap;
import static org.glassfish.jersey.servlet.ServletProperties.JAXRS_APPLICATION_CLASS;

@Order(0)
public class CustomCamundaBpmRestInitializer implements ServletContextInitializer {

    private final CamundaBpmProperties properties;

    public CustomCamundaBpmRestInitializer(CamundaBpmProperties properties) {
        this.properties = properties;
    }

    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {

        String applicationPath = properties.getWebapp().getApplicationPath();

        /*
         * Custom registration of the engine REST API JAX-RS application with our
         * customized ObjectMapper. See CamundaBpmWebappInitializer for comparison.
         */
        Dynamic registration = servletContext.addServlet("REST APIs", ServletContainer.class);
        registration.addMapping(applicationPath + "/api/engine/*");
        registration.setInitParameters(singletonMap(JAXRS_APPLICATION_CLASS, CustomEngineRestApplication.class.getName()));
    }

}