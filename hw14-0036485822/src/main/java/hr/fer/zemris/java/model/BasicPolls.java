package hr.fer.zemris.java.model;

import java.util.ArrayList;
import java.util.List;

import hr.fer.zemris.java.model.PollData.PollOption;

/**
 * Class which defines 2 basic polls, one for voting for your favorite band and
 * one for voting for your favorite food.
 * 
 * @author tin
 *
 */
public class BasicPolls {

  /**
   * Basic poll for picking your favorite band.
   */
  private static PollData basicPoll;
  
  /**
   * Basic poll for picking your favorite beer.
   */
  private static PollData basicBeerPoll;
  
  static {
    List<PollOption> basicOptions = new ArrayList<>();
    basicOptions.add(new PollOption("The Beatles", "https://www.youtube.com/watch?v=z9ypq6_5bsg", 0, 12));
    basicOptions.add(new PollOption("The Platters", "https://www.youtube.com/watch?v=H2di83WAOhU", 0, 2));
    basicOptions.add(new PollOption("The Beach Boys", "https://www.youtube.com/watch?v=2s4slliAtQU", 0, 32));
    basicOptions.add(new PollOption("The Four Seasons", "https://www.youtube.com/watch?v=y8yvnqHmFds", 0, 14));
    basicOptions.add(new PollOption("The Marcels", "https://www.youtube.com/watch?v=qoi3TH59ZEs", 0, 39));
    basicOptions.add(new PollOption("The Everly Brothers", "https://www.youtube.com/watch?v=tbU3zdAgiX8", 0, 5));
    basicOptions.add(new PollOption("The Mamas And The Papas", "https://www.youtube.com/watch?v=N-aK6JnyFmk", 0, 7));
    basicPoll = new PollData("Band voting", "Vote for your favorite band", basicOptions);
    
    List<PollOption> basicBeerOptions = new ArrayList<>();
    basicBeerOptions.add(new PollOption("Guinness", "https://www.guinness.com/", 0, 26));
    basicBeerOptions.add(new PollOption("Oharas", "http://www.carlowbrewing.com/", 0, 37));
    basicBeerOptions.add(new PollOption("Paulaner", "https://www.paulaner.com/", 0, 18));
    basicBeerOptions.add(new PollOption("Löwenbräu", "https://loewenbraeu.de", 0, 2));
    basicBeerOptions.add(new PollOption("5th Element", "https://www.5th-element.com.hr", 0, 14));
    basicBeerOptions.add(new PollOption("Desperados", "https://www.desperados.com", 0, 22));
    basicBeerOptions.add(new PollOption("Leffe", "http://www.leffe.com", 0, 22));
    basicBeerOptions.add(new PollOption("Zmajsko", "http://www.zmajskapivovara.hr/", 0, 39));
    basicBeerPoll = new PollData("Beer voting", "Vote for your favorite beer", basicBeerOptions);
  }

  /**
   * Returns band poll.
   * 
   * @return band poll
   */
  public static PollData getBasicPoll() {
    return basicPoll;
  }
  
  /**
   * Returns beer poll.
   * 
   * @return beer poll
   */
  public static PollData getBasicBeerPoll() {
    return basicBeerPoll;
  }
}
