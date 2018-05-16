package hr.fer.zemris.java.custom.collections.demo;

import hr.fer.zemris.java.custom.collections.ObjectStack;

/**
 * Demonstration class that demonstrates that ObjectStack class works as
 * expected. It takes 1 command line argument which should be expression
 * containing integer numbers and /, +, *, -, % operators separated by spaces
 * and uses them to test ObjectStack. Each number encountered is pushed on stack
 * and each operator pops 2 numbers from stack and replaces them with the
 * operation result. If more than 1 or 0 command line arguments are passed,
 * prints error. If input causes 1 or more operations can not be performed due
 * to too few elements being on stack, prints error. If input causes program to
 * end with stack size not being 1, prints error. If argument is in wrong
 * format, prints error. If divisor is 0 in 1 or more opearions, prints error.
 * 
 * @author tin
 *
 */
public class StackDemo {
  /**
   * Method which is called upon running program.
   * 
   * @param args commang line argument
   */
  public static void main(String[] args) {
    if (args.length != 1) {
      System.out.println("Program accepts only 1 command line argument.");
    } else {
      String[] arguments = args[0].split(" +");
      if (checkArguments(arguments)) {
        try {
          System.out.println(stackSimulator(arguments));
        } catch (Exception e) {
          System.out.println(e.getMessage());
        }

      } else {
        System.out.println("Arguments invalid.");
      }
    }

  }

  /**
   * Method which checks if the arguments are integer numbers or operators /, +,
   * *, -, %.
   * 
   * @param arguments array of arguments
   * @return true if all the arguments are integer numbers or operators /, +, *,
   *         -, %, otherwise false
   */
  public static boolean checkArguments(String[] arguments) {
    char[] operators = new char[] { '/', '+', '*', '-', '%' };
    for (String argument : arguments) {
      if (argument.length() == 1 && new String(operators).contains(argument)) {
        continue;
      }
      try {
        Integer.parseInt(argument);
      } catch (NumberFormatException e) {
        return false;
      }
    }

    return true;
  }

  /**
   * Method which simulates stack behaviour from given numbers and operators.
   * 
   * @param arguments input numbers and operators
   * @return last element on stack after the input is parsed
   */
  public static Object stackSimulator(String[] arguments) {
    ObjectStack stack = new ObjectStack();
    for (String argument : arguments) {
      try {
        stack.push(Integer.parseInt(argument));
      } catch (NumberFormatException e) {
        stack.push(stackOperation(argument, stack));
      }
    }
    if (stack.size() != 1) {
      throw new IllegalArgumentException(
          "Input you provided causes " + "stack to end with stack size different than 1.");
    }
    return stack.pop();
  }

  /**
   * Method which operates upon 2 elements on top of the given stack with given
   * operator.
   * 
   * @param operator operator with with the operation will be performed
   * @param stack stack from which the 2 elements will be popped
   * @return operation result
   */
  public static int stackOperation(String operator, ObjectStack stack) {
    if (stack.size() < 2) {
      throw new IllegalArgumentException(
          "One or more operations can not be performed due to too few elements being on stack.");
    }
    int firstNumber;
    int secondNumber;
    switch (operator.charAt(0)) {

    case '+':
      return (int) stack.pop() + (int) stack.pop();
    case '-':
      secondNumber = (int) stack.pop();
      firstNumber = (int) stack.pop();
      return firstNumber - secondNumber;
    case '*':
      secondNumber = (int) stack.pop();
      firstNumber = (int) stack.pop();
      return firstNumber * secondNumber;
    case '/':
      secondNumber = (int) stack.pop();
      firstNumber = (int) stack.pop();
      if (secondNumber == 0) {
        throw new IllegalArgumentException("Divisor is 0.");
      }
      return firstNumber / secondNumber;
    default:
      secondNumber = (int) stack.pop();
      firstNumber = (int) stack.pop();
      return firstNumber % secondNumber;
    }
  }

}
