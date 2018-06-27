package hr.fer.zemris.java.hw16.objects.editors;

import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JTextField;

import hr.fer.zemris.java.hw16.objects.FilledCircle;

public class FilledCircleEditor extends GeometricalObjectEditor {
  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  private FilledCircle filledCircle;
  private JTextField x;
  private JTextField y;
  private JTextField radius;
  private JTextField r;
  private JTextField g;
  private JTextField b;
  private JTextField r2;
  private JTextField g2;
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
    // TODO Auto-generated method stub

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
