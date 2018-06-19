package hr.fer.zemris.java.hw15.crypto;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Implements SHA-1 hash algorithm for passwords. Two given passwords will have
 * the same hash. Method does not work the other way around it. hash can not be
 * converted to password.
 * 
 * @author tin
 *
 */
public class Crypto {

  /**
   * Digests given password using SHA-1 hash algorithm and converting its output
   * to hex encoding. Returns that hex encoded string.
   * 
   * @param password password to be digested and hex encoded
   * @return hashed and hex encoded password
   * @throws NoSuchAlgorithmException if there is problem with
   *           {@link MessageDigest} initialization
   */
  public static String digestPassword(String password) {
    MessageDigest messageDigest = null;
    try {
      messageDigest = MessageDigest.getInstance("SHA-1");
    } catch (NoSuchAlgorithmException e) {
      System.out.println("Problem with MessageDigest initialization.");
    }
    messageDigest.update(password.getBytes(), 0, password.length());

    String digestHex = Util.byteToHex(messageDigest.digest());
    return digestHex;
  }
}
