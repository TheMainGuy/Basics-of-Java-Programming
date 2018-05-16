package hr.fer.zemris.lsystems.impl.commands;

import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.Command;
import hr.fer.zemris.lsystems.impl.Context;
import hr.fer.zemris.lsystems.impl.TurtleState;
import hr.fer.zemris.math.Vector2D;

/**
 * Class which implements rotate command for turtle state. It rotates turtle
 * angle attribute by given angle.
 * 
 * @author tin
 *
 */
public class RotateCommand implements Command {

  private double angle;

  /**
   * Creates rotate command with given angle.
   * 
   * @param angle angle
   */
  public RotateCommand(double angle) {
    this.angle = angle;
  }

  /**
   * Takes turtle state from top of the Context ctx stack and changes its angle
   * attribute by rotating it by angle value.
   * 
   * @param ctx stack of turtle states
   * @param painter irrelevant here
   */
  @Override
  public void execute(Context ctx, Painter painter) {
    Vector2D newAngle = ctx.getCurrentState().getAngle().rotated(angle);
    TurtleState newTurtleState = new TurtleState(ctx.getCurrentState().getPosition(), newAngle.getDegreeAngle(),
        ctx.getCurrentState().getColor(), ctx.getCurrentState().getShiftLength());
    ctx.popState();
    ctx.pushState(newTurtleState);

  }

}
