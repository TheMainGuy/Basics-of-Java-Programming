package hr.fer.zemris.java.database;

import java.util.ArrayList;
import java.util.List;

import hr.fer.zemris.java.database.PollData.PollOption;

/**
 * Class which defines 2 basic polls, one for voting for your favorite band and
 * one for voting for your favorite food.
 * 
 * @author tin
 *
 */
public class BasicPolls {

  private static PollData basicPoll;

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
  }

  public static PollData getBasicPoll() {
    return basicPoll;
  }
}
