package hr.fer.zemris.lsystems.impl.commands;

import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.Command;
import hr.fer.zemris.lsystems.impl.Context;
import hr.fer.zemris.lsystems.impl.TurtleState;
import hr.fer.zemris.math.Vector2D;

/**
 * Class which implements skipping a line from turtle position to new position
 * that is step value distanced from starting position and changing current
 * position to new position afterwards.
 * 
 * @author tin
 *
 */
public class SkipCommand implements Command {
  private double step;
  
  /**
   * Creates skip command with step step.
   * @param step step
   */
  public SkipCommand(double step) {
    this.step = step;
  }
  
  /**
   * Skips line from current position on top of the stack to new position
   * calculated by going step distance in direction the turtle is looking. After
   * that changes current state to new position.
   * 
   * @param ctx turtle state stack
   * @param painter irrelevant here
   */
  @Override
  public void execute(Context ctx, Painter painter) {
    Vector2D newPosition = ctx.getCurrentState().getPosition()
        .translated(ctx.getCurrentState().getAngle().scaled(step).scaled(ctx.getCurrentState().getShiftLength()));

    TurtleState newTurtleState = new TurtleState(newPosition, ctx.getCurrentState().getAngle().getDegreeAngle(),
        ctx.getCurrentState().getColor(), ctx.getCurrentState().getShiftLength());
    
    ctx.popState();
    ctx.pushState(newTurtleState);
  }

}
