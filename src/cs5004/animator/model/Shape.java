package cs5004.animator.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * An abstract class representing a Shape with a name, a color, an appearing time, and a
 * disappearing time. Implemented as actual Shapes by subclasses. Shapes store ITransformations that
 * apply to them in a list.
 */

public abstract class Shape implements IShape {

  protected final String name;
  protected final Color color;
  protected float appear;
  protected float disappear;
  protected ShapeType shapeType;
  protected List<ITransformation> transformations;

  /**
   * Creates an instance of the Shape class.
   *
   * @param name The name of the Shape.
   * @param color The color of the Shape, represented by its own object.
   * @param appear The time at which the Shape appears.
   * @param disappear The time at which the Shape disappears.
   * @throws IllegalArgumentException if appear < 0.
   */
  public Shape(String name, Color color, int appear, int disappear)
      throws IllegalArgumentException {
    if (appear < 0) {
      throw new IllegalArgumentException("Shape cannot appear before t=0.");
    }
    if (disappear < appear) {
      throw new IllegalArgumentException("Shape cannot disappear before it appears.");
    }
    this.name = name.toUpperCase();
    this.color = color;
    this.appear = appear;
    this.disappear = disappear;
    this.transformations = new ArrayList<>();
  }

  /**
   * Gets the type of this Shape.
   *
   * @return the type (rectangle, oval, etc.)
   */
  public ShapeType getType() {
    return this.shapeType;
  }

  /**
   * Gets the color of this Shape.
   *
   * @return the Color.
   */
  public Color getColor() {
    return new Color(this.color.getRed(), this.color.getGreen(), this.color.getBlue());
  }

  /**
   * Gets the name of this Shape.
   *
   * @return a String containing the name.
   */
  public String getName() {
    return this.name;
  }

  /**
   * Gets the time at which this Shape appears.
   *
   * @return an integer containing the time.
   */
  public float getAppear() {
    return this.appear;
  }

  /**
   * Gets the time at which this Shape disappears.
   *
   * @return an integer containing the time.
   */
  public float getDisappear() {
    return this.disappear;
  }

  /**
   * Returns the largest x coordinate touched by this IShape throughout the animation.
   *
   * @return a float with the largest x coordinate.
   */
  public float getMaxXCoord() {
    float maxX = this.getXCoord();

    for (ITransformation transformation : this.transformations) {
      float x = transformation.getMaxXCoord();
      if (x > maxX) {
        maxX = x;
      }
    }

    return maxX + this.getXLength();
  }

  /**
   * Returns the largest y coordinate touched by this IShape throughout the animation.
   *
   * @return a float with the largest y coordinate.
   */
  public float getMaxYCoord() {
    float maxY = this.getYCoord();

    for (ITransformation transformation : this.transformations) {
      float y = transformation.getMaxYCoord();
      if (y > maxY) {
        maxY = y;
      }
    }

    return maxY + this.getYLength();
  }

  /**
   * Determines whether this IShape should be displayed at the given tick.
   *
   * @param tick an arbitrary unit of time.
   * @return true if the IShape should be displayed, false if not.
   */
  public boolean isActive(int tick) {
    return ((this.appear <= tick) && (tick < this.disappear));
  }

  /**
   * Adds an ITransformation to this Shape's list of ITransformations. It must apply to this
   * IShape.
   *
   * @param transformation an ITransformation applying to this Shape.
   * @throws NullPointerException if object is null.
   * @throws IllegalArgumentException if ITransformation does not apply to this Shape.
   */
  public void addTransformation(ITransformation transformation)
      throws IllegalArgumentException, NullPointerException {
    Objects.requireNonNull(transformation);
    if (!transformation.getShapeName().equals(this.name)) {
      throw new IllegalArgumentException(
          "error adding ITransformation to this Shape; name must be the same as Shape");
    }
    this.transformations.add(transformation);
  }

  /**
   * Represents this Shape at the given tick. Depending on the tick, applies the relevant
   * ITransformation (including tweening) and returns a read-only IRenderableShape.
   *
   * @param tick the tick at which to generate this Shape.
   * @return a read-only IRenderableShape
   * @throws IllegalArgumentException if the Shape has not appeared at this tick yet.
   */
  public IRenderableShape generateFrame(int tick) throws IllegalArgumentException {
    if (tick < this.appear) {
      throw new IllegalArgumentException(
          "error generating frame from shape; tick cannot be before shape appears");
    }

    int x = (int) this.getXCoord();
    int y = (int) this.getYCoord();
    float xDist = this.getXLength();
    float yDist = this.getYLength();
    Color color = this.getColor();

    IRenderableShape renderableShape = RenderableFactory
        .makeRenderableShape(this.shapeType, x, y, xDist, yDist, color);

    // gradually update information about the Shape if necessary
    // by passing it through successive calls of tween(),
    // one for each ITransformation

    for (ITransformation transformation : this.transformations) {
      renderableShape = transformation.tween(renderableShape, this.shapeType, tick);
    }

    return renderableShape;
  }
}