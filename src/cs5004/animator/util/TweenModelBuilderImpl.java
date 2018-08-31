package cs5004.animator.util;

import cs5004.animator.model.Animation;
import cs5004.animator.model.Color;
import cs5004.animator.model.IAnimation;
import cs5004.animator.model.IShape;
import cs5004.animator.model.ITransformation;
import cs5004.animator.model.ShapeFactory;
import cs5004.animator.model.ShapeType;
import cs5004.animator.model.TransformFactory;
import java.awt.geom.Point2D;
import java.awt.geom.Point2D.Double;

/**
 * This Class works with AnimationFileReader to create animations. Specifically, AnimationFileReader
 * parses the input file into an intermediate format, then this Class builds an IAnimation.
 */

public class TweenModelBuilderImpl implements TweenModelBuilder {

  private IAnimation animation;

  /**
   * Creates a new instance of the Tween Model Builder class.
   */
  public TweenModelBuilderImpl() {
    this.animation = new Animation();
  }

  /**
   * Private method for the Tween Model Builder. Adjusts all color values in a given color to the
   * result of multiplying them by 100. Used to handle conversion between input values and model
   * values.
   *
   * @param originalColor the Color to be changed.
   * @return a new Color with values modified by * 100.
   */
  private static Color colorsBy100(Color originalColor) {
    float newRed = originalColor.getRed() * 100;
    float newGreen = originalColor.getGreen() * 100;
    float newBlue = originalColor.getBlue() * 100;
    return new Color(newRed, newGreen, newBlue);
  }

  /**
   * Add a new oval to the model with the given specifications.
   *
   * @param name the unique name given to this shape
   * @param cx the x-coordinate of the center of the oval
   * @param cy the y-coordinate of the center of the oval
   * @param xRadius the x-radius of the oval
   * @param yRadius the y-radius of the oval
   * @param red the red component of the color of the oval
   * @param green the green component of the color of the oval
   * @param blue the blue component of the color of the oval
   * @param startOfLife the time tick at which this oval appears
   * @param endOfLife the time tick at which this oval disappears
   * @return the builder object
   */
  public TweenModelBuilder addOval(
      String name,
      float cx, float cy,
      float xRadius, float yRadius,
      float red, float green, float blue,
      int startOfLife, int endOfLife) {

    Point2D.Double center = new Double(cx, cy);
    Color inputColor = new Color(red, green, blue);
    Color color = colorsBy100(inputColor);
    IShape newShape;

    if (xRadius == yRadius) {
      newShape = ShapeFactory.makeShapeCircle(name, color, startOfLife, endOfLife,
          center, (double) xRadius);
    } else {
      newShape = ShapeFactory.makeShapeOval(name, color, startOfLife, endOfLife,
          center, (double) xRadius, (double) yRadius);
    }

    this.animation.addShape(newShape);
    return this;
  }

  /**
   * Add a new rectangle to the model with the given specifications.
   *
   * @param name the unique name given to this shape
   * @param lx the minimum x-coordinate of a corner of the rectangle
   * @param ly the minimum y-coordinate of a corner of the rectangle
   * @param width the xSize of the rectangle
   * @param height the ySize of the rectangle
   * @param red the red component of the color of the rectangle
   * @param green the green component of the color of the rectangle
   * @param blue the blue component of the color of the rectangle
   * @param startOfLife the time tick at which this rectangle appears
   * @param endOfLife the time tick at which this rectangle disappears
   * @return the builder object
   */
  public TweenModelBuilder addRectangle(
      String name,
      float lx, float ly,
      float width, float height,
      float red, float green, float blue,
      int startOfLife, int endOfLife) {

    Point2D.Double lowerLeft = new Double(lx, ly);
    Color inputColor = new Color(red, green, blue);
    Color color = colorsBy100(inputColor);

    IShape newShape = ShapeFactory
        .makeShapeRectangle(name, color, startOfLife, endOfLife,
            lowerLeft, (double) width, (double) height);

    this.animation.addShape(newShape);
    return this;
  }

  /**
   * Move the specified shape to the given position during the given time interval.
   *
   * @param name the unique name of the shape to be moved
   * @param moveFromX the x-coordinate of the initial position of this shape. What this x-coordinate
   *        represents depends on the shape.
   * @param moveFromY the y-coordinate of the initial position of this shape. what this y-coordinate
   *        represents depends on the shape.
   * @param moveToX the x-coordinate of the final position of this shape. What this x-coordinate
   *        represents depends on the shape.
   * @param moveToY the y-coordinate of the final position of this shape. what this y-coordinate
   *        represents depends on the shape.
   * @param startTime the time tick at which this movement should start
   * @param endTime the time tick at which this movement should end
   */
  public TweenModelBuilder addMove(
      String name,
      float moveFromX, float moveFromY,
      float moveToX, float moveToY,
      int startTime, int endTime) {

    Point2D.Double from = new Point2D.Double(moveFromX, moveFromY);
    Point2D.Double to = new Point2D.Double(moveToX, moveToY);
    IShape shape = this.animation.getFirstIShape(name);
    ShapeType thisType;
    try {
      thisType = shape.getType();
    } catch (NullPointerException e) {
      System.err.println(e.getMessage());
      return this; // return current animation if failure to add
    }
    ITransformation newTransformation = TransformFactory
        .makeTransformMove(from, to, startTime, endTime, name, thisType);
    this.animation.addTransformation(newTransformation);
    return this;
  }

  /**
   * Change the color of the specified shape to the new specified color in the specified time
   * interval.
   *
   * @param name the unique name of the shape whose color is to be changed
   * @param oldR the r-component of the old color
   * @param oldG the g-component of the old color
   * @param oldB the b-component of the old color
   * @param newR the r-component of the new color
   * @param newG the g-component of the new color
   * @param newB the b-component of the new color
   * @param startTime the time tick at which this color change should start
   * @param endTime the time tick at which this color change should end
   */
  public TweenModelBuilder addColorChange(
      String name,
      float oldR, float oldG, float oldB,
      float newR, float newG, float newB,
      int startTime, int endTime) {

    Color oldInputColor = new Color(oldR, oldG, oldB);
    Color oldColor = colorsBy100(oldInputColor);
    Color newInputColor = new Color(newR, newG, newB);
    Color newColor = colorsBy100(newInputColor);

    ITransformation newTransformation = TransformFactory
        .makeTransformColor(startTime, endTime, name, oldColor, newColor);

    this.animation.addTransformation(newTransformation);
    return this;
  }

  /**
   * Change the x and y extents of this shape from the specified extents to the specified target
   * extents. What these extents actually mean depends on the shape, but these are roughly the
   * extents of the box enclosing the shape
   */
  public TweenModelBuilder addScaleToChange(
      String name,
      float fromSx,
      float fromSy,
      float toSx,
      float toSy,
      int startTime,
      int endTime) {
    IShape shape = this.animation.getFirstIShape(name);
    ShapeType thisType;
    try {
      thisType = shape.getType();
    } catch (NullPointerException e) {
      System.err.println(e.getMessage());
      return this; // return current animation if failure to add
    }
    ITransformation newTransformation = TransformFactory
        .makeTransformScale(fromSx, fromSy, toSx, toSy, startTime, endTime, name, thisType);
    this.animation.addTransformation(newTransformation);
    return this;
  }

  /**
   * Return the model built so far.
   *
   * @return the model that was constructed so far
   */
  public IAnimation build() {
    return this.animation;
  }

  /**
   * Return itself as a String.
   *
   * @return an empty string, as the builder itself stores no information.
   */
  @Override
  public String toString() {
    return "";
  }

}
