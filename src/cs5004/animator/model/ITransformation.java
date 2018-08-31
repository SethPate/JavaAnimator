package cs5004.animator.model;

/**
 * An interface representing a Transformation. Transformation affect Shapes in an Animation: e.g. a
 * Shape may move from one position to another.
 */

public interface ITransformation extends Comparable<ITransformation> {

  /**
   * Represents this ITransformation as a String in the Text format.
   *
   * @param thisStart the time in seconds at which to render this Transformation's start.
   * @param thisEnd the time in seconds at which to render this Transformation's end.
   * @return a String containing this Transformation's values formatted for Text output.
   */
  String toStringText(float thisStart, float thisEnd);

  /**
   * Represents this ITransformation as a String in the SVG format.
   *
   * @param thisStart the time at which to render this Transformation's start.
   * @param thisEnd the time at which to render this Transformation's end.
   * @return a String containing this Transformation's values formatted for SVG output.
   */
  String toStringSVG(float thisStart, float thisEnd);

  /**
   * Gets the type of ITransformation this is.
   *
   * @return a TransformType containing the type.
   */
  TransformType getType();

  /**
   * Gets the start time of this ITransformation.
   *
   * @return An int with the start time.
   */
  float getStart();

  /**
   * Gets the end time of this ITransformation.
   *
   * @return An int with the end time.
   */
  float getEnd();

  /**
   * Gets the name of this ITransformation.
   *
   * @return A String with the name.
   */
  String getShapeName();

  /**
   * Returns the maximum x coordinate which this ITransformation touches.
   *
   * @return x a float with the x coordinate.
   */
  float getMaxXCoord();

  /**
   * Returns the maximum y coordinate which this ITransformation touches.
   *
   * @return y a float with the y coordinate.
   */
  float getMaxYCoord();

  /**
   * Determines whether this ITransformation should be displayed at the given tick.
   *
   * @param tick an arbitrary unit of time.
   * @return true if the ITransformation should be displayed, false if not.
   */
  boolean isActive(int tick);

  /**
   * Creates an IRenderableShape that has had this ITransformation applied to it. Uses a tweening
   * algorithm to give exact dimensions for that tick. Returns the shape unchanged if the tick is
   * before the ITransformation, returns the shape with the final ITransformation dimensions applied
   * if the tick is after it's over, tweens if the tick's in the middle of the ITransformation.
   *
   * @param shape the RenderableShape to be tweened.
   * @param shapeType the type of this IRenderableShape.
   * @param tick the tick at which to render this ITransformation.
   */
  IRenderableShape tween(IRenderableShape shape, ShapeType shapeType, int tick);
}
