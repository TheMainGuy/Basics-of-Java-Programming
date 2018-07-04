package hr.fer.zemris.java.hw17.gallery.model;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.ServletContextEvent;

/**
 * Simple image database which stores list of images. Implements method
 * fillFromFile which fills list of images from give file and stores them in
 * relative path. Acts as an adapter towards images list, but offers getImages
 * method which returns the list itself.
 * 
 * @author tin
 *
 */
public class ImageDB {
  /**
   * Location of images on server.
   */
  private static String IMAGES_LOCATION = "/WEB-INF/slike";

  /**
   * List of images.
   */
  private static List<Image> images = new ArrayList<>();

  /**
   * Returns number of images.
   * 
   * @return number of images
   */
  public static int getSize() {
    return images.size();
  }

  /**
   * Returns element at given index from list of images.
   * 
   * @param index index used to get image from list
   * @return image located at index index
   */
  public static Image getElement(int index) {
    return images.get(index);
  }

  /**
   * Adds image to list.
   * 
   * @param image image to be added
   */
  public static void add(Image image) {
    images.add(image);
  }

  /**
   * Returns list of images.
   * 
   * @return list of images
   */
  public static List<Image> getImages() {
    return images;
  }

  /**
   * Returns list of tags. Tags are collected from all images stored in list. It
   * will not return any duplicates.
   * 
   * @return list of all tags
   */
  public static List<String> getTags() {
    Set<String> tags = new HashSet<>();

    for (Image image : images) {
      for (String tag : image.getTags()) {
        tags.add(tag);
      }
    }

    return new ArrayList<>(tags);
  }

  /**
   * Fills list of images from given path and sce. Path is used to determine file
   * describing all images. {@link ServletContextEvent} object is used to
   * determine path to images.
   * 
   * @param path path to file describing all images
   * @param sce servlet context event used to determine context path
   */
  public static void fillFromFile(Path path, ServletContextEvent sce) {
    List<String> lines = null;
    try {
      lines = Files.readAllLines(path);
    } catch (IOException e) {
      System.out.println("Problem with reading opisnik.txt file.");
    }

    Path contextPath = Paths.get(sce.getServletContext().getRealPath(IMAGES_LOCATION));
    for (int i = 0, n = lines.size(); i < n; i++) {
      String imageFile = lines.get(i++);
      String imageName = lines.get(i++);
      String[] imageTags = lines.get(i).split(", ");
      images.add(new Image(contextPath.resolve(Paths.get(imageFile))
          .toAbsolutePath(), Arrays.asList(imageTags), imageName));
    }
  }
}
