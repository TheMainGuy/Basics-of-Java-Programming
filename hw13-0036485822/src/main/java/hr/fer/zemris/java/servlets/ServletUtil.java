package hr.fer.zemris.java.servlets;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

import javax.servlet.http.HttpServletRequest;

/**
 * Helper class which is used by servlets to create vote file, get band name or
 * song from band id.
 * 
 * @author tin
 *
 */
public class ServletUtil {

  /**
   * Creates file with path fileName that has voting information. In each line,
   * file has id of one band and number of votes that band got separated with TAB
   * in form:
   * 
   * BAND_ID\tNUMBER_OF_VOTES
   * 
   * @param req {@link HttpServletRequest} object used to determine file path
   * @param fileName relative file path
   * @throws IOException if there is problem with reading or writing file
   */
  public static void createVoteFile(HttpServletRequest req, String fileName) throws IOException {
    String definitionFileName = req.getServletContext().getRealPath("/WEB-INF/glasanje-definicija.txt");
    String definitionFile = new String(Files.readAllBytes(Paths.get(definitionFileName)), StandardCharsets.UTF_8);
    OutputStream fileStream = Files.newOutputStream(Paths.get(fileName));
    String[] bandList = definitionFile.split("\n");
    for (int i = 0; i < bandList.length; i++) {
      String s = bandList[i].split("\t")[0] + "\t0";
      if(i != bandList.length - 1) {
        s += "\n";
      }
      byte[] b = s.getBytes();

      fileStream.write(b);
    }
  }

  /**
   * Finds band name from band id. Returns band name if band is found,
   * <code>null</code> if it is not.
   * 
   * @param req {@link HttpServletRequest} object used to determine file path
   * @param id id which will be used to search for band name
   * @return band name if band is found, <code>null</code> otherwise
   * @throws IOException if there is problem with reading or writing file
   */
  public static String getBand(HttpServletRequest req, String id) throws IOException {
    String definitionFileName = req.getServletContext().getRealPath("/WEB-INF/glasanje-definicija.txt");
    String definitionFile = new String(Files.readAllBytes(Paths.get(definitionFileName)), StandardCharsets.UTF_8);
    String[] bandList = definitionFile.split("\n");
    for (String band : bandList) {
      if(band.split("\t")[0].equals(id)) {
        return band.split("\t")[1];
      }
    }
    return null;
  }

  /**
   * Finds band song from band id. Returns band song if band is found,
   * <code>null</code> if it is not.
   * 
   * @param req {@link HttpServletRequest} object used to determine file path
   * @param id id which will be used to search for band song
   * @return band song if band is found, <code>null</code> otherwise
   * @throws IOException if there is problem with reading or writing file
   */
  public static String getSong(HttpServletRequest req, String id) throws IOException {
    String definitionFileName = req.getServletContext().getRealPath("/WEB-INF/glasanje-definicija.txt");
    String definitionFile = new String(Files.readAllBytes(Paths.get(definitionFileName)), StandardCharsets.UTF_8);
    String[] bandList = definitionFile.split("\n");
    for (String band : bandList) {
      if(band.split("\t")[0].equals(id)) {
        return band.split("\t")[2];
      }
    }
    return null;
  }
}
