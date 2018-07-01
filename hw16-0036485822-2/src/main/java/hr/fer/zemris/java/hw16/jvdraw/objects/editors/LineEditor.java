package hr.fer.zemris.java.hw16.jvdraw.objects.editors;

import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JTextField;

import hr.fer.zemris.java.hw16.jvdraw.objects.Line;

/**
 * Implements editor for {@link Line}.
 * 
 * @author tin
 *
 */
public class LineEditor extends GeometricalObjectEditor {

  /**
   * Serial version UID.
   */
  private static final long serialVersionUID = 1L;

  /**
   * Line which is being edited.
   */
  private Line line;

  /**
   * x start coordinate
   */
  private JTextField x;

  /**
   * y start coordinate
   */
  private JTextField y;

  /**
   * x end coordinate
   */
  private JTextField x2;

  /**
   * y end coordinate
   */
  private JTextField y2;
  /**
   * Red component of line color.
   */
  private JTextField r;

  /**
   * Green component of line color.
   */
  private JTextField g;

  /**
   * Blue component of line color.
   */
  private JTextField b;

  /**
   * Constructor.
   * 
   * @param line which will be edited
   */
  public LineEditor(Line line) {
    this.line = line;
    this.setLayout(new GridLayout(0, 2));
    JLabel xText = new JLabel("x");
    x = new JTextField(10);
    x.setText(Integer.toString(line.getX()));

    JLabel yText = new JLabel("y");
    y = new JTextField(10);
    y.setText(Integer.toString(line.getY()));

    JLabel x2Text = new JLabel("x2");
    x2 = new JTextField(10);
    x2.setText(Integer.toString(line.getX2()));

    JLabel y2Text = new JLabel("y2");
    y2 = new JTextField(10);
    y2.setText(Integer.toString(line.getY2()));

    JLabel red = new JLabel("red");
    r = new JTextField(10);
    r.setText(Integer.toString(line.getColor().getRed()));
    JLabel green = new JLabel("green");
    g = new JTextField(10);
    g.setText(Integer.toString(line.getColor().getGreen()));
    JLabel blue = new JLabel("blue");
    b = new JTextField(10);
    b.setText(Integer.toString(line.getColor().getBlue()));

    this.add(xText);
    this.add(x);
    this.add(yText);
    this.add(y);
    this.add(x2Text);
    this.add(x2);
    this.add(y2Text);
    this.add(y2);

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
      Integer.parseInt(x2.getText());
      Integer.parseInt(y2.getText());
      int red = Integer.parseInt(r.getText());
      int green = Integer.parseInt(g.getText());
      int blue = Integer.parseInt(b.getText());
      if(red < 0 || red > 255 || green < 0 || green > 255 || blue < 0 || blue > 255) {
        throw new IllegalArgumentException("Colors must be in [0,255] range.");
      }
    } catch (Exception e) {
      throw new IllegalArgumentException(e);
    }
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
