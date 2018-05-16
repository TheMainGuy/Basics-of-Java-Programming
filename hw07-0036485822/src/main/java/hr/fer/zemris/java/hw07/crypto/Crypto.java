package hr.fer.zemris.java.hw07.crypto;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.spec.AlgorithmParameterSpec;
import java.util.Scanner;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * Implements SHA-256 digesting and AES encryption and decryption algorithms.
 * Initialization vector must be provided both for digesting and crypting and
 * password must be provided for crypting.
 * 
 * @author tin
 *
 */
public class Crypto {
  /**
   * Digest command line argument. If user provides this argument, program will
   * digest given file.
   */
  private final static String ARGUMENT_DIGEST = "checksha";

  /**
   * Encrypt command line argument. If user provides this argument, program will
   * encrypt given file to given output file.
   */
  private final static String ARGUMENT_ENCRYPT = "encrypt";

  /**
   * Decrypt command line argument. If user provides this argument, program will
   * decrypt given file to given output file.
   */
  private final static String ARGUMENT_DECRYPT = "decrypt";

  /**
   * Buffer size used.
   */
  private final static int BUFFER_SIZE = 4096;

  /**
   * Method which is called when program starts.
   * 
   * @param args command line arguments
   */
  public static void main(String[] args) {
    if(args.length < 2) {
      throw new IllegalArgumentException("There must be 2 or more command line arguments.");
    }
    if(args[0].equals(ARGUMENT_DIGEST)) {
      digestFile(args[1]);
    } else if(args[0].equals(ARGUMENT_ENCRYPT)) {
      cryptFile(args[1], args[2], true);
      System.out.println("Encryption completed. Generated file " + args[2] + " based on file " + args[1] + ".");
    } else if(args[0].equals(ARGUMENT_DECRYPT)) {
      cryptFile(args[1], args[2], false);
      System.out.println("Decryption completed. Generated file " + args[2] + " based on file " + args[1] + ".");
    }
  }

  /**
   * Method encrypts or decrypts given file. Result of encryption or decryption is
   * stored to second file specified in arguments. Method is using AES with
   * password. When called, it will ask user to enter password and initialization
   * vector.
   * 
   * @param fileName file to be encrypted or decrypted
   * @param cryptedFileName result file
   * @param encrypt if <code>true</code>, method will encrypt file, if
   *          <code>false</code>, method will decrypt file
   */
  private static void cryptFile(String fileName, String cryptedFileName, boolean encrypt) {
    Scanner scanner = new Scanner(System.in);
    System.out.println("Please provide password as hex-encoded text (16 bytes, i.e. 32 hex-digits):");
    String keyText = scanner.nextLine();
    System.out.println("Please provide initialization vector as hex-encoded text (32 hex-digits):");
    String ivText = scanner.nextLine();
    scanner.close();

    SecretKeySpec keySpec = new SecretKeySpec(Util.hexToByte(keyText), "AES");
    AlgorithmParameterSpec paramSpec = new IvParameterSpec(Util.hexToByte(ivText));
    Cipher cipher = null;
    try {
      cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
      cipher.init(encrypt ? Cipher.ENCRYPT_MODE : Cipher.DECRYPT_MODE, keySpec, paramSpec);
    } catch (Exception e) {
      System.out.println("Problem with Cipher initialization.");
    }

    InputStream fileInputStream = null;
    OutputStream fileOutputStream = null;
    try {
      fileInputStream = Files.newInputStream(Paths.get(fileName));
      fileOutputStream = Files.newOutputStream(Paths.get(cryptedFileName));
    } catch (IOException e) {
      System.out.println("Problem with reading file: " + fileName);
    }

    byte[] buffer = new byte[BUFFER_SIZE];
    byte[] outputBuffer = new byte[BUFFER_SIZE];
    int bytesRead;
    try {
      while ((bytesRead = fileInputStream.read(buffer)) != -1) {
        if(bytesRead < BUFFER_SIZE) {
          outputBuffer = cipher.doFinal(buffer, 0, bytesRead);
        } else {
          outputBuffer = cipher.update(buffer, 0, bytesRead);
        }

        fileOutputStream.write(outputBuffer, 0, outputBuffer.length);
      }

    } catch (IOException e) {
      System.out.println("Problem with reading file.");
    } catch (BadPaddingException e) {
      System.out.println("bad padding");
    } catch (IllegalBlockSizeException e) {
      System.out.println("Illegal block size.");
    }
  }

  /**
   * Digests given file from its file name and compares it with digest given by
   * the user in standard input. Informs user about 2 digests being equal and if
   * they are not, prints digest it calculated.
   * 
   * @param fileName name of file
   */
  private static void digestFile(String fileName) {
    System.out.println("Please provide expected sha-256 digest for " + fileName + ".");
    Scanner scanner = new Scanner(System.in);
    String expectedDigest = scanner.nextLine();
    scanner.close();

    MessageDigest messageDigest = null;
    try {
      messageDigest = MessageDigest.getInstance("SHA-256");
    } catch (NoSuchAlgorithmException e) {
      System.out.println("Problem with MessageDigest initialization.");
    }

    InputStream fileInputStream = null;
    try {
      fileInputStream = Files.newInputStream(Paths.get(fileName));
    } catch (IOException e) {
      System.out.println("Problem with reading file: " + fileName);
    }

    byte[] buffer = new byte[BUFFER_SIZE];
    int bytesRead;
    try {
      while ((bytesRead = fileInputStream.read(buffer)) != -1) {
        messageDigest.update(buffer, 0, bytesRead);
      }

    } catch (IOException e) {
      System.out.println("Problem with reading file.");
    }

    String digestHex = Util.byteToHex(messageDigest.digest());

    if(expectedDigest.equals(digestHex)) {
      System.out.println("Digesting completed. Digest of " + fileName + " matches expected digest.");
    } else {
      System.out.println("Digesting completed. Digest of " + fileName
          + " does not match the expected digest. Digestwas: " + digestHex);
    }
  }
}
