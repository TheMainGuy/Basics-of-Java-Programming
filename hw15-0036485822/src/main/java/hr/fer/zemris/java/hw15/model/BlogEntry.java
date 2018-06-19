package hr.fer.zemris.java.hw15.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Cacheable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Defines blog entry object and models it in database. Blog entry has id, list
 * of {@link BlogComment}s which are posted on it, date of creation, date of
 * last modification, title, text and reference to its creator.
 * 
 * @author tin
 *
 */
@NamedQueries({
    @NamedQuery(name = "BlogEntry.upit1", query = "select b from BlogComment as b where b.blogEntry=:be and b.postedOn>:when") })
@Entity
@Table(name = "blog_entries")
@Cacheable(true)
public class BlogEntry {
  /**
   * Unique identifier.
   */
  private Long id;

  /**
   * List of comment which are posted on this entry.
   */
  private List<BlogComment> comments = new ArrayList<>();

  /**
   * Date of creation.
   */
  private Date createdAt;

  /**
   * Date of last modification.
   */
  private Date lastModifiedAt;

  /**
   * Title.
   */
  private String title;

  /**
   * Text.
   */
  private String text;

  /**
   * Reference to creator.
   */
  private BlogUser creator;

  /**
   * Returns id.
   * 
   * @return id
   */
  @Id
  @GeneratedValue
  public Long getId() {
    return id;
  }

  /**
   * Sets id to given id.
   * 
   * @param id id to which parameter id will be set
   */
  public void setId(Long id) {
    this.id = id;
  }

  /**
   * Returns comments.
   * @return comments
   */
  @OneToMany(mappedBy = "blogEntry", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST, orphanRemoval = true)
  @OrderBy("postedOn")
  public List<BlogComment> getComments() {
    return comments;
  }

  /**
   * Sets comments to given list.
   * @param comments list to which comments will be set
   */
  public void setComments(List<BlogComment> comments) {
    this.comments = comments;
  }

  /**
   * Returns createdAt.
   * @return createdAt
   */
  @Temporal(TemporalType.TIMESTAMP)
  @Column(nullable = false)
  public Date getCreatedAt() {
    return createdAt;
  }

  /**
   * Sets createdAt to given date.
   * @param createdAt date to which createdAt will be set
   */
  public void setCreatedAt(Date createdAt) {
    this.createdAt = createdAt;
  }

  /**
   * Returns lastModifiedAt.
   * @return lastModifiedAt
   */
  @Temporal(TemporalType.TIMESTAMP)
  @Column(nullable = true)
  public Date getLastModifiedAt() {
    return lastModifiedAt;
  }

  /**
   * Sets lastModifiedAt to given date.
   * @param lastModifiedAt date to which lastModifiedAt will be set
   */
  public void setLastModifiedAt(Date lastModifiedAt) {
    this.lastModifiedAt = lastModifiedAt;
  }

  /**
   * Returns title.
   * @return title
   */
  @Column(length = 200, nullable = false)
  public String getTitle() {
    return title;
  }

  /**
   * Sets title to given value.
   * @param title value to which title will be set
   */
  public void setTitle(String title) {
    this.title = title;
  }

  /**
   * Returns text.
   * @return text
   */
  @Column(length = 4096, nullable = false)
  public String getText() {
    return text;
  }

  /**
   * Sets text to given string.
   * @param text string to which text will be set
   */
  public void setText(String text) {
    this.text = text;
  }

  /**
   * @return the creator
   */
  @ManyToOne
  @JoinColumn(name = "creator", nullable = false)
  public BlogUser getCreator() {
    return creator;
  }

  /**
   * @param creator the creator to set
   */
  public void setCreator(BlogUser creator) {
    this.creator = creator;
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
    BlogEntry other = (BlogEntry) obj;
    if(id == null) {
      if(other.id != null)
        return false;
    } else if(!id.equals(other.id))
      return false;
    return true;
  }
}