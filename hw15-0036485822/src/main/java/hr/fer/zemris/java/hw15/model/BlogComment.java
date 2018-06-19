package hr.fer.zemris.java.hw15.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Defines blog comment object and models it in database. Blog comment has id,
 * reference to {@link BlogEntry} object to which it belongs, email of
 * {@link BlogUser} to which it belongs, message and date of post.
 * 
 * @author tin
 *
 */
@Entity
@Table(name = "blog_comments")
public class BlogComment {

  /**
   * Unique identifier.
   */
  private Long id;
  
  /**
   * Reference to blog entry to which this blog comment belongs.
   */
  private BlogEntry blogEntry;
  
  /**
   * Email of user to which this blog comment belongs.
   */
  private String usersEMail;
  
  /**
   * Message which is displayed.
   */
  private String message;
  
  /**
   * Date of post.
   */
  private Date postedOn;

  /**
   * Returns id.
   * @return id
   */
  @Id
  @GeneratedValue
  public Long getId() {
    return id;
  }

  /**
   * Sets id to given id.
   * @param id id to which parameter id will be set
   */
  public void setId(Long id) {
    this.id = id;
  }

  /**
   * Returns {@link BlogEntry} object to which this blog comment belongs.
   * @return {@link BlogEntry} object to which this blog comment belongs.
   */
  @ManyToOne
  @JoinColumn(nullable = false)
  public BlogEntry getBlogEntry() {
    return blogEntry;
  }

  /**
   * Sets {@link BlogEntry} object to given blogEntry parameter.
   * @param blogEntry blog entry to which blogEntry variable will be set to
   */
  public void setBlogEntry(BlogEntry blogEntry) {
    this.blogEntry = blogEntry;
  }

  /**
   * Returns usersEmail.
   * @return usersEmail
   */
  @Column(length = 100, nullable = false)
  public String getUsersEMail() {
    return usersEMail;
  }

  /**
   * Sets usersEmail to given string.
   * @param usersEMail value to which usersEmail will be set to
   */
  public void setUsersEMail(String usersEMail) {
    this.usersEMail = usersEMail;
  }

  /**
   * Returns message.
   * @return messsage
   */
  @Column(length = 4096, nullable = false)
  public String getMessage() {
    return message;
  }

  /**
   * Sets message to given message.
   * @param message value to which message will be set to
   */
  public void setMessage(String message) {
    this.message = message;
  }

  /**
   * Returns postedOn.
   * @return postedOn
   */
  @Temporal(TemporalType.TIMESTAMP)
  @Column(nullable = false)
  public Date getPostedOn() {
    return postedOn;
  }

  /**
   * Sets postedOn to given date.
   * @param postedOn date to which postedOn will be set
   */
  public void setPostedOn(Date postedOn) {
    this.postedOn = postedOn;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((id == null) ? 0 : id.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if(this == obj)
      return true;
    if(obj == null)
      return false;
    if(getClass() != obj.getClass())
      return false;
    BlogComment other = (BlogComment) obj;
    if(id == null) {
      if(other.id != null)
        return false;
    } else if(!id.equals(other.id))
      return false;
    return true;
  }
}