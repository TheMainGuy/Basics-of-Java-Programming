package hr.fer.zemris.java.hw11.jnotepadpp.local;

import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JFrame;

public class FormLocalizationProvider extends LocalizationProviderBridge {

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
