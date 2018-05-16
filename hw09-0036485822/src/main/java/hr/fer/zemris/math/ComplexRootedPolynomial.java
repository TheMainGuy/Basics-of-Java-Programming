package hr.fer.zemris.math;

import java.util.ArrayList;
import java.util.List;

/**
 * Implements complex number root polynomial. This polynomial is in form
 * (z-z1)*(z-z2)...(z-zn) where z1 to zn are its roots.
 * 
 * 
 * @author tin
 *
 */
public class ComplexRootedPolynomial {
  /**
   * List of polynomial roots.
   */
  private List<Complex> roots;

  /**
   * Constructor.
   * 
   * @param roots roots
   */
  public ComplexRootedPolynomial(List<Complex> roots) {
    if(roots.size() == 0) {
      throw new IllegalArgumentException("There must be at least one root value.");
    }
    for (Complex root : roots) {
      if(root == null) {
        throw new NullPointerException("Factor at index " + roots.indexOf(root) + " is null.");
      }
    }
    this.roots = roots;
  }

  /**
   * Calculates polynomial value at given point z by multiplying differences
   * between z and all its roots.
   * 
   * @param z point at which the polynomial is calculated
   * @return calculated polynomial
   */
  public Complex apply(Complex z) {
    Complex result = Complex.ONE;
    for (Complex root : roots) {
      result = result.multiply(z.sub(root));
    }
    return result;
  }

  /**
   * Converts this representation of complex polynomial to
   * {@link ComplexPolynomial} and returns new {@link ComplexPolynomial}
   * object.
   * 
   * @return this representation converted to {@link ComplexPolynomial} object 
   */
  public ComplexPolynomial toComplexPolynom() {
    List<Complex> factors = new ArrayList<>(1);
    factors.add(Complex.ONE);
    ComplexPolynomial polynomial = new ComplexPolynomial(factors);
    for (Complex root : roots) {
      factors = new ArrayList<>(2);
      factors.add(root.negate());
      factors.add(Complex.ONE);
      polynomial = polynomial.multiply(new ComplexPolynomial(factors));
    }

    return polynomial;
  }

  @Override
  public String toString() {
    StringBuilder stringBuilder = new StringBuilder();
    for (int i = 0, n = roots.size(); i < n; i++) {
      if(i != 0) {
        stringBuilder.append("*");
      }
      stringBuilder.append("(z-");
      stringBuilder.append(roots.get(i).toString()).append(")");
    }

    return stringBuilder.toString();
  }

  /**
   * Finds index of closest root for given complex number z that is within
   * treshold. If there is no such root, returns -1
   * 
   * @param z complex number z around which the root is searched for
   * @param treshold maximum distance between z and roots searched for
   * @return index of closest root within treshold, -1 if it does not exist
   */
  public int indexOfClosestRootFor(Complex z, double treshold) {
    double min = -1;
    int index = -1;
    for (int i = 0, n = roots.size(); i < n; i++) {
      double distance = z.sub(roots.get(i)).module();
      if(distance < min || min == -1) {
        min = distance;
        index = i;
      }
    }
    if(min < treshold) {
      return index;
    } else {
      return -1;
    }
    
  }

}
