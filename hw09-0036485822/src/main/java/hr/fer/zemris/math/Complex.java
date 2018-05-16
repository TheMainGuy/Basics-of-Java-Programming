package hr.fer.zemris.math;

import java.util.ArrayList;
import java.util.List;

/**
 * Class which defines a complex number having real and imaginary part. It also
 * defines methods for module, adding, subtracting, multiplying, dividing,
 * negating, powering and rooting complex numbers as well as static constants
 * that create complex numbers from real part, imaginary part, magnitude and
 * angle.
 * 
 * @author tin
 *
 */
public class Complex {
  /**
   * Defines complex number with both real and imaginary part equal to zero.
   */
  public static final Complex ZERO = new Complex(0, 0);

  /**
   * Defines complex number with real part equal to one.
   */
  public static final Complex ONE = new Complex(1, 0);

  /**
   * Defines complex number with real part equal to minus one.
   */
  public static final Complex ONE_NEG = new Complex(-1, 0);

  /**
   * Defines complex number with imaginary part equal to one.
   */
  public static final Complex IM = new Complex(0, 1);

  /**
   * Defines complex number with imaginary part equal to minus one.
   */
  public static final Complex IM_NEG = new Complex(0, -1);

  /**
   * Real part of complex number.
   */
  private final double real;

  /**
   * Imaginary part of complex number
   */
  private final double imaginary;

  /**
   * Constructor.
   */
  public Complex() {
    real = 0;
    imaginary = 0;
  }

  /**
   * Constructor.
   * 
   * @param re real part
   * @param im imaginary part
   */
  public Complex(double re, double im) {
    this.real = re;
    this.imaginary = im;
  }

  /**
   * Calculates module of this complex number.
   * 
   * @return module
   */
  public double module() {
    return Math.sqrt(real * real + imaginary * imaginary);
  }

  /**
   * Creates a new {@link Complex} number by multiplying complex number c with
   * this complex number.
   * 
   * @param c {@link Complex} number to be multiplied with this complex number
   * @return result of the multiply operation
   */
  public Complex multiply(Complex c) {
    return new Complex(real * c.getReal() - imaginary * c.getImaginary(),
        real * c.getImaginary() + imaginary * c.getReal());
  }

  /**
   * Creates a new {@link Complex} number by dividing this complex number by
   * complex number c.
   * 
   * @param c complex number by wich this complex number will be divided
   * @return result of the divide operation
   * @throws IllegalArgumentException if c is 0
   */
  public Complex divide(Complex c) {
    if(c.module() == 0) {
      throw new IllegalArgumentException();
    }

    double divisor = c.getReal() * c.getReal() + c.getImaginary() * c.getImaginary();
    Complex numerator = multiply(new Complex(c.getReal(), -c.getImaginary()));
    return new Complex(numerator.getReal() / divisor, numerator.getImaginary() / divisor);
  }

  /**
   * Method which adds complex number c to this complex number.
   * 
   * @param c complex number to be added to this complex number
   * @return result of the add operation
   */
  public Complex add(Complex c) {
    return new Complex(real + c.getReal(), imaginary + c.getImaginary());
  }

  /**
   * Method which subtracts complex number c from this complex number.
   * 
   * @param c complex number to be subtracted from this complex number
   * @return result of the subtract operation
   */
  public Complex sub(Complex c) {
    return new Complex(real - c.getReal(), imaginary - c.getImaginary());
  }

  /**
   * Creates a new {@link Complex} number by negating this complex number.
   * 
   * @return result of negating this complex number
   */
  public Complex negate() {
    return new Complex(-real, -imaginary);
  }

  /**
   * Method wich calculates the complex number to the nth power.
   * 
   * @param n nth power
   * @return complex number to the nth power
   * @throws IllegalArgumentException if n < 0
   */
  public Complex power(int n) {
    if (n < 0) {
      throw new IllegalArgumentException();
    }
    return Complex.fromMagnitudeAndAngle(Math.pow(module(), n), getAngle() * n);
  }

  /**
   * Method which calculates nth root of the complex number.
   * 
   * @param n root number
   * @return array of nth roots
   * @throws IllegalArgumentException if n <= 0
   */
  public List<Complex> root(int n) {
    if(n <= 0) {
      throw new IllegalArgumentException();
    }
    List<Complex> roots = new ArrayList<>(n);
    roots.add(Complex.fromMagnitudeAndAngle(Math.pow(module(), 1.0 / n), getAngle() / n));
    for (int i = 1; i < n; i++) {
      roots.add(Complex.fromMagnitudeAndAngle(roots.get(i-1).module(),
          roots.get(i-1).getAngle() + Math.PI * 2 / n));
    }
    return roots;
  }

  /**
   * Method which creates complex number from magnitude and angle.
   * 
   * @param magnitude magnitude
   * @param angle angle
   * @return new complex number made from magnitude anad angle
   * @throws IllegalArgumentException if magnitude < 0
   */
  public static Complex fromMagnitudeAndAngle(double magnitude, double angle) {
    if (magnitude < 0) {
      throw new IllegalArgumentException();
    }
    double real = Math.cos(angle) * magnitude;
    double imaginary = Math.sin(angle) * magnitude;
    return new Complex(real, imaginary);
  }

  /**
   * Method which returns angle of the complex number in polar form.
   * 
   * @return angle
   */
  public double getAngle() {
    double angle = Math.atan(imaginary / real);
    if(real < 0) {
      angle = angle + Math.PI;
    }
    angle = angle % (Math.PI * 2);
    if(angle < 0) {
      angle += 2 * Math.PI;
    }
    return angle;
  }

  /**
   * Returns real part.
   * 
   * @return real part
   */
  public double getReal() {
    return real;
  }

  /**
   * Returns imaginary part.
   * 
   * @return imaginary part
   */
  public double getImaginary() {
    return imaginary;
  }
  
  @Override
  public String toString() {
    if (real == 0 && imaginary == 0) {
      return "0.0";
    }
    String s = "";
    if (real != 0) {
      s += real;
    }
    if (real != 0 && imaginary > 0) {
      s += "+";
    }
    if (imaginary != 0) {
      s += imaginary + "i";
    }

    return s;
  }
}
