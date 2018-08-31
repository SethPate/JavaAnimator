package cs5004.animator.controller;

import cs5004.animator.model.IAnimation;
import cs5004.animator.model.IRenderableShape;
import cs5004.animator.model.OutputType;
import cs5004.animator.view.IAnimationView;
import cs5004.animator.view.ViewFile;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Objects;
import javax.swing.Timer;

public class AnimationControllerGUI implements IAnimationController {

  private IAnimation animation;
  private IAnimationView view;
  private int defaultTicksPerSecond;
  private int ticksPerSecond;
  private OutputType outputType;
  private Timer timer;
  private int tick;
  private int lastTick;

  /**
   * Creates a new instance of a AnimationController.
   *
   * @param animation something to be animated; the model.
   * @param view a ViewFile object with information about the output destination.
   * @param speed the speed at which to animate the model.
   * @param outputType the format in which to animate the model.
   * @throws NullPointerException if any argument is null.
   */
  public AnimationControllerGUI(IAnimation animation, IAnimationView view, int speed,
      OutputType outputType) throws NullPointerException {
    Objects.requireNonNull(animation);
    Objects.requireNonNull(view);
    Objects.requireNonNull(outputType);
    this.animation = animation;
    this.view = view;
    this.defaultTicksPerSecond = speed;
    this.ticksPerSecond = speed;
    this.lastTick = this.animation.lastTick();
    this.outputType = outputType;

    this.timer = new Timer(1000 / this.ticksPerSecond, new ActionListener() {

      @Override
      public void actionPerformed(ActionEvent e) {
        if (tick < lastTick) {
          List<IRenderableShape> shapeList = animation.generateFrame(tick);
          view.render(shapeList);
          tick++;
        } else {
          timer.stop();
        }
      }
    });
  }

  /**
   * Uses a timer object to sample frames from the IAnimation at a certain rate. Requests
   * information from the IAnimation as a List of IRenderableShapes, then passes that information to
   * the IAnimationView to be rendered.
   */
  public void run() {
    timer.start();
  }

  /**
   * Stops the timer, for example, in response to a button press of the IAnimationView.
   */
  public void stopTimer() {
    timer.stop();
  }

  /**
   * Starts the timer, for example, in response to a button press of the IAnimationView. Can restart
   * the timer if called when the IAnimation is at its last tick.
   */
  public void startTimer() {
    if (tick >= this.animation.lastTick()) {
      this.ticksPerSecond = this.defaultTicksPerSecond; // resets speed to original
      tick = 0;
    }
    timer.start();
  }

  /**
   * Increases the ticks per second by a 5, with no limit. In response to an ActionEvent of the
   * IAnimationView.
   */
  public void speedUp() {
    this.ticksPerSecond += 5;
    timer.setDelay(1000 / ticksPerSecond);
  }

  /**
   * Decreases the ticks per second by 5, to a minimum of 1. In response to an ActionEvent of the
   * IAnimationView.
   */
  public void speedDown() {
    if (ticksPerSecond - 5 < 1) {
      this.ticksPerSecond = 1;
    } else {
      this.ticksPerSecond -= 5;
    }
    timer.setDelay(1000 / ticksPerSecond);
  }

  /**
   * Saves an SVG to a filename specified by user. Creates a new IAnimationView object and requests
   * the IAnimation to generate SVG data for it at a given speed. Performed in response to a request
   * from the IAnimationView.
   *
   * @param filename the name to save the SVG file as.
   */
  public void saveSVG(String filename) {
    IAnimationView viewFile = new ViewFile(filename);
    viewFile.render(this.animation.generate(OutputType.SVG, this.ticksPerSecond));
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
