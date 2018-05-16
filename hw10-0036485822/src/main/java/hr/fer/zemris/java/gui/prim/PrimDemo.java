package hr.fer.zemris.java.gui.prim;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListModel;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;

/**
 * 
 * @author tin
 *
 */
public class PrimDemo {
  /**
   * Method which is called when program starts.
   * 
   * @param args command line arguments
   */
  public static void main(String[] args) {
    SwingUtilities.invokeLater(() -> {
      JFrame frame = new ListFrame();
      frame.setVisible(true);
    });
  }

  /**
   * Implments basic prime number list model. Prime numbers are generated as the
   * method next is called. Starts with number 1 in list.
   * 
   * @author tin
   *
   */
  static class PrimListModel implements ListModel<Integer> {
    private List<Integer> primeNumbers = new ArrayList<>();
    private List<ListDataListener> dataListeners = new ArrayList<>();

    public PrimListModel() {
      primeNumbers.add(1);
    }

    @Override
    public void addListDataListener(ListDataListener arg0) {
      dataListeners.add(arg0);
    }

    @Override
    public Integer getElementAt(int arg0) {
      return primeNumbers.get(arg0);
    }

    @Override
    public int getSize() {
      return primeNumbers.size();
    }

    @Override
    public void removeListDataListener(ListDataListener arg0) {
      dataListeners.remove(arg0);
    }

    /**
     * Calculates prime number based on last prime number and saves it to list. Then
     * notifies all observers about made changes.
     */
    public void next() {
      int pos = primeNumbers.size();
      int lastPrime = primeNumbers.get(primeNumbers.size() - 1);
      while (true) {
        lastPrime++;
        if(isPrime(lastPrime)) {
          primeNumbers.add(lastPrime);
          break;
        }
      }

      ListDataEvent event = new ListDataEvent(this, ListDataEvent.INTERVAL_ADDED, pos, pos);
      for (ListDataListener listener : dataListeners) {
        listener.intervalAdded(event);
      }
    }

    /**
     * Checks if given number is prime number. Returns <code>true</code> if it is
     * and <code>false</code> if it is not.
     * 
     * @param lastPrime number
     * @return <code>true</code> if lastPrime is prime, <code>false</code> if it is
     *         not
     */
    private boolean isPrime(int lastPrime) {
      if(lastPrime == 2) {
        return true;
      }
      for (int i = 2, n = (int) Math.sqrt(lastPrime); i <= n; i++) {
        if(lastPrime % i == 0) {
          return false;
        }
      }
      return true;
    }

  }

  /**
   * Implements frame with some default parameters. Draws 2 scrollable lists on
   * screen and button which adds new prime number on both lists.
   * 
   * @author tin
   *
   */
  public static class ListFrame extends JFrame {
    /**
     * Serial version UID.
     */
    private static final long serialVersionUID = 1L;

    /**
     * Constructor.
     */
    public ListFrame() {
      setLocation(50, 50);
      setSize(300, 200);
      setTitle("Lists");
      setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
      initGUI();
    }

    /**
     * Initializes GUI elements.
     */
    private void initGUI() {
      Container cp = getContentPane();
      cp.setLayout(new BorderLayout());
      PrimListModel listModel = new PrimListModel();

      JList<Integer> list1 = new JList<>(listModel);
      JList<Integer> list2 = new JList<>(listModel);

      JButton addPrimeNumber = new JButton("Add new prime!");
      addPrimeNumber.addActionListener(a -> listModel.next());
      cp.add(addPrimeNumber, BorderLayout.SOUTH);

      JPanel central = new JPanel(new GridLayout(1, 0));
      central.add(new JScrollPane(list1));
      central.add(new JScrollPane(list2));
      cp.add(central, BorderLayout.CENTER);
    }
  }
}
