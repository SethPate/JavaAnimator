import static org.junit.Assert.assertEquals;

import cs5004.animator.model.Color;
import org.junit.Before;
import org.junit.Test;

/**
 * A Junit test for the Color class. Colors can only have values from 0-100.
 */

public class ColorTest {

  private Color red;

  /**
   * Creates a test case for Color.
   */
  @Before
  public void setUp() {
    red = new Color(1f, 0f, 0f);
  }

  /**
   * Cannot create a Color with red value above 100.
   */
  @Test(expected = IllegalArgumentException.class)
  public void colorRedAbove255() {
    new Color(200f, 0, 0);
  }

  /**
   * Cannot create a Color with green value above 100.
   */
  @Test(expected = IllegalArgumentException.class)
  public void colorGreenAbove255() {
    new Color(0, 200, 0);
  }

  /**
   * Cannot create a Color with blue value above 100.
   */
  @Test(expected = IllegalArgumentException.class)
  public void colorBlueAbove255() {
    new Color(0, 0, 200);
  }

  /**
   * Cannot create a Color with red value below 0.
   */
  @Test(expected = IllegalArgumentException.class)
  public void colorRedBelow0() {
    new Color(-1, 0, 0);
  }

  /**
   * Cannot create a Color with green value below 0.
   */
  @Test(expected = IllegalArgumentException.class)
  public void colorGreenBelow0() {
    new Color(0, -1, 0);
  }

  /**
   * Cannot create a Color with blue value below 0.
   */
  @Test(expected = IllegalArgumentException.class)
  public void colorBlueBelow0() {
    new Color(0, 0, -1);
  }

  /**
   * Colors should return themselves as properly formatted Strings.
   */
  @Test
  public void testToString() {
    assertEquals("(1.0,0.0,0.0)", red.toString());
  }
}