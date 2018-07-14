package hr.fer.zemris.java.hw16.jvdraw.objects.editors;

import java.awt.Color;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JTextField;

import hr.fer.zemris.java.hw16.jvdraw.objects.Coordinate;
import hr.fer.zemris.java.hw16.jvdraw.objects.FilledCircle;
import hr.fer.zemris.java.hw16.jvdraw.objects.FilledPolygon;

/**
 * Implements editor for {@link FilledCircle}.
 * 
 * @author tin
 *
 */
public class PolyEditor extends GeometricalObjectEditor {
  /**
   * Serial version UID.
   */
  private static final long serialVersionUID = 1L;

  /**
   * Circle which is being edited.
   */
  private FilledPolygon fp;
  
  private List<JTextField> coos = new ArrayList<>();
  /**
   * Red component of circle color.
   */
  private JTextField r;

  /**
   * Green component of circle color.
   */
  private JTextField g;

  /**
   * Blue component of circle color.
   */
  private JTextField b;

  /**
   * Red component of circle fill color.
   */
  private JTextField r2;

  /**
   * Green component of circle fill color.
   */
  private JTextField g2;

  /**
   * Blue component of circle fill color.
   */
  private JTextField b2;

  public PolyEditor(FilledPolygon fp) {
    this.fp = fp;
    this.setLayout(new GridLayout(0, 2));
    
    for(int i = 0; i < fp.getCoordinates().size(); i++) {
      Coordinate co = fp.getCoordinates().get(i);
      JLabel tlx = new JLabel("Coordinate x" + String.valueOf(i) + ": ");
      JTextField tfx = new JTextField(10);
      tfx.setText(String.valueOf(co.getX()));
      coos.add(tfx);
      
      JLabel tly = new JLabel("Coordinate y" + String.valueOf(i) + ": ");
      JTextField tfy = new JTextField(10);
      tfy.setText(String.valueOf(co.getY()));
      coos.add(tfy);
      this.add(tlx);
      this.add(tfx);
      this.add(tly);
      this.add(tfy);
    }

    JLabel red = new JLabel("red");
    r = new JTextField(10);
    r.setText(Integer.toString(fp.getColor().getRed()));
    JLabel green = new JLabel("green");
    g = new JTextField(10);
    g.setText(Integer.toString(fp.getColor().getGreen()));
    JLabel blue = new JLabel("blue");
    b = new JTextField(10);
    b.setText(Integer.toString(fp.getColor().getBlue()));

    JLabel red2 = new JLabel("fill red");
    r2 = new JTextField(10);
    r2.setText(Integer.toString(fp.getFillColor().getRed()));
    JLabel green2 = new JLabel("fill green");
    g2 = new JTextField(10);
    g2.setText(Integer.toString(fp.getFillColor().getGreen()));
    JLabel blue2 = new JLabel("fill blue");
    b2 = new JTextField(10);
    b2.setText(Integer.toString(fp.getFillColor().getBlue()));

    this.add(red);
    this.add(r);
    this.add(green);
    this.add(g);
    this.add(blue);
    this.add(b);
    this.add(red2);
    this.add(r2);
    this.add(green2);
    this.add(g2);
    this.add(blue2);
    this.add(b2);
  }

  @Override
  public void checkEditing() {
    List<Coordinate> coos2 = new ArrayList<>();
    try {
      for(int i = 0; i < coos.size() / 2; i++) {
        int x = Integer.parseInt(coos.get(i * 2).getText());
        int y = Integer.parseInt(coos.get(i * 2 + 1).getText());
        coos2.add(new Coordinate(x, y));
//        if(i != 0) {
//          if(Math.sqrt(Math.pow(x - coos2.get(i).getX(), 2) + Math.pow(y - coos2.get(i).getY(), 2)) < 3){
//            throw new IllegalArgumentException("Polynom coordinates too close.");
//          }
//        }
      }
      int red = Integer.parseInt(r.getText());
      int green = Integer.parseInt(g.getText());
      int blue = Integer.parseInt(b.getText());
      if(red < 0 || red > 255 || green < 0 || green > 255 || blue < 0 || blue > 255) {
        throw new IllegalArgumentException("Colors must be in [0,255] range.");
      }
      red = Integer.parseInt(r2.getText());
      green = Integer.parseInt(g2.getText());
      blue = Integer.parseInt(b2.getText());
      if(red < 0 || red > 255 || green < 0 || green > 255 || blue < 0 || blue > 255) {
        throw new IllegalArgumentException("Colors must be in [0,255] range.");
      }
      FilledPolygon fp2 = new FilledPolygon(fp.getX(), fp.getY(), fp.getColor(), coos2, fp.getFillColor());
      if(!fp2.isConvex()) {
        throw new IllegalArgumentException("Polinom not convex");
      }
    }catch (Exception e) {
      throw new IllegalArgumentException(e);
    }
  }

  @Override
  public void acceptEditing() {
    for(int i = 0; i < coos.size()/2; i++) {
      fp.getCoordinates().get(i).setX(Integer.parseInt(coos.get(i * 2).getText()));
      fp.getCoordinates().get(i).setY(Integer.parseInt(coos.get(i * 2 + 1).getText()));

    }
    fp.setColor(
        new Color(Integer.parseInt(r.getText()), Integer.parseInt(g.getText()), Integer.parseInt(b.getText())));
    fp.setFillColor(
        new Color(Integer.parseInt(r2.getText()), Integer.parseInt(g2.getText()), Integer.parseInt(b2.getText())));

  }
}
