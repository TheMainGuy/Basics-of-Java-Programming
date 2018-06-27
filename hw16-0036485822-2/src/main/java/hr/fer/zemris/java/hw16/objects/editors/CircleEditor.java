package hr.fer.zemris.java.hw16.objects.editors;

import java.awt.Color;

import javax.swing.JTextField;

import hr.fer.zemris.java.hw16.objects.Circle;

public class CircleEditor extends GeometricalObjectEditor {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  private Circle circle;
  private JTextField x;
  private JTextField y;
  private JTextField radius;
  private JTextField r;
  private JTextField g;
  private JTextField b;

  public CircleEditor(Circle circle) {
    this.circle = circle;
    x = new JTextField(circle.getX());
    y = new JTextField(circle.getY());
    radius = new JTextField(circle.getRadius());
    r = new JTextField(circle.getColor().getRed());
    g = new JTextField(circle.getColor().getGreen());
    b = new JTextField(circle.getColor().getBlue());

    this.add(x);
    this.add(y);
    this.add(radius);
    this.add(r);
    this.add(g);
    this.add(b);
  }

  @Override
  public void checkEditing() {
    // TODO Auto-generated method stub

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
