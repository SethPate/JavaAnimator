package cs5004.animator.util;

/**
 * A class containing static methods for use in calculating tween values. This is mainly for use by
 * the IAnimation model in calculating attributes for IRenderableShapes.
 */
public class TweenTools {

  /**
   * This Tweening algorithm returns the value for a given attribute using linear interpolation.
   *
   * @param valueAtStart the value of the attribute at the beginning of the animation.
   * @param valueAtEnd the value of the attribute at the end of the animation.
   * @param start the beginning time of the animation.
   * @param end the ending time of the animation.
   * @param time the time to calculate the value of.
   * @return the value of the attribute at the given time.
   */
  public static float tween(float valueAtStart, float valueAtEnd, float start, float end,
      float time) {
    float diff = end - start;
    float a = valueAtStart * ((end - time) / diff);
    float b = valueAtEnd * ((time - start) / diff);
    return a + b;
  }

}
