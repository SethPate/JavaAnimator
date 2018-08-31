package cs5004.animator.model;

import java.awt.geom.Point2D;
import java.util.Objects;

/**
 * A class representing an Oval. Its center is a Point2D double and it has an x radius and a y
 * radius.
 */

public class ShapeOval extends Shape {

  private final Point2D.Double center;
  private final double widthRadius;
  private final double heightRadius;

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
   * @throws IllegalArgumentException if either radius is <= 0.
   * @throws NullPointerException if color is null.
   */
  public ShapeOval(String name, Color color, int appear, int disappear, Point2D.Double center,
      double widthRadius, double heightRadius)
      throws IllegalArgumentException, NullPointerException {
    super(name, color, appear, disappear);
    Objects.requireNonNull(color);
    if (heightRadius <= 0 || widthRadius <= 0) {
      throw new IllegalArgumentException(
          "Height Radius and Width Radius must both be larger than 0.");
    }
    this.shapeType = ShapeType.OVAL;
    this.center = center;
    this.widthRadius = widthRadius;
    this.heightRadius = heightRadius;
  }

  /**
   * Represents this Shape as a String in the Text format.
   *
   * @param thisAppear the adjusted time at which this Shape should appear.
   * @param thisDisappear the adjusted time at which this Shape should disappear.
   * @return a String containing this Shape's values formatted for Text output.
   */
  public String toStringText(float thisAppear, float thisDisappear) {
    return String.format(
        "Name: %s\nType: oval\nCenter: (%.1f,%.1f), X radius: %.1f,"
            + " Y radius: %.1f, Color: %s\nAppears at t=%.1fs\nDisappears at t=%.1fs\n",
        this.getName(), this.center.x, this.center.y,
        this.widthRadius, this.heightRadius, this.color.toString(), thisAppear,
        thisDisappear);
  }

  /**
   * Produces an opening tag for this Shape in the SVG format, as well as an animation tag to make
   * the Shape appear and disappear at a certain time.
   *
   * @param thisAppear the time in milliseconds at which this Shape should appear.
   * @param thisDisappear the time in milliseconds at which this Shape should disappear.
   * @return a String containing this Shape's values formatted for SVG output.
   */
  public String toStringSVG(float thisAppear, float thisDisappear) {
    String openTag = String.format(
        "<ellipse id=\"%s\" cx=\"%d\" cy=\"%d\" rx=\"%d\" ry=\"%d\" "
            + "fill=\"rgb(%.1f%%,%.1f%%,%.1f%%)\" visibility=\"hidden\" >\n",
        this.name,
        Math.round(this.center.x),
        Math.round(this.center.y),
        Math.round(this.widthRadius),
        Math.round(this.heightRadius),
        this.color.getRed(),
        this.color.getGreen(),
        this.color.getBlue());
    float duration = thisDisappear - thisAppear;
    String setVisible = String.format("  <set attributeName=\"visibility\" attributeType=\"CSS\" "
            + "to=\"visible\" begin=\"%.1fms\" duration=\"%.1fms\" fill=\"freeze\" />\n",
        thisAppear, duration);
    return openTag + setVisible;
  }

  /**
   * Creates a closing tag for use in an SVG output.
   *
   * @return a String containing a correct SVG closing tag.
   */
  public String toStringSVGClose() {
    return "</ellipse>\n";
  }

  /**
   * Returns the x coordinate of this IShape.
   *
   * @return x a float with the x coordinate.
   */
  public float getXCoord() {
    float x = (float) this.center.x;
    return x;
  }

  /**
   * Returns the y coordinate of this IShape.
   *
   * @return y a float with the y coordinate.
   */
  public float getYCoord() {
    float y = (float) this.center.y;
    return y;
  }

  /**
   * Returns the x distance of this IShape.
   *
   * @return x a float with the x distance.
   */
  public float getXLength() {
    float dist = (float) this.widthRadius;
    return dist;
  }

  /**
   * Returns the y distance of this IShape.
   *
   * @return y a float with the y distance.
   */
  public float getYLength() {
    float dist = (float) this.heightRadius;
    return dist;
  }
}