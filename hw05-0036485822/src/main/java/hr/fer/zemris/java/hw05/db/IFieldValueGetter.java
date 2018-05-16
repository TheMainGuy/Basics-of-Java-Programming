package hr.fer.zemris.java.hw05.db;

/**
 * Interface defines strategy pattern for obtaining field value from given
 * {@link StudentRecord}.
 * 
 * @author tin
 *
 */
public interface IFieldValueGetter {
  /**
   * Method returns single attribute from given student record {@link StudentRecord}.
   * 
   * @param record student record
   * @return one student record attribute
   */
  public String get(StudentRecord record);
}
