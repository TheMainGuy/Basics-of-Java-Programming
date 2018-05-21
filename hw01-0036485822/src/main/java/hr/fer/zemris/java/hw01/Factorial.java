package hr.fer.zemris.java.hw01;

import java.util.Scanner;

/**
 * Program which prints factorial of an input number. It scans the input for
 * numbers between 1 and 20 and prints factorial of those numbers until "kraj"
 * is scanned.
 * 
 * @author tin
 * @version 1.0
 */
public class Factorial {
  /**
   * Method which is called when the program starts.
   * 
   * @param args Unused
   */
  public static void main(String[] args) {
    Scanner scanner = new Scanner(System.in);

    while (true) {
      System.out.print("Unesite broj > ");

      String input = scanner.nextLine();

      if(input.equals("kraj")) {
        System.out.println("Doviđenja.");
        scanner.close();
        break;
      }
      else {
        try {
          int number = Integer.parseInt(input);
          if(number < 1 || number > 20) {
            System.out.println("'" + number + "' nije broj u dozvoljenom rasponu.");
          }
          else {
            System.out.println(number + "! = " + calculateFactorial(number));
          }
        } catch (NumberFormatException nfe) {
          System.out.println("'" + input + "' nije cijeli broj.");
        }
      }
    }
  }

  /**
   * Method that recursively calculates factorial of a given number. Valid
   * arguments are in range of 0, 20.
   * 
   * @param number Argument number
   * @return Returns factorial of a given number
   * @throws IllegalArgumentException if number is not in valid range
   */
  public static long calculateFactorial(int number) {
    if(number < 0 || number > 20) {
      throw new IllegalArgumentException(Integer.toString(number) + " is not in valid range.");
    }
    if(number == 1 || number == 0) {
      return 1;
    }
    else {
      return number * calculateFactorial(number - 1);
    }
  }

}
