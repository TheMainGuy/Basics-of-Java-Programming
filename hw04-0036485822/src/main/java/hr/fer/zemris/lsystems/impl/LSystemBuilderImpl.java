package hr.fer.zemris.lsystems.impl;

import java.awt.Color;

import hr.fer.zemris.java.custom.collections.Dictionary;
import hr.fer.zemris.lsystems.LSystem;
import hr.fer.zemris.lsystems.LSystemBuilder;
import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.commands.ColorCommand;
import hr.fer.zemris.lsystems.impl.commands.DrawCommand;
import hr.fer.zemris.lsystems.impl.commands.PopCommand;
import hr.fer.zemris.lsystems.impl.commands.PushCommand;
import hr.fer.zemris.lsystems.impl.commands.RotateCommand;
import hr.fer.zemris.lsystems.impl.commands.ScaleCommand;
import hr.fer.zemris.lsystems.impl.commands.SkipCommand;
import hr.fer.zemris.math.Vector2D;

/**
 * Class which implements LsystemBuilder task of creating Lsystem which draws
 * graph. It takes productions, commands and other parameters for adjusting
 * drawing.
 * 
 * @author tin
 *
 */
public class LSystemBuilderImpl implements LSystemBuilder {
  private Dictionary commands = new Dictionary();
  private Dictionary productions = new Dictionary();
  private double unitLength = 0.1;
  private double unitLengthDegreeScaler = 1;
  private Vector2D origin = new Vector2D(0, 0);
  private double angle = 0;
  private String axiom = "";

  /**
   * Class with generates string of orders to be executed to draw specific graph.
   * 
   * @author tin
   *
   */
  class LSystemImpl implements LSystem {

    /**
     * Constructor
     */
    public LSystemImpl() {

    }

    /**
     * Draws specified graph using painter. Calculates which graph level to draw
     * according to level parameter.
     * 
     * @param level level
     * @painter painter
     */
    @Override
    public void draw(int level, Painter painter) {
      Context context = new Context();
      context
          .pushState(new TurtleState(origin, angle, Color.BLACK, unitLength * Math.pow(unitLengthDegreeScaler, level)));
      String finalString = generate(level);
      for (int i = 0, n = finalString.length(); i < n; i++) {
        if(commands.get(finalString.charAt(i)) == null) {
          continue;
        }
        ((Command) commands.get(finalString.charAt(i))).execute(context, painter);
      }
    }

    /**
     * Generates string of commands that need to be executed to draw specific graph.
     * For each level performs productions on previous level starting with axiom.
     * 
     * @param level level
     */
    @Override
    public String generate(int level) {
      if(axiom.length() == 0) {
        return "";
      }
      String temporaryString = axiom;

      for (int levelIterator = 0; levelIterator < level; levelIterator++) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0, n = temporaryString.length(); i < n; i++) {
          String productionString = (String) productions.get(temporaryString.charAt(i));
          if(productionString == null) {
            stringBuilder.append(temporaryString.charAt(i));
          }
          else {
            stringBuilder.append(productionString);
          }
        }
        temporaryString = stringBuilder.toString();
      }

      return temporaryString;
    }

  }

  /**
   * Creates new Lsystem.
   */
  @Override
  public LSystem build() {
    return new LSystemImpl();
  }

  /**
   * Configures LSystemBuilder from {@link String} array.
   * 
   * @param data {@link String} array
   */
  @Override
  public LSystemBuilder configureFromText(String[] data) {
    for (String s : data) {
      s = s.replaceAll("\\s{2,}", " ").trim();
      if(s.length() > 0) {
        parseInputString(s);
      }
    }
    return this;
  }

  /**
   * Parses a single input string to determine which parameter to change or
   * command/production to register.
   * 
   * @param s string s
   */
  private void parseInputString(String s) {
    String[] parts = s.split(" ");
    if(parts[0].equals("command")) {
      registerCommand(parts[1].charAt(0), s.replaceFirst("command . ", ""));
    }
    else if(parts[0].equals("origin")) {
      checkArgumentLength(parts, 3, "Origin usage: origin arg1 arg2");
      try {
        origin = new Vector2D(Double.parseDouble(parts[1]), Double.parseDouble(parts[2]));
      } catch (Exception e) {
        throw new IllegalArgumentException("Origin can not be set to specified values.");
      }
    }
    else if(parts[0].equals("angle")) {
      checkArgumentLength(parts, 2, "Angle usage: angle a");
      try {
        angle = Double.parseDouble(parts[1]);
      } catch (Exception e) {
        throw new IllegalArgumentException("Angle can not be set to specified value.");
      }
    }
    else if(parts[0].equals("unitLength")) {
      checkArgumentLength(parts, 2, "Unit Length usage: unitLength l");
      try {
        unitLength = Double.parseDouble(parts[1]);
      } catch (Exception e) {
        throw new IllegalArgumentException("UnitLength can not be set to specified value.");
      }
    }
    else if(parts[0].equals("unitLengthDegreeScaler")) {
      parseUnitDegreeScaler(s.replaceFirst("unitLengthDegreeScaler ", ""));
    }
    else if(parts[0].equals("axiom")) {
      checkArgumentLength(parts, 2, "Axiom usage: axiom string");
      axiom = parts[1];
    }
    else if(parts[0].equals("production")) {
      checkArgumentLength(parts, 3, "Production usage: production p productionString");
      registerProduction(parts[1].charAt(0), parts[2]);
    }
  }

  /**
   * Parses unitDegreeScaler from string s.
   * 
   * @param s s
   */
  private void parseUnitDegreeScaler(String s) {
    String[] parts = s.split("/");
    if(parts.length == 2) {
      unitLengthDegreeScaler = Double.parseDouble(parts[0]) / Double.parseDouble(parts[1]);
    }
    else if(parts.length == 1) {
      unitLengthDegreeScaler = Double.parseDouble(s);
    }
    else {
      throw new IllegalArgumentException("UnitLengthDegreeScaler must be double value or in double/double format.");
    }
  }

  /**
   * Registers command to commands {@link Dictionary} from character and command.
   * 
   * @param character character
   * @param command command
   * @throws IllegalArgumentException if command for character c is already
   *           defined.
   */
  @Override
  public LSystemBuilder registerCommand(char character, String command) {
    command = command.replaceAll("\\s{2,}", " ").trim();
    if(commands.get(character) != null) {
      throw new IllegalArgumentException("Command for character " + character + " already defined.");
    }

    String[] commandParts = command.split(" ");

    if(command.split(" ")[0].toLowerCase().equals("draw")) {
      parseDrawCommand(character, commandParts);
    }
    else if(commandParts[0].toLowerCase().equals("skip")) {
      parseSkipCommand(character, commandParts);
    }
    else if(commandParts[0].toLowerCase().equals("scale")) {
      parseScaleCommand(character, commandParts);
    }
    else if(commandParts[0].toLowerCase().equals("rotate")) {
      parseRotateCommand(character, commandParts);
    }
    else if(commandParts[0].toLowerCase().equals("push")) {
      parsePushCommand(character, commandParts);
    }
    else if(commandParts[0].toLowerCase().equals("pop")) {
      parsePopCommand(character, commandParts);
    }
    else if(commandParts[0].toLowerCase().equals("color")) {
      parseColorCommand(character, commandParts);
    }
    else {
      throw new IllegalArgumentException("Command not supported.");
    }
    return this;
  }

  /**
   * Parses color command for changing draw color from character and array of
   * command parts.
   * 
   * @param character character
   * @param commandParts command parts
   * @throws IllegalArgumentException if color is not in valid format
   */
  private void parseColorCommand(char character, String[] commandParts) {
    checkArgumentLength(commandParts, 2, "Color command usage: color rrggbb");

    try {
      ColorCommand colorCommand = new ColorCommand(new Color(Integer.valueOf(commandParts[1].substring(0, 2), 16),
          Integer.valueOf(commandParts[1].substring(2, 4), 16), Integer.valueOf(commandParts[1].substring(4, 6), 16)));

      commands.put(character, colorCommand);
    } catch (Exception e) {
      throw new IllegalArgumentException(commandParts[1] + " is not in valid color format.");
    }
  }

  /**
   * Parses pop command for changing turtle state from character and array of
   * command parts.
   * 
   * @param character character
   * @param commandParts command parts
   * @throws IllegalArgumentException if there are any arguments
   */
  private void parsePopCommand(char character, String[] commandParts) {
    checkArgumentLength(commandParts, 1, "Pop command can not have arguments.");
    commands.put(character, new PopCommand());
  }

  /**
   * Parses push command for changing turtle state from character and array of
   * command parts.
   * 
   * @param character character
   * @param commandParts command parts
   * @throws IllegalArgumentException if there are any arguments
   */
  private void parsePushCommand(char character, String[] commandParts) {
    checkArgumentLength(commandParts, 1, "Push command can not have arguments.");
    commands.put(character, new PushCommand());
  }

  /**
   * Parses rotate command for changing angle from character and array of command
   * parts.
   * 
   * @param character character
   * @param commandParts command parts
   * @throws IllegalArgumentException if there is more than 1 argument
   */
  private void parseRotateCommand(char character, String[] commandParts) {
    checkArgumentLength(commandParts, 2, "Rotate command usage: rotate a");
    try {
      commands.put(character, new RotateCommand(Double.parseDouble(commandParts[1])));
    } catch (Exception e) {
      throw new IllegalArgumentException(commandParts[1] + " is not a valid double value.");
    }
  }

  /**
   * Parses scale command for changing scaler from character and array of command
   * parts.
   * 
   * @param character character
   * @param commandParts command parts
   * @throws IllegalArgumentException if there is more than 1 argument
   */
  private void parseScaleCommand(char character, String[] commandParts) {
    checkArgumentLength(commandParts, 2, "Scale command usage: scale s");

    try {
      commands.put(character, new ScaleCommand(Double.parseDouble(commandParts[1])));
    } catch (Exception e) {
      throw new IllegalArgumentException(commandParts[1] + " is not a valid double value.");
    }

  }

  /**
   * Parses skip command for jumping from current state coordinates by unitLength
   * from character and array of command parts.
   * 
   * @param character character
   * @param commandParts command parts
   * @throws IllegalArgumentException if there is more than 1 argument
   */
  private void parseSkipCommand(char character, String[] commandParts) {
    checkArgumentLength(commandParts, 2, "Skip command usage: skip s");

    try {
      commands.put(character, new SkipCommand(Double.parseDouble(commandParts[1])));
    } catch (Exception e) {
      throw new IllegalArgumentException(commandParts[1] + " is not a valid double value.");
    }

  }

  /**
   * Parses skip command for drawing a line from current state coordinates by
   * unitLength from character and array of command parts.
   * 
   * @param character character
   * @param commandParts command parts
   * @throws IllegalArgumentException if there is more than 1 argument
   */
  private void parseDrawCommand(char character, String[] commandParts) {
    checkArgumentLength(commandParts, 2, "Draw command usage: draw s");

    try {
      commands.put(character, new DrawCommand(Double.parseDouble(commandParts[1])));
    } catch (Exception e) {
      throw new IllegalArgumentException(commandParts[1] + " is not a valid double value.");
    }

  }

  /**
   * Checks if there is correct number of arguments in commandParts. Throws
   * exception if there is not.
   * 
   * @param commandParts command parts
   * @param expectedLength expected number of arguments
   * @param message exception message
   * @throws IllegalArgumentException if command parts have wrong number of
   *           arguments
   */
  private void checkArgumentLength(String[] commandParts, int expectedLength, String message) {
    if(commandParts.length != expectedLength) {
      throw new IllegalArgumentException(message);
    }
  }

  /**
   * Registers new production in production Dictionary. Production can not be null
   * nor can 2 productions be added for single character.
   * 
   * @param c character
   * @param production production value to be saved
   * @throws NullPointerException if production is null string
   * @throws IllegalArgumentException if there already exists a production with character c
   */
  @Override
  public LSystemBuilder registerProduction(char c, String production) {
    if(production == null) {
      throw new NullPointerException("Production can not be null.");
    }
    if(productions.get(c) != null) {
      throw new IllegalArgumentException("Production for character " + c + " already defined.");
    }

    productions.put(c, production);
    return this;
  }

  /**
   * Sets LsystemBuilder angle to angle parameter.
   * 
   * @param angle
   */
  @Override
  public LSystemBuilder setAngle(double angle) {
    this.angle = angle;
    return this;
  }

  /**
   * Sets LsystemBuilder axiom to axiom parameter.
   * 
   * @param axiom axiom
   * @throws NullPointerException if axiom is null
   */
  @Override
  public LSystemBuilder setAxiom(String axiom) {
    if(axiom == null) {
      throw new NullPointerException("Axiom can not be null.");
    }
    this.axiom = axiom;
    return this;
  }

  /**
   * Sets LSystemBuilder origin to Vector2D with x,y coordinates.
   * 
   * @param x x coordinate
   * @param y y coordinate
   */
  @Override
  public LSystemBuilder setOrigin(double x, double y) {
    this.origin = new Vector2D(x, y);
    return this;
  }

  /**
   * Sets LSystemBuilder unit length to unitLength parameter.
   * 
   * @param unitLength unitLength
   */
  @Override
  public LSystemBuilder setUnitLength(double unitLength) {
    this.unitLength = unitLength;
    return this;
  }

  /**
   * Sets LSystemBuilder unit length degree scaler to unitLengthDegreeScaler parameter.
   * 
   * @param unitLengthDegreeScaler unitLengthDegreeScaler
   */
  @Override
  public LSystemBuilder setUnitLengthDegreeScaler(double unitLengthDegreeScaler) {
    this.unitLengthDegreeScaler = unitLengthDegreeScaler;
    return this;
  }

}
