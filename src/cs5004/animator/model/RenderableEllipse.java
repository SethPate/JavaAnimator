package cs5004.animator.model;

import java.awt.Graphics;

/**
 * An extension of the RenderableShape abstract class that represents an Ellipse. It will draw both
 * ShapeCircles and ShapeOvals for an IView.
 */

public class RenderableEllipse extends RenderableShape {

  /**
   * Creates a new RenderableEllipse.
   *
   * @param x the x coordinate of the center.
   * @param y the y coordinate of the center.
   * @param xRadius the x radius of the ellipse.
   * @param yRadius the y radius of the ellipse.
   * @param color the Color of the ellipse.
   */
  public RenderableEllipse(int x, int y, float xRadius, float yRadius, Color color) {
    super(x, y, xRadius, yRadius, color);
  }

  /**
   * When provided a Graphics object, every IRenderableShape can draw itself onto that object. The
   * method used to draw depends on whether the IRenderableShape is a Circle, Rectangle, etc. Due to
   * the graphics requirements of Swing, ovals must have their x and y coordinates adjusted by
   * subtracting their height and width, and must have their x and y distances multiplied by two
   * (from radius to diameter).
   *
   * @param graphics A Graphics object, probably belonging to a DrawingPanel.
   */
  public void drawSelf(Graphics graphics) {
    int xDiameter = (int) xSize * 2;
    int yDiameter = (int) ySize * 2;
    int xAdjusted = (int) (x - xSize);
    int yAdjusted = (int) (y - ySize);
    int red = Math.round(this.color.getRed() / 100 * 255);
    int green = Math.round(this.color.getGreen() / 100 * 255);
    int blue = Math.round(this.color.getBlue() / 100 * 255);
    graphics.setColor(new java.awt.Color(red, green, blue));
    graphics.drawOval(xAdjusted, yAdjusted, xDiameter, yDiameter);
    graphics.fillOval(xAdjusted, yAdjusted, xDiameter, yDiameter);
  }

}
