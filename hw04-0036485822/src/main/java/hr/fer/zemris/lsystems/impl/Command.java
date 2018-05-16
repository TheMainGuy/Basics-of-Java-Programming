package hr.fer.zemris.lsystems.impl;

import hr.fer.zemris.lsystems.Painter;

/**
 * Interface which 
 * @author tin
 *
 */
public interface Command {
  /**
   * Executes specific command.
   * 
   * @param ctx context
   * @param painter painter
   */
  void execute(Context ctx, Painter painter);
}
