package hr.fer.zemris.math;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import hr.fer.zemris.math.Complex;


public class ComplexTest {
  private static final double DELTA = 0.0001;
  double x = 4;
  double y = 5;
  Complex number = new Complex(x, y);

  @Test
  public void baicGetters() {
    Assert.assertEquals(x, number.getReal(), 0);
    Assert.assertEquals(y, number.getImaginary(), 0);
  }

  @Test
  public void fromMagnitudeAndAngle() {
    number = Complex.fromMagnitudeAndAngle(1, 0);
    Assert.assertEquals(1, number.getReal(), 0);
    Assert.assertEquals(0, number.getImaginary(), 0);

    number = Complex.fromMagnitudeAndAngle(1.4, 1);
    Assert.assertEquals(Math.cos(1) * 1.4, number.getReal(), 0);
    Assert.assertEquals(Math.sin(1) * 1.4, number.getImaginary(), 0);

    number = Complex.fromMagnitudeAndAngle(3.4, 2);
    Assert.assertEquals(-Math.cos(Math.PI - 2) * 3.4, number.getReal(), DELTA);
    Assert.assertEquals(Math.sin(Math.PI - 2) * 3.4, number.getImaginary(), DELTA);

    number = Complex.fromMagnitudeAndAngle(3.4, 4);
    Assert.assertEquals(-Math.cos(4 - Math.PI) * 3.4, number.getReal(), DELTA);
    Assert.assertEquals(-Math.sin(4 - Math.PI) * 3.4, number.getImaginary(), DELTA);

    number = Complex.fromMagnitudeAndAngle(3.4, 6);
    Assert.assertEquals(Math.cos(2 * Math.PI - 6) * 3.4, number.getReal(), DELTA);
    Assert.assertEquals(-Math.sin(2 * Math.PI - 6) * 3.4, number.getImaginary(), DELTA);
  }

  @Test
  public void moduleAndGetAngle() {
    number = Complex.fromMagnitudeAndAngle(1.4, 1);
    Assert.assertEquals(1.4, number.module(), 0);
    Assert.assertEquals(1, number.getAngle(), DELTA);
  }

  @Test
  public void add() {
    Complex number2 = new Complex(-2.1, -3);
    Complex number3 = number.add(number2);
    Assert.assertEquals(1.9, number3.getReal(), 0);
    Assert.assertEquals(2, number3.getImaginary(), 0);
  }

  @Test
  public void sub() {
    Complex number2 = new Complex(-2.1, -3);
    Complex number3 = number.sub(number2);
    Assert.assertEquals(6.1, number3.getReal(), 0);
    Assert.assertEquals(8, number3.getImaginary(), 0);
  }

  @Test
  public void multiply() {
    Complex number2 = Complex.fromMagnitudeAndAngle(3, 30 * Math.PI / 180);
    Complex number3 = Complex.fromMagnitudeAndAngle(3, 30 * Math.PI / 180);
    Complex number4 = number2.multiply(number3);
    Assert.assertEquals(9, number4.module(), DELTA);
    Assert.assertEquals(60 * Math.PI / 180, number4.getAngle(), DELTA);

    number2 = new Complex(-2.1, -3);
    Assert.assertEquals(3.66196668472, number2.module(), DELTA);
    Assert.assertEquals(4.101663369, number2.getAngle(), DELTA);
    number3 = number.multiply(number2);
    Assert.assertEquals(6.6, number3.getReal(), DELTA);
    Assert.assertEquals(-22.5, number3.getImaginary(), DELTA);
  }

  @Test
  public void divide() {
    Complex number2 = new Complex(-2.1, -3);
    Complex number3 = number.divide(number2);
    Assert.assertEquals(-1.74496644, number3.getReal(), DELTA);
    Assert.assertEquals(0.111856823, number3.getImaginary(), DELTA);
  }

  @Test(expected = IllegalArgumentException.class)
  public void divByZero() {
    Complex number2 = Complex.ZERO;
    number.divide(number2);
  }

  @Test
  public void power() {
    Complex number2 = number.power(3);
    Assert.assertEquals(-236, number2.getReal(), DELTA);
    Assert.assertEquals(115, number2.getImaginary(), DELTA);

    number2 = new Complex(-2.1, -3);
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
    Complex[] roots = new Complex[2];
    roots[0] = new Complex(2.2806933, 1.0961578);
    roots[1] = new Complex(-2.2806933, -1.0961578);
    List<Complex> number2 = number.root(2);
    Assert.assertEquals(roots[0].getReal(), number2.get(0).getReal(), DELTA);
    Assert.assertEquals(roots[0].getImaginary(), number2.get(0).getImaginary(), DELTA);
    Assert.assertEquals(roots[1].getReal(), number2.get(1).getReal(), DELTA);
    Assert.assertEquals(roots[1].getImaginary(), number2.get(1).getImaginary(), DELTA);

    Complex number3 = new Complex(-2.1, -3);
    number2 = number3.root(3);
    Complex[] roots2 = new Complex[3];
    roots2[0] = new Complex(0.311621, 1.50953);
    roots2[1] = new Complex(-1.4631, -0.484895);
    roots2[2] = new Complex(1.151484, -1.024637);
    Assert.assertEquals(roots2[0].getReal(), number2.get(0).getReal(), DELTA);
    Assert.assertEquals(roots2[0].getImaginary(), number2.get(0).getImaginary(), DELTA);
    Assert.assertEquals(roots2[1].getReal(), number2.get(1).getReal(), DELTA);
    Assert.assertEquals(roots2[1].getImaginary(), number2.get(1).getImaginary(), DELTA);
    Assert.assertEquals(roots2[2].getReal(), number2.get(2).getReal(), DELTA);
    Assert.assertEquals(roots2[2].getImaginary(), number2.get(2).getImaginary(), DELTA);
  }

  @Test(expected = IllegalArgumentException.class)
  public void rootIllegalArgument() {
    number.root(0);
  }

  @Test
  public void toStringTest() {
    Assert.assertEquals("4.0+5.0i", number.toString());
    number = new Complex(-2.1, -3);
    Assert.assertEquals("-2.1-3.0i", number.toString());
    number = new Complex(0, 5);
    Assert.assertEquals("5.0i", number.toString());
    number = new Complex(0, -2);
    Assert.assertEquals("-2.0i", number.toString());
    number = Complex.ONE;
    Assert.assertEquals("1.0", number.toString());
    number = new Complex(0, 0);
    Assert.assertEquals("0.0", number.toString());
  }

}
