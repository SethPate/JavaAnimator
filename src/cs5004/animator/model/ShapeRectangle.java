package cs5004.animator.model;

import java.awt.geom.Point2D;
import java.util.Objects;

/**
 * A class representing a Rectangle. Its center is a Point2D double and it has a width and a
 * height.
 */

public class ShapeRectangle extends Shape {

  private final Point2D.Double lowerLeftCorner;
  private final double width;
  private final double height;

  /**
   * Creates an instance of the ShapeRectangle class.
   *
   * @param name The name of this Shape.
   * @param color The color of this Shape.
   * @param appear The time at which this Shape appears.
   * @param disappear The time at which this Shape disappears.
   * @param lowerLeftCorner The lower left corner of this Shape.
   * @param width The x length of this Shape.
   * @param height The y length of this Shape.
   * @throws NullPointerException if color is null
   * @throws IllegalArgumentException if width or height are <= 0.
   */
  public ShapeRectangle(String name, Color color, int appear, int disappear,
      Point2D.Double lowerLeftCorner, double width, double height)
      throws NullPointerException, IllegalArgumentException {
    super(name, color, appear, disappear);
    Objects.requireNonNull(color);
    if (width <= 0 || height <= 0) {
      throw new IllegalArgumentException(
          "Width and Height must both be larger than 0.");
    }
    this.shapeType = ShapeType.RECTANGLE;
    this.lowerLeftCorner = lowerLeftCorner;
    this.width = width;
    this.height = height;
  }

  /**
   * Represents this Shape as a String in the Text format.
   *
   * @param thisAppear the adjusted time at which this Shape should appear.
   * @param thisDisappear the adjusted time at which this Shape should disappear.
   * @return a String containing this Shape's values formatted for Text output.
   */
  public String toStringText(float thisAppear, float thisDisappear) {
    String output = "Name: " + this.name + "\n";
    output += "Type: rectangle\n";
    output += String.format("Min corner: (%.1f,%.1f), Width: %.1f,"
            + " Height: %.1f, Color: %s\n", this.lowerLeftCorner.x, this.lowerLeftCorner.y,
        this.width, this.height, this.color.toString());
    output += String.format("Appears at t=%.1fs\nDisappears at t=%.1fs\n", thisAppear,
        thisDisappear);
    return output;
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
        "<rect id=\"%s\" x=\"%d\" y=\"%d\" width=\"%d\" height=\"%d\" "
            + "fill=\"rgb(%.1f%%,%.1f%%,%.1f%%)\" visibility=\"hidden\" >\n",
        this.name,
        Math.round(this.lowerLeftCorner.x),
        Math.round(this.lowerLeftCorner.y),
        Math.round(this.width),
        Math.round(this.height),
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
    return "</rect>\n";
  }

  /**
   * Returns the x coordinate of this IShape.
   *
   * @return x a float with the x coordinate.
   */
  public float getXCoord() {
    float x = (float) this.lowerLeftCorner.x;
    return x;
  }

  /**
   * Returns the y coordinate of this IShape.
   *
   * @return y a float with the y coordinate.
   */
  public float getYCoord() {
    float y = (float) this.lowerLeftCorner.y;
    return y;
  }

  /**
   * Returns the x distance of this IShape.
   *
   * @return x a float with the x distance.
   */
  public float getXLength() {
    float dist = (float) this.width;
    return dist;
  }

  /**
   * Returns the y distance of this IShape.
   *
   * @return y a float with the y distance.
   */
  public float getYLength() {
    float dist = (float) this.height;
    return dist;
  }
}