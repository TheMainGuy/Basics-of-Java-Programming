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

import hr.fer.zemris.java.hw17.gallery.model.Image;

@Provider
@Produces(MediaType.APPLICATION_JSON)
public class ImageListWriter implements MessageBodyWriter<List<Image>> {

  @Override
  public boolean isWriteable(Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType) {
    return type == ArrayList.class;
  }

  @Override
  public void writeTo(List<Image> imageList, Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType,
      MultivaluedMap<String, Object> httpHeaders, OutputStream entityStream)
      throws IOException, WebApplicationException {
    Gson gson = new Gson();
    Type listType = new TypeToken<List<Image>>() {}.getType();
    entityStream.write(gson.toJson(imageList, listType).toString().getBytes(StandardCharsets.UTF_8));

  }

}