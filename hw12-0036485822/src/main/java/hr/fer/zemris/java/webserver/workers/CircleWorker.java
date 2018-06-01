package hr.fer.zemris.java.webserver.workers;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

import hr.fer.zemris.java.webserver.IWebWorker;
import hr.fer.zemris.java.webserver.RequestContext;

/**
 * Creates 200 x 200 image and draws white circle on it. Then write that image
 * to socket using given {@link RequestContext} object.
 * 
 * @author tin
 *
 */
public class CircleWorker implements IWebWorker {

  @Override
  public void processRequest(RequestContext context) throws Exception {
    BufferedImage bim = new BufferedImage(200, 200, BufferedImage.TYPE_3BYTE_BGR);
    Graphics2D g2d = bim.createGraphics();
    g2d.drawOval(20, 20, 30, 30);
    g2d.dispose();
    ByteArrayOutputStream bos = new ByteArrayOutputStream();
    try {
      ImageIO.write(bim, "png", bos);
      context.setMimeType("image/gif");
      context.write(bos.toByteArray());
    } catch (IOException e) {
      e.printStackTrace();
    }

  }

}
