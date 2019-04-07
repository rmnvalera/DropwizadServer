package com.server.ozgeek.resources;

import com.codahale.metrics.annotation.Timed;
import com.server.ozgeek.api.Saying;
import com.server.ozgeek.auth.User;
import io.dropwizard.auth.Auth;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import java.util.concurrent.atomic.AtomicLong;
import java.util.Optional;

@Path("/v1/api")
@Produces(MediaType.APPLICATION_JSON)
public class ServerResource {
    private final String template;
    private final String defaultName;
    private final AtomicLong counter;

    public ServerResource(String template, String defaultName) {
        this.template = template;
        this.defaultName = defaultName;
        this.counter = new AtomicLong();
    }

    @GET
    @Timed
    public Saying sayHello(@QueryParam("name") Optional<String> name, @Auth User sender) {
        final String value = String.format(name.orElse(defaultName));
        return new Saying(counter.incrementAndGet(), value);
    }
}