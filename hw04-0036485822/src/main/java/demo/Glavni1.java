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
public class Glavni1 {
  
  /**
   * Method which is called when program starts.
   * 
   * @param args command line arguments
   */
  public static void main(String[] args) {
    LSystemViewer.showLSystem(createKochCurve(LSystemBuilderImpl::new));
  }

  /**
   * Creates windows which takes level of Lindermayer fractal and draws graph
   * @param provider
   * @return
   */
  private static LSystem createKochCurve(LSystemBuilderProvider provider) {
    return provider.createLSystemBuilder().registerCommand('F', "draw 1").registerCommand('+', "rotate 60")
        .registerCommand('-', "rotate -60").setOrigin(0.05, 0.4).setAngle(0).setUnitLength(0.9)
        .setUnitLengthDegreeScaler(1.0 / 3.0).registerProduction('F', "F+F--F+F").setAxiom("F").build();
  }
}
