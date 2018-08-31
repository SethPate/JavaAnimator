package cs5004.animator.model;

/**
 * A Factory class providing a static method for creating objects of the IRenderableShape interface.
 * Has a single method for creating each subclass of RenderableShape.
 */

public class RenderableFactory {

  /**
   * Creates a new RenderableEllipse.
   *
   * @param x an int, the x coordinate of the center.
   * @param y an int, the y coordinate of the center.
   * @param width a float, the x radius.
   * @param height a float, the y radius.
   * @param color the color of the Shape.
   * @return a RenderableEllipse.
   */
  public static IRenderableShape createRenderableEllipse(int x, int y, float width, float height,
      Color color) {
    return new RenderableEllipse(x, y, width, height, color);
  }

  /**
   * Creates a new RenderableRect.
   *
   * @param x an int, the x coordinate of the lower left corner.
   * @param y an int, the y coordinate of the lower left corner.
   * @param width a float, the x length.
   * @param height a float, the y length.
   * @param color the color of the Shape.
   * @return a RenderableRect.
   */
  public static IRenderableShape createRenderableRect(int x, int y, float width, float height,
      Color color) {
    return new RenderableRect(x, y, width, height, color);
  }

  /**
   * Creates an IRenderableShape, depending on type.
   *
   * @param shapeType the type of Shape to create.
   * @param x an int, the x coordinate of the lower left corner.
   * @param y an int, the y coordinate of the lower left corner.
   * @param xLength a float, the x length.
   * @param yLength a float, the y length.
   * @param color the color of the Shape.
   * @return an IRenderableShape.
   */
  public static IRenderableShape makeRenderableShape(ShapeType shapeType, int x, int y,
      float xLength, float yLength, Color color) {
    IRenderableShape returnShape;
    if (shapeType.equals(ShapeType.RECTANGLE)) {
      returnShape = RenderableFactory.createRenderableRect(x, y, xLength, yLength, color);
    } else if ((shapeType.equals(ShapeType.CIRCLE))
        || (shapeType.equals(ShapeType.OVAL))) {
      returnShape = RenderableFactory.createRenderableEllipse(x, y, xLength, yLength, color);
    } else {
      throw new IllegalArgumentException(
          "error in shape.generateFrame. could not identify its type.");
    }
    return returnShape;
  }

}
