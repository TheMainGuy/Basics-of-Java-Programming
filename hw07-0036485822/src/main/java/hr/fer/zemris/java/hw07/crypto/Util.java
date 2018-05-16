package hr.fer.zemris.java.hw07.crypto;

/**
 * Class implements methods that allow user to convert hex-encoded
 * {@link String}s to byte array and byte array values to hex-encoded
 * {@link String}.
 * 
 * @author tin
 *
 */
public class Util {
  /**
   * Method converts hex-encoded {@link String} to byte array. Each pair of
   * characters if interpreted as one byte made from 2 hexadecimal digits and is
   * converted to 1 byte. Throws exception if {@link String} has odd number of
   * characters or contains invalid characters.
   * 
   * @param keyText hex-encoded {@link String}
   * @return array of bytes decoded from hex
   * @throws IllegalArgumentException if hex-encoded {@link String} has odd number
   *           of characters
   * @throws IllegalArgumentException if hex-encoded {@link String} contains
   *           invalid characters
   */
  public static byte[] hexToByte(String keyText) {
    if(keyText.length() % 2 == 1) {
      throw new IllegalArgumentException("Hex-encoded string must have even number of characters.");
    }
    if(keyText.length() == 0) {
      return new byte[0];
    }

    byte[] decoded = new byte[keyText.length() / 2];
    for (int i = 0, n = keyText.length(); i < n; i += 2) {
      decoded[i / 2] = getByte(keyText.charAt(i), keyText.charAt(i + 1));
    }

    return decoded;
  }

  /**
   * Returns byte value from pair of characters. First character c1 represents hex
   * character of higher value ie. to be multiplied by 16. Second character c2
   * represents hex character of lower value.
   * 
   * @param c1 first character
   * @param c2 second character
   * @return byte value made from hex value of pair (c1,c2)
   */
  private static byte getByte(char c1, char c2) {
    return (byte) (intValue(c1) * 16 + intValue(c2));
  }

  /**
   * Returns int value of hex character c. Throws exception if character c is
   * neither a digit nor a letter from [a-f] or [A-F].
   * 
   * @param c character c
   * @return integer value of hex character c
   * @throws IllegalArgumentException if c is invalid character
   */
  private static int intValue(char c) {
    c = Character.toLowerCase(c);
    if(Character.isDigit(c)) {
      return c - '0';
    } else if(Character.isLetter(c)) {
      int intValue = c - 'a';
      if(!(intValue < 0 || intValue > 5)) {
        return intValue + 10;
      }
    }
    throw new IllegalArgumentException("Invalid character: " + c);
  }

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
