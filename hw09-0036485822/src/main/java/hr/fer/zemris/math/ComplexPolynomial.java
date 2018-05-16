package hr.fer.zemris.math;

import java.util.ArrayList;
import java.util.List;

/**
 * Implements polynomial over complex numbers. Each given value is factor in
 * polynomial order. First value in {@link List} given in constructor is factor
 * multiplying z to the power of zero. Each subsequent value i is multiplying z
 * to the power of i.
 * 
 * @author tin
 *
 */
public class ComplexPolynomial {
  /**
   * List of factors in polynomial order.
   */
  private List<Complex> factors;

  /**
   * Constructor.
   * 
   * @param factors factors
   * @throws IllegalArgumentException if there are no factors
   */
  public ComplexPolynomial(List<Complex> factors) {
    if(factors.size() == 0) {
      throw new IllegalArgumentException("There must be at least one factor value.");
    }
    for (Complex factor : factors) {
      if(factor == null) {
        throw new NullPointerException("Factor at index " + factors.indexOf(factor) + " is null.");
      }
    }
    this.factors = factors;
  }

  /**
   * Returns polynomial order.
   * 
   * @return polynomial order
   */
  public short order() {
    return (short) (factors.size() - 1);
  }

  /**
   * Multiplies 2 {@link ComplexPolynomial}s. Result is new polynomial that is the
   * result of multiplying this polynomial with polynomial p.
   * 
   * @param p other polynomial
   * @return new {@link ComplexPolynomial} that is the result of multiplication
   */
  public ComplexPolynomial multiply(ComplexPolynomial p) {
    List<Complex> result = new ArrayList<>();

    for (int i = 0, n = factors.size() + p.factors.size() - 1; i < n; i++) {
      Complex number = Complex.ZERO;
      for (int j = 0; j <= i; j++) {
        if(factors.size() <= j || p.factors.size() <= i - j) {
          continue;
        }
        number = number.add(factors.get(j).multiply(p.factors.get(i - j)));
      }

      result.add(number);
    }

    return new ComplexPolynomial(result);
  }

  /**
   * Derives {@link ComplexPolynomial}. Result is new polynomial derived from this
   * polynomial.
   * 
   * @return derived polynomial
   */
  public ComplexPolynomial derive() {
    List<Complex> result = new ArrayList<>();
    for (int i = 1, n = factors.size(); i < n; i++) {
      result.add(factors.get(i).multiply(new Complex(i, 0)));
    }

    return new ComplexPolynomial(result);
  }

  /**
   * Calculates polynomial value at given point z.
   * 
   * @param z point at which the polynomial is calculated
   * @return new polynomial value around point z
   */
  public Complex apply(Complex z) {
    Complex result = Complex.ZERO;
    for (int i = 0, n = factors.size(); i < n; i++) {
      result = result.add(factors.get(i).multiply(z.power(i)));
    }

    return result;
  } 

  @Override
  public String toString() {
    StringBuilder stringBuilder = new StringBuilder();
    for (int i = factors.size() - 1; i >= 0; i--) {
      if(factors.get(i).getImaginary() == 0 && factors.get(i).getReal() < 0) {
        //stringBuilder.append("-");
      }
      else if (i != factors.size() -1){
        stringBuilder.append("+");
      }
      if(factors.get(i).getImaginary() != 0 && i != 0) {
        stringBuilder.append("(").append(factors.get(i)).append(")");
      } else {
        stringBuilder.append(factors.get(i).toString());
      }
      if(i > 0) {
        stringBuilder.append("z^").append(i);
      }
    }
    
    return stringBuilder.toString();
  }
}
