package cs5004.animator.controller;

import cs5004.animator.model.IAnimation;
import cs5004.animator.model.OutputType;
import cs5004.animator.view.IAnimationView;
import java.util.Objects;
import javax.swing.Timer;

/**
 * An implementation of the IAnimationController interface. This AnimationController must be
 * initialized with the model (IAnimation) and a view (IAnimationView). Its sole purpose is to
 * execute run().
 */
public class AnimationController implements IAnimationController {

  private IAnimation animation;
  private IAnimationView view;
  private int ticksPerSecond;
  private OutputType outputType;
  private Timer timer;
  private IAnimationController controller;

  /**
   * Creates a new instance of a AnimationController.
   *
   * @param animation something to be animated; the model.
   * @param view a ViewFile object with information about the output destination.
   * @param speed the speed at which to animate the model.
   * @param outputType the format in which to animate the model.
   * @throws NullPointerException if any argument is null.
   */
  public AnimationController(IAnimation animation, IAnimationView view, int speed,
      OutputType outputType) throws NullPointerException {
    Objects.requireNonNull(animation);
    Objects.requireNonNull(view);
    Objects.requireNonNull(outputType);
    this.animation = animation;
    this.view = view;
    this.ticksPerSecond = speed;
    this.outputType = outputType;
  }

  /**
   * Requests an output from the IAnimation with the given output type and speed. Hands that
   * information to the IAnimationView to be displayed.
   */
  public void run() {
    try {
      // request information of the correct format from the model
      String animateThis = animation.generate(outputType, ticksPerSecond);

      // pass information to the view to output
      view.render(animateThis);
    } catch (Exception e) {
      System.out.println(e.getMessage());
      System.exit(-1);
    }
  }

  /**
   * Prints the contents of this AnimationController to a String.
   *
   * @return a String with the animation, view, speed, and output type as lines.
   */
  @Override
  public String toString() {
    String output = this.animation.toString() + "\n";
    output += this.view.toString() + "\n";
    output += this.ticksPerSecond + "\n";
    output += this.outputType + "\n";
    return output;
  }

  /**
   * Gets the current ticks per second of this IAnimationController.
   *
   * @return the ticks per second.
   */
  public int getTicksPerSecond() {
    return this.ticksPerSecond;
  }
}