package hr.fer.zemris.java.hw06.demo4;

/**
 * Represents 1 student record containing jmbag, first name, last name, midterm
 * score, final score and final grade.
 * 
 * @author tin
 *
 */
public class StudentRecord {
  /**
   * Student's jmbag
   */
  private String jmbag;

  /**
   * Student's last name
   */
  private String lastName;

  /**
   * Student's first name
   */
  private String firstName;

  /**
   * Student's midterm exam score
   */
  private double midScore;

  /**
   * Student's final exam score
   */
  private double finalScore;

  /**
   * Student's laboratory exercices score
   */
  private double labScore;

  /**
   * Student's final grade
   */
  private int grade;

  /**
   * Constructor.
   * 
   * @param jmbag jmbag
   * @param lastName last name
   * @param firstName first name
   * @param midScore midterm exam score
   * @param finalScore final exam score
   * @param labScore laboratory exercices score
   * @param grade final grade
   */
  public StudentRecord(String jmbag, String lastName, String firstName, double midScore, double finalScore,
      double labScore, int grade) {
    super();
    this.jmbag = jmbag;
    this.lastName = lastName;
    this.firstName = firstName;
    this.midScore = midScore;
    this.finalScore = finalScore;
    this.labScore = labScore;
    this.grade = grade;
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
   * Returns last name.
   * 
   * @return lastName
   */
  public String getLastName() {
    return lastName;
  }

  /**
   * Returns first name.
   * 
   * @return firstName
   */
  public String getFirstName() {
    return firstName;
  }

  /**
   * Returns midterm exam score
   * 
   * @return midScore
   */
  public double getMidScore() {
    return midScore;
  }

  /**
   * Returns final exam score.
   * 
   * @return finalScore
   */
  public double getFinalScore() {
    return finalScore;
  }

  /**
   * Returns laboratory excerices score.
   * 
   * @return labScore
   */
  public double getLabScore() {
    return labScore;
  }

  /**
   * Returns final grade
   * 
   * @return grade
   */
  public int getGrade() {
    return grade;
  }

  /**
   * Sums midterm score, final score and lab score.
   * 
   * @return sum of scores
   */
  public double scoreSum() {
    return midScore + finalScore + labScore;
  }

}
