package hr.fer.zemris.java.hw01;

import java.util.Scanner;

/**
 * Program which calculates area and perimeter of the rectangle based upon width and height input.
 * If there are 2 command line arguments it interprets them as width and height to calculate area and perimeter.
 * Otherwise, scans input until width and height are input to calculate width and height.
 * @author tin
 * @version 1.0
 */
public class Rectangle {

	/**
	 * Method that is called when the program starts. 
	 * 
	 * @param args Optional width and height of a rectangle.
	 */
	public static void main(String[] args) {
		if (args.length != 0 && args.length != 2) {
			System.out.println("Broj argumenata naredbenog retka mora biti 0 ili 2.");
		} else {
			if (args.length == 2) {
				try {
					double width = Double.parseDouble(args[0]);
					double height = Double.parseDouble(args[1]);
					
					if(width <= 0 || height <= 0) {
						System.out.println("Barem jedan or argumenata naredbenog retka nije pozitivan broj.");
					}
					else {
						ispis(width, height);
					}
				} catch (NumberFormatException e) {
					System.out.println("Argumenti naredbenog retka nisu protumačeni kao brojevi.");
				}
			} else {
				Scanner scanner = new Scanner(System.in);
				double width = getDimension("širinu", scanner);
				double height = getDimension("visinu", scanner);
				scanner.close();
				
				ispis(width, height);
			}
		}
	}

	/**
	 * Method that scans input for a number. It asks for specific parameter from user to input.
	 * Method loops until number is input.
	 * @param inputParameter Parameter for which method asks user to input
	 * @param scanner Scanner scanner
	 * @return Number which is scanned
	 */
	public static double getDimension(String inputParameter, Scanner scanner) {
		while (true) {
			System.out.print("Unesite " + inputParameter + " > ");
			String input = scanner.nextLine();
			try {
				double number = Double.parseDouble(input);
				if(number <= 0) {
					System.out.println("'" + number + "' nije pozitivan broj.");
				}
				else {
					return number;
				}
			} catch (NumberFormatException e) {
				System.out.println("'" + input + "' se ne može protumačiti kao broj.");
			}

		}
	}

	/**
	 * Method that calculates area of a rectangle from a given width and height
	 * @param width Width of a rectangle
	 * @param height Height of a rectangle
	 * @return area of a rectangle
	 */
	public static double area(double width, double height) {
		return width * height;
	}

	/**
	 * Method that calculates perimeter of a rectangle from a given width and height
	 * @param width Width of a rectangle
	 * @param height Height of a rectangle
	 * @return area of a rectangle
	 */
	public static double perimeter(double width, double height) {
		return 2 * width + 2 * height;
	}

	/**
	 * Method which prints out the area and perimeter of a rectangle based on its width and height
	 * @param width Width of a rectangle
	 * @param height Height of a rectangle
	 */
	public static void ispis(double width, double height) {
		System.out.println("Pravokutnik širine " + width + " i visine " + height + " ima površinu "
				+ area(width, height) + " te opseg " + perimeter(width, height) + ".");
	}
}
