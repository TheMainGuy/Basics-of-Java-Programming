package hr.fer.zemris.java.hw01;

import org.junit.Assert;
import org.junit.Test;

public class FactorialTest {
	@Test
	public void forZero() {
		Assert.assertEquals(1, Factorial.calculateFactorial(0));
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void forNegative() {
		Factorial.calculateFactorial(-4);
	}
	
	@Test
	public void someTestNumbers() {
		Assert.assertEquals(1, Factorial.calculateFactorial(1));
		Assert.assertEquals(2, Factorial.calculateFactorial(2));
		Assert.assertEquals(6, Factorial.calculateFactorial(3));
		Assert.assertEquals(3628800, Factorial.calculateFactorial(10));
		Assert.assertEquals(2432902008176640000L, Factorial.calculateFactorial(20));
	}
	

}
