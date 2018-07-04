package hr.fer.zemris.java.hw17.gallery.servlets;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;

public class Util {
  private static int BUFFER_SIZE = 8192;

  public static String IMAGES_PATH = "WEB-INF/slike";

  public static String THUMBNAILS_PATH = "WEB-INF/thumbnails";

  private static int THUMBNAIL_SIZE_HORIZONTAL = 150;

  private static int THUMBNAIL_SIZE_VERTICAL = 150;

  /**
   * Method sends image using given file name and {@link HttpServletResponse}
   * objects. Parameter fileName is used to locate the image. Parameter resp is
   * used to send data using its output stream.
   * 
   * @param resp
   * @param fileName
   * @throws IOException
   */
  public static void sendImage(HttpServletResponse resp, String fileName) throws IOException {
    resp.setContentType(fileName);
    File file = new File(fileName);
    resp.setContentLength((int) file.length());
    FileInputStream inputStream = new FileInputStream(file);
    OutputStream outputStream = resp.getOutputStream();
    byte[] buf = new byte[BUFFER_SIZE];
    int bytesRead = 0;
    while ((bytesRead = inputStream.read(buf)) != -1) {
      outputStream.write(buf, 0, bytesRead);
    }
    outputStream.close();
    inputStream.close();
  }

  /**
   * Method creates thumbnail file from given fileName by using fileName as an
   * absolute path to image which should be converted to thumbnail. Thumbnail will
   * be written to thumbnailFile parameter.
   * 
   * @param fileName
   * @param thumbnailFile
   * @throws IOException 
   */
  public static void createThumbnail(String fileName, File thumbnailFile) throws IOException {
    BufferedImage originalImage = ImageIO.read(new File(fileName));
    BufferedImage thumbnailImage = new BufferedImage(THUMBNAIL_SIZE_HORIZONTAL, THUMBNAIL_SIZE_VERTICAL, originalImage.getType());
    Image scaledImage = originalImage.getScaledInstance(THUMBNAIL_SIZE_HORIZONTAL, THUMBNAIL_SIZE_VERTICAL, Image.SCALE_SMOOTH);
    thumbnailImage.createGraphics().drawImage(scaledImage, 0, 0, null);
    ImageIO.write(thumbnailImage, "jpg", thumbnailFile);
  }

}
