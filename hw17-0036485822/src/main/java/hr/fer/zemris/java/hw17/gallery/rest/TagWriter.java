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

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

/**
 * Class used to convert tags to JSON string. Method isWriteable is used to
 * define which type of data this class can convert to JSON. Method writeTo
 * converts given list of tags to JSON string whenever requested by jersey
 * servlet.
 * 
 * @author tin
 *
 */
@Provider
@Produces(MediaType.APPLICATION_JSON)
public class TagWriter implements MessageBodyWriter<List<String>> {

  @Override
  public boolean isWriteable(Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType) {
    return type == ArrayList.class && genericType.equals(new TypeToken<List<String>>() {
    }.getType());
  }

  @Override
  public void writeTo(List<String> tags, Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType,
      MultivaluedMap<String, Object> httpHeaders, OutputStream entityStream)
      throws IOException, WebApplicationException {
    Gson gson = new Gson();
    Type listType = new TypeToken<List<String>>() {
    }.getType();
    entityStream.write(gson.toJson(tags, listType).toString().getBytes(StandardCharsets.UTF_8));

  }

}