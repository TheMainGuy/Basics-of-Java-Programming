package hr.fer.zemris.java.p12;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.model.PollData;
import hr.fer.zemris.java.p12.dao.DAOProvider;

@WebServlet("/servleti/index.html")
public class IndexServlet extends HttpServlet{

  /**
   * 
   */
  private static final long serialVersionUID = 1L;
  
  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    List<PollData> simplePollList = DAOProvider.getDao().getBasicPollList();
    resp.getWriter().write("<html>");
    resp.getWriter().write("<body>");
    resp.getWriter().write("<h1>Lets vote for something!</h1>");
    for(PollData poll : simplePollList) {
      resp.getWriter().write("<a href=\"glasanje?pollID="+ poll.getId() +"\">" + poll.getTitle() + "</a><br>");
    }
    
    resp.getWriter().write("</body>");
    resp.getWriter().write("</html>");
    resp.getWriter().flush();
  }
}
