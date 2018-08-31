package cs5004.animator.controller;

/**
 * An implementation of the IAnimationController interface. This AnimationController must be
 * initialized with the model (IAnimation) and a view (IAnimationView). Its sole purpose is to
 * execute run().
 */

public interface IAnimationController {

  /**
   * Requests an output from the IAnimation with the given output type and speed. Hands that
   * information to the IAnimationView to be displayed.
   */
  void run();

  /**
   * Gets the current ticks per second of this IAnimationController.
   *
   * @return the ticks per second.
   */
  int getTicksPerSecond();
}
