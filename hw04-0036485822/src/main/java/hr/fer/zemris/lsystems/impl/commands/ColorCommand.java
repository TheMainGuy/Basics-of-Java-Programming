package hr.fer.zemris.lsystems.impl.commands;

import java.awt.Color;

import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.Command;
import hr.fer.zemris.lsystems.impl.Context;
import hr.fer.zemris.lsystems.impl.TurtleState;

/**
 * Class which implements color command for turtle state. It changes turtle
 * state color.
 * 
 * @author tin
 *
 */
public class ColorCommand implements Command {

  private Color color;

  public ColorCommand(Color color) {
    this.color = color;
  }

  /**
   * Takes turtle state from top of the Context ctx stack and changes its color.
   * 
   * @param ctx stack of turtle states
   * @param painter irrelevant here
   */
  @Override
  public void execute(Context ctx, Painter painter) {
    TurtleState newTurtleState = new TurtleState(ctx.getCurrentState().getPosition(),
        ctx.getCurrentState().getAngle().getDegreeAngle(), color, ctx.getCurrentState().getShiftLength());
    ctx.popState();
    ctx.pushState(newTurtleState);
  }

}
