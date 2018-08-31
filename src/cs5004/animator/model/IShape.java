package cs5004.animator.model;

/**
 * An interface representing a Shape, such as a Rectangle or Circle. Shapes have a name, a Color,
 * some basic position with x and y coordinates, an appearing time, and a disappearing time.
 */

public interface IShape {

  /**
   * Gets the Color of this Shape.
   *
   * @return the Color.
   */
  Color getColor();

  /**
   * Gets the type of this Shape.
   *
   * @return the type (rectangle, oval, etc.)
   */
  ShapeType getType();

  /**
   * Gets the name of this Shape.
   *
   * @return a String containing the name of the Shape.
   */
  String getName();

  /**
   * Gets the time at which this Shape appears.
   *
   * @return an integer with the time.
   */
  float getAppear();

  /**
   * Gets the time at which this Shape disappears.
   *
   * @return an integer with the time.
   */
  float getDisappear();

  /**
   * Returns the x coordinate of this IShape.
   *
   * @return x a float with the x coordinate.
   */
  float getXCoord();

  /**
   * Returns the y coordinate of this IShape.
   *
   * @return y a float with the y coordinate.
   */
  float getYCoord();

  /**
   * Returns the x length of this IShape.
   *
   * @return x a float with the x length.
   */
  float getXLength();

  /**
   * Returns the y length of this IShape.
   *
   * @return y a float with the y length.
   */
  float getYLength();

  /**
   * Returns the largest x coordinate touched by this IShape throughout the animation.
   *
   * @return a float with the largest x coordinate.
   */
  float getMaxXCoord();

  /**
   * Returns the largest y coordinate touched by this IShape throughout the animation.
   *
   * @return a float with the largest y coordinate.
   */
  float getMaxYCoord();

  /**
   * Determines whether this IShape should be displayed at the given tick.
   *
   * @param tick an arbitrary unit of time.
   * @return true if the IShape should be displayed, false if not.
   */
  boolean isActive(int tick);

  /**
   * Adds an ITransformation to this Shape's list of ITransformations. It must apply to this
   * IShape.
   *
   * @param transformation an ITransformation applying to this Shape.
   * @throws NullPointerException if object is null.
   * @throws IllegalArgumentException if ITransformation does not apply to this Shape.
   */
  void addTransformation(ITransformation transformation);

  /**
   * Represents this Shape as a String in the Text format.
   *
   * @return a String containing this Shape's values formatted for Text output.
   */
  String toStringText(float thisAppear, float thisDisappear);

  /**
   * Represents this Shape as a String in the SVG format.
   *
   * @return a String containing this Shape's values formatted for SVG output.
   */
  String toStringSVG(float thisAppear, float thisDisappear);

  /**
   * Creates a closing tag for use in an SVG output.
   *
   * @return a String containing a correct SVG closing tag.
   */
  String toStringSVGClose();

  /**
   * Represents this IShape at the given tick. Depending on the tick, applies the relevant
   * ITransformation (including tweening) and returns a read-only IRenderableShape.
   *
   * @param tick the tick at which to generate this IShape.
   * @return a read-only IRenderableShape
   * @throws IllegalArgumentException if the IShape has not appeared at this tick yet.
   */
  IRenderableShape generateFrame(int tick);
}