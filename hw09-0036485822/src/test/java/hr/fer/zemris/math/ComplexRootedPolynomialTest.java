package hr.fer.zemris.math;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import hr.fer.zemris.math.Complex;
import hr.fer.zemris.math.ComplexPolynomial;
import hr.fer.zemris.math.ComplexRootedPolynomial;

public class ComplexRootedPolynomialTest {
  List<Complex> roots = new ArrayList<>();
  
  @Test
  public void toPolynomialTest() {
    roots.add(Complex.IM_NEG);
    roots.add(Complex.ONE);
    ComplexRootedPolynomial polynomial = new ComplexRootedPolynomial(roots);
    ComplexPolynomial polynomial2 = polynomial.toComplexPolynom();
    System.out.println(polynomial);
    System.out.println(polynomial2);
  }
  
}
