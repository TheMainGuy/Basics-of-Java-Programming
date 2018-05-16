package hr.fer.zemris.java.hw07.shell;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.SortedMap;
import java.util.TreeMap;

import hr.fer.zemris.java.hw07.shell.commands.CatShellCommand;
import hr.fer.zemris.java.hw07.shell.commands.CdShellCommand;
import hr.fer.zemris.java.hw07.shell.commands.CharsetsShellCommand;
import hr.fer.zemris.java.hw07.shell.commands.CopyShellCommand;
import hr.fer.zemris.java.hw07.shell.commands.CptreeShellCommand;
import hr.fer.zemris.java.hw07.shell.commands.DropdShellCommand;
import hr.fer.zemris.java.hw07.shell.commands.ExitShellCommand;
import hr.fer.zemris.java.hw07.shell.commands.HelpShellCommand;
import hr.fer.zemris.java.hw07.shell.commands.HexdumpShellCommand;
import hr.fer.zemris.java.hw07.shell.commands.ListdShellCommand;
import hr.fer.zemris.java.hw07.shell.commands.LsShellCommand;
import hr.fer.zemris.java.hw07.shell.commands.MassrenameShellCommand;
import hr.fer.zemris.java.hw07.shell.commands.MkdirShellCommand;
import hr.fer.zemris.java.hw07.shell.commands.PopdShellCommand;
import hr.fer.zemris.java.hw07.shell.commands.PushdShellCommand;
import hr.fer.zemris.java.hw07.shell.commands.PwdShellCommand;
import hr.fer.zemris.java.hw07.shell.commands.RmtreeShellCommand;
import hr.fer.zemris.java.hw07.shell.commands.SymbolShellCommand;
import hr.fer.zemris.java.hw07.shell.commands.TreeShellCommand;

/**
 * Implements basic command shell. Shell supports all basic and some advanced
 * commands: cd, ls, mkdir, exit, cat, copy, help, hexdump, charsets, pwd,
 * pushd, popd, listd, dropd.
 * 
 * @author tin
 *
 */
public class MyShell {

  /**
   * Method which is called when program starts.
   * 
   * @param args command line arguments
   */
  public static void main(String[] args) {
    System.out.println("Welcome to MyShell v 1.0");
    SortedMap<String, ShellCommand> commands = new TreeMap<>();
    commands.put(ExitShellCommand.EXIT_COMMAND, new ExitShellCommand());
    commands.put(SymbolShellCommand.SYMBOL_COMMAND, new SymbolShellCommand());
    commands.put(CharsetsShellCommand.CHARSETS_COMMAND, new CharsetsShellCommand());
    commands.put(MkdirShellCommand.MKDIR_COMMAND, new MkdirShellCommand());
    commands.put(CatShellCommand.CAT_COMMAND, new CatShellCommand());
    commands.put(LsShellCommand.LS_COMMAND, new LsShellCommand());
    commands.put(HelpShellCommand.HELP_COMMAND, new HelpShellCommand());
    commands.put(TreeShellCommand.TREE_COMMAND, new TreeShellCommand());
    commands.put(CopyShellCommand.COPY_COMMAND, new CopyShellCommand());
    commands.put(HexdumpShellCommand.HEXDUMP_COMMAND, new HexdumpShellCommand());
    commands.put(PwdShellCommand.PWD_COMMAND, new PwdShellCommand());
    commands.put(CdShellCommand.CD_COMMAND, new CdShellCommand());
    commands.put(PushdShellCommand.PUSH_DIRECTORY_COMMAND, new PushdShellCommand());
    commands.put(PopdShellCommand.POP_DIRECTORY_COMMAND, new PopdShellCommand());
    commands.put(ListdShellCommand.LIST_DIRECTORY_COMMAND, new ListdShellCommand());
    commands.put(DropdShellCommand.DROP_DIRECTORY_COMMAND, new DropdShellCommand());
    commands.put(RmtreeShellCommand.REMOVE_TREE_COMMAND, new RmtreeShellCommand());
    commands.put(CptreeShellCommand.COPY_TREE_COMMAND, new CptreeShellCommand());
    commands.put(MassrenameShellCommand.MASS_RENAME_COMMAND, new MassrenameShellCommand());

    Path path = FileSystems.getDefault().getPath("").toAbsolutePath();
    BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));
    Environment env = new EnvironmentImpl(commands, reader, writer, path);

    while (true) {
      env.write(env.getPromptSymbol() + " ");

      String input = env.readLine();
      String commandName = input.split(" ")[0];
      String arguments;
      if(commandName.length() == input.length()) {
        arguments = "";
      } else {
        arguments = input.substring(commandName.length() + 1, input.length());
      }

      if(!commands.containsKey(commandName)) {
        env.writeln(commandName + " can not be interpreted as command.");
        continue;
      }

      ShellCommand command = commands.get(commandName);
      if(command.executeCommand(env, arguments) == ShellStatus.TERMINATE) {
        break;
      }
    }

  }
}
