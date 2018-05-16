package hr.fer.zemris.java.hw05.db;

/**
 * Interface defines a filter for student records search. If method accepts
 * returns <code>true</code> for specific {@link StudentRecord}, that record
 * passed filtration. If it returns <code>false</code>, it is filtered out.
 * 
 * @author tin
 *
 */
public interface IFilter {

  /**
   * Determines wether the {@link StudentRecord} record is filtered out or
   * accepted.
   * 
   * @param record record
   * @return <code>true</code> if record is accepted, <code>false</code> if it is
   *         not
   */
  public boolean accepts(StudentRecord record);
}
