package hr.fer.zemris.java.zi.z2;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.hw16.jvdraw.model.DrawingModel;
import hr.fer.zemris.java.hw16.jvdraw.model.DrawingModelImpl;
import hr.fer.zemris.java.hw16.jvdraw.objects.Circle;
import hr.fer.zemris.java.hw16.jvdraw.objects.Coordinate;
import hr.fer.zemris.java.hw16.jvdraw.objects.FilledCircle;
import hr.fer.zemris.java.hw16.jvdraw.objects.FilledPolygon;
import hr.fer.zemris.java.hw16.jvdraw.objects.Line;
import hr.fer.zemris.java.hw16.jvdraw.objects.visitors.GeometricalObjectBBCalculator;
import hr.fer.zemris.java.hw16.jvdraw.objects.visitors.GeometricalObjectPainter;

@WebServlet("/crtaj")
public class CrtajServlet extends HttpServlet {
  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    BufferedImage image = null;
    try {
    DrawingModel dw = new DrawingModelImpl();
    List<String> lines = new ArrayList<>();
    String[] parts = req.getParameter("jvdText").split("\n");
    for (String s : parts) {
      lines.add(s);
    }
    
    dw = openDrawingModel(dw, lines);
    
    GeometricalObjectBBCalculator bbcalc = new GeometricalObjectBBCalculator();
    for (int i = 0, n = dw.getSize(); i < n; i++) {
      dw.getObject(i).accept(bbcalc);
    }
    Rectangle box = bbcalc.getBoundingBox();
//    System.out.println(box.x + " " + box.y + " " + box.width + " " + box.height);
    image = new BufferedImage(box.width, box.height, BufferedImage.TYPE_3BYTE_BGR);
    Graphics2D g = image.createGraphics();
    g.translate(-box.x, -box.y);
    g.fillRect(box.x, box.y, box.width, box.height);
    GeometricalObjectPainter painter = new GeometricalObjectPainter(g);
    for (int i = 0, n = dw.getSize(); i < n; i++) {
      dw.getObject(i).accept(painter);
    }
    g.dispose();
//    GeometricalObjectBBCalculator bbcalc = new GeometricalObjectBBCalculator();
//    for (int i = 0, n = dw.getSize(); i < n; i++) {
//      dw.getObject(i).accept(bbcalc);
//    }
//    BufferedImage image = new BufferedImage(bbcalc.getBoundingBox().width, bbcalc.getBoundingBox().height,
//        BufferedImage.TYPE_3BYTE_BGR);
//
//    Graphics2D g2d = (Graphics2D) image.getGraphics();
//    g2d.setColor(Color.white);
//    g2d.fillRect(0, 0, bbcalc.getBoundingBox().width, bbcalc.getBoundingBox().height);
//    draw(g2d, dw);
    } catch (Exception e) {
      req.getRequestDispatcher("/WEB-INF/error.html").forward(req, resp);
      return;
    }
    ByteArrayOutputStream bos = new ByteArrayOutputStream();
    try {
      ImageIO.write(image, "png", bos);
      resp.setContentType("image/png");
      resp.getOutputStream().write(bos.toByteArray());
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public static DrawingModel openDrawingModel(DrawingModel drawingModel, List<String> lines) throws IOException {
    for (int i = 0; i < drawingModel.getSize();) {
      drawingModel.remove(drawingModel.getObject(i));
    }
    for (String line : lines) {
      String[] parts = line.split(" ");
      String fix = parts[parts.length - 1];
      String fix2 = "";
      for(int i = 0; i < fix.length(); i++) {
        if(Character.isDigit(fix.charAt(i))) {
          fix2 += fix.charAt(i);
        }
      }
      parts[parts.length - 1] = fix2;
//          parts[parts.length - 1].substring(0, parts[parts.length - 1].length() - 1);
      if(parts[0].equals("LINE")) {
        drawingModel.add(new Line(Integer.parseInt(parts[1]), Integer.parseInt(parts[2]),
            new Color(Integer.parseInt(parts[5]), Integer.parseInt(parts[6]), Integer.parseInt(parts[7])),
            Integer.parseInt(parts[3]), Integer.parseInt(parts[4])));
      } else if(parts[0].equals("CIRCLE")) {
        drawingModel.add(new Circle(Integer.parseInt(parts[1]), Integer.parseInt(parts[2]),
            new Color(Integer.parseInt(parts[4]), Integer.parseInt(parts[5]), Integer.parseInt(parts[6])),
            Integer.parseInt(parts[3])));
      } else if(parts[0].equals("FCIRCLE")) {
        drawingModel.add(new FilledCircle(Integer.parseInt(parts[1]), Integer.parseInt(parts[2]),
            new Color(Integer.parseInt(parts[4]), Integer.parseInt(parts[5]), Integer.parseInt(parts[6])),
            Integer.parseInt(parts[3]),
            new Color(Integer.parseInt(parts[7]), Integer.parseInt(parts[8]), Integer.parseInt(parts[9]))));
      } else if(parts[0].equals("FPOLY")) {
        List<Coordinate> coos = new ArrayList<>();
        int n = Integer.parseInt(parts[1]);
        for (int i = 0; i < n; i++) {
          coos.add(new Coordinate(Integer.parseInt(parts[2 + i * 2]), Integer.parseInt(parts[3 + i * 2])));
        }
        if(parts.length < 14) {
          throw new IllegalArgumentException("Invalid data");
        }
        
        FilledPolygon fp = new FilledPolygon(0, 0,
            new Color(Integer.parseInt(parts[n * 2 + 2]), Integer.parseInt(parts[n * 2 + 3]),
                Integer.parseInt(parts[n * 2 + 4])),
            coos, new Color(Integer.parseInt(parts[n * 2 + 5]), Integer.parseInt(parts[n * 2 + 6]),
                Integer.parseInt(parts[n * 2 + 7])));
        
        drawingModel.add(fp);
        if(!fp.isConvex()) {
          throw new IllegalArgumentException("Polynom must be convex");
        }
      }
    }
    return drawingModel;
  }

  private void draw(Graphics2D g2d, DrawingModel dw) {

    GeometricalObjectPainter painter = new GeometricalObjectPainter(g2d);
    for (int i = 0, n = dw.getSize(); i < n; i++) {
      dw.getObject(i).accept(painter);
    }
  }
}
