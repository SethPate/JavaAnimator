import static org.junit.Assert.assertEquals;

import cs5004.animator.model.Color;
import cs5004.animator.model.TransformColor;
import org.junit.Before;
import org.junit.Test;

/**
 * A junit test for the TransformColor class. TransformColor needs to be able to return its own
 * start and end values in order to pass a read only shape to the View.
 */
public class TransformColorTest {

  private TransformColor testTransformColor;

  /**
   * Creates test cases for TransformColor.
   */
  @Before
  public void setUp() {
    testTransformColor = new TransformColor(2, 8, "A",
        new Color(0f, 0f, 100f),
        new Color(100f, 0, 100f));
  }

  /**
   * TransfromColor should properly return its start color.
   */
  @Test
  public void getStartColor() {
    assertEquals("(0.0,0.0,100.0)", testTransformColor.getStartColor().toString());
  }

  /**
   * TransfromColor should properly return its end color.
   */
  @Test
  public void getEndColor() {
    assertEquals("(100.0,0.0,100.0)", testTransformColor.getEndColor().toString());
  }
}