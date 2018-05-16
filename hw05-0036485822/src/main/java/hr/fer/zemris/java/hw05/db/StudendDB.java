package hr.fer.zemris.java.hw05.db;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

/**
 * Main program which utilizes all classes made for 5th OPJJ homework 2018.
 * Gives user query prompt in which user should type queries in: "query
 * expressions-separated-by-and-operator" format. Expression is combination of
 * database field, comparison operator and string literal with which database
 * field will be compared. Supported operators are: <, <=, >, >=, =, !=.
 * 
 * @author tin
 *
 */
public class StudendDB {

  private static final int JMBAG_LENGTH = 10;

  /**
   * Method starts when program starts.
   * 
   * @param args command line arguments
   */
  public static void main(String[] args) {
    // Reads file database.txt
    List<String> lines = null;
    try {
      lines = Files.readAllLines(Paths.get("./database.txt"), StandardCharsets.UTF_8);
    } catch (IOException e) {
      System.out.println("File: database.txt can not be read.");
    }

    // Creates student database.
    StudentDatabase studentDatabase = new StudentDatabase(lines);

    Scanner scanner = new Scanner(System.in);
    while (true) {
      System.out.print("> ");
      String inputLine = scanner.nextLine();
      if(inputLine.equals("exit")) {
        System.out.println("Goodbye!");
        break;
      }
      if((inputLine = trimQuery(inputLine)).equals("")) {
        System.out.println("Your query is not in valid format.");
        continue;
      }
      QueryParser parser = null;
      try {
         parser = new QueryParser(inputLine);
      } catch (Exception e) {
        System.out.println("Your expression is not valid. " + e.getMessage());
        continue;
      }
      

      if(parser.isDirectQuery()) {
        StudentRecord record = studentDatabase.forJMBAG(parser.getQueriedJMBAG());
        List<StudentRecord> records = new ArrayList<>(1);
        records.add(record);
        formatAndPrint(records);
      }
      else {
        try {
        formatAndPrint(studentDatabase.filter(new QueryFilter(parser.getQuery())));
        } catch (Exception e) {
          System.out.println("Your expression is not valid. " + e.getMessage());
        }
      }
    }
    scanner.close();

  }

  /**
   * Trims out word "query" from query command. Leaves only expressions. Returns
   * empty string if query is not on start of the input line.
   * 
   * @param inputLine input line
   * @return input line without word "query"
   */
  private static String trimQuery(String inputLine) {
    if(inputLine.length() < 5) {
      return "";
    }
    if(inputLine.substring(0, 5).equals("query")) {
      return inputLine.substring(5, inputLine.length());
    }
    return "";
  }

  /**
   * Formats result elements in table using +, = and | as table lines in format:
   * 
   * <pre>
   * +===========+====+========+======+=======+
   * | something | is | inside | this | table |
   * +===========+====+========+======+=======+
   * </pre>
   * 
   * 
   * @param records {@link List} of student records to be printed in a table
   */
  private static void formatAndPrint(List<StudentRecord> records) {
    int longestFirstName = 0;
    int longestLastName = 0;
    
    Collections.sort(records);
    
    for (StudentRecord record : records) {
      if(record == null) {
        System.out.println("Record not found.");
        return;
      }
      if(record.getFirstName().length() > longestFirstName) {
        longestFirstName = record.getFirstName().length();
      }
      if(record.getLastName().length() > longestLastName) {
        longestLastName = record.getLastName().length();
      }
    }
    String tableLine = getTableLine(longestLastName, longestFirstName);
    for (StudentRecord record : records) {
      System.out.println(tableLine);
      printRecordLine(record, longestLastName, longestFirstName);
    }
    if(records.size() != 0) {
      System.out.println(tableLine);
    }
    System.out.println("Records selected: " + records.size());
    System.out.println();
  }

  /**
   * Prints one record line in table. Each element is separated by | symbol and is
   * in format:
   * 
   * <pre>
   * | something | is | in | this | table |
   * </pre>
   * 
   * Each column is aligned to longest element in that column.
   * 
   * @param record student record to be printed 
   * @param longestLastName longest last name element in table
   * @param longestFirstName longest first name element in table
   */
  private static void printRecordLine(StudentRecord record, int longestLastName, int longestFirstName) {
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("| ").append(record.getJmbag()).append(" | ").append(record.getLastName());
    for(int i = record.getLastName().length(); i < longestLastName; i++) {
      stringBuilder.append(" ");
    }
    stringBuilder.append(" | ").append(record.getFirstName());
    for(int i = record.getFirstName().length(); i < longestFirstName; i++) {
      stringBuilder.append(" ");
    }
    stringBuilder.append(" | ").append(record.getFinalGrade()).append(" |");
    System.out.println(stringBuilder.toString());
  }

  /**
   * From longest last and first name creates horizontal table lines made of + and
   * =. Table always has 2 more = than longest attribute in that column.
   * 
   * @param longestLastName length of longest last name
   * @param longestFirstName length of longest first name
   * @return horizontal table line
   */
  private static String getTableLine(int longestLastName, int longestFirstName) {
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("+");
    for (int i = 0; i < JMBAG_LENGTH + 2; i++) {
      stringBuilder.append("=");
    }
    stringBuilder.append("+");
    for (int i = 0; i < longestLastName + 2; i++) {
      stringBuilder.append("=");
    }
    stringBuilder.append("+");

    for (int i = 0; i < longestFirstName + 2; i++) {
      stringBuilder.append("=");
    }
    stringBuilder.append("+===+");

    return stringBuilder.toString();
  }

}
