package cs5004.animator.view;

import java.util.Objects;

/**
 * An implementation of the IAnimationView interface. ViewSystemOut prints directly to System.out.
 */

public class ViewSystemOut implements IAnimationView {

  /**
   * Takes a String of animation instructions that the model has formatted to the desired spec (for
   * example, text or SVG). Render() writes this String to System.out.
   *
   * @param animateThis a String in the correct format to be animated.
   * @throws IllegalArgumentException if called with null parameter.
   */
  public void render(Object animateThis) throws IllegalArgumentException {
    Objects.requireNonNull(animateThis);
    System.out.println((String) animateThis);
  }

  /**
   * Returns this view as a String.
   *
   * @return an empty String.
   */
  @Override
  public String toString() {
    return "";
  }
}
