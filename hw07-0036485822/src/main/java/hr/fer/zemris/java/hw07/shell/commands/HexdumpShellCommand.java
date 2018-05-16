package hr.fer.zemris.java.hw07.shell.commands;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import hr.fer.zemris.java.hw07.crypto.Util;
import hr.fer.zemris.java.hw07.shell.Environment;
import hr.fer.zemris.java.hw07.shell.ShellCommand;
import hr.fer.zemris.java.hw07.shell.ShellStatus;

/**
 * Implements hexdump shell command which prints hex-output from a given file to
 * user output.
 * 
 * @author tin
 *
 */
public class HexdumpShellCommand implements ShellCommand {
  /**
   * Defines hexdump string which user can use to print out hex-output of a file.
   */
  public static final String HEXDUMP_COMMAND = "hexdump";

  @Override
  public ShellStatus executeCommand(Environment env, String arguments) {
    List<String> args = ArgumentParser.getArguments(arguments);
    if(args.size() != 1) {
      env.writeln("Command " + HEXDUMP_COMMAND + " must have one argument.");
      return ShellStatus.CONTINUE;
    }

    try {
      InputStream reader = Files.newInputStream(env.getCurrentDirectory().resolve(Paths.get(args.get(0))));
      byte[] buffer = new byte[16];
      int bytesRead;
      int hexCount = 0;
      while ((bytesRead = reader.read(buffer)) > 0) {
        env.writeln(getHexdumpLine(hexCount, bytesRead, buffer));
        hexCount++;
      }
    } catch (IOException e) {
      env.writeln(HEXDUMP_COMMAND + " had problem with reading file: " + args.get(0));
      return ShellStatus.CONTINUE;
    }
    return ShellStatus.CONTINUE;
  }

  /**
   * Helper method creates hexdump line ready for output. Line is in format:
   * 0000000000: 43 54 65 76 76 87 7E 56|39 20 6E 6B 20 56 54 | text
   * 
   * @param hexCount number of lines preceding this line
   * @param bytesRead number of bytes in this line
   * @param buffer data
   */
  private String getHexdumpLine(int hexCount, int bytesRead, byte[] buffer) {
    StringBuilder stringBuilder = new StringBuilder();
    String s = Integer.toString(hexCount);
    for (int i = s.length() + 1; i < 8; i++) {
      stringBuilder.append("0");
    }
    for (int i = 0; i < s.length(); i++) {
      stringBuilder.append(s.charAt(i));
    }
    stringBuilder.append("0: ");

    s = Util.byteToHex(buffer).toUpperCase();
    for (int i = 0; i < 16; i++) {
      if(i < bytesRead) {
        stringBuilder.append(s.charAt(i * 2)).append(s.charAt(i * 2 + 1));
      } else {
        stringBuilder.append("  ");
      }
      if(i == 7) {
        stringBuilder.append("|");
      }
      if(i == 15) {
        stringBuilder.append(" | ");
      } else {
        stringBuilder.append(" ");
      }
    }

    for (int i = 0; i < bytesRead; i++) {
      if(buffer[i] < 37 || buffer[i] > 127) {
        stringBuilder.append(".");
      } else {
        stringBuilder.append((char) buffer[i]);
      }
    }
    return stringBuilder.toString();
  }

  @Override
  public String getCommandName() {
    return HEXDUMP_COMMAND;
  }

  @Override
  public List<String> getCommandDescription() {
    List<String> description = new ArrayList<>();
    description.add("Command " + HEXDUMP_COMMAND + " prints out hex-output content of given file.");
    description.add("Usage: " + HEXDUMP_COMMAND + " FILE_NAME");
    description.add("Must be called with one argument, file to be read and hex-output.");
    return description;
  }
}
