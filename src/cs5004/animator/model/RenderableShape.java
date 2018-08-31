package cs5004.animator.model;

/**
 * An abstract class implementing IRenderableShape. All IRenderableShapes should inherit from this
 * class, whose protected attributes should not be modified. This is because an IRenderableShape
 * should pass directly from the model to the viewer, and should not be changed along the way.
 */

public abstract class RenderableShape implements IRenderableShape {

  protected final int x;
  protected final int y;
  protected final float xSize;
  protected final float ySize;
  protected final Color color;

  /**
   * Constructs a new RenderableShape.
   *
   * @param x the x coordinate of this shape.
   * @param y the y coordinate of this shape.
   * @param xSize the xSize of this shape.
   * @param ySize the ySize of this shape.
   * @param color the Color (floats of RGB) of this shape.
   */
  public RenderableShape(int x, int y, float xSize, float ySize, Color color) {
    this.x = x;
    this.y = y;
    this.xSize = xSize;
    this.ySize = ySize;
    this.color = color;
  }

  /**
   * Represents this RenderableShape as a String, for testing purposes.
   *
   * @return this shape as a String.
   */
  @Override
  public String toString() {
    return String.format("Shape %d,%d, w %.1f, h %.1f, color %s\n", x, y, xSize, ySize,
        color.toString());
  }

  /**
   * Get the x coordinate of this IRenderableShape.
   *
   * @return an int with the x coordinate.
   */
  public int getX() {
    return x;
  }

  /**
   * Gets the y coordinate of this IRenderableShape.
   *
   * @return an int with the y coordinate.
   */
  public int getY() {
    return y;
  }

  /**
   * Gets the x length of this IRenderableShape.
   *
   * @return an int with the x length.
   */
  public float getXSize() {
    return xSize;
  }

  /**
   * Gets the y length of this IRenderableShape.
   *
   * @return an int with the y length.
   */
  public float getYSize() {
    return ySize;
  }

  /**
   * Gets the color of this IRenderableShape.
   *
   * @return a Color object.
   */
  public Color getColor() {
    return new Color(this.color.getRed(),
        this.color.getGreen(),
        this.color.getBlue());
  }
}
