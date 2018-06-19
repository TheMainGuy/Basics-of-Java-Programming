package hr.fer.zemris.java.hw15.crypto;

/**
 * Class implements methods that allow user to convert byte array values to
 * hex-encoded {@link String}.
 * 
 * @author tin
 *
 */
public class Util {
  /**
   * Method converts byte array to hex-encoded {@link String}. Each byte from
   * array is converted to 2 hex characters, each representing 4 bits of data from
   * byte they are converted from.
   * 
   * @param byteArray array of bytes
   * @return hex-encoded {@link String}
   */
  public static String byteToHex(byte[] byteArray) {
    StringBuilder stringBuilder = new StringBuilder(byteArray.length * 2);
    for (int i = 0, n = byteArray.length; i < n; i++) {
      int c1;
      int c2;
      if((int) byteArray[i] >= 0) {
        c1 = ((int) byteArray[i]) / 16;
        c2 = ((int) byteArray[i]) % 16;
      } else {
        c1 = ((int) byteArray[i] + 256) / 16;
        c2 = ((int) byteArray[i] + 256) % 16;
      }

      stringBuilder.append(getCharacter(c1)).append(getCharacter(c2));
    }
    return stringBuilder.toString();
  }

  /**
   * Method converts int number to hex character. Hex character is either a digit
   * [0-9] or a letter [a-f]. Letters [a-f] represent hex digits [10-15].
   * 
   * @param c number
   * @return number converted to hex character
   */
  private static char getCharacter(int c) {
    if(c < 10) {
      return (char) ('0' + c);
    } else {
      return (char) ('a' + c - 10);
    }
  }

}
