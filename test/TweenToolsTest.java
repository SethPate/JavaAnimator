import static org.junit.Assert.assertEquals;

import cs5004.animator.util.TweenTools;
import org.junit.Test;

/**
 * A Junit test for the Tween Tools set. The Tween algorithm needs to work properly to send shapes
 * to the viewer.
 */

public class TweenToolsTest {

  /**
   * Tween should return the proper value for several cases.
   */
  @Test
  public void tween() {
    assertEquals(0.0, TweenTools.tween(0, 100, 1, 10, 1), .001);
    assertEquals(44.4444, TweenTools.tween(0, 100, 1, 10, 5), .001);
    assertEquals(100.0, TweenTools.tween(0, 100, 1, 10, 10), .001);
  }
}