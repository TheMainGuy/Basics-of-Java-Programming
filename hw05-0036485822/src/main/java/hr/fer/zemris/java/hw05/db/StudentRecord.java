package hr.fer.zemris.java.hw05.db;

/**
 * Class represents single student record from database. Student records are
 * jmbag unique and are considered equal if they have equal jmbag.
 * 
 * @author tin
 *
 */
public class StudentRecord implements Comparable<StudentRecord> {
  // Unique attribute jmbag represents jmbag of a student
  private String jmbag;

  // Represents first name of a student
  private String firstName;

  // Represents last name of a student
  private String lastName;

  // Represents student's final grade
  private int finalGrade;

  // Minimal final grade value
  private final int MINIMAL_GRADE_VALUE = 1;

  // Maximal final grade value
  private final int MAXIMAL_GRADE_VALUE = 5;

  /**
   * Creates student record from jmbag, first name, last name and final grade.
   * Throws exception if any parameter is null or final grade is not in valid
   * range.
   * 
   * @param jmbag student's jmbag
   * @param firstName student's first name
   * @param lastName student's last name
   * @param finalGrade student's final grade
   */
  public StudentRecord(String jmbag, String firstName, String lastName, int finalGrade) {
    if(jmbag == null || firstName == null || lastName == null) {
      throw new NullPointerException("Student record parameters can not be null.");
    }
    if(finalGrade < MINIMAL_GRADE_VALUE || finalGrade > MAXIMAL_GRADE_VALUE) {
      throw new IllegalArgumentException(
          "Final grade must be in [" + MINIMAL_GRADE_VALUE + "," + MAXIMAL_GRADE_VALUE + "] range.");
    }
    this.jmbag = jmbag;
    this.firstName = firstName;
    this.lastName = lastName;
    this.finalGrade = finalGrade;
  }

  /**
   * Calculates hashCode of this object based on jmbag value.
   * 
   * @return hashCode
   */
  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((jmbag == null) ? 0 : jmbag.hashCode());
    return result;
  }

  /**
   * Checks if this object is equal to given object. Returns <code>true</code> if
   * they are equal and <code>false</code> if they are not.
   * 
   * @param obj other object
   * @return <code>true</code> if they are equal and <code>false</code> if they
   *         are not.
   */
  @Override
  public boolean equals(Object obj) {
    if(this == obj)
      return true;
    if(obj == null)
      return false;
    if(getClass() != obj.getClass())
      return false;
    StudentRecord other = (StudentRecord) obj;
    if(jmbag == null) {
      if(other.jmbag != null)
        return false;
    }
    else if(!jmbag.equals(other.jmbag))
      return false;
    return true;
  }

  /**
   * Converts this object to {@link String}. Appends all field values and
   * separates them with tabs.
   * 
   * @return {@link String} made from separating all field values with tabs
   */
  @Override
  public String toString() {
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append(jmbag).append("\t").append(lastName).append("\t").append(firstName).append("\t")
        .append(finalGrade);
    return stringBuilder.toString();
  }

  /**
   * Returns jmbag.
   * 
   * @return jmbag
   */
  public String getJmbag() {
    return jmbag;
  }

  /**
   * Returns first name.
   * 
   * @return first name
   */
  public String getFirstName() {
    return firstName;
  }

  /**
   * Returns last name.
   * 
   * @return last name
   */
  public String getLastName() {
    return lastName;
  }

  /**
   * Returns final grade.
   * 
   * @return final grade
   */
  public int getFinalGrade() {
    return finalGrade;
  }

  /**
   * Used for comparing this object to another {@link StudentRecord} record object
   * based on their hashCode value. Returns negative number if other object has
   * greater hashCode, 0 if they have equal hashCode and positive number if this
   * object has greater hashCode.
   * 
   * @param arg0 another {@link StudentRecord} object
   * @return -1 if other object has greater hashCode, 0 if they have equal
   *         hashCode and 1 if this object has greater hashCode
   */
  @Override
  public int compareTo(StudentRecord arg0) {
    if(this.hashCode() < arg0.hashCode()) {
      return -1;
    }
    else if(this.hashCode() > arg0.hashCode()) {
      return 1;
    }
    return 0;
  }

}
