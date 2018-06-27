package hr.fer.zemris.java.hw16.objects.editors;

import java.awt.Color;

import javax.swing.JTextField;

import hr.fer.zemris.java.hw16.objects.FilledCircle;

public class FilledCircleEditor extends GeometricalObjectEditor{
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
    x = new JTextField(filledCircle.getX());
    y = new JTextField(filledCircle.getY());
    radius = new JTextField(filledCircle.getRadius());
    r = new JTextField(filledCircle.getColor().getRed());
    g = new JTextField(filledCircle.getColor().getGreen());
    b = new JTextField(filledCircle.getColor().getBlue());
    r2 = new JTextField(filledCircle.getFillColor().getRed());
    g2 = new JTextField(filledCircle.getFillColor().getGreen());
    b2 = new JTextField(filledCircle.getFillColor().getBlue());

    this.add(x);
    this.add(y);
    this.add(radius);
    this.add(r);
    this.add(g);
    this.add(b);
    this.add(r2);
    this.add(g2);
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
