package hr.fer.zemris.java.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

/**
 * Implements servlet, which when its doGet method is called, generates
 * powers.xls file containing all numbers from parameter a to parameter b and
 * their powers on each one of n sheets, where n is another parameter.
 * 
 * Parameters a and be must be in range [-100, 100] while parameter n must be in
 * range [1,5].
 * 
 * On each of n sheets, i-th power of each number is calculated and stored in
 * the cell next to the number of which it is power of, where i is sheet index.
 * 
 * @author tin
 *
 */
@WebServlet("/powers")
public class PowersInExcelServlet extends HttpServlet {

  /**
   * Serial version UID.
   */
  private static final long serialVersionUID = 1L;

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    int a = Integer.parseInt(req.getParameter("a"));
    int b = Integer.parseInt(req.getParameter("b"));
    int n = Integer.parseInt(req.getParameter("n"));
    if(n < 1 || n > 5 || a < 0 || a > 100 || b < 0 || b > 100) {
      req.getRequestDispatcher("/WEB-INF/pages/powersInvalidParameters.jsp").forward(req, resp);
      return;
    }

    if(a > b) {
      a = a - b;
      b = a + b;
      a = b - a;
    }
    HSSFWorkbook workBook = new HSSFWorkbook();
    for (int i = 1; i <= n; i++) {
      HSSFSheet sheet = workBook.createSheet("page " + i);
      for (int j = a; j < b; j++) {
        HSSFRow row = sheet.createRow(j - a);
        row.createCell(0).setCellValue(j);
        row.createCell(1).setCellValue(Math.pow(j, i));
      }
    }
    resp.setHeader("Content-Disposition", "attachment; filename=\"powers.xls\"");
    workBook.write(resp.getOutputStream());
    workBook.close();
  }
}
