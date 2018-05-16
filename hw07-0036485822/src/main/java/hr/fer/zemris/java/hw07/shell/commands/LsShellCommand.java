package hr.fer.zemris.java.hw07.shell.commands;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributeView;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import hr.fer.zemris.java.hw07.shell.Environment;
import hr.fer.zemris.java.hw07.shell.ShellCommand;
import hr.fer.zemris.java.hw07.shell.ShellStatus;

/**
 * Implements ls shell command. Takes one argument and lists all files and
 * folder in given directory ie. directory listing. Whole directory listing is
 * in the following format: drwx size date time name. drwx represent directory,
 * readable, writable and executable respectively, size is file size in bytes,
 * date and time are last modified date and time and name is file's name.
 * 
 * @author tin
 *
 */
public class LsShellCommand implements ShellCommand {
  /**
   * Defines ls string which user can use to print out directory listing from
   * specified directory.
   */
  public static final String LS_COMMAND = "ls";

  /**
   * Determines size of second column in number of characters.
   */
  private final int SECOND_COLUMN_SIZE = 10;

  @Override
  public ShellStatus executeCommand(Environment env, String arguments) {
    List<String> args = ArgumentParser.getArguments(arguments);
    if(args.size() != 1) {
      env.writeln(LS_COMMAND + " must be provided with one argument.");
      return ShellStatus.CONTINUE;
    }

    try {
      File[] children = (new File(env.getCurrentDirectory().resolve(Paths.get(args.get(0))).toString())).listFiles();
      Arrays.sort(children);
      for (File file : children) {
        env.write(getFirstColumn(file));
        env.write(" " + getSecondColumn(file));
        env.write(" " + getThirdColumn(file));
        env.writeln(" " + file.getName());
      }
    } catch (Exception e) {
      env.writeln("Given directory does not exist.");
    }
    return ShellStatus.CONTINUE;
  }

  @Override
  public String getCommandName() {
    return LS_COMMAND;
  }

  @Override
  public List<String> getCommandDescription() {
    List<String> description = new ArrayList<>();
    description.add("Command " + LS_COMMAND + " prints out directory listing from specified directory.");
    description.add("Usage: " + LS_COMMAND + " DIRECTORY");
    description
        .add("If more than one argument is provided or given directory does not exist, invalid message will be printed"
            + "instead of directory listing.");
    return description;
  }

  private String getThirdColumn(File file) {
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    Path path = Paths.get(file.toString());
    BasicFileAttributeView faView = Files.getFileAttributeView(path, BasicFileAttributeView.class,
        LinkOption.NOFOLLOW_LINKS);
    BasicFileAttributes attributes = null;
    try {
      attributes = faView.readAttributes();
    } catch (IOException e) {
      return null;
    }
    FileTime fileTime = attributes.creationTime();
    String formattedDateTime = sdf.format(new Date(fileTime.toMillis()));
    return formattedDateTime;
  }

  /**
   * Helper method which creates {@link String} from file size in bytes. Returned
   * {@link String} will be SECOND_COLUMN_SIZE characters long with leading
   * whitespaces.
   * 
   * @param file file
   * @return SECOND_COLUMN_SIZE characters long string indicating file size in
   *         bytes
   */
  private String getSecondColumn(File file) {
    StringBuilder stringBuilder = new StringBuilder(SECOND_COLUMN_SIZE);
    String s = Long.toString(file.length());
    for (int i = s.length(); i < 10; i++) {
      stringBuilder.append(" ");
    }
    return stringBuilder.append(s).toString();
  }

  /**
   * Helper method checks if given file is directory, readable, writable and
   * executable and constructs a simple {@link String} containing info about those
   * properties.
   * 
   * @param file file
   * @return
   */
  private String getFirstColumn(File file) {
    StringBuilder stringBuilder = new StringBuilder(4);
    if(file.isDirectory()) {
      stringBuilder.append('d');
    } else {
      stringBuilder.append('-');
    }

    if(file.canRead()) {
      stringBuilder.append('r');
    } else {
      stringBuilder.append('-');
    }

    if(file.canWrite()) {
      stringBuilder.append('w');
    } else {
      stringBuilder.append('-');
    }

    if(file.canExecute()) {
      stringBuilder.append('x');
    } else {
      stringBuilder.append('-');
    }

    return stringBuilder.toString();
  }

}
