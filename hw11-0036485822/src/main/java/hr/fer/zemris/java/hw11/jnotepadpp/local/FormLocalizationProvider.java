package hr.fer.zemris.java.hw11.jnotepadpp.local;

import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JFrame;

/**
 * Adds windows listener to localiaztion provider bridge object. Window listener
 * calls connect method when window is opened and disconnect when window is
 * closed.
 * 
 * @author tin
 *
 */
public class FormLocalizationProvider extends LocalizationProviderBridge {

  /**
   * Constructor.
   * 
   * @param localizationProvider localization provider which is delegated to
   *          {@link LocalizationProviderBridge} which this object extends.
   * @param frame frame on which the {@link WindowListener} will be attached to
   */
  public FormLocalizationProvider(ILocalizationProvider localizationProvider, JFrame frame) {
    super(localizationProvider);
    frame.addWindowListener(new WindowListener() {

      @Override
      public void windowOpened(WindowEvent arg0) {
        connect();
      }

      @Override
      public void windowIconified(WindowEvent arg0) {
      }

      @Override
      public void windowDeiconified(WindowEvent arg0) {
      }

      @Override
      public void windowDeactivated(WindowEvent arg0) {
      }

      @Override
      public void windowClosing(WindowEvent arg0) {
      }

      @Override
      public void windowClosed(WindowEvent arg0) {
        disconnect();
      }

      @Override
      public void windowActivated(WindowEvent arg0) {
      }
    });

  }

}
