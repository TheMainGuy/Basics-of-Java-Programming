package hr.fer.zemris.java.hw02;

import org.junit.Assert;
import org.junit.Test;


public class ComplexNumberTest {
  private static final double DELTA = 0.0001;
  double x = 4;
  double y = 5;
  ComplexNumber number = new ComplexNumber(x, y);

  @Test
  public void baicGetters() {
    Assert.assertEquals(x, number.getReal(), 0);
    Assert.assertEquals(y, number.getImaginary(), 0);
  }

  @Test
  public void parseAllCombinations() {
    number = ComplexNumber.parse("-2.1-3i");
    Assert.assertEquals(-2.1, number.getReal(), 0);
    Assert.assertEquals(-3, number.getImaginary(), 0);
    number = ComplexNumber.parse("3.7i");
    Assert.assertEquals(0, number.getReal(), 0);
    Assert.assertEquals(3.7, number.getImaginary(), 0);
    number = ComplexNumber.parse("2i");
    Assert.assertEquals(0, number.getReal(), 0);
    Assert.assertEquals(2, number.getImaginary(), 0);
    number = ComplexNumber.parse("2.5-3i");
    Assert.assertEquals(2.5, number.getReal(), 0);
    Assert.assertEquals(-3, number.getImaginary(), 0);
    number = ComplexNumber.parse("-4");
    Assert.assertEquals(-4, number.getReal(), 0);
    Assert.assertEquals(0, number.getImaginary(), 0);
    number = ComplexNumber.parse("-2.45i");
    Assert.assertEquals(0, number.getReal(), 0);
    Assert.assertEquals(-2.45, number.getImaginary(), 0);
    number = ComplexNumber.parse("-2.1+3i");
    Assert.assertEquals(-2.1, number.getReal(), 0);
    Assert.assertEquals(3, number.getImaginary(), 0);
    number = ComplexNumber.parse("2.1+3i");
    Assert.assertEquals(2.1, number.getReal(), 0);
    Assert.assertEquals(3, number.getImaginary(), 0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void parseIllegalArgument() {
    ComplexNumber.parse("2.1i-3i");
  }

  @Test
  public void fromRealAndImaginery() {
    number = ComplexNumber.fromReal(x);
    Assert.assertEquals(x, number.getReal(), 0);
    Assert.assertEquals(0, number.getImaginary(), 0);

    number = ComplexNumber.fromImaginary(x);
    Assert.assertEquals(0, number.getReal(), 0);
    Assert.assertEquals(x, number.getImaginary(), 0);
  }

  @Test
  public void fromMagnitudeAndAngle() {
    number = ComplexNumber.fromMagnitudeAndAngle(1, 0);
    Assert.assertEquals(1, number.getReal(), 0);
    Assert.assertEquals(0, number.getImaginary(), 0);

    number = ComplexNumber.fromMagnitudeAndAngle(1.4, 1);
    Assert.assertEquals(Math.cos(1) * 1.4, number.getReal(), 0);
    Assert.assertEquals(Math.sin(1) * 1.4, number.getImaginary(), 0);

    number = ComplexNumber.fromMagnitudeAndAngle(3.4, 2);
    Assert.assertEquals(-Math.cos(Math.PI - 2) * 3.4, number.getReal(), DELTA);
    Assert.assertEquals(Math.sin(Math.PI - 2) * 3.4, number.getImaginary(), DELTA);

    number = ComplexNumber.fromMagnitudeAndAngle(3.4, 4);
    Assert.assertEquals(-Math.cos(4 - Math.PI) * 3.4, number.getReal(), DELTA);
    Assert.assertEquals(-Math.sin(4 - Math.PI) * 3.4, number.getImaginary(), DELTA);

    number = ComplexNumber.fromMagnitudeAndAngle(3.4, 6);
    Assert.assertEquals(Math.cos(2 * Math.PI - 6) * 3.4, number.getReal(), DELTA);
    Assert.assertEquals(-Math.sin(2 * Math.PI - 6) * 3.4, number.getImaginary(), DELTA);
  }

  @Test
  public void getMagnitudeAndGetAngle() {
    number = ComplexNumber.fromMagnitudeAndAngle(1.4, 1);
    Assert.assertEquals(1.4, number.getMagnitude(), 0);
    Assert.assertEquals(1, number.getAngle(), DELTA);
  }

  @Test
  public void add() {
    ComplexNumber number2 = ComplexNumber.parse("-2.1-3i");
    ComplexNumber number3 = number.add(number2);
    Assert.assertEquals(1.9, number3.getReal(), 0);
    Assert.assertEquals(2, number3.getImaginary(), 0);
  }

  @Test
  public void sub() {
    ComplexNumber number2 = ComplexNumber.parse("-2.1-3i");
    ComplexNumber number3 = number.sub(number2);
    Assert.assertEquals(6.1, number3.getReal(), 0);
    Assert.assertEquals(8, number3.getImaginary(), 0);
  }

  @Test
  public void mul() {
    ComplexNumber number2 = ComplexNumber.fromMagnitudeAndAngle(3, 30 * Math.PI / 180);
    ComplexNumber number3 = ComplexNumber.fromMagnitudeAndAngle(3, 30 * Math.PI / 180);
    ComplexNumber number4 = number2.mul(number3);
    Assert.assertEquals(9, number4.getMagnitude(), DELTA);
    Assert.assertEquals(60 * Math.PI / 180, number4.getAngle(), DELTA);

    number2 = new ComplexNumber(-2.1, -3);
    Assert.assertEquals(3.66196668472, number2.getMagnitude(), DELTA);
    Assert.assertEquals(4.101663369, number2.getAngle(), DELTA);
    number3 = number.mul(number2);
    Assert.assertEquals(6.6, number3.getReal(), DELTA);
    Assert.assertEquals(-22.5, number3.getImaginary(), DELTA);
  }

  @Test
  public void div() {
    ComplexNumber number2 = ComplexNumber.parse("-2.1-3i");
    ComplexNumber number3 = number.div(number2);
    Assert.assertEquals(-1.74496644, number3.getReal(), DELTA);
    Assert.assertEquals(0.111856823, number3.getImaginary(), DELTA);
  }

  @Test(expected = IllegalArgumentException.class)
  public void divByZero() {
    ComplexNumber number2 = ComplexNumber.parse("0");
    number.div(number2);
  }

  @Test
  public void power() {
    ComplexNumber number2 = number.power(3);
    Assert.assertEquals(-236, number2.getReal(), DELTA);
    Assert.assertEquals(115, number2.getImaginary(), DELTA);

    number2 = ComplexNumber.parse("-2.1-3i");
    number2 = number2.power(5);
    Assert.assertEquals(-57.85101, number2.getReal(), DELTA);
    Assert.assertEquals(655.9785, number2.getImaginary(), DELTA);
  }

  @Test(expected = IllegalArgumentException.class)
  public void powerIllegalArgument() {
    number.power(-1);
  }

  @Test
  public void root() {
    ComplexNumber[] roots = new ComplexNumber[2];
    roots[0] = new ComplexNumber(2.2806933, 1.0961578);
    roots[1] = new ComplexNumber(-2.2806933, -1.0961578);
    ComplexNumber[] number2 = number.root(2);
    Assert.assertEquals(roots[0].getReal(), number2[0].getReal(), DELTA);
    Assert.assertEquals(roots[0].getImaginary(), number2[0].getImaginary(), DELTA);
    Assert.assertEquals(roots[1].getReal(), number2[1].getReal(), DELTA);
    Assert.assertEquals(roots[1].getImaginary(), number2[1].getImaginary(), DELTA);

    ComplexNumber number3 = ComplexNumber.parse("-2.1-3i");
    number2 = number3.root(3);
    ComplexNumber[] roots2 = new ComplexNumber[3];
    roots2[0] = new ComplexNumber(0.311621, 1.50953);
    roots2[1] = new ComplexNumber(-1.4631, -0.484895);
    roots2[2] = new ComplexNumber(1.151484, -1.024637);
    Assert.assertEquals(roots2[0].getReal(), number2[0].getReal(), DELTA);
    Assert.assertEquals(roots2[0].getImaginary(), number2[0].getImaginary(), DELTA);
    Assert.assertEquals(roots2[1].getReal(), number2[1].getReal(), DELTA);
    Assert.assertEquals(roots2[1].getImaginary(), number2[1].getImaginary(), DELTA);
    Assert.assertEquals(roots2[2].getReal(), number2[2].getReal(), DELTA);
    Assert.assertEquals(roots2[2].getImaginary(), number2[2].getImaginary(), DELTA);
  }

  @Test(expected = IllegalArgumentException.class)
  public void rootIllegalArgument() {
    number.root(0);
  }

  @Test
  public void toStringTest() {
    Assert.assertEquals("4.0+5.0i", number.toString());
    number = ComplexNumber.parse("-2.1-3i");
    Assert.assertEquals("-2.1-3.0i", number.toString());
    number = ComplexNumber.fromImaginary(5);
    Assert.assertEquals("5.0i", number.toString());
    number = ComplexNumber.fromImaginary(-2);
    Assert.assertEquals("-2.0i", number.toString());
    number = ComplexNumber.fromReal(1);
    Assert.assertEquals("1.0", number.toString());
    number = new ComplexNumber(0, 0);
    Assert.assertEquals("0.0", number.toString());
  }

}
