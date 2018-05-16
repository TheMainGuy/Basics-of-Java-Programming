package hr.fer.zemris.java.hw02;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Class which defines a complex number having real and imaginary part. It also
 * defines methods for adding, subtracting, multiplying, dividing, powering and
 * rooting complex numbers as well as static methods that create complex numbers
 * from real part, imaginary part, magnitude and angle.
 * 
 * @author tin
 *
 */
public class ComplexNumber {
  private double real;
  private double imaginary;

  /**
   * Constructor which creates complex number fromm real and imaginary part.
   * 
   * @param real real part
   * @param imaginary imaginary part
   */
  public ComplexNumber(double real, double imaginary) {
    this.real = real;
    this.imaginary = imaginary;
  }

  /**
   * Method which creates complex number from real part.
   * 
   * @param real real part
   * @return new complex number created from real part
   */
  public static ComplexNumber fromReal(double real) {
    return new ComplexNumber(real, 0);
  }

  /**
   * Method which creates complex number from imaginary part.
   * 
   * @param imaginary imaginary part
   * @return new complex number created from imaginary part
   */
  public static ComplexNumber fromImaginary(double imaginary) {
    return new ComplexNumber(0, imaginary);
  }

  /**
   * Method which creates complex number from magnitude and angle.
   * 
   * @param magnitude magnitude
   * @param angle angle
   * @return new complex number made from magnitude anad angle
   * @throws IllegalArgumentException if magnitude < 0
   */
  public static ComplexNumber fromMagnitudeAndAngle(double magnitude, double angle) {
    if (magnitude < 0) {
      throw new IllegalArgumentException();
    }
    double real = Math.cos(angle) * magnitude;
    double imaginary = Math.sin(angle) * magnitude;
    return new ComplexNumber(real, imaginary);
  }

  /**
   * Method which creates complex number from string.
   * 
   * @param s complex number written in string
   * @return new complex number made from string
   * @throws IllegalArgumentException if s is not in valid format
   */
  public static ComplexNumber parse(String s) {
    s = s.replaceAll("\\s+", "");
    Pattern pattern = Pattern.compile("(-)?\\d+(\\.\\d+)?(i|([+-]\\d+(\\.\\d+)?i))?");
    Matcher matcher = pattern.matcher(s);
    boolean matches = matcher.matches();
    if (!matches) {
      throw new IllegalArgumentException("String is not in valid format.");
    }

    boolean hasImaginary = false;
    if (s.indexOf("i") > 0) {
      hasImaginary = true;
    }

    String[] parts = s.split("i");

    double real = 0;
    double imaginary = 0;
    if (hasImaginary) {
      parts = parts[0].split("\\+");
      if (parts.length == 2) {
        real = Double.parseDouble(parts[0]);
        imaginary = Double.parseDouble(parts[1]);
      } else if (parts.length == 1) {
        parts = parts[0].split("-");
        if (parts.length == 1) {
          imaginary = Double.parseDouble(parts[0]);
        } else if (parts.length == 3) {
          real = -Double.parseDouble(parts[1]);
          imaginary = -Double.parseDouble(parts[2]);
        } else {
          if (parts[0].length() == 0) {
            imaginary = -Double.parseDouble(parts[1]);
          } else {
            real = Double.parseDouble(parts[0]);
            imaginary = -Double.parseDouble(parts[1]);
          }
        }
      }
    } else {
      parts = parts[0].split("-");
      if (parts.length == 1) {
        real = Double.parseDouble(parts[0]);
      } else {
        real = -Double.parseDouble(parts[1]);
      }
    }

    return new ComplexNumber(real, imaginary);

  }

  /**
   * Method which returns real part of the complex number.
   * 
   * @return real part
   */
  public double getReal() {
    return real;
  }

  /**
   * Method which returns imaginary part of the complex number.
   * 
   * @return imaginary part
   */
  public double getImaginary() {
    return imaginary;
  }

  /**
   * Method which returns magnitude of the complex number in polar form.
   * 
   * @return magnitude
   */
  public double getMagnitude() {
    return Math.sqrt(real * real + imaginary * imaginary);
  }

  /**
   * Method which returns angle of the complex number in polar form.
   * 
   * @return angle
   */
  public double getAngle() {
    double angle = Math.atan(imaginary / real);
    if (real < 0) {
      angle = angle + Math.PI;
    }
    return angle % (Math.PI * 2);
  }

  /**
   * Method which adds complex number c to this complex number.
   * 
   * @param c complex number to be added to this complex number
   * @return result of the add operation
   */
  public ComplexNumber add(ComplexNumber c) {
    return new ComplexNumber(real + c.getReal(), imaginary + c.getImaginary());
  }

  /**
   * Method which subtracts complex number c from this complex number.
   * 
   * @param c complex number to be subtracted from this complex number
   * @return result of the subtract operation
   */
  public ComplexNumber sub(ComplexNumber c) {
    return new ComplexNumber(real - c.getReal(), imaginary - c.getImaginary());
  }

  /**
   * Method which multiplies complex number c with this complex number.
   * 
   * @param c complex number to be multiplied with this complex number
   * @return result of the multiply operation
   */
  public ComplexNumber mul(ComplexNumber c) {
    return ComplexNumber.fromMagnitudeAndAngle(getMagnitude() * c.getMagnitude(), 
        getAngle() + c.getAngle());
  }

  /**
   * Method which divides this complex number by complex number c.
   * 
   * @param c complex number by wich this complex number will be divided
   * @return result of the divide operation
   * @throws IllegalArgumentException if c is 0
   */
  public ComplexNumber div(ComplexNumber c) {
    if (c.getMagnitude() == 0) {
      throw new IllegalArgumentException();
    }
    return ComplexNumber.fromMagnitudeAndAngle(getMagnitude() / c.getMagnitude(), 
        getAngle() - c.getAngle());
  }

  /**
   * Method wich calculates the complex number to the nth power.
   * 
   * @param n nth power
   * @return complex number to the nth power
   * @throws IllegalArgumentException if n < 0
   */
  public ComplexNumber power(int n) {
    if (n < 0) {
      throw new IllegalArgumentException();
    }
    return ComplexNumber.fromMagnitudeAndAngle(Math.pow(getMagnitude(), n), getAngle() * n);
  }

  /**
   * Method which calculates nth root of the complex number.
   * 
   * @param n root number
   * @return array of nth roots
   * @throws IllegalArgumentException if n <= 0
   */
  public ComplexNumber[] root(int n) {
    if (n <= 0) {
      throw new IllegalArgumentException();
    }
    ComplexNumber[] roots = new ComplexNumber[n];
    roots[0] = ComplexNumber.fromMagnitudeAndAngle(Math.pow(getMagnitude(), 1.0 / n), 
        getAngle() / n);
    for (int i = 1; i < n; i++) {
      roots[i] = ComplexNumber.fromMagnitudeAndAngle(roots[i - 1].getMagnitude(),
          roots[i - 1].getAngle() + Math.PI * 2 / n);
    }
    return roots;
  }

  /**
   * Method which returns the string made from the complex number.
   */
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
