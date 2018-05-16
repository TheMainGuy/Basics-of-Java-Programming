package hr.fer.zemris.java.custom.scripting.exec;

import org.junit.Assert;
import org.junit.Test;

public class ValueWrapperTest {
  final double DELTA = 0.000001;

  @Test
  public void constructorGetterAndSetter() {
    ValueWrapper wrapper = new ValueWrapper(3);
    Assert.assertEquals(Integer.valueOf(3), (Integer) wrapper.getValue());
    wrapper.setValue(2);
    Assert.assertEquals(Integer.valueOf(2), (Integer) wrapper.getValue());
  }

  @Test
  public void addIntegerValues() {
    ValueWrapper wrapper = new ValueWrapper(3);
    wrapper.add(Integer.valueOf(2));
    Assert.assertEquals(Integer.valueOf(5), (Integer) wrapper.getValue());
  }

  @Test
  public void addDoubleValues() {
    ValueWrapper wrapper = new ValueWrapper(2.4);
    wrapper.add(2.1);
    Assert.assertEquals(Double.valueOf(4.5), (Double) wrapper.getValue(), DELTA);
  }

  @Test
  public void addMixedValues() {
    ValueWrapper wrapper = new ValueWrapper(2);
    wrapper.add(2.1);
    Assert.assertEquals(Double.valueOf(4.1), (Double) wrapper.getValue(), DELTA);
  }

  @Test
  public void subtractIntegerValues() {
    ValueWrapper wrapper = new ValueWrapper(3);
    wrapper.subtract(Integer.valueOf(2));
    Assert.assertEquals(Integer.valueOf(1), (Integer) wrapper.getValue());
  }

  @Test
  public void subtractDoubleValues() {
    ValueWrapper wrapper = new ValueWrapper(2.4);
    wrapper.subtract(2.1);
    Assert.assertEquals(Double.valueOf(0.3), (Double) wrapper.getValue(), DELTA);
  }

  @Test
  public void subtractMixedValues() {
    ValueWrapper wrapper = new ValueWrapper(2);
    wrapper.subtract(2.1);
    Assert.assertEquals(Double.valueOf(-0.1), (Double) wrapper.getValue(), DELTA);
  }

  @Test
  public void multiplyIntegerValues() {
    ValueWrapper wrapper = new ValueWrapper(3);
    wrapper.multiply(Integer.valueOf(2));
    Assert.assertEquals(Integer.valueOf(6), (Integer) wrapper.getValue());
  }

  @Test
  public void multiplyDoubleValues() {
    ValueWrapper wrapper = new ValueWrapper(2.4);
    wrapper.multiply(2.1);
    Assert.assertEquals(Double.valueOf(5.04), (Double) wrapper.getValue(), DELTA);
  }

  @Test
  public void multiplyMixedValues() {
    ValueWrapper wrapper = new ValueWrapper(2);
    wrapper.multiply(2.1);
    Assert.assertEquals(Double.valueOf(4.2), (Double) wrapper.getValue(), DELTA);
  }

  @Test
  public void divideIntegerValues() {
    ValueWrapper wrapper = new ValueWrapper(3);
    wrapper.divide(Integer.valueOf(2));
    Assert.assertEquals(Integer.valueOf(1), (Integer) wrapper.getValue());
  }

  @Test
  public void divideDoubleValues() {
    ValueWrapper wrapper = new ValueWrapper(2.4);
    wrapper.divide(2.1);
    Assert.assertEquals(Double.valueOf(1.14285714), (Double) wrapper.getValue(), DELTA);
  }

  @Test
  public void divideMixedValues() {
    ValueWrapper wrapper = new ValueWrapper(2);
    wrapper.divide(2.1);
    Assert.assertEquals(Double.valueOf(0.9523809), (Double) wrapper.getValue(), DELTA);
  }

  @Test
  public void integerFromString() {
    ValueWrapper wrapper = new ValueWrapper("3");
    wrapper.add(2);
    Assert.assertEquals(Integer.valueOf(5), (Integer) wrapper.getValue());
  }

  @Test
  public void doubleFromString() {
    ValueWrapper wrapper = new ValueWrapper("2.4");
    wrapper.add(-1);
    Assert.assertEquals(Double.valueOf(1.4), (Double) wrapper.getValue(), DELTA);
    wrapper.setValue("1.434E3");
    wrapper.divide(100);
    Assert.assertEquals(Double.valueOf(14.34), (Double) wrapper.getValue(), DELTA);
    wrapper.setValue("115E-2");
    wrapper.add(1);
    Assert.assertEquals(Double.valueOf(2.15), (Double) wrapper.getValue(), DELTA);
  }

  @Test
  public void homeworkExample1() {
    ValueWrapper v1 = new ValueWrapper(null);
    ValueWrapper v2 = new ValueWrapper(null);
    v1.add(v2.getValue()); // v1 now stores Integer(0); v2 still stores null.
    Assert.assertEquals(Integer.valueOf(0), (Integer) v1.getValue());
    Assert.assertNull(v2.getValue());
  }

  @Test
  public void homeworkExample2() {
    ValueWrapper v3 = new ValueWrapper("1.2E1");
    ValueWrapper v4 = new ValueWrapper(Integer.valueOf(1));
    v3.add(v4.getValue()); // v3 now stores Double(13); v4 still stores Integer(1).
    Assert.assertEquals(Double.valueOf(13), (Double) v3.getValue(), DELTA);
    Assert.assertEquals(Integer.valueOf(1), (Integer) v4.getValue());
  }

  @Test
  public void homeworkExample3() {
    ValueWrapper v5 = new ValueWrapper("12");
    ValueWrapper v6 = new ValueWrapper(Integer.valueOf(1));
    v5.add(v6.getValue()); // v5 now stores Integer(13); v6 still stores Integer(1).
    Assert.assertEquals(Integer.valueOf(13), (Integer) v5.getValue());
    Assert.assertEquals(Integer.valueOf(1), (Integer) v6.getValue());
  }

  @Test(expected = IllegalArgumentException.class)
  public void homeworkExample4() {
    ValueWrapper v7 = new ValueWrapper("Ankica");
    ValueWrapper v8 = new ValueWrapper(Integer.valueOf(1));
    v7.add(v8.getValue()); // throws RuntimeException
  }
  
  @Test
  public void integersInString() {
    ValueWrapper v5 = new ValueWrapper(2);
    ValueWrapper v6 = new ValueWrapper("30");
    int x = v5.numCompare(v6.getValue());
    if(x<0) {
      x = -1;
    }
    else if (x > 0) {
      x = 1;
    }
    else {
      x = 0;
    }
    Assert.assertEquals(-1, x);
  }

}
