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
      error(req, resp, "404", "Author does not exist.");
      return;
    }
    if(parts.length == 1) {
      List<BlogEntry> entries = DAOProvider.getDAO().getListOfEntries(blogUser.getId());
      req.setAttribute("entries", entries);
      req.setAttribute("selectedUser", blogUser);
      req.getRequestDispatcher("/WEB-INF/pages/ListEntries.jsp").forward(req, resp);
    } else if(parts.length == 2) {
      req.setAttribute("selectedUser", blogUser);
      if(parts[1].equals("new") || parts[1].equals("edit")) {
        if(req.getSession().getAttribute("current.user.nick") == null
            || !req.getSession().getAttribute("current.user.nick").equals(parts[0])) {
          System.out.println(req.getSession().getAttribute("current.user.nick"));
          System.out.println(parts[0]);
          error(req, resp, "550", "You are not authorized to edit this post");
          return;
        }
        if(parts[1].equals("new")) {
          createNewEntry(req, resp);
        } else if(parts[1].startsWith("edit")) {
          editEntry(req, resp);
        }
      } else {
        try {
          req.setAttribute("blogEntry", DAOProvider.getDAO().getBlogEntry(Long.parseLong(parts[1])));
        } catch (Exception e) {
          error(req, resp, "400", "Provided post can not be interpreted as a number");
          return;
        }
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
        error(req, resp, "Authorization error", "You do not have permission to save this entry.");
        return;
      }
    } else if(parts.length == 2 && parts[1].equals("postComment")) {
      postComment(req, resp);
    }
  }

  /**
   * Posts comment from given request. Comment must have email address and body in
   * order to be valid. If the comment is not valid, it will not be posted. After
   * saving comment, user will be redirected to blog entry the comment was posted
   * on.
   * 
   * @param req request
   * @param resp response
   * @throws IOException if there is problem with database
   * @throws ServletException if there is problem with forwarding
   */
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

  /**
   * Saves entry from request. Entry must have title and body in order to be
   * valid. If the entry is not valid, it will not be posted. After saving entry,
   * user will be redirected to his list of blog entries.
   * 
   * @param req request
   * @param resp response
   * @throws ServletException if there is problem with database
   * @throws IOException if there is problem with forwarding
   */
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

  /**
   * Sets attributes for editing existing entry. Redirects user to entry form
   * filled with data from old entry.
   * 
   * @param req request
   * @param resp response
   * @throws ServletException if there is problem with database
   * @throws IOException if there is problem with forwarding
   */
  private void editEntry(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    BlogEntry blogEntry = null;
    try {
      blogEntry = DAOProvider.getDAO().getBlogEntry(Long.parseLong(req.getParameter("id")));
      if(blogEntry == null) {
        throw new NullPointerException();
      }
      if(!req.getSession().getAttribute("current.user.nick").equals(blogEntry.getCreator().getNick())) {
        error(req, resp, "550", "You are not authorized to edit this post");
        return;
      }
    } catch (NumberFormatException e) {

      error(req, resp, "404", "Provided post can not be interpreted as a number");
      return;
    } catch (Exception e) {
      error(req, resp, "400", "There is no post with provided id");
      return;
    }
    BlogEntryForm form = new BlogEntryForm();
    form.fillFromBlogEntry(blogEntry);
    req.setAttribute("form", form);
    req.getRequestDispatcher("/WEB-INF/pages/EntryForm.jsp").forward(req, resp);
  }

  /**
   * Sets attributes for creating new entry. Redirects user to empty entry form.
   * 
   * @param req request
   * @param resp response
   * @throws ServletException if there is problem with database
   * @throws IOException if there is problem with forwarding
   */
  private void createNewEntry(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    BlogEntry blogEntry = new BlogEntry();
    BlogEntryForm form = new BlogEntryForm();
    form.fillFromBlogEntry(blogEntry);
    req.setAttribute("form", form);
    req.getRequestDispatcher("/WEB-INF/pages/EntryForm.jsp").forward(req, resp);
  }

  /**
   * Sends error with given error and error message by filling request with error
   * information and forwarding to ErrorPage.jsp.
   * 
   * @param req request
   * @param resp response
   * @param error error
   * @param errorMessage error message
   * @throws IOException
   * @throws ServletException
   */
  private void error(HttpServletRequest req, HttpServletResponse resp, String error, String errorMessage)
      throws ServletException, IOException {
    req.setAttribute("error", error);
    req.setAttribute("errorMessage", errorMessage);
    req.getRequestDispatcher("/WEB-INF/pages/ErrorPage.jsp").forward(req, resp);
    return;
  }
}
