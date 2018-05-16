package hr.fer.zemris.java.hw01;

import org.junit.Assert;
import org.junit.Test;
import hr.fer.zemris.java.hw01.UniqueNumbers.TreeNode;

public class UniqueNumbersTest {
	@Test
	public void addingValues() {
		TreeNode root = null;
		root = UniqueNumbers.addNode(root, 42);
		root = UniqueNumbers.addNode(root, 76);
		root = UniqueNumbers.addNode(root, 21);
		root = UniqueNumbers.addNode(root, 76);
		root = UniqueNumbers.addNode(root, 35);
		Assert.assertEquals(43, root.value);
		Assert.assertEquals(76, root.right.value);
		Assert.assertEquals(21, root.left.value);
		Assert.assertEquals(35, root.left.right.value);
	}
	
	@Test
	public void countingNodes() {
		TreeNode root = null;
		Assert.assertEquals(0, UniqueNumbers.treeSize(root));
		root = UniqueNumbers.addNode(root, 42);
		root = UniqueNumbers.addNode(root, 76);
		root = UniqueNumbers.addNode(root, 21);
		root = UniqueNumbers.addNode(root, 76);
		root = UniqueNumbers.addNode(root, 35);
		Assert.assertEquals(4, UniqueNumbers.treeSize(root));
	}
	
	@Test
	public void containsValueMethod() {
		TreeNode root = null;
		root = UniqueNumbers.addNode(root, 42);
		root = UniqueNumbers.addNode(root, 76);
		root = UniqueNumbers.addNode(root, 21);
		root = UniqueNumbers.addNode(root, 76);
		root = UniqueNumbers.addNode(root, 35);
		Assert.assertEquals(true, UniqueNumbers.containsValue(root, 42));
		Assert.assertEquals(true, UniqueNumbers.containsValue(root, 76));
		Assert.assertEquals(false, UniqueNumbers.containsValue(root, 100));
	}

}
