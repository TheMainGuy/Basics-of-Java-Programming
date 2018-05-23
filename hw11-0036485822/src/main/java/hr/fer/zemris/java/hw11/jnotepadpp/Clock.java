package hr.fer.zemris.java.hw11.jnotepadpp;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Insets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.swing.JComponent;
import javax.swing.SwingUtilities;

class Clock extends JComponent {

  private static final long serialVersionUID = 1L;
  
  volatile String time;
  volatile boolean stopRequested;
  DateTimeFormatter formatter = DateTimeFormatter.ofPattern("YYYY/MM/dd HH:mm:ss");
  
  public Clock() {
    updateTime();
    
    Thread t = new Thread(()->{
      while(true) {
        try {
          Thread.sleep(500);
        } catch(Exception ex) {}
        if(stopRequested) break;
        SwingUtilities.invokeLater(()->{
          updateTime();
        });
      }
      System.out.println("Stopped");
    });
    t.setDaemon(true);
    t.start();
  }
  
  public void stop() {
    stopRequested = true;
  }
  
  private void updateTime() {
    time = formatter.format(LocalDateTime.now());
    repaint();
  }

  @Override
  protected void paintComponent(Graphics g) {
    Insets ins = getInsets();
    Dimension dim = getSize();
    g.drawString(time, dim.width - ins.left - g.getFontMetrics().stringWidth(time) , dim.height-ins.bottom-(dim.height-ins.top-ins.bottom-g.getFontMetrics().getAscent())/2);
  }
}
