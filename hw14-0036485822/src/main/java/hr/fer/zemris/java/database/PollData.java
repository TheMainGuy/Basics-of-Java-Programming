package hr.fer.zemris.java.database;

import java.util.List;

/**
 * Class which represents one poll for database input. Poll has title, message
 * and a list of options.
 * 
 * @author tin
 *
 */
public class PollData {
  /**
   * Title.
   */
  private String title;

  /**
   * Message.
   */
  private String message;

  /**
   * List of options.
   */
  private List<PollOption> options;

  /**
   * Poll id.
   */
  private long id;

  /**
   * Constructor. When id is not needed or yet known. Sets id to 0.
   * 
   * @param title poll title
   * @param message poll message
   * @param options list of poll options
   */
  public PollData(String title, String message, List<PollOption> options) {
    this(title, message, options, 0);
  }

  /**
   * Constructor.
   * 
   * @param title poll title
   * @param message poll message
   * @param options list of poll options
   * @param id poll id
   */
  public PollData(String title, String message, List<PollOption> options, long id) {
    this.title = title;
    this.message = message;
    this.options = options;
    this.id = id;
  }

  /**
   * @return the title
   */
  public String getTitle() {
    return title;
  }

  /**
   * @return the message
   */
  public String getMessage() {
    return message;
  }

  /**
   * @return the options
   */
  public List<PollOption> getOptions() {
    return options;
  }

  /**
   * @return the id
   */
  public long getId() {
    return id;
  }

  /**
   * Class which represents one poll option. Option has title, link, poll id and
   * votes count. Poll id defines poll to which this option belongs to but is
   * currently unused.
   * 
   * @author tin
   *
   */
  public static class PollOption {
    /**
     * Option title.
     */
    private String optionTitle;

    /**
     * Link to something which represents this option.
     */
    private String optionLink;

    /**
     * Poll id which indicates to which poll this option belongs to.
     */
    private long pollID;

    /**
     * Number of votes this option got.
     */
    private int votesCount;

    /**
     * Constructor.
     * 
     * @param optionTitle option title
     * @param optionLink option link
     * @param pollID id of a poll to which this option belongs to
     * @param votesCount number of votes this option got
     */
    public PollOption(String optionTitle, String optionLink, long pollID, int votesCount) {
      super();
      this.optionTitle = optionTitle;
      this.optionLink = optionLink;
      this.pollID = pollID;
      this.votesCount = votesCount;
    }

    /**
     * Constructor. Used if poll id is unknown. Will set pollID to 0.
     * 
     * @param optionTitle option title
     * @param optionLink option link
     * @param votesCount number of votes this option got
     */
    public PollOption(String optionTitle, String optionLink, int votesCount) {
      this(optionTitle, optionLink, 0, votesCount);
    }

    /**
     * @return the optionTitle
     */
    public String getOptionTitle() {
      return optionTitle;
    }

    /**
     * @return the optionLink
     */
    public String getOptionLink() {
      return optionLink;
    }

    /**
     * @return the pollID
     */
    public long getPollID() {
      return pollID;
    }

    /**
     * @return the votesCount
     */
    public int getVotesCount() {
      return votesCount;
    }

  }
}
