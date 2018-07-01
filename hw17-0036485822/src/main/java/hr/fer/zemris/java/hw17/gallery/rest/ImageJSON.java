package hr.fer.zemris.java.hw17.gallery.rest;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import hr.fer.zemris.java.hw17.gallery.model.Image;
import hr.fer.zemris.java.hw17.gallery.model.ImageDB;

@Path("/tag")
public class ImageJSON {

  @Path("{tag}")
  @GET
  @Produces(MediaType.APPLICATION_JSON)
  public Response getImage(@PathParam("tag") String tag) {
    List<Image> images = new ArrayList<>();
    for(Image image : ImageDB.getImages()) {
      if(image.getTags().contains(tag)) {
        images.add(image);
      }
    }
    GenericEntity<List<Image>> entity = new GenericEntity<List<Image>>(images) {};
    return Response.ok(entity).build();
  }

}
