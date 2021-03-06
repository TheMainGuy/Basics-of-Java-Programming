package hr.fer.zemris.java.gui.layouts;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.LayoutManager2;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

/**
 * Implements calculator layout which can be used to implement basic calculator.
 * Layout is similar to GridLayout and has fixed 5 rows and 7 columns. Field
 * (1,1) is extended to (1,5); thus, coordinates (1,2) to (1,5) are unusable.
 * Layout will leave empty space instead of any unused field, including empty
 * rows and columns.
 * 
 * @author tin
 *
 */
public class CalcLayout implements LayoutManager2 {
  /**
   * Number of rows.
   */
  private final int NUMBER_OF_ROWS = 5;

  /**
   * Number of columns.
   */
  private final int NUMBER_OF_COLUMNS = 7;

  /**
   * Number of pixels between columns and rows.
   */
  private int spaceSize;

  /**
   * Components. Null for empty component.
   */
  Component[][] components = new Component[NUMBER_OF_ROWS][NUMBER_OF_COLUMNS];

  /**
   * Constructor.
   * 
   * @param spaceSize number of pixels between columns and rows.
   */
  public CalcLayout(int spaceSize) {
    this.spaceSize = spaceSize;
  }

  /**
   * Constructor.
   */
  public CalcLayout() {
    new CalcLayout(0);
  }

  @Override
  public void addLayoutComponent(String arg0, Component arg1) {

  }

  @Override
  public void layoutContainer(Container arg0) {
    Dimension preferredLayoutSize = preferredLayoutSize(arg0);
    int widthWithoutInsets = preferredLayoutSize.width - arg0.getInsets().left - arg0.getInsets().right;
    int heightWithoutInsets = preferredLayoutSize.height - arg0.getInsets().top - arg0.getInsets().bottom;
    int widthOfOneComponent = (widthWithoutInsets - (NUMBER_OF_COLUMNS - 1) * spaceSize) / NUMBER_OF_COLUMNS;
    int heightOfOneComponent = (heightWithoutInsets - (NUMBER_OF_ROWS - 1) * spaceSize) / NUMBER_OF_ROWS;
    List<Component> componentList = Arrays.asList(arg0.getComponents());

    for (int i = 0; i < NUMBER_OF_ROWS; i++) {
      for (int j = 0; j < NUMBER_OF_COLUMNS; j++) {
        if(components[i][j] == null) {
          continue;
        }
        int x = arg0.getInsets().left + j * (widthOfOneComponent + spaceSize);
        int y = arg0.getInsets().top + i * (heightOfOneComponent + spaceSize);
        if(i == 0 && j == 0) {
          arg0.getComponent(componentList.indexOf(components[i][j])).setBounds(x, y,
              widthOfOneComponent * 5 + spaceSize * 4, heightOfOneComponent);
        } else {
          arg0.getComponent(componentList.indexOf(components[i][j])).setBounds(x, y, widthOfOneComponent,
              heightOfOneComponent);
        }
      }
    }

  }

  @Override
  public Dimension minimumLayoutSize(Container arg0) {
    return layoutSize(arg0, (x) -> x.getMinimumSize());
  }

  @Override
  public Dimension preferredLayoutSize(Container arg0) {
    return layoutSize(arg0, x -> x.getPreferredSize());
  }

  @Override
  public void removeLayoutComponent(Component arg0) {
    for (int i = 0; i < NUMBER_OF_ROWS; i++) {
      for (int j = 0; j < NUMBER_OF_COLUMNS; j++) {
        if(components[i][j] == arg0) {
          components[i][j] = null;
        }
      }
    }

  }

  @Override
  public void addLayoutComponent(Component arg0, Object arg1) {
    RCPosition position;
    if(!(arg1 instanceof RCPosition)) {
      String[] parts = ((String) arg1).split(",");
      position = new RCPosition(Integer.parseInt(parts[0]), Integer.parseInt(parts[1]));
    } else {
      position = (RCPosition) arg1;
    }
    if(position.getRow() < 1 || position.getColumn() < 1 || position.getRow() > NUMBER_OF_ROWS
        || position.getColumn() > NUMBER_OF_COLUMNS) {
      throw new CalcLayoutException(
          "Component position must be in range ([1, 1], [" + NUMBER_OF_ROWS + ", " + NUMBER_OF_COLUMNS + "])");
    } else if(position.getRow() == 1 && position.getColumn() > 1 && position.getColumn() < 6) {
      throw new CalcLayoutException("Positions from (1,2) to (1,5) are not valid.");
    } else if(components[position.getRow() - 1][position.getColumn() - 1] != null) {
      throw new CalcLayoutException("Position already occupied");
    }

    components[position.getRow() - 1][position.getColumn() - 1] = arg0;

  }

  @Override
  public float getLayoutAlignmentX(Container arg0) {
    // TODO Auto-generated method stub
    return 0;
  }

  @Override
  public float getLayoutAlignmentY(Container arg0) {
    // TODO Auto-generated method stub
    return 0;
  }

  @Override
  public void invalidateLayout(Container arg0) {
  }

  @Override
  public Dimension maximumLayoutSize(Container arg0) {
    return layoutSize(arg0, x -> x.getMaximumSize());
  }

  /**
   * Helper method used to calculate minimumLayoutSize, preferredLayoutSize and
   * maximumLayoutSize.
   * 
   * @param arg0 container
   * @param f function used to determine which layoutSize is being calculated
   * @return layout size of given container
   */
  private Dimension layoutSize(Container arg0, Function<Component, Dimension> f) {
    Insets insets = arg0.getInsets();
    int maxComponentHeight = 0;
    int maxComponentWidth = 0;
    for (int i = 0; i < NUMBER_OF_ROWS; i++) {
      for (int j = 0; j < NUMBER_OF_COLUMNS; j++) {
        if(components[i][j] == null) {
          continue;
        }
        Dimension componentSize = f.apply(components[i][j]);
        if(componentSize == null) {
          continue;
        }
        if(i == 0 && j == 0) {
          int normalizedSize = (int) (componentSize.getWidth() - 4 * spaceSize) / 5;
          maxComponentWidth = maxComponentWidth > normalizedSize ? maxComponentWidth : normalizedSize;
          j = 5;
        } else {
          maxComponentWidth = (int) (maxComponentWidth > componentSize.getWidth() ? maxComponentWidth
              : componentSize.getWidth());
        }

        maxComponentHeight = (int) (maxComponentHeight > componentSize.getHeight() ? maxComponentHeight
            : componentSize.getHeight());
      }
    }

    return new Dimension(
        NUMBER_OF_COLUMNS * maxComponentWidth + spaceSize * (NUMBER_OF_COLUMNS - 1) + insets.left + insets.right,
        NUMBER_OF_ROWS * maxComponentHeight + spaceSize * (NUMBER_OF_ROWS - 1) + insets.top + insets.bottom);
  }

}
