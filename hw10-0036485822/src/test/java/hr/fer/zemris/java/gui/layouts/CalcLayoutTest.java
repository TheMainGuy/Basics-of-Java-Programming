package hr.fer.zemris.java.gui.layouts;

import java.awt.Dimension;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.junit.Assert;
import org.junit.Test;

public class CalcLayoutTest {

  private final double DELTA = 0.000001;
  JPanel p = new JPanel(new CalcLayout(2));
  @Test
  public void preferredSizeTest() {
    JLabel l1 = new JLabel("");
    l1.setPreferredSize(new Dimension(10, 30));
    JLabel l2 = new JLabel("");
    l2.setPreferredSize(new Dimension(20, 15));
    p.add(l1, new RCPosition(2, 2));
    p.add(l2, new RCPosition(3, 3));
    Dimension dim = p.getPreferredSize();
    Assert.assertEquals(152, dim.getWidth(), DELTA);
    Assert.assertEquals(158, dim.getHeight(), DELTA);
  }
  
  @Test
  public void preferredSizeTest2() {
    JLabel l1 = new JLabel(""); l1.setPreferredSize(new Dimension(108,15));
    JLabel l2 = new JLabel(""); l2.setPreferredSize(new Dimension(16,30));
    p.add(l1, new RCPosition(1,1));
    p.add(l2, new RCPosition(3,3));
    Dimension dim = p.getPreferredSize();
    Assert.assertEquals(152, dim.getWidth(), DELTA);
    Assert.assertEquals(158, dim.getHeight(), DELTA);
  }
  
  @Test(expected=CalcLayoutException.class)
  public void outOfBoundsException() {
    p.add(new JButton(), new RCPosition(0, 2));
  }
  
  @Test(expected=CalcLayoutException.class)
  public void outOfBoundsException2() {
    p.add(new JButton(), new RCPosition(1, 9));
  }
  
  @Test(expected=CalcLayoutException.class)
  public void outOfBoundsException3() {
    p.add(new JButton(), new RCPosition(10, 2));
  }
  
  @Test(expected=CalcLayoutException.class)
  public void outOfBoundsException4() {
    p.add(new JButton(), new RCPosition(2, 0));
  }
  
  @Test(expected=CalcLayoutException.class)
  public void bigButtonCoordinate() {
    p.add(new JButton(), new RCPosition(1, 2));
  }
  
  @Test(expected=CalcLayoutException.class)
  public void bigButtonCoordinate2() {
    p.add(new JButton(), new RCPosition(1, 5));
  }
  
  @Test(expected=CalcLayoutException.class)
  public void sameCoordinate() {
    p.add(new JButton(), new RCPosition(2, 2));
    p.add(new JButton(), new RCPosition(2, 2));
  }
  
  
  
}
