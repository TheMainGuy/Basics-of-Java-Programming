package hr.fer.zemris.java.hw16.objects.editors;

import java.awt.Color;

import javax.swing.JTextField;

import hr.fer.zemris.java.hw16.objects.Line;

public class LineEditor extends GeometricalObjectEditor {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  private Line line;
  private JTextField x;
  private JTextField y;
  private JTextField x2;
  private JTextField y2;
  private JTextField r;
  private JTextField g;
  private JTextField b;

  public LineEditor(Line line) {
    this.line = line;
    x = new JTextField(line.getX());
    y = new JTextField(line.getY());
    x2 = new JTextField(line.getX2());
    y2 = new JTextField(line.getY2());
    r = new JTextField(line.getColor().getRed());
    g = new JTextField(line.getColor().getGreen());
    b = new JTextField(line.getColor().getBlue());

    this.add(x);
    this.add(y);
    this.add(x2);
    this.add(y2);
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
    line.setX(Integer.parseInt(x.getText()));
    line.setY(Integer.parseInt(y.getText()));
    line.setX2(Integer.parseInt(x2.getText()));
    line.setY2(Integer.parseInt(y2.getText()));
    line.setColor(
        new Color(Integer.parseInt(r.getText()), Integer.parseInt(g.getText()), Integer.parseInt(b.getText())));
  }

}
