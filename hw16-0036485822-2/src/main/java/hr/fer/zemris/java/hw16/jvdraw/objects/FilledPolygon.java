package hr.fer.zemris.java.hw16.jvdraw.objects;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import hr.fer.zemris.java.hw16.jvdraw.objects.editors.GeometricalObjectEditor;
import hr.fer.zemris.java.hw16.jvdraw.objects.editors.PolyEditor;
import hr.fer.zemris.java.hw16.jvdraw.objects.visitors.GeometricalObjectVisitor;

public class FilledPolygon extends GeometricalObject {
  private List<Coordinate> coordinates = new ArrayList<>();
  /**
   * Color used to fill.
   */
  private Color fillColor;

  public FilledPolygon(int x, int y, Color color, List<Coordinate> coos, Color fillColor) {
    super(x, y, color);
    this.coordinates = coos;
    this.fillColor = fillColor;
  }

  @Override
  public void accept(GeometricalObjectVisitor v) {
    v.visit(this);
  }

  @Override
  public GeometricalObjectEditor createGeometricalObjectEditor() {
    return new PolyEditor(this);
  }

  /**
   * @return the coordinates
   */
  public List<Coordinate> getCoordinates() {
    return coordinates;
  }

  /**
   * @param coordinates the coordinates to set
   */
  public void setCoordinates(List<Coordinate> coordinates) {
    this.coordinates = coordinates;
    notifyAllListeners(l -> {
      l.geometricalObjectChanged(this);
      return null;
    });
  }

  /**
   * @return the fillColor
   */
  public Color getFillColor() {
    return fillColor;
  }

  /**
   * @param fillColor the fillColor to set
   */
  public void setFillColor(Color fillColor) {
    this.fillColor = fillColor;
    notifyAllListeners(l -> {
      l.geometricalObjectChanged(this);
      return null;
    });
  }

  public boolean isConvex() {
    List<Vector3> vecs = new ArrayList<>();
    int n = coordinates.size();
    for (int i = 0; i < coordinates.size(); i++) {
      int j = i % coordinates.size();
      Vector3 v1 = new Vector3((double) coordinates.get(j).getX(), (double) coordinates.get(j).getY(), 0);
      j = (j + 1) % n;
      Vector3 v2 = new Vector3((double) coordinates.get(j).getX(), (double) coordinates.get(j).getY(), 0);
      j = (j + 1) % n;
      Vector3 v3 = new Vector3((double) coordinates.get(j).getX(), (double) coordinates.get(j).getY(), 0);

      Vector3 v12 = v2.sub(v1);
      Vector3 v13 = v3.sub(v1);

      Vector3 v = v12.cross(v13);
      vecs.add(v);
    }
    int pn = 0;
    for (int i = 0; i < vecs.size(); i++) {
      if(vecs.get(i).getZ() < 0) {
        if(pn == 0) {
          pn = -1;
        } else if(pn == -1) {
          continue;
        } else {
          return false;
        }
      } else if(vecs.get(i).getZ() > 0) {
        if(pn == 0) {
          pn = 1;
        } else if(pn == 1) {
          continue;
        } else {
          return false;
        }
      }
    }
    
    return true;

  }
  
  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder("Polygon ");
    for(int i = 0; i < coordinates.size(); i++) {
      sb.append(coordinates.get(i).getX()).append(" ").append(coordinates.get(i).getX()).append(" ");
    }
    String colorString = String.format("%02X%02X%02X ", getColor().getRed(), getColor().getGreen(), getColor().getBlue());
    
    String colorString2 = String.format("%02X%02X%02X", fillColor.getRed(), fillColor.getGreen(), fillColor.getBlue());
    sb.append("#").append(colorString).append("#").append(colorString2);
    return sb.toString();
  }

}
