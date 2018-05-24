package hr.fer.zemris.java.hw11.jnotepadpp;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Insets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.swing.JComponent;
import javax.swing.SwingUtilities;

/**
 * Implements basic clock and draws it to panel it is assigned on. Clock is in
 * format YYYY/MM/dd HH:mm:ss and updated approximately every 500ms.
 * 
 * @author tin
 *
 */
class Clock extends JComponent {

  /**
   * Serial version UID.
   */
  private static final long serialVersionUID = 1L;

  /**
   * String which represents current time.
   */
  volatile String time;
  
  /**
   * Variable which is set to <code>true</code> when stop is requested.
   */
  volatile boolean stopRequested;
  
  /**
   * Determines clock format.
   */
  DateTimeFormatter formatter = DateTimeFormatter.ofPattern("YYYY/MM/dd HH:mm:ss");

  /**
   * Constructor.
   */
  public Clock() {
    updateTime();

    Thread t = new Thread(() -> {
      while (true) {
        try {
          Thread.sleep(500);
        } catch (Exception ex) {
        }
        if(stopRequested)
          break;
        SwingUtilities.invokeLater(() -> {
          updateTime();
        });
      }
    });
    t.setDaemon(true);
    t.start();
  }

  /**
   * Stops clock.
   */
  public void stop() {
    stopRequested = true;
  }

  /**
   * Updates time.
   */
  private void updateTime() {
    time = formatter.format(LocalDateTime.now());
    repaint();
  }

  @Override
  protected void paintComponent(Graphics g) {
    Insets ins = getInsets();
    Dimension dim = getSize();
    g.drawString(time, dim.width - ins.left - g.getFontMetrics().stringWidth(time),
        dim.height - ins.bottom - (dim.height - ins.top - ins.bottom - g.getFontMetrics().getAscent()) / 2);
  }
}
