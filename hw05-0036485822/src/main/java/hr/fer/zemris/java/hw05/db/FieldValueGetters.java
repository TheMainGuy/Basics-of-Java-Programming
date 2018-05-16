package hr.fer.zemris.java.hw05.db;

/**
 * Class implements field value getter constants for getting
 * {@link StudentRecord} record values. Each constant implements method get that
 * returns one {@link StudentRecord} value for given student record.
 * 
 * @author tin
 *
 */
public class FieldValueGetters {
  // Represents first name field of student record. Method get returns first name
  // from given student record.
  public static final IFieldValueGetter FIRST_NAME = (record) -> record.getFirstName();

  // Represents last name field of student record. Method get returns last name
  // from given student record.
  public static final IFieldValueGetter LAST_NAME = (record) -> record.getLastName();

  // Represents jmbag field of student record. Method get returns jmbag
  // from given student record.
  public static final IFieldValueGetter JMBAG = (record) -> record.getJmbag();
}
