package hr.fer.zemris.java.hw07.shell.commands.parser;

/**
 * Defines method execute which takes {@link NameBuilderInfo} object and alters
 * its {@link StringBuilder} object by appending stored {@link String}.
 * 
 * @author tin
 *
 */
public interface NameBuilder {
  /**
   * Generates name by appending {@link String}s to info's {@link StringBuilder}
   * object.
   * 
   * @param info object in which part of the name will be appended
   */
  void execute(NameBuilderInfo info);
}
