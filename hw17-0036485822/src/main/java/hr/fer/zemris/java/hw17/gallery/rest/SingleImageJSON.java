package hr.fer.zemris.java.hw17.gallery.rest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import hr.fer.zemris.java.hw17.gallery.model.Image;
import hr.fer.zemris.java.hw17.gallery.model.ImageDB;

/**
 * Class which uses path parameter to return single image in JSON format. Image
 * is sent in response object.
 * 
 * @author tin
 *
 */
@Path("/image")
public class SingleImageJSON {

  /**
   * Method which goes through list of all images and selects one which has
   * requested parameter. Returns {@link Response} object with selected image
   * wrapped in {@link GenericEntity} object.
   * 
   * @param path path used to identify iamge
   * @return {@link Response} object with identified image
   */
  @Path("{path}")
  @GET
  @Produces(MediaType.APPLICATION_JSON)
  public Response getImage(@PathParam("path") String path) {
    for (Image image : ImageDB.getImages()) {
      if(image.getPath().getFileName().toString().equals(path)) {
        return Response.ok(image).build();
      }
    }
    return null;
  }

}
