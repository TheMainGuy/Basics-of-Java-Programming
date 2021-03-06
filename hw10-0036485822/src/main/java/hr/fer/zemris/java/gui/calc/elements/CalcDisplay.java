package hr.fer.zemris.java.gui.calc.elements;

import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import hr.fer.zemris.java.gui.calc.CalcModel;
import hr.fer.zemris.java.gui.calc.CalcValueListener;

/**
 * Class implements calculator display.
 * 
 * @author tin
 *
 */
public class CalcDisplay extends JLabel implements CalcValueListener {
  /**
   * Serial version UID.
   */
  private static final long serialVersionUID = 1L;

  /**
   * Constructor.
   */
  public CalcDisplay() {
    setOpaque(true);
    setBorder(BorderFactory.createEtchedBorder());
    setBackground(Color.YELLOW);
    setHorizontalAlignment(SwingConstants.RIGHT);
    setText("0");
  }

  @Override
  public void valueChanged(CalcModel model) {
    setText(model.toString());
  }

}
