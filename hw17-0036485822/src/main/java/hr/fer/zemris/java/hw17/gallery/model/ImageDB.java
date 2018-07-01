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

public class ImageDB {
  private static String IMAGES_LOCATION = "./src/main/webapp/WEB-INF/slike";
  
  private static List<Image> images = new ArrayList<>();

  public static int getSize() {
    return images.size();
  }

  public static Image getElement(int index) {
    return images.get(index);
  }

  public static void add(Image image) {
    images.add(image);
  }
  
  public static List<Image> getImages(){
    return images;
  }
  
  public static List<String> getTags(){
    Set<String> tags = new HashSet<>();
    
    for(Image image : images) {
      for(String tag : image.getTags()) {
        tags.add(tag);
      }
    }
    
    return new ArrayList<>(tags);
  }
  
  public static void fillFromFile(Path path) {
    List<String> lines = null;
    try {
      lines = Files.readAllLines(path);
    } catch (IOException e) {
      System.out.println("Problem with reading opisnik.txt file.");
    }
    
    for(int i = 0, n = lines.size(); i < n ; i++) {
      String imageFile = lines.get(i++);
      String imageName = lines.get(i++);
      String[] imageTags = lines.get(i).split(", ");
      images.add(new Image(Paths.get(IMAGES_LOCATION).resolve(Paths.get(imageFile)).toAbsolutePath(), Arrays.asList(imageTags), imageName));
    }
  }
}
