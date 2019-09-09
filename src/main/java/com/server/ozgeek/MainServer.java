package com.server.ozgeek;

import com.server.ozgeek.auth.User;
import com.server.ozgeek.auth.UserAuthenticator;
import com.server.ozgeek.health.TemplateHealthCheck;
import com.server.ozgeek.resources.ServerResource;
import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import org.whispersystems.dropwizard.simpleauth.AuthDynamicFeature;
import org.whispersystems.dropwizard.simpleauth.BasicCredentialAuthFilter;

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
//        ErrorPageErrorHandler errorPageError = new ErrorPageErrorHandler();
//        errorPageError.addErrorPage(404, "/error/404");
//        environment.getApplicationContext().setErrorHandler(errorPageError);

        final TemplateHealthCheck healthCheck = new TemplateHealthCheck(configuration.getTemplate());
        environment.healthChecks().register("template", healthCheck);

        UserAuthenticator          userAuthenticator          = new UserAuthenticator(configuration.getUserToken().getBytes());
//        SignalServiceAuthenticator signalServiceAuthenticator = new SignalServiceAuthenticator(configuration.getSignalServiceConfiguration().getServerAuthenticationToken());


        environment.jersey().register(new AuthDynamicFeature(
                new BasicCredentialAuthFilter.Builder<User>()
                        .setAuthenticator(userAuthenticator)
                        .setPrincipal(User.class)
                        .buildAuthFilter()
//                ,
//                new BasicCredentialAuthFilter.Builder<Admin>()
//                        .setAuthenticator(new AdminAuthenticator())
//                        .setPrincipal(Admin.class)
//                        .buildAuthFilter()
        ));
        ServerResource resource = new ServerResource(
                configuration.getTemplate(),
                configuration.getDefaultName()
        );


        environment.jersey().register(resource);
//        environment.jersey().register(new ErrorResource());
    }

}