import static org.junit.Assert.assertEquals;

import cs5004.animator.model.Color;
import cs5004.animator.model.ITransformation;
import cs5004.animator.model.ShapeType;
import cs5004.animator.model.TransformFactory;
import cs5004.animator.model.TransformType;
import java.awt.geom.Point2D.Double;
import org.junit.Before;
import org.junit.Test;

/**
 * A Junit test for the ITransformation interface. ITransformations must map to a certain Shape and
 * must follow certain rule about their timing.
 */

public class ITransformationTest {

  private Color red = new Color(1f, 0f, 0f);
  private Color blue = new Color(0f, 0f, 1f);
  private ITransformation moveRect;
  private ITransformation moveEllipse;
  private ITransformation color;
  private ITransformation scaleCircle;
  private ITransformation scaleOval;
  private ITransformation scaleRect;

  /**
   * Creates basic test cases for this test.
   */
  @Before
  public void setUp() {
    moveRect = TransformFactory.makeTransformMove(new Double(0d, 0d),
        new Double(2d, 2d),2, 9, "A", ShapeType.RECTANGLE);
    moveEllipse = TransformFactory.makeTransformMove(new Double(0d, 0d),
        new Double(2d, 2d),2, 9, "A", ShapeType.CIRCLE);
    color = TransformFactory.makeTransformColor(6, 14, "C", blue, red);
    scaleCircle = TransformFactory.makeTransformScale(1, 1, 2, 2,3,4,
        "A", ShapeType.CIRCLE);
    scaleOval = TransformFactory.makeTransformScale(1, 1, 2, 2, 3, 4,
        "A", ShapeType.OVAL);
    scaleRect = TransformFactory.makeTransformScale(1, 1, 2, 2, 3, 4,
        "A", ShapeType.RECTANGLE);
  }

  /**
   * Cannot create a Transformation that starts before t=0.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testTransformationBefore0() {
    TransformFactory.makeTransformColor(-1, 0, "A",
        blue, red);
  }

  /**
   * Cannot create a Transformation that ends before it starts.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testTransformationEndsBeforeStarts() {
    TransformFactory.makeTransformColor(1, 0, "A",
        blue, red);
  }

  /**
   * TransformColor should correctly return itself formatted for output as text.
   */
  @Test
  public void toStringTextTransformColor() {
    assertEquals("Shape C changes color from (0.0,0.0,1.0) to (1.0,0.0,0.0)"
            + " from t=6.0s to t=14.0s\n",
        color.toStringText(6, 14));
  }

  /**
   * TransformMove should correctly return itself formatted for output as text.
   */
  @Test
  public void toStringTextTransformMove() {
    assertEquals("Shape A moves from (0.0,0.0) to (2.0,2.0) from t=2.0s to t=9.0s\n",
        moveRect.toStringText(2, 9));
  }

  /**
   * TransformScale should correctly return itself formatted for output as text.
   */
  @Test
  public void toStringTextTransformScaleCircle() {
    assertEquals("Shape A scales from Radius: 1.0 to Radius: 2.0 from t=3.0s to t=4.0s\n",
        scaleCircle.toStringText(3, 4));
  }

  /**
   * TransformScaleOval should correctly return itself formatted for output as text.
   */
  @Test
  public void toStringTextTransformTransformScaleOval() {
    assertEquals(
        "Shape A scales from X radius: 1.0, Y radius: 1.0 "
            + "to X radius: 2.0, Y radius: 2.0 from t=3.0s to t=4.0s\n",
        scaleOval.toStringText(3, 4));
  }

  /**
   * TransformScaleRect should correctly return itself formatted for output as text.
   */
  @Test
  public void toStringTextTransformTransformScaleRect() {
    assertEquals(
        "Shape A scales from Width: 1.0, Height: 1.0 "
            + "to Width: 2.0, Height: 2.0 from t=3.0s to t=4.0s\n",
        scaleRect.toStringText(3, 4));
  }

  /**
   * TransformColor should correctly return itself formatted for output as SVG.
   */
  @Test
  public void toStringSVGTransformColor() {
    assertEquals("<animate attributeName=\"fill\" attributeType=\"CSS\" "
            + "from=\"rgb(0.0%,0.0%,1.0%)\" "
            + "to=\"rgb(1.0%,0.0%,0.0%)\" begin=\"2000.0ms\" dur=\"7000.0ms\" fill=\"freeze\" />\n",
        color.toStringSVG(2000, 9000));
  }

  /**
   * TransformMove when applied to an ellipse should correctly return itself formatted for output as
   * SVG.
   */
  @Test
  public void toStringSVGTransformMoveEllipse() {
    assertEquals("<animate attributeName=\"cx\" attributeType=\"CSS\" from=\"0\" to=\"2\""
            + " begin=\"2000.0ms\" dur=\"7000.0ms\" fill=\"freeze\" />\n"
            + "  <animate attributeName=\"cy\" attributeType=\"CSS\" from=\"0\" to=\"2\""
            + " begin=\"2000.0ms\" dur=\"7000.0ms\" fill=\"freeze\" />\n",
        moveEllipse.toStringSVG(2000, 9000));
  }

  /**
   * TransformMove applied to a rectangle should return itself formatted as SVG.
   */
  @Test
  public void toStringSVGTransformMoveRect() {
    assertEquals(
        "<animate attributeName=\"x\" attributeType=\"CSS\" from=\"0\" to=\"2\""
            + " begin=\"2000.0ms\" dur=\"7000.0ms\" fill=\"freeze\" />\n"
            + "  <animate attributeName=\"y\" attributeType=\"CSS\" from=\"0\" to=\"2\""
            + " begin=\"2000.0ms\" dur=\"7000.0ms\" fill=\"freeze\" />\n",
        moveRect.toStringSVG(2000, 9000));
  }

  /**
   * TransformScale should correctly return itself formatted for output as SVG.
   */
  @Test
  public void toStringSVGTransformScaleCircle() {
    assertEquals(
        "<animate attributeName=\"r\" attributeType=\"CSS\" from=\"1\" to=\"2\""
            + " begin=\"2000.0ms\" dur=\"7000.0ms\" fill=\"freeze\" />\n",
        scaleCircle.toStringSVG(2000, 9000));
  }

  /**
   * TransformScaleOval should correctly return itself formatted for output as SVG.
   */
  @Test
  public void toStringSVGTransformTransformScaleOval() {
    assertEquals(
        "<animate attributeName=\"rx\" attributeType=\"CSS\" from=\"1\" to=\"2\" "
            + "begin=\"2000.0ms\" dur=\"7000.0ms\" fill=\"freeze\" />\n"
            + "  <animate attributeName=\"ry\" attributeType=\"CSS\" from=\"1\" to=\"2\" "
            + "begin=\"2000.0ms\" dur=\"7000.0ms\" fill=\"freeze\" />\n",
        scaleOval.toStringSVG(2000, 9000));
  }

  /**
   * TransformScaleRect should correctly return itself formatted for output as SVG.
   */
  @Test
  public void toStringSVGTransformScaleRect() {
    assertEquals(
        "<animate attributeName=\"width\" attributeType=\"CSS\" from=\"1\" to=\"2\" "
            + "begin=\"2000.0ms\" dur=\"7000.0ms\" fill=\"freeze\" />\n"
            + "  <animate attributeName=\"height\" attributeType=\"CSS\" from=\"1\" to=\"2\" "
            + "begin=\"2000.0ms\" dur=\"7000.0ms\" fill=\"freeze\" />\n",
        scaleRect.toStringSVG(2000, 9000));
  }

  /**
   * ITransformations should be able to return their type.
   */
  @Test
  public void getType() {
    assertEquals(TransformType.SCALE,scaleCircle.getType());
  }

  /**
   * ITransformations should be able to return the time at which they start.
   */
  @Test
  public void getStart() {
    assertEquals(3,scaleCircle.getStart(), .0001);
  }

  /**
   * ITransformations should be able to return the time at which they end.
   */
  @Test
  public void getEnd() {
    assertEquals(4,scaleCircle.getEnd(), .0001);
  }

  /**
   * ITransformations should be able to return the name of the IShape which they transform.
   */
  @Test
  public void getShapeName() {
    assertEquals("A",scaleCircle.getShapeName());
  }

  /**
   * isActive should return true if ITransformation is active.
   */
  @Test
  public void isActiveTrue() {
    assertEquals(true,moveRect.isActive(2));
  }

  /**
   * isActive should return false if ITransformation has not appeared yet.
   */
  @Test
  public void isActiveFalseBefore() {
    assertEquals(false,moveRect.isActive(1));
  }

  /**
   * isActive should return true if ITransformation has disappeared.
   */
  @Test
  public void isActiveTrueAfter() {
    assertEquals(true,moveRect.isActive(10));
  }

  /**
   * getMaxX should work for TransformColors, Moves, and Scales.
   */
  @Test
  public void getMaxX() {
    assertEquals(0,color.getMaxXCoord(),.001);
    assertEquals(2,moveRect.getMaxXCoord(),.001);
    assertEquals(2,scaleCircle.getMaxXCoord(),.001);
  }

  /**
   * getMaxY should work for TransformColors, Moves, and Scales.
   */
  @Test
  public void getMaxY() {
    assertEquals(0,color.getMaxYCoord(),.001);
    assertEquals(2,moveRect.getMaxYCoord(),.001);
    assertEquals(2,scaleCircle.getMaxYCoord(),.001);
  }
}