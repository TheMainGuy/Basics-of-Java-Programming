package hr.fer.zemris.java.hw17.gallery.rest;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.MessageBodyWriter;
import javax.ws.rs.ext.Provider;

import org.json.JSONObject;

import com.google.gson.reflect.TypeToken;

import hr.fer.zemris.java.hw17.gallery.model.Image;

/**
 * Class used to convert list of images to JSON string. Method isWriteable is
 * used to define which type of data this class can convert to JSON. Method
 * writeTo converts given imageList to JSON string whenever requested by jersey
 * servlet.
 * 
 * @author tin
 *
 */
@Provider
@Produces(MediaType.APPLICATION_JSON)
public class ImageListWriter implements MessageBodyWriter<List<Image>> {

  @Override
  public boolean isWriteable(Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType) {
    return type == ArrayList.class && genericType.equals(new TypeToken<List<Image>>() {
    }.getType());
  }

  @Override
  public void writeTo(List<Image> imageList, Class<?> type, Type genericType, Annotation[] annotations,
      MediaType mediaType, MultivaluedMap<String, Object> httpHeaders, OutputStream entityStream)
      throws IOException, WebApplicationException {
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("[");
    boolean isFirst = true;
    for (Image image : imageList) {
      if(isFirst) {
        isFirst = false;
      } else {
        stringBuilder.append(", ");
      }
      JSONObject imageJSON = new JSONObject();
      imageJSON.put("name", image.getName());
      imageJSON.put("path", image.getPath().getFileName());
      imageJSON.put("tags", image.getTags());
      stringBuilder.append(imageJSON.toString());
    }
    stringBuilder.append("]");
    entityStream.write(stringBuilder.toString().getBytes(StandardCharsets.UTF_8));
  }

}