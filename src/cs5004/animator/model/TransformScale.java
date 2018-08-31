package cs5004.animator.model;

import cs5004.animator.util.TweenTools;

/**
 * A Subclass of the Transformation abstract class. TransformScales change the x and y aspects of a
 * Shape. They have different representations whether they are applied to a Rectangle, Oval, or
 * Circle.
 */
public class TransformScale extends Transformation {

  private float fromX;
  private float fromY;
  private float toX;
  private float toY;
  private final ShapeType shapeType;

  /**
   * Creates a new instance of the TransformScale class.
   *
   * @param fromX the size of the x value at the start of the Transformation.
   * @param fromY the size of the y value at the start of this Transformation.
   * @param toX the size of the x value at the end of the Transformation.
   * @param toY the size of the y value at the end of this Transformation.
   * @param start The time at which this Transformation starts.
   * @param end The time at which this Transformation ends.
   * @param shapeName A String with the name of this Transformation.
   * @throws IllegalArgumentException if the from or to radius is <= 0.
   */
  public TransformScale(float fromX, float fromY, float toX, float toY, int start, int end,
      String shapeName, ShapeType shapeType) throws IllegalArgumentException {
    super(start, end, shapeName);

    if (fromX <= 0
        || toX <= 0) {
      throw new IllegalArgumentException("x and y values cannot be <= 0");
    }
    this.shapeType = shapeType;
    this.type = TransformType.SCALE;
    this.fromX = fromX;
    this.fromY = fromY;
    this.toX = toX;
    this.toY = toY;
  }

  /**
   * Represents this Transformation as a String in the Text format.
   *
   * @param startS the start time of the Transformation in seconds.
   * @param endS the end time of the Transformation in seconds.
   * @return a String containing this Transformation's values formatted for Text output.
   */
  public String toStringText(float startS, float endS) {
    switch (this.shapeType) {
      case CIRCLE:
        return this.toStringTextCircle(startS, endS);
      case RECTANGLE:
        return this.toStringTextRect(startS, endS);
      case OVAL:
        return this.toStringTextOval(startS, endS);
      default:
        throw new IllegalStateException("Attempting to render Transform of unknown ShapeType");
    }
  }

  /**
   * Represents this Transformation as a String in the Text format. Specifically for ShapeCircles.
   *
   * @param startS the start time of the Transformation in seconds.
   * @param endS the end time of the Transformation in seconds.
   * @return a String containing this Transformation's values formatted for Text output.
   */
  private String toStringTextCircle(float startS, float endS) {
    return String.format("Shape %s scales from Radius: %.1f to Radius: %.1f"
            + " from t=%.1fs to t=%.1fs\n",
        this.shapeName, this.fromX, this.toX, startS, endS);
  }

  /**
   * Represents this Transformation as a String in the Text format. For ShapeOvals.
   *
   * @param startS the start time of the Transformation in seconds.
   * @param endS the end time of the Transformation in seconds.
   * @return a String containing this Transformation's values formatted for Text output.
   */
  private String toStringTextOval(float startS, float endS) {
    return String.format(
        "Shape %s scales from X radius: %.1f, Y "
            + "radius: %.1f to X radius: %.1f, Y radius: %.1f "
            + "from t=%.1fs to t=%.1fs\n",
        this.shapeName, this.fromX, this.fromY, this.toX, this.toY, startS,
        endS);
  }

  /**
   * Represents this Transformation as a String in the Text format. For ShapeRectangles.
   *
   * @param startS the start time of the Transformation in seconds.
   * @param endS the end time of the Transformation in seconds.
   * @return a String containing this Transformation's values formatted for Text output.
   */
  private String toStringTextRect(float startS, float endS) {
    return String
        .format("Shape %s scales from Width: %.1f, Height: %.1f "
                + "to Width: %.1f, Height: %.1f "
                + "from t=%.1fs to t=%.1fs\n",
            this.shapeName, this.fromX, this.fromY, this.toX, this.toY,
            startS, endS);
  }


  /**
   * Represents this Transformation as a String in the SVG format.
   *
   * @param startS the start time of the Transformation in seconds.
   * @param endS the end time of the Transformation in seconds.
   * @return a String containing this Transformation's values formatted for Text output.
   */
  public String toStringSVG(float startS, float endS) {
    switch (this.shapeType) {
      case CIRCLE:
        return this.toStringSVGCircle(startS, endS);
      case RECTANGLE:
        return this.toStringSVGRect(startS, endS);
      case OVAL:
        return this.toStringSVGOval(startS, endS);
      default:
        throw new IllegalStateException("Attempting to render Transform of unknown ShapeType");
    }
  }

  /**
   * Represents this Transformation as a String in the SVG format. For ShapeCircles.
   *
   * @param thisStart the time in milliseconds at which this Transformation should begin.
   * @param thisEnd the time in milliseconds at which this Transformation should end.
   * @return a String containing this Transformation's values formatted for SVG output.
   */
  private String toStringSVGCircle(float thisStart, float thisEnd) {
    float duration = thisEnd - thisStart;
    return String.format(
        "<animate attributeName=\"r\" attributeType=\"CSS\""
            + " from=\"%d\" to=\"%d\" begin=\"%.1fms\" dur=\"%.1fms\" fill=\"freeze\" />\n",
        Math.round(this.fromX),
        Math.round(this.toX),
        thisStart,
        duration);
  }

  /**
   * Represents this Transformation as a String in the SVG format. For ShapeOvals.
   *
   * @param thisStart the time in milliseconds at which this Transformation should begin.
   * @param thisEnd the time in milliseconds at which this Transformation should end.
   * @return a String containing this Transformation's values formatted for SVG output.
   */
  public String toStringSVGOval(float thisStart, float thisEnd) {
    float duration = thisEnd - thisStart;
    String scaleX = String.format(
        "<animate attributeName=\"rx\" attributeType=\"CSS\" "
            + "from=\"%d\" to=\"%d\" begin=\"%.1fms\" dur=\"%.1fms\" fill=\"freeze\" />\n",
        Math.round(this.fromX),
        Math.round(this.toX),
        thisStart,
        duration);
    String scaleY = String.format(
        "<animate attributeName=\"ry\" attributeType=\"CSS\" "
            + "from=\"%d\" to=\"%d\" begin=\"%.1fms\" dur=\"%.1fms\" fill=\"freeze\" />\n",
        Math.round(this.fromY),
        Math.round(this.toY),
        thisStart,
        duration);
    return scaleX + "  " + scaleY;
  }

  /**
   * Represents this Transformation as a String in the SVG format. For ShapeRectangles.
   *
   * @param thisStart the time in milliseconds at which this Transformation should begin.
   * @param thisEnd the time in milliseconds at which this Transformation should end.
   * @return a String containing this Transformation's values formatted for SVG output.
   */
  public String toStringSVGRect(float thisStart, float thisEnd) {
    float duration = thisEnd - thisStart;
    String scaleX = String.format(
        "<animate attributeName=\"width\" attributeType=\"CSS\" "
            + "from=\"%d\" to=\"%d\" begin=\"%.1fms\" dur=\"%.1fms\" fill=\"freeze\" />\n",
        Math.round(this.fromX),
        Math.round(this.toX),
        thisStart,
        duration);
    String scaleY = String.format(
        "<animate attributeName=\"height\" attributeType=\"CSS\" "
            + "from=\"%d\" to=\"%d\" begin=\"%.1fms\" dur=\"%.1fms\" fill=\"freeze\" />\n",
        Math.round(this.fromY),
        Math.round(this.toY),
        thisStart,
        duration);
    return scaleX + "  " + scaleY;
  }

  /**
   * Returns the beginning x distance of this ITransformation.
   *
   * @return the x distance at the start of this ITransformation.
   */
  public float getStartXDist() {
    return this.fromX;
  }

  /**
   * Returns the beginning y distance of this ITransformation.
   *
   * @return the y distance at the start of this ITransformation.
   */
  public float getStartYDist() {
    return this.fromY;
  }

  /**
   * Returns the ending x distance of this ITransformation.
   *
   * @return the x distance at the end of this ITransformation.
   */
  public float getEndXDist() {
    return this.toX;
  }

  /**
   * Returns the ending y distance of this ITransformation.
   *
   * @return the y distance at the end of this ITransformation.
   */
  public float getEndYDist() {
    return this.toY;
  }

  /**
   * Returns the maximum x coordinate which this ITransformation touches.
   *
   * @return x a float with the x coordinate.
   */
  public float getMaxXCoord() {
    float x = Math.max(this.fromX,
        this.toX);
    return x;
  }

  /**
   * Returns the maximum y coordinate which this ITransformation touches.
   *
   * @return y a float with the y coordinate.
   */
  public float getMaxYCoord() {
    float y = Math.max(this.fromY,
        this.toY);
    return y;
  }

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
  public IRenderableShape tween(IRenderableShape shape, ShapeType shapeType, int tick) {
    if (!this.isActive(tick)) {
      return shape; // return shape unchanged if transformation not active
    } else {
      // default to returning the shape with final transformation applied
      int x = shape.getX();
      int y = shape.getY();
      float xDist = this.getEndXDist();
      float yDist = this.getEndYDist();
      Color color = shape.getColor();
      // if transformation is currently taking place, tween it!
      if (!this.hasFinished(tick)) {
        xDist = TweenTools.tween(
            fromX,
            toX,
            start,
            end,
            tick);
        yDist = TweenTools.tween(
            fromY,
            toY,
            start,
            end,
            tick);
      }
      return RenderableFactory.makeRenderableShape(shapeType, x, y, xDist, yDist, color);
    }
  }
}
