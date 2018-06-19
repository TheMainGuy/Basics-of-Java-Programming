package hr.fer.zemris.java.hw15.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Cacheable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

/**
 * Defines blog user object and models it in database. Blog user has id, list of
 * {@link BlogEntry}s which are posted by it, first name, last name, nick, email
 * and passwordHash.
 * 
 * @author tin
 *
 */
@NamedQueries({
    @NamedQuery(name = "BlogUser.upit1", query = "select b from BlogEntry as b where b.creator=:be and b.createdAt>:when") })
@Entity
@Table(name = "blog_users")
@Cacheable(true)
public class BlogUser {
  /**
   * Unique identifier.
   */
  private Long id;

  /**
   * List of entries which are posted by this user.
   */
  private List<BlogEntry> entries = new ArrayList<>();
  
  /**
   * First name.
   */
  private String firstName;
  /**
   * Last name.
   */
  private String lastName;
  
  /**
   * Nick.
   */
  private String nick;
  
  /**
   * E-mail.
   */
  private String email;
  
  /**
   * Hashed password.
   */
  private String passwordHash;

  /**
   * @return the id
   */
  @Id
  @GeneratedValue
  public Long getId() {
    return id;
  }

  /**
   * @param id the id to set
   */
  public void setId(Long id) {
    this.id = id;
  }

  /**
   * @return the entries
   */
  @OneToMany(mappedBy = "creator", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST, orphanRemoval = true)
  @OrderBy("createdAt")
  public List<BlogEntry> getEntries() {
    return entries;
  }

  /**
   * @param entries the entries to set
   */
  public void setEntries(List<BlogEntry> entries) {
    this.entries = entries;
  }

  /**
   * @return the firstName
   */
  @Column(length = 32, nullable = false)
  public String getFirstName() {
    return firstName;
  }

  /**
   * @param firstName the firstName to set
   */
  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  /**
   * @return the lastName
   */
  @Column(length = 64, nullable = false)
  public String getLastName() {
    return lastName;
  }

  /**
   * @param lastName the lastName to set
   */
  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  /**
   * @return the nick
   */
  @Column(length = 64, nullable = false, unique = true)
  public String getNick() {
    return nick;
  }

  /**
   * @param nick the nick to set
   */
  public void setNick(String nick) {
    this.nick = nick;
  }

  /**
   * @return the email
   */
  @Column(length = 64, nullable = false)
  public String getEmail() {
    return email;
  }

  /**
   * @param email the email to set
   */
  public void setEmail(String email) {
    this.email = email;
  }

  /**
   * @return the passwordHash
   */
  public String getPasswordHash() {
    return passwordHash;
  }

  /**
   * @param passwordHash the passwordHash to set
   */
  public void setPasswordHash(String passwordHash) {
    this.passwordHash = passwordHash;
  }

}
