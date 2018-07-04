package hr.fer.zemris.java.hw17.gallery.rest;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;

import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.MessageBodyWriter;
import javax.ws.rs.ext.Provider;

import org.json.JSONObject;

import hr.fer.zemris.java.hw17.gallery.model.Image;

@Provider
@Produces(MediaType.APPLICATION_JSON)
public class ImageWriter implements MessageBodyWriter<Image> {

  @Override
  public boolean isWriteable(Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType) {
    return type == Image.class;
  }

  @Override
  public void writeTo(Image image, Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType,
      MultivaluedMap<String, Object> httpHeaders, OutputStream entityStream)
      throws IOException, WebApplicationException {
    JSONObject imageJSON = new JSONObject();
    imageJSON.put("name", image.getName());
    imageJSON.put("path", image.getPath().getFileName());
    imageJSON.put("tags", image.getTags());
    entityStream.write(imageJSON.toString().getBytes(StandardCharsets.UTF_8));
  }

}
