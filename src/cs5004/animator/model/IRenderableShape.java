package cs5004.animator.model;

import java.awt.Graphics;

/**
 * An Interface representing a Shape that can be easily rendered in an IAnimation. IRenderableShapes
 * are designed to work with a Java Swing Graphics object, and contain a method to draw themselves
 * onto that object. IRenderableShapes should not have setters; their attributes are final.
 */

public interface IRenderableShape {

  /**
   * When provided a Graphics object, every IRenderableShape can draw itself onto that object. The
   * method used to draw depends on whether the IRenderableShape is a Circle, Rectangle, etc.
   *
   * @param graphics A Graphics object, probably belonging to a DrawingPanel.
   */
  void drawSelf(Graphics graphics);

  /**
   * Get the x coordinate of this IRenderableShape.
   *
   * @return an int with the x coordinate.
   */
  int getX();

  /**
   * Gets the y coordinate of this IRenderableShape.
   *
   * @return an int with the y coordinate.
   */
  int getY();

  /**
   * Gets the x length of this IRenderableShape.
   *
   * @return an int with the x length.
   */
  float getXSize();

  /**
   * Gets the y length of this IRenderableShape.
   *
   * @return an int with the y length.
   */
  float getYSize();

  /**
   * Gets the color of this IRenderableShape.
   *
   * @return a Color object.
   */
  Color getColor();

}
