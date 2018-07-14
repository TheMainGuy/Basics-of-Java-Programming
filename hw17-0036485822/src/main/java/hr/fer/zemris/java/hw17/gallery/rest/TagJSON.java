package hr.fer.zemris.java.hw17.gallery.rest;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import hr.fer.zemris.java.hw17.gallery.model.ImageDB;

/**
 * Class which returns all image tags in JSON format. Tags are sent in response
 * object.
 * 
 * @author tin
 *
 */
@Path("/tags")
public class TagJSON {

  /**
   * Method which creates a list of all image tags. Returns {@link Response}
   * object with tags wrapped in {@link GenericEntity} object.
   * 
   * @return {@link Response} object with list of tags
   */
  @GET
  @Produces(MediaType.APPLICATION_JSON)
  public Response getImage() {
    List<String> tags = ImageDB.getTags();
    GenericEntity<List<String>> entity = new GenericEntity<List<String>>(tags) {
    };
    return Response.ok(entity).build();
  }

}
