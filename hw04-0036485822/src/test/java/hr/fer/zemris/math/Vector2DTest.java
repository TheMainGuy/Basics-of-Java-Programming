package hr.fer.zemris.math;

import org.junit.Assert;
import org.junit.Test;

public class Vector2DTest {
  public static final double DELTA = 0.001;

  @Test
  public void constructorAndGetters() {
    Vector2D vector = new Vector2D(4, 5);
    Assert.assertEquals(4, vector.getX(), DELTA);
    Assert.assertEquals(5, vector.getY(), DELTA);
  }
  
  @Test
  public void translateAndTranslated() {
    Vector2D vector = new Vector2D(4, 5);
    Vector2D vector2 = new Vector2D(-1, 2);
    vector.translate(vector2);
    Assert.assertEquals(3, vector.getX(), DELTA);
    Assert.assertEquals(7, vector.getY(), DELTA);
    Vector2D vector3 = vector.translated(vector2);
    Assert.assertEquals(2, vector3.getX(), DELTA);
    Assert.assertEquals(9, vector3.getY(), DELTA);
  }
  
  @Test(expected = NullPointerException.class)
  public void translateNull() {
    Vector2D vector = new Vector2D(4, 5);
    vector.translate(null);
  }

  @Test(expected = NullPointerException.class)
  public void translatedNull() {
    Vector2D vector = new Vector2D(4, 5);
    vector.translated(null);
  }
  
  @Test
  public void rotateAndRotated() {
    Vector2D vector = new Vector2D(4, 4);
    vector.rotate(45);
    Assert.assertEquals(0, vector.getX(), DELTA);
    Assert.assertEquals(Math.sqrt(16+16), vector.getY(), DELTA);
    vector.rotate(-45);
    Assert.assertEquals(4, vector.getX(), DELTA);
    Assert.assertEquals(4, vector.getY(), DELTA);
    vector.rotate(90);
    Assert.assertEquals(-4, vector.getX(), DELTA);
    Assert.assertEquals(4, vector.getY(), DELTA);
    vector.rotate(90);
    Assert.assertEquals(-4, vector.getX(), DELTA);
    Assert.assertEquals(-4, vector.getY(), DELTA);
    vector.rotate(90);
    Assert.assertEquals(4, vector.getX(), DELTA);
    Assert.assertEquals(-4, vector.getY(), DELTA);
    vector.rotate(90);
    Assert.assertEquals(4, vector.getX(), DELTA);
    Assert.assertEquals(4, vector.getY(), DELTA);
    vector.rotate(360);
    Assert.assertEquals(4, vector.getX(), DELTA);
    Assert.assertEquals(4, vector.getY(), DELTA);
    vector.rotate(180);
    Assert.assertEquals(-4, vector.getX(), DELTA);
    Assert.assertEquals(-4, vector.getY(), DELTA);
    Vector2D vector2 = vector.rotated(0);
    Assert.assertEquals(-4, vector2.getX(), DELTA);
    Assert.assertEquals(-4, vector2.getY(), DELTA);
  }
  
  @Test
  public void scaleAndScaled() {
    Vector2D vector = new Vector2D(4, 5);
    vector.scale(2);
    Assert.assertEquals(8, vector.getX(), DELTA);
    Assert.assertEquals(10, vector.getY(), DELTA);
    vector.scale(-2);
    Assert.assertEquals(-16, vector.getX(), DELTA);
    Assert.assertEquals(-20, vector.getY(), DELTA);
    vector.scale(-0.25);
    Assert.assertEquals(4, vector.getX(), DELTA);
    Assert.assertEquals(5, vector.getY(), DELTA);
    Vector2D vector2 = vector.scaled(1);
    Assert.assertEquals(vector.getX(), vector2.getX(), DELTA);
    Assert.assertEquals(vector.getY(), vector2.getY(), DELTA);
    Vector2D vector3 = vector.scaled(4);
    Assert.assertEquals(vector.getX() * 4, vector3.getX(), DELTA);
    Assert.assertEquals(vector.getY() * 4, vector3.getY(), DELTA);
  }
  
  @Test
  public void copyTest() {
    Vector2D vector = new Vector2D(4, 5);
    Vector2D vector2 = vector.copy();
    Assert.assertEquals(vector.getX(), vector2.getX(), DELTA);
    Assert.assertEquals(vector.getY(), vector2.getY(), DELTA);
  }
  
  @Test
  public void degreeAngleTest() {
    Vector2D vector = new Vector2D(4, 4);
    Assert.assertEquals(45, vector.getDegreeAngle(), DELTA);
    vector.rotate(20);
    Assert.assertEquals(65, vector.getDegreeAngle(), DELTA);
    vector.rotate(40);
    Assert.assertEquals(105, vector.getDegreeAngle(), DELTA);
    vector.rotate(100);
    Assert.assertEquals(205, vector.getDegreeAngle(), DELTA);
    vector.rotate(-40);
    Assert.assertEquals(165, vector.getDegreeAngle(), DELTA);
    vector.rotate(200);
    Assert.assertEquals(5, vector.getDegreeAngle(), DELTA);
    vector.rotate(-40);
    Assert.assertEquals(325, vector.getDegreeAngle(), DELTA);
    vector.rotate(0);
    Assert.assertEquals(325, vector.getDegreeAngle(), DELTA);
    
  }
}
