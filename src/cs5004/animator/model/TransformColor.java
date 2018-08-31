package cs5004.animator.model;

import cs5004.animator.util.TweenTools;
import java.util.Objects;

/**
 * An object representing a color Transformation. This changes the Color of a Shape.
 */

public class TransformColor extends Transformation {

  private final Color startColor;
  private final Color endColor;

  /**
   * Creates a new instance of the TransformColor class.
   *
   * @param start The time at which this Transformation starts.
   * @param end The time at which this Transformation ends.
   * @param shapeName A String with the name of this Transformation.
   * @param endColor The Color to which this Transformation is changing.
   * @throws NullPointerException if either the start or end color are null.
   */
  public TransformColor(Integer start, Integer end, String shapeName,
      Color startColor, Color endColor) {
    super(start, end, shapeName);
    Objects.requireNonNull(startColor);
    Objects.requireNonNull(endColor);
    this.type = TransformType.COLOR;
    this.startColor = startColor;
    this.endColor = endColor;
  }

  /**
   * Represents this Transformation as a String in the Text format.
   *
   * @return a String containing this Transformation's values formatted for Text output.
   */
  public String toStringText(float thisStart, float thisEnd) {
    return String.format("Shape %s changes color from %s to %s"
            + " from t=%.1fs to t=%.1fs\n",
        this.shapeName,
        this.startColor,
        this.endColor,
        thisStart,
        thisEnd);
  }

  /**
   * Represents this Transformation as a String in the SVG format.
   *
   * @param thisStart the time in milliseconds at which this Transformation should begin.
   * @param thisEnd the time in milliseconds at which this Transformation should end.
   * @return a String containing this Transformation's values formatted for SVG output.
   */
  public String toStringSVG(float thisStart, float thisEnd) {
    float duration = thisEnd - thisStart;
    return String.format("<animate attributeName=\"fill\" attributeType=\"CSS\""
            + " from=\"rgb(%.1f%%,%.1f%%,%.1f%%)\" to=\"rgb(%.1f%%,%.1f%%,%.1f%%)\" "
            + "begin=\"%.1fms\" dur=\"%.1fms\" fill=\"freeze\" />\n",
        this.startColor.getRed(),
        this.startColor.getGreen(),
        this.startColor.getBlue(),
        this.endColor.getRed(),
        this.endColor.getGreen(),
        this.endColor.getBlue(),
        thisStart,
        duration);
  }

  /**
   * Returns the maximum x coordinate which this ITransformation touches.
   *
   * @return x a float with the x coordinate.
   */
  public float getMaxXCoord() {
    return 0;
  }

  /**
   * Returns the maximum y coordinate which this ITransformation touches.
   *
   * @return y a float with the y coordinate.
   */
  public float getMaxYCoord() {
    return 0;
  }

  /**
   * Gets the start color of this ITransformation.
   *
   * @return the color the shape starts as.
   */
  public Color getStartColor() {
    return new Color(this.startColor.getRed(),
        this.startColor.getGreen(),
        this.startColor.getBlue());
  }

  /**
   * Gets the end color of this ITransformation.
   *
   * @return the color the shape ends as.
   */
  public Color getEndColor() {
    return new Color(this.endColor.getRed(),
        this.endColor.getGreen(),
        this.endColor.getBlue());
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
      float xDist = shape.getXSize();
      float yDist = shape.getYSize();
      Color color = this.getEndColor();
      // if transformation is currently taking place, tween it!
      if (!this.hasFinished(tick)) {
        float red = TweenTools.tween(startColor.getRed(),
            endColor.getRed(),
            start,
            end,
            tick);
        float green = TweenTools.tween(startColor.getGreen(),
            endColor.getGreen(),
            start,
            end,
            tick);
        float blue = TweenTools.tween(startColor.getBlue(),
            endColor.getBlue(),
            start,
            end,
            tick);
        color = new Color(red, green, blue);
      }
      return RenderableFactory.makeRenderableShape(shapeType, x, y, xDist, yDist, color);
    }
  }
}