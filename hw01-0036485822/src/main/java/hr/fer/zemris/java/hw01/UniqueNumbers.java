package hr.fer.zemris.java.hw01;

import java.util.Scanner;

/**
 * Program which builds binary search tree by scanning user input values and storing them as tree nodes.
 * It scans user input and builds tree with it until "kraj" input is given,
 * then prints tree in ascending and descending order.
 * @author tin
 * @version 1.0
 */
public class UniqueNumbers {
	
	/**
	 * Class used to represent tree node in a binary search tree
	 * @author tin
	 *
	 */
	public static class TreeNode {
		int value;
		TreeNode left;
		TreeNode right;
	}

	/**
	 * Method which starts when program is run.
	 * @param args
	 */
	public static void main(String[] args) {
		TreeNode root = null;
		
		Scanner scanner = new Scanner(System.in);
		while(true) {
			System.out.print("Unesite Broj > ");
			
			String input = scanner.nextLine();
			if(input.equals("kraj")) {
				System.out.print("Ispis od najmanjeg:");
				printTreeAscending(root);
				System.out.print("\nIspis od najvećeg: ");
				printTreeDescending(root);
				break;
			}
			else {
				try {
					int number = Integer.parseInt(input);
					if(containsValue(root, number)) {
						System.out.println("Broj već postoji. Preskačem.");
					}
					else {
						root = addNode(root, number);
						System.out.println("Dodano.");
					}
				}catch (NumberFormatException e) {
					System.out.println("'" + input + "' nije cijeli broj.");
				}
			}
		}
		scanner.close();
	}
	/**
	 * Method which adds node to binary tree
	 * @param root Tree in which to add node
	 * @param value Value to be added in tree
	 * @return New tree with new node
	 */
	public static TreeNode addNode(TreeNode root, int value) {
		if(root == null) {
			root = new TreeNode();
			root.value = value;
			return root;
		}
		else {
			if(value < root.value) {
				root.left = addNode(root.left, value);
				return root;
			}
			else if(value > root.value) {
				root.right = addNode(root.right, value);
				return root;
			}
			return root;
		}
	}
	
	
	/**
	 * Method which counts the number of tree nodes
	 * @param root Tree for which the calculation will be made
	 * @return Number of nodes in root tree
	 */
	public static int treeSize(TreeNode root) {
		if(root == null) {
			return 0;
		}
		else {
			return treeSize(root.left) + treeSize(root.right) + 1;
		}
	}
	
	/**
	 * Method which checks for a specific value
	 * @param root Tree in which it searches for value
	 * @param value Value which will be searched
	 * @return True if value is found. False if it's not
	 */
	
	public static boolean containsValue(TreeNode root, int value) {
		if(root==null) {
			return false;
		}
		else {
			if(value == root.value) {
				return true;
			}
			else if(value < root.value) {
				return containsValue(root.left, value);
			}
			else {
				return containsValue(root.right, value);
			}
		}
	}
	
	/**
	 * Method which prints tree in ascending order.
	 * Format of printing is " %d" for each node value
	 * @param root Tree which the method will print
	 */

	public static void printTreeAscending(TreeNode root) {
		if(root == null) {
			return;
		}
		else {
			printTreeAscending(root.left);
			System.out.print(" " + root.value);
			printTreeAscending(root.right);
		}
	}
	

	/**
	 * Method which prints tree in descending order.
	 * Format of printing is " %d" for each node value
	 * @param root Tree which the method will print
	 */
	public static void printTreeDescending(TreeNode root) {
		if(root == null) {
			return;
		}
		else {
			printTreeDescending(root.right);
			System.out.print(" " + root.value);
			printTreeDescending(root.left);
		}
	}
}
