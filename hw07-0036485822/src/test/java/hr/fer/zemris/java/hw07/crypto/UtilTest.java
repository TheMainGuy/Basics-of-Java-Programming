package hr.fer.zemris.java.hw07.crypto;

import org.junit.Test;
import org.junit.Assert;

public class UtilTest {
  @Test
  public void hexToByteTest() {
    byte[] byteArray = Util.hexToByte("01aE22");
    Assert.assertArrayEquals(new byte[] {1, -82, 34}, byteArray);
  }
  
  @Test(expected=IllegalArgumentException.class)
  public void hexToByteWithInvalidCharacter() {
    Util.hexToByte("01%404");
  }
  
  @Test(expected=IllegalArgumentException.class)
  public void hexToByteWithOddNumberOfCharacter() {
    Util.hexToByte("0123456");
  }
  
  @Test
  public void byteToHexTest() {
    String hexString = Util.byteToHex(new byte[] {1, -82, 34});
    Assert.assertEquals("01ae22", hexString);
  }
  
  @Test
  public void emptyHexToByte() {
    byte[] byteArray = Util.hexToByte("");
    Assert.assertArrayEquals(new byte[] {}, byteArray);
  }
  
  @Test
  public void emptyByteToHex() {
    String hexString = Util.byteToHex(new byte[] {});
    Assert.assertEquals("", hexString);
  }
}
