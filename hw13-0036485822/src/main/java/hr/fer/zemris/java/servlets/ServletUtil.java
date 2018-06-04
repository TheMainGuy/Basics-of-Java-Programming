package hr.fer.zemris.java.servlets;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

import javax.servlet.http.HttpServletRequest;

public class ServletUtil {
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
  
  public static String getBand(HttpServletRequest req, String id) throws IOException {
    String definitionFileName = req.getServletContext().getRealPath("/WEB-INF/glasanje-definicija.txt");
    String definitionFile = new String(Files.readAllBytes(Paths.get(definitionFileName)), StandardCharsets.UTF_8);
    String[] bandList = definitionFile.split("\n");
    for(String band : bandList) {
      if(band.split("\t")[0].equals(id)) {
        return band.split("\t")[1];
      }
    }
    return null;
  }
  
  public static String getSong(HttpServletRequest req, String id) throws IOException {
    String definitionFileName = req.getServletContext().getRealPath("/WEB-INF/glasanje-definicija.txt");
    String definitionFile = new String(Files.readAllBytes(Paths.get(definitionFileName)), StandardCharsets.UTF_8);
    String[] bandList = definitionFile.split("\n");
    for(String band : bandList) {
      if(band.split("\t")[0].equals(id)) {
        return band.split("\t")[2];
      }
    }
    return null;
  }
}
