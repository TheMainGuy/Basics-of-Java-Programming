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

/**
 * Implements servlet, which handles any requests made to servleti/author. This
 * happens when user follows link to list all author entries, reads entries,
 * edits entries, saves entries and comments.
 * 
 * Method doGet is called when user clicks on any blog user or any of its posts.
 * It is also called when post edit or new post request arrive.
 * 
 * Method doPost is called when user fills form for saving comment, edited or
 * new post. It checks if user is authorized to do so and if he is, alters
 * database in order to match with specified information.
 * 
 * @author tin
 *
 */
@WebServlet("/servleti/author/*")
public class AuthorServlet extends HttpServlet {
  /**
   * Serial version UID.
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
    String[] parts = req.getPathInfo().substring(1).split("/");
    BlogUser blogUser = DAOProvider.getDAO().getUserFromNick(parts[0]);
    req.setAttribute("selectedUser", blogUser);
    if(req.getParameter("method").equals("Cancel")) {
      resp.sendRedirect(req.getServletContext().getContextPath() + "/servleti/author/" + blogUser.getNick());
      return;
    }

    if(parts.length == 2 && parts[1].equals("save")) {
      if(req.getSession().getAttribute("current.user.nick").equals(parts[0])) {
        saveEntry(req, resp);
      } else {
        req.setAttribute("error", "Authorization error");
        req.setAttribute("errorMessage", "You do not have permission to save this entry.");
        req.getRequestDispatcher("/WEB-INF/pages/ErrorPage.jsp").forward(req, resp);
        return;
      }
    } else if(parts.length == 2 && parts[1].equals("postComment")) {
      postComment(req, resp);
    }
  }

  private void postComment(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
    BlogCommentForm form = new BlogCommentForm();
    BlogComment blogComment = new BlogComment();
    form.fillFromHttpRequest(req);
    form.fillToBlogComment(blogComment);
    form.validate();
    if(form.hasErrors()) {
      req.setAttribute("form", form);
      req.setAttribute("blogEntry", DAOProvider.getDAO().getBlogEntry(blogComment.getBlogEntry().getId()));
      req.getRequestDispatcher("/WEB-INF/pages/ShowEntry.jsp").forward(req, resp);
      return;
    }

    blogComment.setPostedOn(Calendar.getInstance().getTime());
    DAOProvider.getDAO().addNewComment(blogComment);
    resp.sendRedirect(blogComment.getBlogEntry().getId().toString());
  }

  private void saveEntry(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    BlogEntryForm form = new BlogEntryForm();
    form.fillFromHttpRequest(req);
    form.validate();
    BlogEntry blogEntry = new BlogEntry();
    blogEntry.setCreator(
        DAOProvider.getDAO().getUserFromNick(req.getSession().getAttribute("current.user.nick").toString()));
    form.fillToBlogEntry(blogEntry);
    if(form.hasErrors()) {
      req.setAttribute("form", form);
      req.setAttribute("blogEntry", blogEntry);
      req.getRequestDispatcher("/WEB-INF/pages/EntryForm.jsp").forward(req, resp);
      return;
    }
    if(form.getId().equals("0")) {
      DAOProvider.getDAO().addNewEntry(blogEntry);
    } else {
      DAOProvider.getDAO().editEntry(blogEntry);
    }
    resp.sendRedirect(
        req.getServletContext().getContextPath() + "/servleti/author/" + blogEntry.getCreator().getNick());
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
