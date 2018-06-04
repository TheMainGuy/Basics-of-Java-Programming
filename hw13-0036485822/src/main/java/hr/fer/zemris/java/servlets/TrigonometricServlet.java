package hr.fer.zemris.java.servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Implements servlet, which when its doGet method is called, dynamically
 * renders table of basic trigonometric info. Numbers from parameter a to
 * parameter b are in the first column, their sin value in second column and cos
 * value in third column.
 * 
 * Sin and cos are calculated using degree angles.
 * 
 * @author tin
 *
 */
@WebServlet("/trigonometric")
public class TrigonometricServlet extends HttpServlet {

  /**
   * Serial version UID.
   */
  private static final long serialVersionUID = 1L;

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    int a;
    int b;
    if(req.getParameter("a") == null) {
      a = 0;
    } else {
      a = Integer.parseInt(req.getParameter("a"));
    }
    if(req.getParameter("b") == null) {
      b = 360;
    } else {
      b = Integer.parseInt(req.getParameter("b"));
    }

    if(a > b) {
      a = a - b;
      b = a + b;
      a = b - a;
    }

    if(b > a + 720) {
      b = a + 720;
    }

    List<TrigonometricInfo> trigonometricInfo = new ArrayList<>();

    for (int i = a; i <= b; i++) {
      trigonometricInfo.add(new TrigonometricInfo(i, Math.sin(i * Math.PI / 180), Math.cos(i * Math.PI / 180)));
    }

    req.setAttribute("trigonometricInfo", trigonometricInfo);
    req.getRequestDispatcher("/WEB-INF/pages/trigonometric.jsp").forward(req, resp);
  }

  public static class TrigonometricInfo {
    int number;
    double sin;
    double cos;

    public TrigonometricInfo(int number, double sin, double cos) {
      super();
      this.number = number;
      this.sin = sin;
      this.cos = cos;
    }

    /**
     * @return the number
     */
    public int getNumber() {
      return number;
    }

    /**
     * @return the sin
     */
    public double getSin() {
      return sin;
    }

    /**
     * @return the cos
     */
    public double getCos() {
      return cos;
    }

  }

}
