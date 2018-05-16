package hr.fer.zemris.java.hw05.db;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import org.junit.Test;

public class QueryFilterTest {
  @Test
  public void basicFilterTest() throws IOException {
    List<String> lines = Files.readAllLines(Paths.get("./database.txt"), StandardCharsets.UTF_8);
    StudentDatabase studentDatabase = new StudentDatabase(lines);
    QueryParser parser = new QueryParser("firstName LIKE \"M*\"");
    if(parser.isDirectQuery()) {
      StudentRecord r = studentDatabase.forJMBAG(parser.getQueriedJMBAG());
      System.out.println(r);
    }
    else {
      for (StudentRecord r : studentDatabase.filter(new QueryFilter(parser.getQuery()))) {
        System.out.println(r);
      }
    }
  }
}
