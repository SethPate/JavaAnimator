package cs5004.animator.model;

import cs5004.animator.util.TweenTools;
import java.awt.geom.Point2D;
import java.awt.geom.Point2D.Double;
import java.util.Objects;

/**
 * A class representing a Transformation of position. This Transformation moves a Shape from one
 * spot to another.
 */

public class TransformMove extends Transformation {

  private final Point2D.Double startPosition;
  private final Point2D.Double endPosition;
  private final ShapeType shapeType;

  /**
   * Creates a new instance of the TransformColor class.
   *
   * @param start The time at which this Transformation starts.
   * @param end The time at which this Transformation ends.
   * @param shapeName A String with the name of this Transformation.
   * @param startPosition The position at which this Shape will start.
   * @param endPosition The position to which the Shape will move.
   * @param shapeType The type of Shape this Transform is being applied to.
   * @throws NullPointerException if either the start or end position are null.
   */
  public TransformMove(
      Point2D.Double startPosition, Point2D.Double endPosition,
      int start, int end,
      String shapeName, ShapeType shapeType)
      throws NullPointerException {
    super(start, end, shapeName);
    Objects.requireNonNull(startPosition);
    Objects.requireNonNull(endPosition);
    this.type = TransformType.MOVE;
    this.startPosition = startPosition;
    this.endPosition = endPosition;
    this.shapeType = shapeType;
  }

  /**
   * Represents this Transformation as a String in the Text format.
   *
   * @return a String containing this Transformation's values formatted for Text output.
   */
  public String toStringText(float thisStart, float thisEnd) {
    return String.format("Shape %s moves from (%.1f,%.1f) to (%.1f,%.1f)"
            + " from t=%.1fs to t=%.1fs\n",
        this.shapeName, this.startPosition.x, this.startPosition.y,
        this.endPosition.x, this.endPosition.y, thisStart, thisEnd);
  }

  /**
   * Represents this Transformation as a String in the SVG format.
   *
   * @param thisStart the time in milliseconds at which this Transformation should begin.
   * @param thisEnd the time in milliseconds at which this Transformation should end.
   * @return a String containing this Transformation's values formatted for SVG output.
   */
  public String toStringSVG(float thisStart, float thisEnd) {
    switch (this.shapeType) {
      case OVAL:
        return toStringSVGEllipse(thisStart, thisEnd);
      case CIRCLE:
        return toStringSVGEllipse(thisStart, thisEnd);
      case RECTANGLE:
        return toStringSVGRect(thisStart, thisEnd);
      default:
        throw new IllegalArgumentException("failed to turn ITransformation into SVG");
    }
  }

  /**
   * Represents this Transformation as a String in the SVG format. Specifically when moving an oval
   * or ellipse.
   *
   * @param thisStart the time in milliseconds at which this Transformation should begin.
   * @param thisEnd the time in milliseconds at which this Transformation should end.
   * @return a String containing this Transformation's values formatted for SVG output.
   */
  private String toStringSVGEllipse(float thisStart, float thisEnd) {
    float duration = thisEnd - thisStart;
    String tagX = String.format(
        "<animate attributeName=\"cx\" attributeType=\"CSS\" "
            + "from=\"%d\" to=\"%d\" begin=\"%.1fms\" dur=\"%.1fms\" fill=\"freeze\" />\n",
        Math.round(this.startPosition.x),
        Math.round(this.endPosition.x),
        thisStart, duration);
    String tagY = String.format(
        "<animate attributeName=\"cy\" attributeType=\"CSS\" "
            + "from=\"%d\" to=\"%d\" begin=\"%.1fms\" dur=\"%.1fms\" fill=\"freeze\" />\n",
        Math.round(this.startPosition.y),
        Math.round(this.endPosition.y),
        thisStart, duration);
    return tagX + "  " + tagY;
  }

  /**
   * Represents this Transformation as a String in the SVG format. Specifically when moving
   * rectangles.
   *
   * @param thisStart the time in milliseconds at which this Transformation should begin.
   * @param thisEnd the time in milliseconds at which this Transformation should end.
   * @return a String containing this Transformation's values formatted for SVG output.
   */
  private String toStringSVGRect(float thisStart, float thisEnd) {
    float duration = thisEnd - thisStart;
    String tagX = String.format(
        "<animate attributeName=\"x\" attributeType=\"CSS\" "
            + "from=\"%d\" to=\"%d\" begin=\"%.1fms\" dur=\"%.1fms\" fill=\"freeze\" />\n",
        Math.round(this.startPosition.x),
        Math.round(this.endPosition.x),
        thisStart, duration);
    String tagY = String.format(
        "<animate attributeName=\"y\" attributeType=\"CSS\" "
            + "from=\"%d\" to=\"%d\" begin=\"%.1fms\" dur=\"%.1fms\" fill=\"freeze\" />\n",
        Math.round(this.startPosition.y),
        Math.round(this.endPosition.y),
        thisStart, duration);
    return tagX + "  " + tagY;
  }

  /**
   * Gets the start position of this TransformMove.
   *
   * @return the position at which this transformation starts.
   */
  public Point2D.Double getStartPosition() {
    return new Double(this.startPosition.getX(), this.startPosition.getY());
  }

  /**
   * Gets the end position of this TransformMove.
   *
   * @return the position at which this transformation ends.
   */
  public Point2D.Double getEndPosition() {
    return new Double(this.endPosition.getX(), this.endPosition.getY());
  }

  /**
   * Returns the maximum x coordinate which this ITransformation touches.
   *
   * @return x a float with the x coordinate.
   */
  public float getMaxXCoord() {
    return (float) Math.max(this.startPosition.x, this.endPosition.x);
  }

  /**
   * Returns the maximum y coordinate which this ITransformation touches.
   *
   * @return y a float with the y coordinate.
   */
  public float getMaxYCoord() {
    return (float) Math.max(this.startPosition.y, this.endPosition.y);
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
      int x = (int) this.getEndPosition().getX();
      int y = (int) this.getEndPosition().getY();
      float xDist = shape.getXSize();
      float yDist = shape.getYSize();
      Color color = shape.getColor();
      // if transformation is currently taking place, tween it!
      if (!this.hasFinished(tick)) {
        int startX = (int) Math.round(this.startPosition.getX());
        int endX = (int) Math.round(this.endPosition.getX());
        int startY = (int) Math.round(this.startPosition.getY());
        int endY = (int) Math.round(this.getEndPosition().getY());
        x = (int) TweenTools.tween(startX,
            endX,
            start,
            end,
            tick);
        y = (int) TweenTools.tween(startY,
            endY,
            start,
            end,
            tick);
      }
      return RenderableFactory.makeRenderableShape(shapeType, x, y, xDist, yDist, color);
    }
  }
}