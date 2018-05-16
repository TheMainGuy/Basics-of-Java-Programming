package hr.fer.zemris.lsystems.impl.commands;

import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.Command;
import hr.fer.zemris.lsystems.impl.Context;
import hr.fer.zemris.lsystems.impl.TurtleState;

/**
 * Class which implements scale command for turtle state. It scales turtle state
 * scale factor by factor factor.
 * 
 * @author tin
 *
 */
public class ScaleCommand implements Command {

  private double factor;

  public ScaleCommand(double factor) {
    this.factor = factor;
  }

  /**
   * Takes turtle state from top of the Context ctx stack and changes its shiftLength
   * attribute scaling it by factor factor.
   * 
   * @param ctx stack of turtle states
   * @param painter irrelevant here
   */
  @Override
  public void execute(Context ctx, Painter painter) {
    double newShiftLength = ctx.getCurrentState().getShiftLength() * factor;
    TurtleState newTurtleState = new TurtleState(ctx.getCurrentState().getPosition(), 
        ctx.getCurrentState().getAngle().getDegreeAngle(), ctx.getCurrentState().getColor(), newShiftLength);
    ctx.popState();
    ctx.pushState(newTurtleState);
  }

}
