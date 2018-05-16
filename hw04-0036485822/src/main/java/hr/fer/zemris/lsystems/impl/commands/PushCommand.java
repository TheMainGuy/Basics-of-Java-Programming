package hr.fer.zemris.lsystems.impl.commands;

import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.Command;
import hr.fer.zemris.lsystems.impl.Context;

/**
 * Class which implements push command for turtle state. It copies state on top
 * of given context and pushes it on top of that state.
 * 
 * @author tin
 *
 */
public class PushCommand implements Command {

  /**
   * Copies state from top of Context ctx stack and pushes it on top of it.
   * 
   * @param ctx context ctx
   * @param painter irrelevant here
   */
  @Override
  public void execute(Context ctx, Painter painter) {
    ctx.pushState(ctx.getCurrentState().copy());
  }

}
