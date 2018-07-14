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

/**
 * Class which returns all images for given tag in JSON format. List is sent in
 * response object.
 * 
 * @author tin
 *
 */
@Path("/tag")
public class ImageJSON {

  /**
   * Method which goes through list of all images and selects out ones which have
   * requested tag. Returns {@link Response} object with list of selected images
   * wrapped in {@link GenericEntity} object.
   * 
   * @param tag tag used to select images
   * @return {@link Response} object with list of selected images
   */
  @Path("{tag}")
  @GET
  @Produces(MediaType.APPLICATION_JSON)
  public Response getImage(@PathParam("tag") String tag) {
    List<Image> images = new ArrayList<>();
    for (Image image : ImageDB.getImages()) {
      if(image.getTags().contains(tag)) {
        images.add(image);
      }
    }
    GenericEntity<List<Image>> entity = new GenericEntity<List<Image>>(images) {
    };
    return Response.ok(entity).build();
  }

}
