package com.example.helloworld.resources;

import com.codahale.metrics.annotation.Timed;
import com.example.helloworld.dao.SayingDao;
import java.util.IllegalFormatException;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;
import javax.inject.Inject;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.InternalServerErrorException;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import com.example.helloworld.models.Saying;

@Path("/hello-world")
@Produces(MediaType.APPLICATION_JSON)
@Consumes( {MediaType.APPLICATION_JSON, "text/json"})
public class HelloWorldResource {

    private SayingDao sayingDao;
    private String defaultName;

    @Inject
    public HelloWorldResource(SayingDao sayingDao, String defaultName) {
        this.sayingDao = sayingDao;
        this.defaultName = defaultName;
    }

    @GET
    @Path("/hello")
    @Timed
    public Saying sayHello(@QueryParam("name") Optional<String> name) {
        // the query parameter 'name' is stored in the name variable, e.g., ?name=yaboi
        // the Optional object is provided by Dropwizard ontop of Jersey's normal functionality
        // final String value = String.format(template, name.orElse(defaultName));
        // Jersey then takes the returned Saying object and uses a built in Dropwizard provider class which allows for producing
        // and consuming Java objects and JSON objects. The provider writes out the JSON and the client receives a 200 ok res with content type application/json
        // return new Saying(counter.incrementAndGet(), value, true);
        String content;
        final Saying saying = sayingDao.getRandomSaying().orElseThrow(
                () -> new NotFoundException("Saying not found")
        );
        try{
            content = String.format(saying.getContent(), name.orElse(defaultName));
        } catch (IllegalFormatException err) {
            content = saying.getContent() + " " + name.orElse(defaultName);
        }
        saying.setContent(content);

        return saying;
    }

    @GET
    @Path("/{id}")
    @Timed
    public Saying getSayingById(@PathParam("id") long id) {
        return sayingDao.getSaying(id).orElseThrow( () -> new NotFoundException("Saying not found"));
    }

    @GET
    @Timed
    public List<Saying> getSayings(){
        return sayingDao.getSayings();
    }

    @POST
    @Timed
    public Saying createSaying(@NotNull @Valid Saying saying) {
        long id = sayingDao.createSaying(saying);
        return sayingDao.getSaying(id).orElseThrow(() -> new InternalServerErrorException("An error occurred when creating the saying"));
    }

    @PUT
    @Timed
    @Path("/{id}")
    public Saying updateSaying(@PathParam("id") long id, @NotNull @Valid Saying updatedSaying) {
        sayingDao.updateSaying(id, updatedSaying);
        return sayingDao.getSaying(id).orElseThrow(() -> new NotFoundException("Saying not found"));
    }

    @DELETE
    @Timed
    @Path("/{id}")
    public void deleteSaying(@PathParam("id") long id){
        sayingDao.getSaying(id).orElseThrow( () -> new NotFoundException("Saying not found"));
        sayingDao.deleteSaying(id);
    }
}
