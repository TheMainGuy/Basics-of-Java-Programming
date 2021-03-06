package hr.fer.zemris.java.gui.calc;

import java.awt.Container;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

import hr.fer.zemris.java.gui.calc.elements.CalcBinaryOperationButton;
import hr.fer.zemris.java.gui.calc.elements.CalcDisplay;
import hr.fer.zemris.java.gui.calc.elements.CalcNumberButton;
import hr.fer.zemris.java.gui.calc.elements.CalcReverseButton;
import hr.fer.zemris.java.gui.calc.elements.CalcUnaryOperationButton;
import hr.fer.zemris.java.gui.layouts.CalcLayout;
import hr.fer.zemris.java.gui.layouts.RCPosition;

public class Calculator {
  private static Stack<Double> stack = new Stack<>();

  /**
   * Method which is called when program starts.
   * 
   * @param args command line arguments.
   */
  public static void main(String[] args) {
    SwingUtilities.invokeLater(() -> {
      JFrame frame = new StandardCalculator();
      frame.pack();
      frame.setVisible(true);
    });
  }

  /**
   * Class which draws calculator buttons using {@link CalcLayout} and sets action
   * listeners to them.
   * 
   * @author tin
   *
   */
  private static class StandardCalculator extends JFrame {
    /**
     * Serial version UID.
     */
    private static final long serialVersionUID = 1L;

    /**
     * Constructor.
     */
    public StandardCalculator() {
      setLocation(20, 50);
      setTitle("Calculator.");
      setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
      initGUI();
    }

    /**
     * Initializes GUI components.
     */
    private void initGUI() {
      CalcModel calculator = new CalcModelImpl();
      Container p = getContentPane();
      p.setLayout(new CalcLayout(4));

      JLabel display = new CalcDisplay();
      calculator.addCalcValueListener((CalcValueListener) display);
      p.add(display, new RCPosition(1, 1));

      JButton equals = new JButton("=");
      equals.addActionListener(a -> {
        equalsAction(display, calculator);
      });
      p.add(equals, new RCPosition(1, 6));

      JButton clear = new JButton("clr");
      clear.addActionListener(a -> {
        calculator.clear();
      });
      p.add(clear, new RCPosition(1, 7));

      addNumberButtons(p, calculator);

      addUnaryOperators(p, calculator);
      addBinaryOperators(p, calculator);
      addUtilityButtons(p, calculator);

      p.setVisible(true);
    }

    /**
     * Method adds buttons for utilities: ., +/-, push, pop, res and inv.
     * 
     * @param p container in which numbers will be added
     * @param calculator {@link CalcModel} object in which information will be
     *          stored upon pressing buttons
     */
    private void addUtilityButtons(Container p, CalcModel calculator) {
      JButton res = new JButton("res");
      res.addActionListener(a -> calculator.clearAll());
      p.add(res, new RCPosition(2, 7));

      JButton push = new JButton("push");
      push.addActionListener(a -> {
        stack.push(calculator.getValue());
      });
      p.add(push, new RCPosition(3, 7));

      JButton pop = new JButton("pop");
      pop.addActionListener(a -> {
        try {
          calculator.setValue(stack.pop());
        } catch (Exception e) {
          JOptionPane.showMessageDialog(this, "Empty stack!", "Error", JOptionPane.ERROR_MESSAGE);
        }

      });
      p.add(pop, new RCPosition(4, 7));

      JButton sign = new JButton("+/-");
      sign.addActionListener(a -> calculator.swapSign());
      p.add(sign, new RCPosition(5, 4));

      JButton decimalSeparator = new JButton(".");
      decimalSeparator.addActionListener(a -> calculator.insertDecimalPoint());
      p.add(decimalSeparator, new RCPosition(5, 5));
    }

    /**
     * Method adds buttons for unary operations: 1/x, log, ln, sin, cos, tan and
     * ctg.
     * 
     * @param p container in which numbers will be added
     * @param calculator {@link CalcModel} object in which information will be
     *          stored upon pressing buttons
     */
    private void addUnaryOperators(Container p, CalcModel calculator) {

      CalcUnaryOperationButton inverse = new CalcUnaryOperationButton("1/x", "1/x", x -> 1 / x, x -> 1 / x, calculator);
      p.add(inverse, new RCPosition(2, 1));

      CalcUnaryOperationButton log = new CalcUnaryOperationButton("log", "10^", x -> Math.log10(x),
          x -> Math.pow(10, x), calculator);
      p.add(log, new RCPosition(3, 1));

      CalcUnaryOperationButton ln = new CalcUnaryOperationButton("ln", "e^", x -> Math.log(x), x -> Math.pow(Math.E, x),
          calculator);
      p.add(ln, new RCPosition(4, 1));

      CalcUnaryOperationButton sin = new CalcUnaryOperationButton("sin", "asin", x -> Math.sin(x), x -> Math.asin(x),
          calculator);
      p.add(sin, new RCPosition(2, 2));

      CalcUnaryOperationButton cos = new CalcUnaryOperationButton("cos", "acos", x -> Math.cos(x), x -> Math.acos(x),
          calculator);
      p.add(cos, new RCPosition(3, 2));

      CalcUnaryOperationButton tan = new CalcUnaryOperationButton("tan", "atan", x -> Math.tan(x), x -> Math.atan(x),
          calculator);
      p.add(tan, new RCPosition(4, 2));

      CalcUnaryOperationButton ctg = new CalcUnaryOperationButton("ctg", "actg", x -> 1 / Math.tan(x),
          x -> 1 / Math.atan(x), calculator);
      p.add(ctg, new RCPosition(5, 2));
      List<CalcUnaryOperationButton> operations = new ArrayList<>();
      operations.add(inverse);
      operations.add(log);
      operations.add(ln);
      operations.add(sin);
      operations.add(cos);
      operations.add(tan);
      operations.add(ctg);

      CalcReverseButton power = new CalcReverseButton("x^n", "root", (x, n) -> Math.pow(x, n),
          (x, n) -> Math.pow(x, 1 / n), calculator);
      p.add(power, new RCPosition(5, 1));

      JCheckBox reverse = new JCheckBox("inv");
      reverse.addActionListener(a -> {
        for (CalcUnaryOperationButton operation : operations) {
          operation.reverse();
        }
        power.reverse();
      });

      p.add(reverse, new RCPosition(5, 7));
    }

    /**
     * Method adds buttons for binary operations: x^n, *, /, + and -. ctg.
     * 
     * @param p container in which numbers will be added
     * @param calculator {@link CalcModel} object in which information will be
     *          stored upon pressing buttons
     */
    private void addBinaryOperators(Container p, CalcModel calculator) {
      CalcBinaryOperationButton add = new CalcBinaryOperationButton("+", (x, y) -> x + y, calculator);
      p.add(add, new RCPosition(5, 6));

      CalcBinaryOperationButton subtract = new CalcBinaryOperationButton("-", (x, y) -> x - y, calculator);
      p.add(subtract, new RCPosition(4, 6));

      CalcBinaryOperationButton multiply = new CalcBinaryOperationButton("*", (x, y) -> x * y, calculator);
      p.add(multiply, new RCPosition(3, 6));

      CalcBinaryOperationButton divide = new CalcBinaryOperationButton("/", (x, y) -> x / y, calculator);
      p.add(divide, new RCPosition(2, 6));
    }

    /**
     * Method adds buttons from 0-9 and their corresponding action listeners to
     * given {@link Container}.
     * 
     * @param p container on which buttons will be added
     * @param calculator {@link CalcModel} object in which information will be
     *          stored upon pressing buttons
     */
    private void addNumberButtons(Container p, CalcModel calculator) {
      int buttonCounter = 0;
      for (int i = 5; i >= 2; i--) {
        for (int j = 3; j <= 5; j++) {
          if(i == 5 && j > 3) {
            break;
          }
          CalcNumberButton numberButton = new CalcNumberButton(buttonCounter, calculator);
          p.add(numberButton, new RCPosition(i, j));
          buttonCounter++;
        }
      }
    }

    /**
     * Method creates action listener object which calculates stored operation using
     * stored operator and operands.
     * 
     * @param display calculator display
     * @param calculator {@link CalcModel} object in which the information is stored
     */
    private void equalsAction(JLabel display, CalcModel calculator) {
      try {
        calculator.setValue(
            calculator.getPendingBinaryOperation().applyAsDouble(calculator.getActiveOperand(), calculator.getValue()));
        calculator.setPendingBinaryOperation(null);
        calculator.setActiveOperand(Double.NaN);
      } catch (IllegalStateException e) {
      }
    }

  }
}
