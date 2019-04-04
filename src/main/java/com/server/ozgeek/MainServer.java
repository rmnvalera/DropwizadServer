package com.server.ozgeek;

import com.codahale.metrics.SharedMetricRegistries;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.server.ozgeek.health.TemplateHealthCheck;
import com.server.ozgeek.resources.ServerResource;
import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

public class MainServer extends Application<ServerConfiguration> {
    public static void main(String[] args) throws Exception {
        new MainServer().run(args);
    }

    @Override
    public String getName() {
        return "oZGeek Server";
    }

    @Override
    public void initialize(Bootstrap<ServerConfiguration> bootstrap) {
        // nothing to do yet
    }

    @Override
    public void run(ServerConfiguration configuration,
                    Environment environment) {
        // nothing to do yet
//        SharedMetricRegistries.add(Constants.METRICS_NAME, environment.metrics());
        final TemplateHealthCheck healthCheck = new TemplateHealthCheck(configuration.getTemplate());
        environment.healthChecks().register("template", healthCheck);

//        environment.getObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
//        environment.getObjectMapper().setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.NONE);
//        environment.getObjectMapper().setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);


        ServerResource resource = new ServerResource(
                configuration.getTemplate(),
                configuration.getDefaultName()
        );


        environment.jersey().register(resource);
    }

}