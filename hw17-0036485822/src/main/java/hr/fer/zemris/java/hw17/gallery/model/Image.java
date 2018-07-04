package hr.fer.zemris.java.hw17.gallery.model;

import java.nio.file.Path;
import java.util.List;

/**
 * Represents one image and its information. Contains image path, tags and name.
 * Implements methods for getting image tags, name, path and the image itself.
 * 
 * @author tin
 *
 */
public class Image {
  /**
   * Image path.
   */
  private Path path;
  
  /**
   * List of image tags.
   */
  private List<String> tags;
  
  /**
   * Image name.
   */
  private String name;

  public Image(Path path, List<String> tags, String name) {
    this.path = path;
    this.tags = tags;
    this.name = name;
  }

  /**
   * @return the path
   */
  public Path getPath() {
    return path;
  }

  /**
   * @return the tags
   */
  public List<String> getTags() {
    return tags;
  }

  /**
   * @return the name
   */
  public String getName() {
    return name;
  }

}
