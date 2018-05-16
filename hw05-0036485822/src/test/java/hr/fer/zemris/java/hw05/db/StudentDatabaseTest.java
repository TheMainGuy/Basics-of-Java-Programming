package hr.fer.zemris.java.hw05.db;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

public class StudentDatabaseTest {

  @Test
  public void simpleTest() throws IOException {
    List<String> lines = Files.readAllLines(Paths.get("./database.txt"), StandardCharsets.UTF_8);
    StudentDatabase studentDatabase = new StudentDatabase(lines);
    Assert.assertEquals("Mirta", studentDatabase.forJMBAG("0000000014").getFirstName());
    Assert.assertEquals(2, studentDatabase.forJMBAG("0000000001").getFinalGrade());
  }

  @Test
  public void filterTest() throws IOException {
    List<String> lines = Files.readAllLines(Paths.get("./database.txt"), StandardCharsets.UTF_8);
    StudentDatabase studentDatabase = new StudentDatabase(lines);

    List<StudentRecord> records = studentDatabase.filter((record) -> true);
    // Uncomment to print all records
    // for(StudentRecord record : records) {
    // System.out.println(record);
    // }
    Assert.assertEquals(63, records.size());

    List<StudentRecord> records2 = studentDatabase.filter((record) -> false);
    // Uncomment to print nothing :)
    // for(StudentRecord record : records2) {
    // System.out.println(record);
    // }
    Assert.assertEquals(0, records2.size());
  }

  @Test
  public void simpleComparisonOperatorsTest() {
    IComparisonOperator oper = ComparisonOperators.LESS;
    IComparisonOperator oper2 = ComparisonOperators.GREATER_OR_EQUALS;
    IComparisonOperator oper3 = ComparisonOperators.EQUALS;
    Assert.assertTrue(oper.satisfied("Ana", "Jasna"));
    Assert.assertFalse(oper2.satisfied("Ana", "Jasna"));
    Assert.assertTrue(oper3.satisfied("Ana", "Ana"));
  }

  @Test
  public void likeOperator() {
    IComparisonOperator oper = ComparisonOperators.LIKE;
    Assert.assertFalse(oper.satisfied("Zagreb", "Aba*")); // false
    Assert.assertFalse(oper.satisfied("AAA", "AA*AA")); // false
    Assert.assertTrue(oper.satisfied("AAAA", "AA*AA")); // true
  }

  @Test(expected = IllegalStateException.class)
  public void likeWithIllegalExpression() {
    IComparisonOperator oper = ComparisonOperators.LIKE;
    Assert.assertTrue(oper.satisfied("AAAA", "AA*A*A"));
  }
  
  @Test
  public void fieldGetters() {
    StudentRecord record = new StudentRecord("0036485822", "Tin", "Ceraj", 5);
    Assert.assertEquals("0036485822",FieldValueGetters.JMBAG.get(record));
    Assert.assertEquals("Tin", FieldValueGetters.FIRST_NAME.get(record));
    Assert.assertEquals("Ceraj", FieldValueGetters.LAST_NAME.get(record));
  }
  
  @Test
  public void conditionalExpressions() {
    ConditionalExpression expr = new ConditionalExpression(
        FieldValueGetters.LAST_NAME,
        "Bos*",
        ComparisonOperators.LIKE
       );
    
       StudentRecord record = new StudentRecord("0036485822", "Tin", "Ceraj", 5);
       boolean recordSatisfies = expr.getComparisonOperator().satisfied(
        expr.getFieldGetter().get(record), // returns lastName from given record
        expr.getStringLiteral() // returns "Bos*"
       );
       Assert.assertFalse(recordSatisfies);
       
       
       record = new StudentRecord("0036485822", "Tin", "Bosit", 5);
       recordSatisfies = expr.getComparisonOperator().satisfied(
           expr.getFieldGetter().get(record), // returns lastName from given record
           expr.getStringLiteral() // returns "Bos*"
          );
       Assert.assertTrue(recordSatisfies);
  }
}
