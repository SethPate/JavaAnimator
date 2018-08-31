package cs5004.animator.model;

import java.util.List;

/**
 * An interface representing an Animation. Animations consist of Shapes and Transformations that be
 * applied to Shapes.
 */

public interface IAnimation {

  /**
   * Adds a Shape to this Animation.
   *
   * @param shape an instance of an object implementing the IShape interface.
   */
  void addShape(IShape shape);

  /**
   * Adds a Transformation to this Animation.
   *
   * @param transformation an instance of an object implementing the ITransformation interface.
   */
  void addTransformation(ITransformation transformation);

  /**
   * Gets the IShapes contained by this Animation.
   *
   * @return A List of IShape containing this Animation's shapes.
   */
  List<IShape> getShapes();

  /**
   * Returns a String formatted to the specification of the OutputType, and according to the given
   * speed.
   *
   * @param outputType an enum containing the desired format of the output.
   * @param speed an integer containing the desired number of ticks per second in the output.
   */
  String generate(OutputType outputType, int speed);

  /**
   * Gets the largest x coordinate touched by this IAnimation. Used to set a background
   *
   * @return the sum of the largest x coordinate of a shape and the largest x value of a shape.
   */
  float getMaxX();

  /**
   * Gets the largest y coordinate touched by this IAnimation. Used to set a background
   *
   * @return the sum of the largest y coordinate of a shape and the largest y value of a shape.
   */
  float getMaxY();

  /**
   * Fetches the first IShape with the given shapeName in this IAnimation, null if no matching
   * IShape.
   *
   * @param shapeName a String, the name of an IShape to search against.
   * @return the first IShape that matches, null otherwise.
   */
  IShape getFirstIShape(String shapeName);

  /**
   * Generates a list of shapes to be rendered at any given tick.
   *
   * @param tick an arbitrary unit of animation time.
   * @return a list of IRenderableShapes (read only).
   */
  List<IRenderableShape> generateFrame(int tick);

  /**
   * Gets the last tick of this IAnimation, which is the time when the last IShape disappears.
   * Useful for terminating
   *
   * @return int the last tick of this IAnimation.
   */
  public int lastTick();
}