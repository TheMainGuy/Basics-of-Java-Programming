package hr.fer.zemris.java.hw06.demo4;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * This class implements solution for 4th assignment in 6th OPJJ homework.
 * 
 * @author tin
 *
 */
public class StudentDemo {
  /**
   * Method which is called when program starts.
   * 
   * @param args command line arguments
   */
  public static void main(String[] args) {
    List<StudentRecord> records = null;
    try {
      List<String> lines = Files.readAllLines(Paths.get("./studenti.txt"), StandardCharsets.UTF_8);
      records = convert(lines);
    } catch (IOException e) {
      System.out.println(e.getMessage());
    }
    long broj = vratiBodovaViseOd25(records);

    System.out.println(broj);

    long broj5 = vratiBrojOdlikasa(records);

    System.out.println(broj5);

    List<StudentRecord> odlikasi = vratiListuOdlikasa(records);

    for (StudentRecord odlikas : odlikasi) {
      System.out.println(odlikas.getLastName() + " " + odlikas.getGrade());
    }

    List<StudentRecord> odlikasiSortirano = vratiSortiranuListuOdlikasa(records);

    for (StudentRecord odlikas : odlikasiSortirano) {
      System.out.println(odlikas.scoreSum() + " " + odlikas.getGrade());
    }

    List<String> nepolozeniJMBAGovi = vratiPopisNepolozenih(records);

    for (String nepolozenJMBAG : nepolozeniJMBAGovi) {
      System.out.println(nepolozenJMBAG);
    }

    Map<Integer, List<StudentRecord>> mapaPoOcjenama = razvrstajStudentePoOcjenama(records);

    for (int ocjena : mapaPoOcjenama.keySet()) {
      System.out.println(ocjena + " " + mapaPoOcjenama.get(ocjena).size());
    }

    Map<Integer, Integer> mapaPoOcjenama2 = vratiBrojStudenataPoOcjenama(records);

    for (int ocjena : mapaPoOcjenama2.keySet()) {
      System.out.println(ocjena + " " + mapaPoOcjenama2.get(ocjena));
    }

    Map<Boolean, List<StudentRecord>> prolazNeprolaz = razvrstajProlazPad(records);

    for (boolean b : prolazNeprolaz.keySet()) {
      System.out.println(b + " " + prolazNeprolaz.get(b).size());
    }
  }

  /**
   * Creates {@link Map} with {@link Boolean} keys and {@link List} of
   * {@link StudentRecord}s. Students which passed the class are mapped to
   * <code>true</code> and those who failed are mapped to <code>false</code>.
   * Student failed class if his or her {@link StudentRecord} stores grade value
   * of 1 and passed class if grade value is greater than 1.
   * 
   * @param records student records
   * @return {@link Map} of students mapped to <code>true</code> or
   *         <code>false</code> depending if they passed or failed the class
   *         respectively
   */
  private static Map<Boolean, List<StudentRecord>> razvrstajProlazPad(List<StudentRecord> records) {
    return records.stream().collect(Collectors.partitioningBy(s -> ((StudentRecord) s).getGrade() > 1));
  }

  /**
   * Creates {@link Map} with {@link Integer} keys and values. Keys are student
   * grades and values are number of student with mapped grade.
   * 
   * @param records student records
   * @return {@link Map} of number of students mapped to each grade
   */
  private static Map<Integer, Integer> vratiBrojStudenataPoOcjenama(List<StudentRecord> records) {
    return records.stream().collect(Collectors.toMap(StudentRecord::getGrade, s -> 1, (s, k) -> s + 1));
  }

  /**
   * Creates {@link Map} with {@link Integer} keys and {@link List} of
   * {@link StudentRecord} values. Each {@link List} contains all
   * {@link StudentRecord}s which have mapped grade.
   * 
   * @param records student records
   * @return {@link Map} of {@link List}s of {@link StudentRecord}s mapped to
   *         grade they have
   */
  private static Map<Integer, List<StudentRecord>> razvrstajStudentePoOcjenama(List<StudentRecord> records) {
    return records.stream().collect(Collectors.groupingBy(StudentRecord::getGrade));
  }

  /**
   * Creates {@link List} of jmbags of students who failed the class. Student
   * failed class if his or her {@link StudentRecord} stores grade value of 1.
   * 
   * @param records student records
   * @return {@link List} of jmbags of students who failed the class
   */
  private static List<String> vratiPopisNepolozenih(List<StudentRecord> records) {
    return records.stream().filter(s -> s.getGrade() == 1).map(s -> s.getJmbag()).sorted((s, k) -> s.compareTo(k))
        .collect(Collectors.toList());
  }

  /**
   * Creates sorted {@link List} of {@link StudentRecord}s of students who passed
   * the class with grade 5. They are sorted in descending order of total score.
   * 
   * @param records student records
   * @return Sorted {@link List} of {@link StudentRecord}s of students who passed
   *         the class with grade 5
   */
  private static List<StudentRecord> vratiSortiranuListuOdlikasa(List<StudentRecord> records) {
    return records.stream().filter(s -> s.getGrade() == 5)
        .sorted((s, k) -> Double.valueOf(k.scoreSum()).compareTo(s.scoreSum())).collect(Collectors.toList());
  }

  /**
   * Creates {@link List} of {@link StudentRecord}s of students who passed the
   * class with grade 5.
   * 
   * @param records student records
   * @return {@link List} of {@link StudentRecord}s of students who passed the
   *         class with grade 5
   */
  private static List<StudentRecord> vratiListuOdlikasa(List<StudentRecord> records) {
    return records.stream().filter(s -> s.getGrade() == 5).collect(Collectors.toList());
  }

  /**
   * Counts the number of students who passed the class with grade 5.
   * 
   * @param records student records
   * @return number of students who passed the class with grade 5
   */
  private static long vratiBrojOdlikasa(List<StudentRecord> records) {
    return records.stream().filter(s -> s.getGrade() == 5).count();
  }

  /**
   * Counts the number of students who accumulated more than 25 points over the
   * semester.
   * 
   * @param records student records
   * @return number of students whose total score is greater than 25
   */
  private static long vratiBodovaViseOd25(List<StudentRecord> records) {
    return records.stream().filter(s -> s.scoreSum() > 25).count();

  }

  /**
   * Helper method used to convert {@link List} of {@link String}s to {@link List}
   * of {@link StudentRecord}s. Recieves student record in each string with record
   * data separated by tab character.
   * 
   * @param lines {@link List} of student records stored as {@link String}s
   * @return {@link List} of student records stored in {@link StudentRecord}s
   */
  private static List<StudentRecord> convert(List<String> lines) {
    List<StudentRecord> records = new ArrayList<>();
    for (String line : lines) {
      String[] record = line.split("\t");
      if(record.length < 7) {
        break;
      }
      records.add(new StudentRecord(record[0], record[1], record[2], Double.parseDouble(record[3]),
          Double.parseDouble(record[4]), Double.parseDouble(record[5]), Integer.parseInt(record[6])));
    }
    return records;
  }
}
