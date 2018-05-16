package hr.fer.zemris.java.hw05.db;

import org.junit.Assert;
import org.junit.Test;


public class QueryParserTest {
  @Test
  public void simpleQueryParserTest() {
  QueryParser qp1 = new QueryParser(" jmbag =\"0123456789\" ");
  Assert.assertTrue(qp1.isDirectQuery());
  Assert.assertEquals("0123456789", qp1.getQueriedJMBAG());
  Assert.assertEquals(1, qp1.getQuery().size());
  }
  
  @Test(expected=IllegalStateException.class)
  public void throwingExceptions() {
    QueryParser qp2 = new QueryParser("jmbag=\"0123456789\" and lastName>\"J\"");
    Assert.assertFalse(qp2.isDirectQuery());
    qp2.getQueriedJMBAG(); // would throw!
  }
}
