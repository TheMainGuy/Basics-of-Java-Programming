package hr.fer.zemris.java.gui.layouts;

import java.awt.Container;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

/**
 * Class used for testing {@link CalcLayout} class.
 * 
 * @author tin
 *
 */
public class CalcLayoutShowDemo {
  /**
   * Method which is called when program starts.
   * 
   * @param args command line arguments.
   */
  public static void main(String[] args) {
    SwingUtilities.invokeLater(() -> {
      JFrame frame = new TestCalc();
      frame.pack();
      frame.setVisible(true);
    });
  }

  /**
   * Class which draws buttons using {@link CalcLayout}.
   * @author tin
   *
   */
  private static class TestCalc extends JFrame {
    /**
     * Serial version UID.
     */
    private static final long serialVersionUID = 1L;

    /**
     * Constructor.
     */
    public TestCalc() {
      setLocation(20, 50);
      setSize(300, 200);
      setTitle("Moj prvi prozor!");
      setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
      initGUI();
    }

    /**
     * GUI initializer.
     */
    private void initGUI() {
      Container p = getContentPane();
      p.setLayout(new CalcLayout(2));
      //JLabel l1 = new JLabel(""); l1.setPreferredSize(new Dimension(10,30));
      JButton l = new JButton("x");
      p.add(l, new RCPosition(1,1));
      p.add(new JButton("y"), new RCPosition(2,3));
      p.add(new JButton("z"), new RCPosition(2,7));
      p.add(new JButton("w"), new RCPosition(4,2));
      p.add(new JButton("a"), new RCPosition(4,5));
      p.add(new JButton("b"), new RCPosition(4,7));
      p.add(new JButton("k"), "3,5");
      p.setVisible(true);

      
    }

  }
}
