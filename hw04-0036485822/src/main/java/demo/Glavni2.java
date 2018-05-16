package demo;

import hr.fer.zemris.lsystems.LSystem;
import hr.fer.zemris.lsystems.LSystemBuilderProvider;
import hr.fer.zemris.lsystems.gui.LSystemViewer;
import hr.fer.zemris.lsystems.impl.LSystemBuilderImpl;

/**
 * Class which tests normal work of LsystemBuilderImpl and all classes on which
 * it depends.
 * 
 * @author tin
 *
 */
public class Glavni2 {
  
  /**
   * Method which is called when program starts.
   * 
   * @param args command line arguments
   */
  public static void main(String[] args) {
    LSystemViewer.showLSystem(createKochCurve2(LSystemBuilderImpl::new));
  }

  /**
   * Creates windows which takes level of Lindermayer fractal and draws graph
   * @param provider
   * @return
   */
  private static LSystem createKochCurve2(LSystemBuilderProvider provider) {
    String[] data = new String[] { "origin 0.05 0.4", "angle 0", "unitLength 0.9", "unitLengthDegreeScaler 1.0 / 3.0",
        "", "command F draw 1", "command + rotate 60", "command - rotate -60", "", "axiom F", "",
        "production F F+F--F+F" };
    return provider.createLSystemBuilder().configureFromText(data).build();
  }
}
