package cs5004.animator.view;

/**
 * An interface representing a way to view an animation. These Views can parse a model into various
 * types of output (i.e. text, SVG). The ViewFile can be configured to display the model's animation
 * instructions at a certain speed (ticks per second).
 */

public interface IAnimationView {

  /**
   * Takes some animation instructions that the model has formatted to the desired spec (for
   * example, text or SVG). Render() writes this String to the desired location (for example,
   * System.out) in the desired format.
   *
   * @param data some sort of formatted data from the model to be displayed.
   */
  void render(Object data);
}
