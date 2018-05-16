package hr.fer.zemris.math;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import hr.fer.zemris.math.Complex;
import hr.fer.zemris.math.ComplexPolynomial;

public class ComplexPolynomialTest {
  List<Complex> factors = new ArrayList<>();
  
  @Test
  public void multiplyTest() {
    factors.add(Complex.IM_NEG);
    factors.add(Complex.ONE);
    ComplexPolynomial polynomial = new ComplexPolynomial(factors);
    ComplexPolynomial polynomial2 = new ComplexPolynomial(factors);
    System.out.println(polynomial.multiply(polynomial2));
  }
  
  @Test
  public void deriveTest() {
    factors.add(Complex.IM_NEG);
    factors.add(Complex.ONE);
    factors.add(new Complex(5, 4));
    factors.add(new Complex(1,-2));
    ComplexPolynomial polynomial = new ComplexPolynomial(factors);
    ComplexPolynomial polynomial2 = polynomial.derive();
    System.out.println(polynomial);
    System.out.println(polynomial2);
  }
  
}
