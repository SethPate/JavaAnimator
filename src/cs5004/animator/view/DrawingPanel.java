package cs5004.animator.view;

import cs5004.animator.model.IRenderableShape;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JPanel;
import javax.swing.Scrollable;

/**
 * An object used by the ViewGUI object to display shapes in a GUI window. Has a list of
 * IRenderableShapes that it can be asked to draw. Can update the shapeList, for example, at every
 * tick.
 */

public class DrawingPanel extends JPanel implements Scrollable {

  private List<IRenderableShape> shapeList;

  /**
   * Creates a new drawing panel to display an IAnimation within.
   *
   * @param panelWidth the max width needed to display the animation.
   * @param panelHeight the max height needed to display the animation.
   */
  public DrawingPanel(int panelWidth, int panelHeight) {
    this.setPreferredSize(new Dimension(panelWidth, panelHeight));
    this.shapeList = new ArrayList<>();
    setBackground(Color.WHITE);
    setOpaque(true);
  }

  /**
   * Gives this DrawingPanel information about Shapes it can then be asked to draw. The shapeList
   * should be updated at each tick.
   *
   * @param newShapeList the read-only list of shapes to be drawn.
   */
  void updateShapes(List<IRenderableShape> newShapeList) {
    this.shapeList = newShapeList;
  }

  /**
   * Uses its Graphics object to draw shapes in a GUI window. Designed to be called once per tick,
   * so that shapes update.
   *
   * @param g a Graphics object used to draw shapes in this window.
   */
  @Override
  public void paintComponent(Graphics g) {
    super.paintComponent(g);
    for (IRenderableShape shape : this.shapeList) {
      shape.drawSelf(g);
    }
  }

  @Override
  public Dimension getPreferredScrollableViewportSize() {
    return new Dimension(800, 600);
  }

  @Override
  public int getScrollableUnitIncrement(Rectangle visibleRect, int orientation, int direction) {
    return 5;
  }

  @Override
  public int getScrollableBlockIncrement(Rectangle visibleRect, int orientation, int direction) {
    return 50;
  }

  @Override
  public boolean getScrollableTracksViewportWidth() {
    return false;
  }

  @Override
  public boolean getScrollableTracksViewportHeight() {
    return false;
  }
}
