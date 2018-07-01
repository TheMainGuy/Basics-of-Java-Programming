package hr.fer.zemris.java.hw16.jvdraw.objects.editors;

import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JTextField;

import hr.fer.zemris.java.hw16.jvdraw.objects.FilledCircle;

/**
 * Implements editor for {@link FilledCircle}.
 * 
 * @author tin
 *
 */
public class FilledCircleEditor extends GeometricalObjectEditor {
  /**
   * Serial version UID.
   */
  private static final long serialVersionUID = 1L;

  /**
   * Circle which is being edited.
   */
  private FilledCircle filledCircle;
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

  public FilledCircleEditor(FilledCircle filledCircle) {
    this.filledCircle = filledCircle;
    this.setLayout(new GridLayout(0, 2));
    JLabel xText = new JLabel("x");
    x = new JTextField(10);
    x.setText(Integer.toString(filledCircle.getX()));

    JLabel yText = new JLabel("y");
    y = new JTextField(10);
    y.setText(Integer.toString(filledCircle.getY()));

    JLabel rText = new JLabel("radius");
    radius = new JTextField(10);
    radius.setText(Integer.toString(filledCircle.getRadius()));

    JLabel red = new JLabel("red");
    r = new JTextField(10);
    r.setText(Integer.toString(filledCircle.getColor().getRed()));
    JLabel green = new JLabel("green");
    g = new JTextField(10);
    g.setText(Integer.toString(filledCircle.getColor().getGreen()));
    JLabel blue = new JLabel("blue");
    b = new JTextField(10);
    b.setText(Integer.toString(filledCircle.getColor().getBlue()));

    JLabel red2 = new JLabel("fill red");
    r2 = new JTextField(10);
    r2.setText(Integer.toString(filledCircle.getFillColor().getRed()));
    JLabel green2 = new JLabel("fill green");
    g2 = new JTextField(10);
    g2.setText(Integer.toString(filledCircle.getFillColor().getGreen()));
    JLabel blue2 = new JLabel("fill blue");
    b2 = new JTextField(10);
    b2.setText(Integer.toString(filledCircle.getFillColor().getBlue()));

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
    this.add(red2);
    this.add(r2);
    this.add(green2);
    this.add(g2);
    this.add(blue2);
    this.add(b2);
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
      red = Integer.parseInt(r2.getText());
      green = Integer.parseInt(g2.getText());
      blue = Integer.parseInt(b2.getText());
      if(red < 0 || red > 255 || green < 0 || green > 255 || blue < 0 || blue > 255) {
        throw new IllegalArgumentException("Colors must be in [0,255] range.");
      }
    }catch (Exception e) {
      throw new IllegalArgumentException(e);
    }
  }

  @Override
  public void acceptEditing() {
    filledCircle.setX(Integer.parseInt(x.getText()));
    filledCircle.setY(Integer.parseInt(y.getText()));
    filledCircle.setRadius(Integer.parseInt(radius.getText()));
    filledCircle.setColor(
        new Color(Integer.parseInt(r.getText()), Integer.parseInt(g.getText()), Integer.parseInt(b.getText())));
    filledCircle.setFillColor(
        new Color(Integer.parseInt(r2.getText()), Integer.parseInt(g2.getText()), Integer.parseInt(b2.getText())));

  }
}
