package hr.fer.zemris.java.hw17.gallery.rest;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import hr.fer.zemris.java.hw17.gallery.model.ImageDB;

@Path("/tags")
public class TagJSON {

  @GET
  @Produces(MediaType.APPLICATION_JSON)
  public Response getImage() {
    List<String> tags = ImageDB.getTags();
    GenericEntity<List<String>> entity = new GenericEntity<List<String>>(tags) {};
    return Response.ok(entity).build();
  }

}
