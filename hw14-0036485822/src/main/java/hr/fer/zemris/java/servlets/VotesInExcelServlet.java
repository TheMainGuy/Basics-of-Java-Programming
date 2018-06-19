package hr.fer.zemris.java.servlets;

import java.io.IOException;
import java.util.Comparator;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import hr.fer.zemris.java.model.PollData;
import hr.fer.zemris.java.model.PollData.PollOption;
import hr.fer.zemris.java.p12.dao.DAOProvider;

/**
 * Implements servlet which when its doGet method is called, generates votes.xls
 * file containing all vote options and their corresponding number of votes in
 * each row.
 * 
 * @author tin
 *
 */
@WebServlet("/servleti/glasanje-xls")
public class VotesInExcelServlet extends HttpServlet {
  /**
   * Serial version UID.
   */
  private static final long serialVersionUID = 1L;

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    if(req.getParameter("pollID") == null) {
      // TODO error
      return;
    }
    PollData poll = DAOProvider.getDao().getPoll(Long.parseLong(req.getParameter("pollID")));

    if(poll == null) {
      return;
    }
    poll.getOptions().sort(new Comparator<PollOption>() {

      @Override
      public int compare(PollOption arg0, PollOption arg1) {
        if(arg0.getVotesCount() > arg1.getVotesCount()) {
          return -1;
        } else if(arg0.getVotesCount() == arg1.getVotesCount()) {
          return 0;
        } else {
          return 1;
        }
      }

    });

    HSSFWorkbook workBook = new HSSFWorkbook();
    HSSFSheet sheet = workBook.createSheet();
    HSSFRow rowHeader = sheet.createRow(0);
    rowHeader.createCell(0).setCellValue("Band name");
    rowHeader.createCell(1).setCellValue("Number of votes");
    for (int i = 0, n = poll.getOptions().size(); i < n; i++) {
      HSSFRow row = sheet.createRow(i + 1);
      row.createCell(0).setCellValue(poll.getOptions().get(i).getOptionTitle());
      row.createCell(1).setCellValue(poll.getOptions().get(i).getVotesCount());
    }

    sheet.autoSizeColumn(0);
    sheet.autoSizeColumn(1);
    resp.setHeader("Content-Disposition", "attachment; filename=\"votes.xls\"");
    workBook.write(resp.getOutputStream());
    workBook.close();
  }
}
