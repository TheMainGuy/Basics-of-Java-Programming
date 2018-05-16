package demo;

import hr.fer.zemris.lsystems.gui.LSystemViewer;
import hr.fer.zemris.lsystems.impl.LSystemBuilderImpl;

/**
 * Class which tests normal work of LsystemBuilderImpl and all classes on which
 * it depends.
 * 
 * @author tin
 *
 */
public class Glavni3 {
  /**
   * Method which is called when program starts.
   * 
   * @param args command line arguments
   */
  public static void main(String[] args) {
    LSystemViewer.showLSystem(LSystemBuilderImpl::new);
  }
}
