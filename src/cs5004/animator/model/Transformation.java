package cs5004.animator.model;

/**
 * An abstract class representing a Transformation in an Animation. A Transformation is applied to a
 * Shape and has a start time and an end time.
 */

public abstract class Transformation implements ITransformation {

  protected float start;
  protected float end;
  protected String shapeName;
  protected TransformType type;

  /**
   * Creates a new instance of the Transformation class.
   *
   * @param start The time at which this Transformation starts.
   * @param end The time at which this Transformation ends.
   * @param shapeName The name of the Shape to which this Transformation is applied.
   * @throws IllegalArgumentException if the start time is < 0 or the end time is < the start.
   */
  public Transformation(float start, float end, String shapeName)
      throws IllegalArgumentException {
    if (start < 0) {
      throw new IllegalArgumentException("Transformation cannot start before t=0.");
    }
    if (end < start) {
      throw new IllegalArgumentException("Transformation cannot end before it starts.");
    }
    this.start = start;
    this.end = end;
    this.shapeName = shapeName.toUpperCase();
  }

  /**
   * Gets the name of this Shape.
   *
   * @return A String with the name.
   */
  public String getShapeName() {
    return this.shapeName;
  }

  /**
   * Gets the type of this Shape.
   *
   * @return A String with the type.
   */
  public TransformType getType() {
    return this.type;
  }

  /**
   * Gets the start time of this Shape.
   *
   * @return An int with the start time.
   */
  public float getStart() {
    return this.start;
  }

  /**
   * Gets the end time of this Shape.
   *
   * @return An int with the end time.
   */
  public float getEnd() {
    return this.end;
  }

  /**
   * Allows the user to compare two ITransformations. An ITransformation is smaller if it starts
   * earlier than another one.
   *
   * @param other an ITransformation.
   */
  public int compareTo(ITransformation other) {
    if (this.getStart() < other.getStart()) {
      return -1;
    } else if (this.getStart() > other.getStart()) {
      return 1;
    } else {
      return 0;
    }
  }

  /**
   * Determines whether this ITransformation should be displayed at the given tick. Whereas IShapes
   * should only be displayed if the tick is after they appear and before they disappear,
   * ITransformations are "sticky"; they are active for every tick after they start.
   *
   * @param tick an arbitrary unit of time.
   * @return true if the ITransformation should be displayed, false if not.
   */
  public boolean isActive(int tick) {
    return (this.start <= tick);
  }

  /**
   * Determines whether this ITransformation has already finished at the given tick. Whereas IShapes
   * should only be displayed if the tick is after they appear and before they disappear,
   * ITransformations are "sticky"; they are active for every tick after they start.
   *
   * @param tick an arbitrary unit of time.
   * @return true if the ITransformation should be displayed, false if not.
   */
  public boolean hasFinished(int tick) {
    return (this.end <= tick);
  }
}