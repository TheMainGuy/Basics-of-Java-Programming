package hr.fer.zemris.java.servlets;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import hr.fer.zemris.java.servlets.GlasanjeRezultatiServlet.VoteData;

/**
 * Implements servlet which when its doGet method is called, generates
 * band_votes.xls file containing all bands and their corresponding number of
 * votes in each row.
 * 
 * @author tin
 *
 */
@WebServlet("/glasanje-xls")
public class BandVotesInExcelServlet extends HttpServlet {
  /**
   * Serial version UID.
   */
  private static final long serialVersionUID = 1L;

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    String fileName = req.getServletContext().getRealPath("/WEB-INF/glasanje-rezultati.txt");
    String file = new String(Files.readAllBytes(Paths.get(fileName)), StandardCharsets.UTF_8);
    String[] voteResults = file.split("\n");
    List<VoteData> sortedVotes = new ArrayList<>();
    for (String voteResult : voteResults) {
      String id = voteResult.split("\t")[0];
      sortedVotes.add(new VoteData(Integer.parseInt(id), ServletUtil.getBand(req, id),
          Integer.parseInt(voteResult.split("\t")[1])));
    }
    sortedVotes.sort(new Comparator<VoteData>() {

      @Override
      public int compare(VoteData arg0, VoteData arg1) {
        if(arg0.getVoteCount() > arg1.getVoteCount()) {
          return -1;
        } else if(arg0.getVoteCount() == arg1.getVoteCount()) {
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
    for (int i = 0, n = sortedVotes.size(); i < n; i++) {
      HSSFRow row = sheet.createRow(i + 1);
      row.createCell(0).setCellValue(sortedVotes.get(i).getBandName());
      row.createCell(1).setCellValue(sortedVotes.get(i).getVoteCount());
    }

    sheet.autoSizeColumn(0);
    sheet.autoSizeColumn(1);
    resp.setHeader("Content-Disposition", "attachment; filename=\"band_votes.xls\"");
    workBook.write(resp.getOutputStream());
    workBook.close();
  }
}
