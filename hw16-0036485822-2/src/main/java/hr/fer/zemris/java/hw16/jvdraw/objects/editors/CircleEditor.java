package hr.fer.zemris.java.hw16.jvdraw.objects.editors;

import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JTextField;

import hr.fer.zemris.java.hw16.jvdraw.objects.Circle;

/**
 * Implements editor for {@link Circle}.
 * 
 * @author tin
 *
 */
public class CircleEditor extends GeometricalObjectEditor {

  /**
   * Serial version UID.
   */
  private static final long serialVersionUID = 1L;

  /**
   * Circle which is being edited.
   */
  private Circle circle;
  
  /**
   * x center coordinate
   */
  private JTextField x;
  
  /**
   * y center coordinate
   */
  private JTextField y;
  
  /**
   * radius
   */
  private JTextField radius;
  
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
   * Constructor.
   * 
   * @param circle circle which will be edited
   */
  public CircleEditor(Circle circle) {
    this.circle = circle;
    this.setLayout(new GridLayout(0, 2));
    JLabel xText = new JLabel("x");
    x = new JTextField(10);
    x.setText(Integer.toString(circle.getX()));

    JLabel yText = new JLabel("y");
    y = new JTextField(10);
    y.setText(Integer.toString(circle.getY()));

    JLabel rText = new JLabel("radius");
    radius = new JTextField(10);
    radius.setText(Integer.toString(circle.getRadius()));

    JLabel red = new JLabel("red");
    r = new JTextField(10);
    r.setText(Integer.toString(circle.getColor().getRed()));
    JLabel green = new JLabel("green");
    g = new JTextField(10);
    g.setText(Integer.toString(circle.getColor().getGreen()));
    JLabel blue = new JLabel("blue");
    b = new JTextField(10);
    b.setText(Integer.toString(circle.getColor().getBlue()));

    this.add(xText);
    this.add(x);
    this.add(yText);
    this.add(y);
    this.add(rText);
    this.add(radius);

    this.add(red);
    this.add(r);
    this.add(green);
    this.add(g);
    this.add(blue);
    this.add(b);
  }

  @Override
  public void checkEditing() {
    try {
      Integer.parseInt(x.getText());
      Integer.parseInt(y.getText());
      Integer.parseInt(radius.getText());
      int red = Integer.parseInt(r.getText());
      int green = Integer.parseInt(g.getText());
      int blue = Integer.parseInt(b.getText());
      if(red < 0 || red > 255 || green < 0 || green > 255 || blue < 0 || blue > 255) {
        throw new IllegalArgumentException("Colors must be in [0,255] range.");
      }
    }catch (Exception e) {
      throw new IllegalArgumentException(e);
    }
  }

  @Override
  public void acceptEditing() {
    circle.setX(Integer.parseInt(x.getText()));
    circle.setY(Integer.parseInt(y.getText()));
    circle.setRadius(Integer.parseInt(radius.getText()));
    circle.setColor(
        new Color(Integer.parseInt(r.getText()), Integer.parseInt(g.getText()), Integer.parseInt(b.getText())));

  }

}
