package hr.fer.zemris.java.gui.charts;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

/**
 * Class used for demonstrating {@link BarChart} and {@link BarChartComponent}
 * functionalities.
 * 
 * @author tin
 *
 */
public class BarChartDemo extends JFrame {
  /**
   * Serial version UID.
   */
  private static final long serialVersionUID = 1L;

  /**
   * Method which is called when program starts.
   * 
   * @param args command line arguments
   */
  public static void main(String[] args) {
    if(args.length != 1) {
      throw new IllegalArgumentException("1 argument must be provided.");
    }
    List<String> lines = readLines(args[0]);
    List<XYValue> values = new ArrayList<>();
    for (String value : lines.get(2).split("\\s+")) {
      String[] xy = value.split(",");
      values.add(new XYValue(Integer.parseInt(xy[0]), Integer.parseInt(xy[1])));
    }

    BarChart barChart = new BarChart(values, lines.get(0), lines.get(1), Integer.parseInt(lines.get(3)),
        Integer.parseInt(lines.get(4)), Integer.parseInt(lines.get(5)));

    SwingUtilities.invokeLater(() -> {
      JFrame frame = new JFrame();
      frame.setLocation(20, 20);
      frame.setSize(450, 360);
      frame.setTitle("Bar chart.");
      frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);;
      frame.setVisible(true);
      
      BarChartComponent component = new BarChartComponent(barChart);
      frame.add(component);
      component.paint(frame.getGraphics());
      
    });

  }

  /**
   * Helper method used to read first 5 lines from given file and store them in
   * list.
   * 
   * @param string file path
   * @return list of strings, each containing one line from file
   */
  private static List<String> readLines(String string) {
    try {
      BufferedReader fileReader = Files.newBufferedReader(Paths.get(string));
      String line;
      List<String> lines = new ArrayList<>();
      while ((line = fileReader.readLine()) != null) {
        lines.add(line);
        if(lines.size() == 6) {
          break;
        }
      }
      if(lines.size() != 6) {
        throw new IllegalArgumentException("File must have at least 5 lines.");
      }
      return lines;
    } catch (IOException e) {
      throw new IllegalArgumentException("Problem with reading file.");
    }

  }
}
