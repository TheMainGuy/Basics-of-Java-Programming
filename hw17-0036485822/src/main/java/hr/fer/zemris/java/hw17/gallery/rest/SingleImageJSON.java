package hr.fer.zemris.java.hw17.gallery.rest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import hr.fer.zemris.java.hw17.gallery.model.Image;
import hr.fer.zemris.java.hw17.gallery.model.ImageDB;

@Path("/image")
public class SingleImageJSON {

  @Path("{path}")
  @GET
  @Produces(MediaType.APPLICATION_JSON)
  public Response getImage(@PathParam("path") String path) {
    for(Image image : ImageDB.getImages()) {
      if(image.getPath().getFileName().toString().equals(path)) {
        return Response.ok(image).build();
      }
    }
    return null;
  }

}
