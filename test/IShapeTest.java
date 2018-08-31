import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import cs5004.animator.model.Color;
import cs5004.animator.model.IShape;
import cs5004.animator.model.ShapeFactory;
import cs5004.animator.model.ShapeType;
import java.awt.geom.Point2D.Double;
import org.junit.Before;
import org.junit.Test;

/**
 * A Junit class for the IShape test. IShapes have logical physical coordinates and be able to
 * generate their own output representations.
 */

public class IShapeTest {

  private Color red = new Color(1f, 0f, 0f);
  private Color green = new Color(0f, 1f, 0f);
  private Color blue = new Color(0f, 0f, 1f);
  private IShape circle;
  private IShape oval;
  private IShape rectangle;

  /**
   * Creates test cases for the IShape test. One for each kind of IShape.
   */
  @Before
  public void setUp() {
    circle = ShapeFactory.makeShapeCircle("A", red, 1, 10,
        new Double(0d, 0d), 10d);
    oval = ShapeFactory.makeShapeOval("B", green, 2, 10,
        new Double(2d, 2d), 6d, 5d);
    rectangle = ShapeFactory.makeShapeRectangle("C", blue, 5, 15,
        new Double(3d, 4d), 7d, 8d);
  }


  /**
   * Cannot make a ShapeCircle with a radius <= 0.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testShapeCircleInvalidRadius() {
    ShapeFactory.makeShapeCircle("A", red, 0, 1, new Double(0, 0),
        0d);
  }

  /**
   * Cannot make a ShapeOval with widthRadius <= 0.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testShapeOvalInvalidWidthRadius() {
    ShapeFactory.makeShapeOval("B", green, 2, 10,
        new Double(2d, 2d), 0d, 5d);
  }

  /**
   * Cannot make a ShapeOval with either heightRadius <= 0.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testShapeOvalInvalidHeightRadius() {
    ShapeFactory.makeShapeOval("B", green, 2, 10,
        new Double(2d, 2d), 2d, 0d);
  }

  /**
   * Cannot make a ShapeRectangle with width <= 0.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testShapeRectangleInvalidWidth() {
    ShapeFactory.makeShapeRectangle("C", blue, 5, 15,
        new Double(4d, 4d), 0d, 8d);
  }

  /**
   * Cannot make a ShapeRectangle with height <= 0.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testShapeRectangleInvalidHeight() {
    ShapeFactory.makeShapeRectangle("C", blue, 5, 15,
        new Double(4d, 4d), 8d, 0d);
  }

  /**
   * Cannot make a Shape that appears before t=0.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testStartBeforeZero() {
    ShapeFactory.makeShapeCircle("A", blue, -1, 20, new Double(0d, 0d),
        5d);
  }

  /**
   * Cannot make a Shape that disappears before it appears.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testEndBeforeStart() {
    ShapeFactory.makeShapeCircle("A", blue, 1, 0, new Double(0d, 0d),
        5d);
  }

  /**
   * ShapeCircle should correctly return itself formatted for output as text.
   */
  @Test
  public void toStringTextShapeCircle() {
    assertEquals("Name: A\n"
        + "Type: circle\n"
        + "Center: (0.0,0.0), Radius: 10.0, Color: (1.0,0.0,0.0)\n"
        + "Appears at t=1.0s\n"
        + "Disappears at t=10.0s\n", circle.toStringText(1, 10));
  }

  /**
   * ShapeOval should correctly return itself formatted for output as text.
   */
  @Test
  public void toStringTextShapeOval() {
    assertEquals("Name: B\n"
        + "Type: oval\n"
        + "Center: (2.0,2.0), X radius: 6.0, Y radius: 5.0, Color: (0.0,1.0,0.0)\n"
        + "Appears at t=2.0s\n"
        + "Disappears at t=10.0s\n", oval.toStringText(2, 10));
  }

  /**
   * ShapeRect should correctly return itself formatted for output as text.
   */
  @Test
  public void toStringTextShapeRect() {
    assertEquals("Name: C\n"
        + "Type: rectangle\n"
        + "Min corner: (3.0,4.0), Width: 7.0, Height: 8.0, Color: (0.0,0.0,1.0)\n"
        + "Appears at t=5.0s\n"
        + "Disappears at t=15.0s\n", rectangle.toStringText(5, 15));
  }

  /**
   * ShapeCircle should correctly return itself formatted for output as SVG.
   */
  @Test
  public void toStringSVGShapeCircle() {
    assertEquals(
        "<circle id=\"A\" cx=\"0\" cy=\"0\" r=\"10\" fill=\"rgb(1.0%,0.0%,0.0%)\""
            + " visibility=\"hidden\" >\n"
            + "  <set attributeName=\"visibility\" attributeType=\"CSS\" to=\"visible\""
            + " begin=\"1000.0ms\" duration=\"9000.0ms\" fill=\"freeze\" />\n",
        circle.toStringSVG(1000, 10000));
  }

  /**
   * ShapeCircle should print its correct SVG closing tag.
   */
  @Test
  public void toStringSVGCloseShapeCircle() {
    assertEquals("</circle>\n", circle.toStringSVGClose());
  }

  /**
   * ShapeOval should correctly return itself formatted for output as SVG.
   */
  @Test
  public void toStringSVGShapeOval() {
    assertEquals(
        "<ellipse id=\"B\" cx=\"2\" cy=\"2\" rx=\"6\" ry=\"5\""
            + " fill=\"rgb(0.0%,1.0%,0.0%)\" visibility=\"hidden\" >\n"
            + "  <set attributeName=\"visibility\" attributeType=\"CSS\" to=\"visible\""
            + " begin=\"2000.0ms\" duration=\"8000.0ms\" fill=\"freeze\" />\n",
        oval.toStringSVG(2000, 10000));
  }

  /**
   * ShapeOval should print the correct return SVG closing tag.
   */
  @Test
  public void toStringSVGCloseShapeOval() {
    assertEquals("</ellipse>\n",
        oval.toStringSVGClose());
  }

  /**
   * ShapeRect should correctly return itself formatted for output as SVG.
   */
  @Test
  public void toStringSVGShapeRect() {
    assertEquals(
        "<rect id=\"C\" x=\"3\" y=\"4\" width=\"7\" height=\"8\""
            + " fill=\"rgb(0.0%,0.0%,1.0%)\" visibility=\"hidden\" >\n"
            + "  <set attributeName=\"visibility\" attributeType=\"CSS\" to=\"visible\""
            + " begin=\"3000.0ms\" duration=\"7000.0ms\" fill=\"freeze\" />\n",
        rectangle.toStringSVG(3000, 10000));
  }

  /**
   * ShapeRect should return its proper SVG closing tag.
   */
  @Test
  public void toStringSVGCloseShapeRect() {
    assertEquals("</rect>\n", rectangle.toStringSVGClose());
  }

  /**
   * IShape should be able to return the time at which it starts.
   */
  @Test
  public void getAppear() {
    assertEquals(1, circle.getAppear(), .00001);
  }

  /**
   * IShape should be able to return the time at which it disappears.
   */
  @Test
  public void getDisappear() {
    assertEquals(10, circle.getDisappear(), .0001);
  }

  /**
   * IShape should be able to return its Color.
   */
  @Test
  public void getColor() {
    assertEquals(red.toString(), circle.getColor().toString());
  }

  /**
   * IShape should be able to return its ShapeType.
   */
  @Test
  public void getType() {
    assertEquals(ShapeType.CIRCLE, circle.getType());
  }

  /**
   * IShape should be able to return its name.
   */
  @Test
  public void getName() {
    assertEquals("A", circle.getName());
  }

  /**
   * isActive should return true if IShape is active.
   */
  @Test
  public void isActiveTrue() {
    assertTrue(circle.isActive(2));
  }

  /**
   * isActive should return false if IShape has not appeared yet.
   */
  @Test
  public void isActiveFalseBefore() {
    assertFalse(oval.isActive(1));
  }

  /**
   * isActive should return true if IShape has disappeared.
   */
  @Test
  public void isActiveFalseAfter() {
    assertEquals(false,circle.isActive(11));
  }

  /**
   * getMaxX should work properly with a Shape.
   */
  @Test
  public void getMaxX() {
    assertEquals(10,circle.getMaxXCoord(),.001);
    assertEquals(8,oval.getMaxXCoord(),.001);
  }

  /**
   * getMaxX should work properly with a Shape.
   */
  @Test
  public void getMaxY() {
    assertEquals(10,circle.getMaxYCoord(),.001);
    assertEquals(7,oval.getMaxYCoord(),.001);
  }
}