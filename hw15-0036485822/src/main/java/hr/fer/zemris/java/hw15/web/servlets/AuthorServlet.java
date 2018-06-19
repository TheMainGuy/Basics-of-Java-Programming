package hr.fer.zemris.java.hw15.web.servlets;

import java.io.IOException;
import java.util.Calendar;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.hw15.dao.DAOProvider;
import hr.fer.zemris.java.hw15.model.BlogComment;
import hr.fer.zemris.java.hw15.model.BlogCommentForm;
import hr.fer.zemris.java.hw15.model.BlogEntry;
import hr.fer.zemris.java.hw15.model.BlogEntryForm;
import hr.fer.zemris.java.hw15.model.BlogUser;

@WebServlet("/servleti/author/*")
public class AuthorServlet extends HttpServlet{
   /**
   * 
   */
  private static final long serialVersionUID = 1L;

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    String[] parts = req.getPathInfo().substring(1).split("/");
    BlogUser blogUser = DAOProvider.getDAO().getUserFromNick(parts[0]);
    if(blogUser == null) {
      req.setAttribute("error", "404");
      req.setAttribute("errorMessage", "Author does not exist.");
      req.getRequestDispatcher("/WEB-INF/pages/ErrorPage.jsp").forward(req, resp);
    }
    if(parts.length == 1) {
      List<BlogEntry> entries = DAOProvider.getDAO().getListOfEntries(blogUser.getId());
      req.setAttribute("entries", entries);
      req.setAttribute("selectedUser", blogUser);
      req.getRequestDispatcher("/WEB-INF/pages/ListEntries.jsp").forward(req, resp);
    } else if(parts.length == 2) {
      req.setAttribute("selectedUser", blogUser);
      if(parts[1].equals("new")) {
        createNewEntry(req, resp);
      } else if(parts[1].startsWith("edit")) {
        editEntry(req, resp);
      } else {
        req.setAttribute("blogEntry", DAOProvider.getDAO().getBlogEntry(Long.parseLong(parts[1])));
        req.getRequestDispatcher("/WEB-INF/pages/ShowEntry.jsp").forward(req, resp);
      }
    } 
  }
  
  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    if(req.getParameter("method").equals("Cancel")) {
      resp.sendRedirect("");
      return;
    }
    
    String[] parts = req.getPathInfo().substring(1).split("/");
    if(parts.length == 2 && parts[1].equals("save")) {
      if(req.getSession().getAttribute("current.user.nick").equals(parts[0])) {
        saveEntry(req, resp);
      } else {
        req.setAttribute("error", "Authorization error");
        req.setAttribute("errorMessage", "You do not have permission to save this entry.");
        req.getRequestDispatcher("/WEB-INF/pages/ErrorPage.jsp").forward(req, resp);
      }
    } else if(parts.length == 2 && parts[1].equals("postComment")) {
      postComment(req, resp);
    }
  }
  
  private void postComment(HttpServletRequest req, HttpServletResponse resp) throws IOException {
    BlogCommentForm form = new BlogCommentForm();
    BlogComment blogComment = new BlogComment();
    form.fillFromHttpRequest(req);
    form.fillToBlogComment(blogComment);
    blogComment.setPostedOn(Calendar.getInstance().getTime());
    DAOProvider.getDAO().addNewComment(blogComment);
    resp.sendRedirect(blogComment.getBlogEntry().getId().toString());
  }

  private void saveEntry(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    BlogEntryForm form = new BlogEntryForm();
    form.fillFromHttpRequest(req);
    BlogEntry blogEntry = new BlogEntry();
    blogEntry.setCreator(DAOProvider.getDAO().getUserFromNick(req.getSession().getAttribute("current.user.nick").toString()));
    form.fillToBlogEntry(blogEntry);
    if(form.getId().equals("0")) {
      DAOProvider.getDAO().addNewEntry(blogEntry);
    } else {
      DAOProvider.getDAO().editEntry(blogEntry);
    }
    resp.sendRedirect("");
  }

  private void editEntry(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    BlogEntry blogEntry = DAOProvider.getDAO().getBlogEntry(Long.parseLong(req.getParameter("id")));
    BlogEntryForm form = new BlogEntryForm();
    form.fillFromBlogEntry(blogEntry);
    req.setAttribute("form", form);
    req.getRequestDispatcher("/WEB-INF/pages/EntryForm.jsp").forward(req, resp);
  }

  private void createNewEntry(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    BlogEntry blogEntry = new BlogEntry();
    BlogEntryForm form = new BlogEntryForm();
    form.fillFromBlogEntry(blogEntry);
    req.setAttribute("form", form);
    req.getRequestDispatcher("/WEB-INF/pages/EntryForm.jsp").forward(req, resp);
  }
}
