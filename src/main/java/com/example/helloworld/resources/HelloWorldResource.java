package com.example.helloworld.resources;

import com.codahale.metrics.annotation.Timed;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import com.example.helloworld.models.Saying;

// @Path and @Produces are provided by the Dropwizard framework's library Jersey
@Path("/hello-world")
@Produces(MediaType.APPLICATION_JSON)
public class HelloWorldResource {
    private final String template;
    private final String defaultName;
    private final AtomicLong counter;

    public HelloWorldResource(String template, String defaultName) {
        this.template = template;
        this.defaultName = defaultName;
        this.counter = new AtomicLong();
    }

    @GET
    @Timed // Dropwizard automatically records the duration and rate of invocations as a Metrics Timer
    public Saying sayHello(@QueryParam("name") Optional<String> name) {
        // the query parameter 'name' is stored in the name variable, e.g., ?name=yaboi
        // the Optional object is provided by Dropwizard ontop of Jersey's normal functionality
        final String value = String.format(template, name.orElse(defaultName));
        // Jersey then takes the returned Saying object and uses a built in Dropwizard provider class which allows for producing
        // and consuming Java objects and JSON objects. The provider writes out the JSON and the client receives a 200 ok res with content type application/json
        return new Saying(counter.incrementAndGet(), value);
    }
}
