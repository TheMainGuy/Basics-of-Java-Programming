package hr.fer.zemris.lsystems.impl.commands;

import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.Command;
import hr.fer.zemris.lsystems.impl.Context;

/**
 * Class which implements pop command for turtle state.
 * 
 * @author tin
 *
 */
public class PopCommand implements Command {

  /**
   * Executes pop command. Pops state from Context ctx.
   * 
   * @param ctx context
   * @param painter irrelevant here
   */
  @Override
  public void execute(Context ctx, Painter painter) {
    ctx.popState();
  }

}
