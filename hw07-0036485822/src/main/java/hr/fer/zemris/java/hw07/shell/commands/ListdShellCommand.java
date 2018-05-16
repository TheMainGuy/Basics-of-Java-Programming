package hr.fer.zemris.java.hw07.shell.commands;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import hr.fer.zemris.java.hw07.shell.Environment;
import hr.fer.zemris.java.hw07.shell.EnvironmentImpl;
import hr.fer.zemris.java.hw07.shell.ShellCommand;
import hr.fer.zemris.java.hw07.shell.ShellStatus;

/**
 * Implements list directory shell command which lists all directory pointers
 * stored in stack created by pushd command. Command lists directory pointers
 * starting with last pointer pushed on stack.
 * 
 * @author tin
 *
 */
public class ListdShellCommand implements ShellCommand {
  /**
   * Defines list directory string which user can use print out all directory
   * pointers from stack created by pushd command.
   */
  public static final String LIST_DIRECTORY_COMMAND = "listd";

  @SuppressWarnings("unchecked")
  @Override
  public ShellStatus executeCommand(Environment env, String arguments) {
    List<String> args = ArgumentParser.getArguments(arguments);
    if(args.size() != 0) {
      env.writeln(LIST_DIRECTORY_COMMAND + " must be called with no arguments.");
      return ShellStatus.CONTINUE;
    }
    
    if(env.getSharedData(EnvironmentImpl.CD_STACK) == null
        || ((Stack<Path>) env.getSharedData(EnvironmentImpl.CD_STACK)).isEmpty()) {
      env.writeln("No stored directories.");
    } else {
      Stack<Path> tempStack = new Stack<Path>();
      Stack<Path> stack = ((Stack<Path>) env.getSharedData(EnvironmentImpl.CD_STACK));
      while(!stack.isEmpty()) {
        env.writeln(stack.peek().toString());
        tempStack.push(stack.pop());
      }
      
      while(!tempStack.isEmpty()) {
        stack.push(tempStack.pop());
      }
    }
    
    return ShellStatus.CONTINUE;
    
  }

  @Override
  public String getCommandName() {
    return LIST_DIRECTORY_COMMAND;
  }

  @Override
  public List<String> getCommandDescription() {
    List<String> description = new ArrayList<>();
    description.add("Command " + LIST_DIRECTORY_COMMAND
        + " prints out all directory pointers stored in stack created by using pushd command.");
    description.add("Usage: " + LIST_DIRECTORY_COMMAND);
    description.add("Must be called with no arguments.");
    description.add("Directory pointers are printed starting from last pointer pushed on stack.");
    return description;
  }
}
