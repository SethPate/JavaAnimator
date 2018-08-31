package cs5004.animator.model;

import java.awt.geom.Point2D;

/**
 * Creates different instances of the Transformation abstract class. The controller uses the static
 * functions of the TransformFactory to create new objects, which decouples the controller from the
 * constructor methods.
 */

public class TransformFactory {

  /**
   * Creates a new instance of the TransformColor class.
   *
   * @param start The time at which this Transformation starts.
   * @param end The time at which this Transformation ends.
   * @param shapeName A String with the name of this Transformation.
   * @param startPosition The position at which this Shape will start.
   * @param endPosition The position to which the Shape will move.
   */
  public static TransformMove makeTransformMove(Point2D.Double startPosition,
      Point2D.Double endPosition, int start, int end, String shapeName, ShapeType shapeType) {
    return new TransformMove(startPosition, endPosition, start, end, shapeName,
        shapeType);
  }

  /**
   * Creates a new instance of the TransformColor class.
   *
   * @param start The time at which this Transformation starts.
   * @param end The time at which this Transformation ends.
   * @param shapeName A String with the name of this Transformation.
   * @param startColor the Color from which this Transformation is changing.
   * @param endColor The Color to which this Transformation is changing.
   */
  public static TransformColor makeTransformColor(Integer start, Integer end, String shapeName,
      Color startColor, Color endColor) {
    return new TransformColor(start, end, shapeName, startColor, endColor);
  }

  /**
   * Creates a new instance of the TransformScale class.
   *
   * @param fromX the size of the X radius at the start of the Transformation.
   * @param fromY the size of the Y radius at the start of the Transformation.
   * @param toX the size of the X radius at the end of the Transformation.
   * @param toY the size of the Y radius at the end of the Transformation.
   * @param start The time at which this Transformation starts.
   * @param end The time at which this Transformation ends.
   * @param shapeName A String with the name of this Transformation.
   * @param shapeType the Type of Shape to which this Transformation applies.
   */
  public static TransformScale makeTransformScale(float fromX, float fromY, float toX, float toY,
      int start, int end, String shapeName, ShapeType shapeType) {
    return new TransformScale(fromX, fromY, toX, toY, start, end, shapeName, shapeType);
  }
}
