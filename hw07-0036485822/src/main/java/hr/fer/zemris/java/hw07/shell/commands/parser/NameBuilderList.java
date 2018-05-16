package hr.fer.zemris.java.hw07.shell.commands.parser;

import java.util.ArrayList;
import java.util.List;

/**
 * Implemetns type of {@link NameBuilder} which concatenates all name builders
 * given in constructor as a {@link List}.
 * 
 * @author tin
 *
 */
public class NameBuilderList implements NameBuilder {

  /**
   * List of all name builders.
   */
  private List<NameBuilder> nameBuilders = new ArrayList<>();

  /**
   * Adds {@link NameBuilder} object to {@link List}.
   * 
   * @param nameBuilder name builder
   */
  public void add(NameBuilder nameBuilder) {
    nameBuilders.add(nameBuilder);
  }

  @Override
  public void execute(NameBuilderInfo info) {
    for (NameBuilder nameBuilder : nameBuilders) {
      nameBuilder.execute(info);
    }
  }

}
