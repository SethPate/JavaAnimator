package cs5004.animator.model;

import java.awt.geom.Point2D;

/**
 * Creates different instances of the Shape abstract class. The controller uses the static functions
 * of the ShapeFactory to create new objects, which decouples the controller from the constructor
 * methods.
 */

public class ShapeFactory {

  /**
   * Creates an instance of the ShapeCircle class.
   *
   * @param name The name of this Shape.
   * @param color The color of this Shape.
   * @param appear The time at which this Shape appears.
   * @param disappear The time at which this Shape disappears.
   * @param center The center of this Shape.
   * @param radius The radius of this Shape.
   */
  public static ShapeCircle makeShapeCircle(String name, Color color, int appear, int disappear,
      Point2D.Double center, Double radius) {
    return new ShapeCircle(name, color, appear, disappear,
        center, radius);
  }

  /**
   * Creates an instance of the ShapeCircle class.
   *
   * @param name The name of this Shape.
   * @param color The color of this Shape.
   * @param appear The time at which this Shape appears.
   * @param disappear The time at which this Shape disappears.
   * @param center The center of this Shape.
   * @param widthRadius The x radius of this Shape.
   * @param heightRadius The y radius of this Shape.
   */
  public static ShapeOval makeShapeOval(String name, Color color, int appear, int disappear,
      Point2D.Double center,
      Double widthRadius, Double heightRadius) {
    return new ShapeOval(name, color, appear, disappear, center, widthRadius, heightRadius);
  }

  /**
   * Creates an instance of the ShapeCircle class.
   *
   * @param name The name of this Shape.
   * @param color The color of this Shape.
   * @param appear The time at which this Shape appears.
   * @param disappear The time at which this Shape disappears.
   * @param lowerLeftCorner The lower left corner of this Shape.
   * @param width The x length of this Shape.
   * @param height The y length of this Shape.
   */
  public static ShapeRectangle makeShapeRectangle(String name, Color color, int appear,
      int disappear,
      Point2D.Double lowerLeftCorner, Double width, Double height) {
    return new ShapeRectangle(name, color, appear, disappear, lowerLeftCorner, width, height);
  }
}
