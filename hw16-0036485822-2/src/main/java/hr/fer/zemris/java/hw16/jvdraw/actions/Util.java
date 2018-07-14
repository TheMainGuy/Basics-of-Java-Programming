package hr.fer.zemris.java.hw16.jvdraw.actions;

import java.awt.Color;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import hr.fer.zemris.java.hw16.jvdraw.model.DrawingModel;
import hr.fer.zemris.java.hw16.jvdraw.objects.Circle;
import hr.fer.zemris.java.hw16.jvdraw.objects.Coordinate;
import hr.fer.zemris.java.hw16.jvdraw.objects.FilledCircle;
import hr.fer.zemris.java.hw16.jvdraw.objects.FilledPolygon;
import hr.fer.zemris.java.hw16.jvdraw.objects.GeometricalObject;
import hr.fer.zemris.java.hw16.jvdraw.objects.Line;

/**
 * Helper class used to ease saving and opening .jvd files. Supports
 * saveDrawingModel and openDrawingModel methods which handle saving and opening
 * .jvd files. Also stores path of the current drawing.
 * 
 * @author tin
 *
 */
public class Util {
  /**
   * Path of the current drawing.
   */
  public static Path currentDrawingPath = null;

  /**
   * Saves drawing model to given path. Drawing model is saved by converting each
   * object to one line. First element in each line defines type of object: LINE,
   * CIRCLE or FCIRCLE.
   * 
   * <pre>
   * Line has format:
   * LINE x y x2 y2 red green blue
   * with x and y pairs being start and end coordinates and red, green and blue being 
   * color components in RGB format.
   * 
   * Circle has format:
   * CIRCLE x y radius red green blue
   * with x and y being center coordinates, radius being radius and red, green and blue 
   * being color components in RGB format.
   * 
   * Filled circle has format:
   * FCIRCLE x y radius red green blue red2 green2 blue2
   * with x and y being center coordinates, radius being radius, red, green and blue 
   * being color components in RGB format and red2, green2 and blue2 
   * being fill color components in RGB format
   * </pre>
   *
   * @param drawingModel drawing model
   * @param path path to which the file will be saved
   * @throws IOException if there is problem with writing to file
   */
  public static void saveDrawingModel(DrawingModel drawingModel, Path path) throws IOException {
    List<String> lines = new ArrayList<>();
    for (int i = 0, n = drawingModel.getSize(); i < n; i++) {
      GeometricalObject object = drawingModel.getObject(i);
      if(object instanceof Line) {
        Line line = (Line) object;
        lines.add(new String("LINE " + line.getX() + " " + line.getY() + " " + line.getX2() + " " + line.getY2() + " "
            + line.getColor().getRed() + " " + line.getColor().getGreen() + " " + line.getColor().getBlue()));
      } else if(object instanceof FilledCircle) {
        FilledCircle fc = (FilledCircle) object;
        lines.add(new String("FCIRCLE " + fc.getX() + " " + fc.getY() + " " + fc.getRadius() + " "
            + fc.getColor().getRed() + " " + fc.getColor().getGreen() + " " + fc.getColor().getBlue() + " "
            + fc.getFillColor().getRed() + " " + fc.getFillColor().getGreen() + " " + fc.getFillColor().getBlue()));
      } else if(object instanceof Circle) {
        Circle c = (Circle) object;
        lines.add(new String("CIRCLE " + c.getX() + " " + c.getY() + " " + c.getRadius() + " " + c.getColor().getRed()
            + " " + c.getColor().getGreen() + " " + c.getColor().getBlue()));
      } else if(object instanceof FilledPolygon) {
        FilledPolygon fp = (FilledPolygon) object;
        StringBuilder sb = new StringBuilder("FPOLY ");
        sb.append(fp.getCoordinates().size()).append(" ");
        for (int j = 0; j < fp.getCoordinates().size(); j++) {
          sb.append(fp.getCoordinates().get(j).getX()).append(" ").append(fp.getCoordinates().get(j).getY())
              .append(" ");
        }
        sb.append(fp.getColor().getRed() + " " + fp.getColor().getGreen() + " " + fp.getColor().getBlue() + " "
            + fp.getFillColor().getRed() + " " + fp.getFillColor().getGreen() + " " + fp.getFillColor().getBlue());
        lines.add(sb.toString());
      }
    }

    if(!path.toString().endsWith(".jvd")) {
      path = Paths.get(path.toString() + ".jvd");
    }
    Files.write(path, lines);
  }

  /**
   * Opens .jvd file and parses its content to recreate drawing. Uses following
   * format to determine geometrical objects.
   * 
   * <pre>
   * Line has format:
   * LINE x y x2 y2 red green blue
   * with x and y pairs being start and end coordinates and red, green and blue being 
   * color components in RGB format.
   * 
   * Circle has format:
   * CIRCLE x y radius red green blue
   * with x and y being center coordinates, radius being radius and red, green and blue 
   * being color components in RGB format.
   * 
   * Filled circle has format:
   * FCIRCLE x y radius red green blue red2 green2 blue2
   * with x and y being center coordinates, radius being radius, red, green and blue 
   * being color components in RGB format and red2, green2 and blue2 
   * being fill color components in RGB format
   * </pre>
   * 
   * @param drawingModel drawing model
   * @param path path to file
   * @throws IOException if there is problem with reading from file
   */
  public static void openDrawingModel(DrawingModel drawingModel, Path path) throws IOException {
    List<String> lines = Files.readAllLines(path);
    for (int i = 0; i < drawingModel.getSize();) {
      drawingModel.remove(drawingModel.getObject(i));
    }
    for (String line : lines) {
      String[] parts = line.split(" ");
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
        drawingModel.add(new FilledPolygon(0, 0,
            new Color(Integer.parseInt(parts[n * 2 + 2]), Integer.parseInt(parts[n * 2 + 3]), Integer.parseInt(parts[n * 2 + 4])), coos,
            new Color(Integer.parseInt(parts[n * 2 + 5]), Integer.parseInt(parts[n * 2 + 6]), Integer.parseInt(parts[n * 2 + 7]))));
      }
    }
  }
}
