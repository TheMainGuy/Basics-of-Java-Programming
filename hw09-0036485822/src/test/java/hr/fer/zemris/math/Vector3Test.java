package hr.fer.zemris.math;

import org.junit.Assert;
import org.junit.Test;

import hr.fer.zemris.math.Vector3;

public class Vector3Test {
  public static final double DELTA = 0.00001;

  @Test
  public void constructorAndGetters() {
    Vector3 vector = new Vector3(4, 5, 6);
    Assert.assertEquals(4, vector.getX(), DELTA);
    Assert.assertEquals(5, vector.getY(), DELTA);
  }
  
  @Test
  public void norm() {
    Vector3 vector = new Vector3(4, 5, 6);
    Assert.assertEquals(8.77496, vector.norm(), DELTA);
  }
  
  @Test
  public void normalized() {
    Vector3 vector = new Vector3(4, 5, 6);
    vector = vector.normalized();
    Assert.assertEquals(4/Math.sqrt(77), vector.getX(), DELTA);
    Assert.assertEquals(5/Math.sqrt(77), vector.getY(), DELTA);
    Assert.assertEquals(6/Math.sqrt(77), vector.getZ(), DELTA);
  }
  
  @Test
  public void add() {
    Vector3 vector = new Vector3(4, 5, 6);
    Vector3 vector2 = new Vector3(-1, 2 ,3);
    vector = vector.add(vector2);
    Assert.assertEquals(3, vector.getX(), DELTA);
    Assert.assertEquals(7, vector.getY(), DELTA);
    Assert.assertEquals(9, vector.getZ(), DELTA);
    Vector3 vector3 = vector.add(vector2);
    Assert.assertEquals(2, vector3.getX(), DELTA);
    Assert.assertEquals(9, vector3.getY(), DELTA);
    Assert.assertEquals(12, vector3.getZ(), DELTA);
  }
  
  @Test
  public void sub() {
    Vector3 vector = new Vector3(4, 5, 6);
    Vector3 vector2 = new Vector3(-1, 2 ,3);
    vector = vector.sub(vector2);
    Assert.assertEquals(5, vector.getX(), DELTA);
    Assert.assertEquals(3, vector.getY(), DELTA);
    Assert.assertEquals(3, vector.getZ(), DELTA);
    vector = vector.sub(vector2);
    Assert.assertEquals(6, vector.getX(), DELTA);
    Assert.assertEquals(1, vector.getY(), DELTA);
    Assert.assertEquals(0, vector.getZ(), DELTA);
  }
  
  @Test(expected = NullPointerException.class)
  public void addNull() {
    Vector3 vector = new Vector3(4, 5, 6);
    vector.add(null);
  }
  
  @Test
  public void scale() {
    Vector3 vector = new Vector3(4, 5, 6);
    vector = vector.scale(2);
    Assert.assertEquals(8, vector.getX(), DELTA);
    Assert.assertEquals(10, vector.getY(), DELTA);
    Assert.assertEquals(12, vector.getZ(), DELTA);
    vector = vector.scale(-2);
    Assert.assertEquals(-16, vector.getX(), DELTA);
    Assert.assertEquals(-20, vector.getY(), DELTA);
    Assert.assertEquals(-24, vector.getZ(), DELTA);
    vector = vector.scale(-0.25);
    Assert.assertEquals(4, vector.getX(), DELTA);
    Assert.assertEquals(5, vector.getY(), DELTA);
    Assert.assertEquals(6, vector.getZ(), DELTA);
  }
  
  @Test
  public void dotProduct() {
    Vector3 vector = new Vector3(4, 5, 6);
    Vector3 vector2 = new Vector3(2, -1, 0);
    double product = vector.dot(vector2);
    Assert.assertEquals(3, product, DELTA);
  }
  
  @Test
  public void crossProduct() {
    Vector3 vector = new Vector3(4, 5, 6);
    Vector3 vector2 = new Vector3(2, -1, 0);
    vector = vector.cross(vector2);
    
    Assert.assertEquals(6, vector.getX(), DELTA);
    Assert.assertEquals(12, vector.getY(), DELTA);
    Assert.assertEquals(-14, vector.getZ(), DELTA);
  }
  
  @Test
  public void toStringTest() {
    Vector3 vector = new Vector3(4, 5, 6);
    Assert.assertEquals("(4.000000, 5.000000, 6.000000)", vector.toString());
    vector = new Vector3(4, -5, -6);
    Assert.assertEquals("(4.000000, -5.000000, -6.000000)", vector.toString());
    vector = new Vector3(-4, 0, 10);
    Assert.assertEquals("(-4.000000, 0.000000, 10.000000)", vector.toString());
  }
  
  @Test
  public void cosAngle() {
    Vector3 vector = new Vector3(4, 5, 6);
    Vector3 vector2 = new Vector3(1,1,2);
    Assert.assertEquals(0.977008, vector.cosAngle(vector2), DELTA);
    
    Vector3 vector3 = new Vector3(-4, -5, -6);
    Assert.assertEquals(-1, vector3.cosAngle(vector), DELTA);
  }
  
  
}
