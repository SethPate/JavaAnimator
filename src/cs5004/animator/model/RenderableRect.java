package cs5004.animator.model;

import java.awt.Graphics;

/**
 * An extension of the RenderableShape abstract class that represents an Ellipse. It will draw both
 * ShapeCircles and ShapeOvals for an IView.
 */

public class RenderableRect extends RenderableShape {

  /**
   * Creates a new RenderableRect.
   * @param x the x coordinate of the lower left corner.
   * @param y the y coordinate of the lower left corner.
   * @param width the xSize of the rect.
   * @param height the ySize of the rect.
   * @param color the Color of the rect.
   */
  public RenderableRect(int x, int y, float width, float height, Color color) {
    super(x, y, width, height, color);
  }

  /**
   * When provided a Graphics object, every IRenderableShape can draw itself onto that object.
   * The method used to draw depends on whether the IRenderableShape is a Circle, Rectangle, etc.
   *
   * @param graphics A Graphics object, probably belonging to a DrawingPanel.
   */
  public void drawSelf(Graphics graphics) {
    int intWidth = (int) xSize;
    int intHeight = (int) ySize;
    int red = Math.round(this.color.getRed() / 100 * 255);
    int green = Math.round(this.color.getGreen() / 100 * 255);
    int blue = Math.round(this.color.getBlue() / 100 * 255);
    graphics.setColor(new java.awt.Color(red,green,blue));
    graphics.drawRect(x,y,intWidth,intHeight);
    graphics.fillRect(x,y,intWidth,intHeight);
  }

}
