package cs5004.animator.model;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Collections;
import java.util.Objects;

/**
 * A class implementing the IAnimation interface. This Animation uses an ArrayList to store Shapes,
 * and a LinkedList to store Transformations.
 */

public class Animation implements IAnimation {

  private List<IShape> shapes;
  private List<ITransformation> transformations;

  /**
   * Creates a new instance of the Animation class.
   */
  public Animation() {
    this.shapes = new ArrayList<>();
    this.transformations = new LinkedList<>();
  }

  /**
   * Gets the IShapes contained by this Animation, sorted by the time at which they appear.
   *
   * @return A List of IShapes containing this Animation's shapes.
   */
  public List<IShape> getShapes() {
    return new ArrayList<>(this.shapes);
  }

  /**
   * Adds a new Shape to this Animation. Will not add an IShape if the IAnimation already has an
   * IShape of that name.
   *
   * @param shape an instance of an object implementing the IShape interface.
   * @throws NullPointerException if shape is null.
   */
  public void addShape(IShape shape) throws NullPointerException {
    Objects.requireNonNull(shape);
    if (containsIShape(shape.getName())) {
      return;
    } else {
      this.shapes.add(shape);
    }
  }

  /**
   * Determines if this IAnimation already contains an IShape with the same name as the given
   * String.
   *
   * @param shapeName an String to check against.
   * @return true if there is a conflict, false otherwise.
   */
  private boolean containsIShape(String shapeName) {
    for (IShape thisShape : this.shapes) {
      if (thisShape.getName().equals(shapeName)) {
        return true;
      }
    }
    return false;
  }

  /**
   * Fetches the first IShape with the given shapeName in this IAnimation, null if no matching
   * IShape.
   *
   * @param shapeName a String, the name of an IShape to search against.
   * @return the first IShape that matches, null otherwise.
   */
  public IShape getFirstIShape(String shapeName) {
    String shapeNameUpper = shapeName.toUpperCase();
    for (IShape shape : this.shapes) {
      if (shape.getName().equals(shapeNameUpper)) {
        return shape;
      }
    }
    return null;
  }

  /**
   * Adds a new Transformation to this Animation. An ITransformation is invalid if: 1) if the
   * Transformation applies to a Shape that does not exist in the Animation, or 2) the
   * Transformation begins before the Shape has appeared, or 3) the Transformation ends after the
   * Shape has disappeared, or 4) the Transformation begins after the Shape has disappeared, or 5)
   * if there is a conflicting Transformation being applied to the Shape at the same time (such as
   * two movements). Also adds a copy to the applicable Shape's list of ITransformations.
   *
   * @param transformation an instance of an object implementing the ITransformation interface.
   * @throws IllegalArgumentException if ITransformation is invalid (see above).
   * @throws NullPointerException if shape is null.
   */
  public void addTransformation(ITransformation transformation) throws IllegalArgumentException {
    Objects.requireNonNull(transformation);
    IShape thisShape = this.getFirstIShape(transformation.getShapeName());
    try {
      if (transformation.getStart() < thisShape.getAppear()) {
        throw new IllegalArgumentException("Transformation cannot begin before Shape appears.");
      }
      if (transformation.getEnd() > thisShape.getDisappear()) {
        throw new IllegalArgumentException("Transformation cannot end after Shape disappears.");
      }
      if (transformation.getStart() > thisShape.getDisappear()) {
        throw new IllegalArgumentException("Transformation cannot start after Shape disappears.");
      }
      if (this.transformationConflict(transformation)) {
        throw new IllegalArgumentException(
            "Transformation cannot start before a Transformation of the same type ends.");
      } else {
        try {
          this.transformations.add(transformation);
          thisShape.addTransformation(transformation);
          Collections.sort(transformations);
        } catch (Exception e) {
          throw new IllegalArgumentException("Could not add Transformation to Animation.");
        }
      }
    } catch (NullPointerException e) {
      throw new IllegalArgumentException("Animation does not contain a Shape of this name.");
    }
  }

  /**
   * Tests whether an Animation contains a Transformation that conflicts with the given
   * Transformation.
   *
   * @param other a Transformation.
   * @return True if there is a conflict, False otherwise.
   * @throws NullPointerException if shape is null.
   */
  private boolean transformationConflict(ITransformation other) throws NullPointerException {
    for (ITransformation transformation : this.transformations) {
      TransformType otherTransformType = other.getType();
      String otherShapeName = other.getShapeName();
      if (transformation.getType().equals(otherTransformType)
          && transformation.getShapeName().equals(otherShapeName)) {
        if (other.getStart() <= transformation.getEnd()) {
          return true;
        }
      }
    }
    return false;
  }

  /**
   * Returns a String formatted to the specification of the OutputType, and according to the given
   * speed.
   *
   * @param outputType an enum containing the desired format of the output.
   * @param speed an integer containing the desired number of ticks per second in the output.
   * @return A String of formatted animation output in the specified type
   * @throws IllegalArgumentException if the outputType was not understood, or speed was <= 0.
   * @throws NullPointerException if outputType is null.
   */
  public String generate(OutputType outputType, int speed)
      throws IllegalArgumentException, NullPointerException {
    Objects.requireNonNull(outputType);
    if (speed <= 0) {
      throw new IllegalArgumentException("speed must be greater than 0");
    }
    switch (outputType) {
      case TEXT:
        return generateText(speed);
      case SVG:
        return generateSVG(speed);
      default:
        throw new IllegalArgumentException("Failed to generate that output type");
    }
  }

  /**
   * Generates this Animation, formatted for output as Text.
   *
   * @param speed the speed in ticks per minute to generate output for.
   * @return A String containing this Animation.
   */
  private String generateText(int speed) {
    String output = "Shapes:\n";
    for (IShape shape : this.shapes) {
      float thisAppear = shape.getAppear() / speed;
      float thisDisappear = shape.getDisappear() / speed;
      output += shape.toStringText(thisAppear, thisDisappear);
      output += "\n";
    }
    for (ITransformation transformation : this.transformations) {
      float thisStart = transformation.getStart() / speed;
      float thisEnd = transformation.getEnd() / speed;
      output += transformation.toStringText(thisStart, thisEnd);
    }
    return output;
  }

  /**
   * Generates this Animation, formatted for output as SVG.
   *
   * @param speed the speed in ticks per minute to generate output for.
   * @return A String containing this Animation.
   */
  private String generateSVG(int speed) {
    // these values auto-adjust
    float animationWidth = this.getMaxX();
    float animationHeight = this.getMaxY();

    String output = String
        .format("<svg width=\"%.1f\" height=\"%.1f\" version=\"1.1\"",
            animationWidth,
            animationHeight);
    output += " xmlns=\"http://www.w3.org/2000/svg\"> \n";

    for (IShape shape : this.shapes) {
      float thisAppearMS = shape.getAppear() / speed * 1000;
      float thisDisappearMS = shape.getDisappear() / speed * 1000;

      output += shape.toStringSVG(thisAppearMS, thisDisappearMS);

      for (ITransformation transformation : this.transformations) {
        if (transformation.getShapeName().equals(shape.getName())) {
          float thisStartMS = transformation.getStart() / speed * 1000;
          float thisEndMS = transformation.getEnd() / speed * 1000;

          output += "  " + transformation.toStringSVG(thisStartMS, thisEndMS);
        }
      }
      output += shape.toStringSVGClose();
    }

    output += "</svg>";
    return output;
  }

  /**
   * Mainly used for testing. Redirects to generating a text animation at speed = 1.
   *
   * @return a String representing the text of this IAnimation.
   */
  @Override
  public String toString() {
    return this.generateText(1);
  }

  /**
   * Gets the largest x coordinate touched by this IAnimation. Used to set a background
   *
   * @return the sum of the largest x coordinate of a shape and the largest x value of a shape.
   * @throws IllegalStateException if failed to pull x coordinate.
   */
  public float getMaxX() {
    float maxX = 0;

    for (IShape shape : this.shapes) {
      if (shape.getMaxXCoord() > maxX) {
        maxX = shape.getMaxXCoord();
      }
    }

    return maxX;

  }

  /**
   * Gets the largest y coordinate touched by this IAnimation. Used to set a background in an
   * animation.
   *
   * @return the sum of the largest y coordinate of a shape and the largest y value of a shape.
   * @throws IllegalStateException if failed to pull y coordinate.
   */
  public float getMaxY() {
    float maxY = 0;

    for (IShape shape : this.shapes) {
      if (shape.getMaxYCoord() > maxY) {
        maxY = shape.getMaxYCoord();
      }
    }

    return maxY;
  }

  /**
   * Gets the last tick of this IAnimation, which is the time when the last IShape disappears.
   * Useful for terminating animations.
   *
   * @return int the last tick of this IAnimation.
   */
  public int lastTick() {
    int lastTick = 0;

    for (IShape shape : this.shapes) {
      if (shape.getDisappear() > lastTick) {
        lastTick = (int) shape.getDisappear();
      }
    }

    return lastTick;
  }

  /**
   * Generates a list of shapes to be rendered at any given tick. Checks every IShape, applies
   * tweening if it's being transformed at the given tick, and creates a new IRenderableShape with
   * the result. If the given tick is after the last tick in the IAnimation, throws an exception to
   * be caught by the IAnimationController.
   *
   * @param tick an arbitrary unit of animation time.
   * @return a list of IRenderableShapes (read only).
   * @throws IllegalArgumentException if tick < 0 or greater than lastTick
   */
  public List<IRenderableShape> generateFrame(int tick) {
    if (tick < 0) {
      throw new IllegalArgumentException("tick cannot be less than zero");
    }
    if (tick >= lastTick()) {
      throw new IllegalArgumentException(
          "generateFrame() will not render a tick greater than or equal to the last tick");
    }

    List<IRenderableShape> renderableShapes = new ArrayList<>();

    for (IShape shape : this.shapes) {
      if (shape.isActive(tick)) {
        try {
          renderableShapes.add(shape.generateFrame(tick));
        } catch (Exception e) {
          System.err.println(e.getMessage());
        }
      }
    }
    return renderableShapes;
  }
}