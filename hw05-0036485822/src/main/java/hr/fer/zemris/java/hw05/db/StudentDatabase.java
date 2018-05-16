package hr.fer.zemris.java.hw05.db;

import java.util.ArrayList;
import java.util.List;

import hr.fer.zemris.java.hw05.collections.SimpleHashtable;
import hr.fer.zemris.java.hw05.collections.SimpleHashtable.TableEntry;

/**
 * Implements {@link StudentRecord} database. Each {@link StudentRecord} is
 * jmbag unique, there can not exist more than 1 student records with same
 * jmbag. If there are multiple records with same jmbag, only last one will be
 * saved to {@link StudentDatabase}.
 * 
 * @author tin
 *
 */
public class StudentDatabase {

  // Student records are stored in this hashtable
  private SimpleHashtable<String, StudentRecord> records;

  /**
   * Constructor. Parses and stores {@link StudentRecord}s from {@link List} of
   * {@link String}s to hashtable.
   * 
   * @param database {@link List} of {@link String}s
   */
  public StudentDatabase(List<String> database) {
    if(database == null) {
      throw new NullPointerException("Database can not be null.");
    }

    records = new SimpleHashtable<String, StudentRecord>(database.size());
    parseData(database);
  }

  /**
   * Parses student records from {@link String}s to {@link StudentRecord} format
   * and saves them to local hashtable.
   * 
   * @param database {@link List} of database {@link String}s
   */
  private void parseData(List<String> database) {
    for (String record : database) {
      String[] recordParts = record.split("\t");
      records.put(recordParts[0],
          new StudentRecord(recordParts[0], recordParts[2], recordParts[1], Integer.parseInt(recordParts[3])));
    }
  }

  /**
   * Returns {@link StudentRecord} for given jmbag in O(1) complexity. If jmbag is
   * <code>null</code> or is not stored among records, returns <code>null</code>.
   * 
   * @param jmbag jmbag
   * @return student record pair for given jmbag, <code>null</code> if jmbag is
   *         <code>null</code> or is not stored
   */
  public StudentRecord forJMBAG(String jmbag) {
    return records.get(jmbag);
  }

  /**
   * Creates and returns {@link List} of all {@link StudentRecord}s which are
   * accepted by {@link IFilter} filter.
   * 
   * @param filter filter
   * @return {@link List} of accepted {@link StudentRecord}s
   */
  public List<StudentRecord> filter(IFilter filter) {
    List<StudentRecord> accepted = new ArrayList<>();
    for (TableEntry<String, StudentRecord> entry : records) {
      if(filter.accepts(entry.getValue())) {
        accepted.add(entry.getValue());
      }
    }
    return accepted;
  }

}
