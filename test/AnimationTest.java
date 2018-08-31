import static org.junit.Assert.assertEquals;

import cs5004.animator.model.Animation;
import cs5004.animator.model.Color;
import cs5004.animator.model.IAnimation;
import cs5004.animator.model.IShape;
import cs5004.animator.model.ITransformation;
import cs5004.animator.model.OutputType;
import cs5004.animator.model.Shape;
import cs5004.animator.model.ShapeFactory;
import cs5004.animator.model.ShapeType;
import cs5004.animator.model.TransformColor;
import cs5004.animator.model.TransformFactory;
import cs5004.animator.model.TransformMove;
import java.awt.geom.Point2D;
import java.awt.geom.Point2D.Double;
import org.junit.Before;
import org.junit.Test;

/**
 * Junit tests for the Animation Class. These tests will ensure that no invalid Shapes or
 * Transformations can be added, and that the animation can describe itself in text.
 */

public class AnimationTest {

  private Animation testAnimation;
  private Color red = new Color(100f, 0f, 0f);
  private Color green = new Color(0f, 100f, 0f);
  private Color blue = new Color(0f, 0f, 100f);
  private IShape circle = ShapeFactory.makeShapeCircle("A", red, 1, 10,
      new Double(50d, 50d), 20d);
  private IShape oval = ShapeFactory.makeShapeOval("B", green, 2, 10,
      new Double(100d, 100d), 30d, 30d);
  private IShape rectangle = ShapeFactory.makeShapeRectangle("C", blue, 5, 15,
      new Double(200, 200d), 40d, 40d);
  private ITransformation move = TransformFactory
      .makeTransformMove(new Double(50d, 50d),
          new Double(100d, 50d), 2, 9, "A", ShapeType.CIRCLE);
  private ITransformation scale =
      TransformFactory.makeTransformScale(30, 30, 60, 60,
          3, 9, "B", ShapeType.OVAL);
  private ITransformation color =
      TransformFactory.makeTransformColor(6, 14, "C", blue, red);

  /**
   * Creates a test instance of the Animation class with one of each type of Shape.
   */
  @Before
  public void setUp() {
    testAnimation = new Animation();
    testAnimation.addShape(circle);
    testAnimation.addShape(oval);
    testAnimation.addShape(rectangle);
  }

  /**
   * Should be able to create an empty animation.
   */
  @Test
  public void testCreateEmptyAnimation() {
    IAnimation newAnimation = new Animation();
    assertEquals("Shapes:\n", newAnimation.toString());
  }

  /**
   * Cannot add a null IShape to an Animation.
   */
  @Test(expected = NullPointerException.class)
  public void testAddNullIShape() {
    testAnimation.addShape(null);
  }

  /**
   * Cannot add a null ITransformation to an Animation.
   */
  @Test(expected = NullPointerException.class)
  public void testAddNullITransformation() {
    testAnimation.addTransformation(null);
  }

  /**
   * Should be able to add a valid ITransformation.
   */
  @Test
  public void testAddITransformationValid() {
    testAnimation.addTransformation(move);
    assertEquals("Shapes:\n"
            + "Name: A\n"
            + "Type: circle\n"
            + "Center: (50.0,50.0), Radius: 20.0, Color: (100.0,0.0,0.0)\n"
            + "Appears at t=1.0s\n"
            + "Disappears at t=10.0s\n"
            + "\n"
            + "Name: B\n"
            + "Type: oval\n"
            + "Center: (100.0,100.0), X radius: 30.0, Y radius: 30.0, Color: (0.0,100.0,0.0)\n"
            + "Appears at t=2.0s\n"
            + "Disappears at t=10.0s\n"
            + "\n"
            + "Name: C\n"
            + "Type: rectangle\n"
            + "Min corner: (200.0,200.0), Width: 40.0, Height: 40.0, Color: (0.0,0.0,100.0)\n"
            + "Appears at t=5.0s\n"
            + "Disappears at t=15.0s\n"
            + "\n"
            + "Shape A moves from (50.0,50.0) to (100.0,50.0) from t=2.0s to t=9.0s\n",
        testAnimation.toString());
  }

  /**
   * Should be able to add a valid IShape.
   */
  @Test
  public void testAddIShapeValid() {
    testAnimation.addShape(circle);
    assertEquals("Shapes:\n"
        + "Name: A\n"
        + "Type: circle\n"
        + "Center: (50.0,50.0), Radius: 20.0, Color: (100.0,0.0,0.0)\n"
        + "Appears at t=1.0s\n"
        + "Disappears at t=10.0s\n"
        + "\n"
        + "Name: B\n"
        + "Type: oval\n"
        + "Center: (100.0,100.0), X radius: 30.0, Y radius: 30.0, Color: (0.0,100.0,0.0)\n"
        + "Appears at t=2.0s\n"
        + "Disappears at t=10.0s\n"
        + "\n"
        + "Name: C\n"
        + "Type: rectangle\n"
        + "Min corner: (200.0,200.0), Width: 40.0, Height: 40.0, Color: (0.0,0.0,100.0)\n"
        + "Appears at t=5.0s\n"
        + "Disappears at t=15.0s\n"
        + "\n", testAnimation.toString());
  }

  /**
   * Should not be able to add a Shape of the same name as an existing Shape.
   */
  @Test
  public void testAddExistingShape() {
    testAnimation.addShape(circle);
    assertEquals("Shapes:\n"
            + "Name: A\n"
            + "Type: circle\n"
            + "Center: (50.0,50.0), Radius: 20.0, Color: (100.0,0.0,0.0)\n"
            + "Appears at t=1.0s\n"
            + "Disappears at t=10.0s\n"
            + "\n"
            + "Name: B\n"
            + "Type: oval\n"
            + "Center: (100.0,100.0), X radius: 30.0, Y radius: 30.0, Color: (0.0,100.0,0.0)\n"
            + "Appears at t=2.0s\n"
            + "Disappears at t=10.0s\n"
            + "\n"
            + "Name: C\n"
            + "Type: rectangle\n"
            + "Min corner: (200.0,200.0), Width: 40.0, Height: 40.0, Color: (0.0,0.0,100.0)\n"
            + "Appears at t=5.0s\n"
            + "Disappears at t=15.0s\n"
            + "\n",
        testAnimation.toString());
  }

  /**
   * Cannot add a Transformation referencing a Shape that does not exist.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testShapeDoesNotExist() {
    testAnimation.addTransformation(
        TransformFactory.makeTransformColor(2, 3, "E", blue, red));
  }

  /**
   * Cannot add a Transformation that begins before a Shape has appeared.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testTransformBeginsShapeNotAppeared() {
    testAnimation
        .addTransformation(TransformFactory.makeTransformColor(0,
            5, "A", blue, red));
  }

  /**
   * Cannot add a Transformation that begins after a Shape has disappeared.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testTransformBeginAfterDisappear() {
    testAnimation.addTransformation(
        TransformFactory.makeTransformColor(11, 12, "A", blue, red));
  }

  /**
   * Cannot add a Transformation that ends after a Shape has disappeared.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testTransformEndAfterDisappear() {
    testAnimation.addTransformation(
        TransformFactory.makeTransformColor(2, 11, "A", blue, red));
  }

  /**
   * Ensure that two simultaneous but non-conflicting transformations can be applied to the same
   * IShape.
   */
  @Test
  public void testNonConflictingTransform() {
    testAnimation.addTransformation(
        new TransformMove(new Point2D.Double(200, 200), new Point2D.Double(300, 300),
            1, 10, "A",
            ShapeType.CIRCLE));
    testAnimation
        .addTransformation(new TransformColor(1, 10, "A",
            new Color(0, 0, 0), new Color(1, 1, 1)));
    assertEquals("Shapes:\n"
            + "Name: A\n"
            + "Type: circle\n"
            + "Center: (50.0,50.0), Radius: 20.0, Color: (100.0,0.0,0.0)\n"
            + "Appears at t=1.0s\n"
            + "Disappears at t=10.0s\n"
            + "\n"
            + "Name: B\n"
            + "Type: oval\n"
            + "Center: (100.0,100.0), X radius: 30.0, Y radius: 30.0, Color: (0.0,100.0,0.0)\n"
            + "Appears at t=2.0s\n"
            + "Disappears at t=10.0s\n"
            + "\n"
            + "Name: C\n"
            + "Type: rectangle\n"
            + "Min corner: (200.0,200.0), Width: 40.0, Height: 40.0, Color: (0.0,0.0,100.0)\n"
            + "Appears at t=5.0s\n"
            + "Disappears at t=15.0s\n"
            + "\n"
            + "Shape A moves from (200.0,200.0) to (300.0,300.0) from t=1.0s to t=10.0s\n"
            + "Shape A changes color from (0.0,0.0,0.0) to (1.0,1.0,1.0) from t=1.0s to t=10.0s\n",
        testAnimation.toString());
  }

  /**
   * Can add to simultaneous, conflicting Transformations that do not apply to the same IShape.
   */
  @Test
  public void testConflictingTransformDifferentShape() {
    testAnimation.addTransformation(
        new TransformMove(new Point2D.Double(200, 200), new Point2D.Double(300, 300),
            1, 10, "A", ShapeType.CIRCLE));
    testAnimation.addTransformation(
        new TransformMove(new Point2D.Double(200, 200), new Point2D.Double(300, 300),
            5, 15, "C", ShapeType.RECTANGLE));
    assertEquals("Shapes:\n"
            + "Name: A\n"
            + "Type: circle\n"
            + "Center: (50.0,50.0), Radius: 20.0, Color: (100.0,0.0,0.0)\n"
            + "Appears at t=1.0s\n"
            + "Disappears at t=10.0s\n"
            + "\n"
            + "Name: B\n"
            + "Type: oval\n"
            + "Center: (100.0,100.0), X radius: 30.0, Y radius: 30.0, Color: (0.0,100.0,0.0)\n"
            + "Appears at t=2.0s\n"
            + "Disappears at t=10.0s\n"
            + "\n"
            + "Name: C\n"
            + "Type: rectangle\n"
            + "Min corner: (200.0,200.0), Width: 40.0, Height: 40.0, Color: (0.0,0.0,100.0)\n"
            + "Appears at t=5.0s\n"
            + "Disappears at t=15.0s\n"
            + "\n"
            + "Shape A moves from (200.0,200.0) to (300.0,300.0) from t=1.0s to t=10.0s\n"
            + "Shape C moves from (200.0,200.0) to (300.0,300.0) from t=5.0s to t=15.0s\n",
        testAnimation.toString());
  }

  /**
   * Should not be able to add a conflicting TransformScale. In other words, a Shape cannot do two
   * conflicting things at once.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testConflictingTransformScale() {
    testAnimation.addTransformation(scale);
    testAnimation.addTransformation(scale);
  }

  /**
   * Should not be able to add a conflicting TransformColor. In other words, a Shape cannot do two
   * conflicting things at once.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testConflictingTransformColor() {
    testAnimation.addTransformation(color);
    testAnimation.addTransformation(color);
  }

  /**
   * Should not be able to add a conflicting TransformMove. In other words, a Shape cannot do two
   * conflicting things at once.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testConflictingTransformMove() {
    testAnimation.addTransformation(move);
    testAnimation.addTransformation(move);
  }

  /**
   * Animation should be able to generate output formatted for text with speed of 1.
   */
  @Test
  public void testGenerateTextSpeed1() {
    testAnimation.addTransformation(move);
    testAnimation.addTransformation(scale);
    testAnimation.addTransformation(color);
    assertEquals("Shapes:\n"
            + "Name: A\n"
            + "Type: circle\n"
            + "Center: (50.0,50.0), Radius: 20.0, Color: (100.0,0.0,0.0)\n"
            + "Appears at t=1.0s\n"
            + "Disappears at t=10.0s\n"
            + "\n"
            + "Name: B\n"
            + "Type: oval\n"
            + "Center: (100.0,100.0), X radius: 30.0, Y radius: 30.0, Color: (0.0,100.0,0.0)\n"
            + "Appears at t=2.0s\n"
            + "Disappears at t=10.0s\n"
            + "\n"
            + "Name: C\n"
            + "Type: rectangle\n"
            + "Min corner: (200.0,200.0), Width: 40.0, Height: 40.0, Color: (0.0,0.0,100.0)\n"
            + "Appears at t=5.0s\n"
            + "Disappears at t=15.0s\n"
            + "\n"
            + "Shape A moves from (50.0,50.0) to (100.0,50.0) from t=2.0s to t=9.0s\n"
            + "Shape B scales from X radius: 30.0, Y radius: 30.0 to X radius: 60.0,"
            + " Y radius: 60.0 from t=3.0s to t=9.0s\n"
            + "Shape C changes color from (0.0,0.0,100.0) to (100.0,0.0,0.0)"
            + " from t=6.0s to t=14.0s\n",
        testAnimation.generate(OutputType.TEXT, 1));
  }

  /**
   * Animation should be able to generate output formatted for text with speed of 2.
   */
  @Test
  public void testGenerateTextSpeed2() {
    testAnimation.addTransformation(move);
    testAnimation.addTransformation(scale);
    testAnimation.addTransformation(color);
    assertEquals("Shapes:\n"
            + "Name: A\n"
            + "Type: circle\n"
            + "Center: (50.0,50.0), Radius: 20.0, Color: (100.0,0.0,0.0)\n"
            + "Appears at t=0.5s\n"
            + "Disappears at t=5.0s\n"
            + "\n"
            + "Name: B\n"
            + "Type: oval\n"
            + "Center: (100.0,100.0), X radius: 30.0, Y radius: 30.0, Color: (0.0,100.0,0.0)\n"
            + "Appears at t=1.0s\n"
            + "Disappears at t=5.0s\n"
            + "\n"
            + "Name: C\n"
            + "Type: rectangle\n"
            + "Min corner: (200.0,200.0), Width: 40.0, Height: 40.0, Color: (0.0,0.0,100.0)\n"
            + "Appears at t=2.5s\n"
            + "Disappears at t=7.5s\n"
            + "\n"
            + "Shape A moves from (50.0,50.0) to (100.0,50.0) from t=1.0s to t=4.5s\n"
            + "Shape B scales from X radius: 30.0, Y radius: 30.0 to X radius: 60.0,"
            + " Y radius: 60.0 from t=1.5s to t=4.5s\n"
            + "Shape C changes color from (0.0,0.0,100.0) to (100.0,0.0,0.0)"
            + " from t=3.0s to t=7.0s\n",
        testAnimation.generate(OutputType.TEXT, 2));
  }

  /**
   * Animation should be able to generate output formatted for SVG at speed 1.
   */
  @Test
  public void testSVGSpeed1() {
    testAnimation.addTransformation(move);
    testAnimation.addTransformation(scale);
    testAnimation.addTransformation(color);
    assertEquals(
        "<svg width=\"240.0\" height=\"240.0\" version=\"1.1\""
            + " xmlns=\"http://www.w3.org/2000/svg\"> \n"
            + "<circle id=\"A\" cx=\"50\" cy=\"50\" r=\"20\" fill=\"rgb(100.0%,0.0%,0.0%)\""
            + " visibility=\"hidden\" >\n"
            + "  <set attributeName=\"visibility\" attributeType=\"CSS\" to=\"visible\""
            + " begin=\"1000.0ms\" duration=\"9000.0ms\" fill=\"freeze\" />\n"
            + "  <animate attributeName=\"cx\" attributeType=\"CSS\" from=\"50\" to=\"100\""
            + " begin=\"2000.0ms\" dur=\"7000.0ms\" fill=\"freeze\" />\n"
            + "  <animate attributeName=\"cy\" attributeType=\"CSS\" from=\"50\" to=\"50\""
            + " begin=\"2000.0ms\" dur=\"7000.0ms\" fill=\"freeze\" />\n"
            + "</circle>\n"
            + "<ellipse id=\"B\" cx=\"100\" cy=\"100\" rx=\"30\" ry=\"30\""
            + " fill=\"rgb(0.0%,100.0%,0.0%)\" visibility=\"hidden\" >\n"
            + "  <set attributeName=\"visibility\" attributeType=\"CSS\" to=\"visible\""
            + " begin=\"2000.0ms\" duration=\"8000.0ms\" fill=\"freeze\" />\n"
            + "  <animate attributeName=\"rx\" attributeType=\"CSS\" from=\"30\" to=\"60\""
            + " begin=\"3000.0ms\" dur=\"6000.0ms\" fill=\"freeze\" />\n"
            + "  <animate attributeName=\"ry\" attributeType=\"CSS\" from=\"30\" to=\"60\""
            + " begin=\"3000.0ms\" dur=\"6000.0ms\" fill=\"freeze\" />\n"
            + "</ellipse>\n"
            + "<rect id=\"C\" x=\"200\" y=\"200\" width=\"40\" height=\"40\""
            + " fill=\"rgb(0.0%,0.0%,100.0%)\" visibility=\"hidden\" >\n"
            + "  <set attributeName=\"visibility\" attributeType=\"CSS\" to=\"visible\""
            + " begin=\"5000.0ms\" duration=\"10000.0ms\" fill=\"freeze\" />\n"
            + "  <animate attributeName=\"fill\" attributeType=\"CSS\""
            + " from=\"rgb(0.0%,0.0%,100.0%)\" to=\"rgb(100.0%,0.0%,0.0%)\""
            + " begin=\"6000.0ms\" dur=\"8000.0ms\" fill=\"freeze\" />\n"
            + "</rect>\n"
            + "</svg>", testAnimation.generate(OutputType.SVG, 1));
  }

  /**
   * Animation should be able to generate output formatted for SVG at speed 2.
   */
  @Test
  public void testSVGSpeed2() {
    testAnimation.addTransformation(move);
    testAnimation.addTransformation(scale);
    testAnimation.addTransformation(color);
    assertEquals(
        "<svg width=\"240.0\" height=\"240.0\" version=\"1.1\""
            + " xmlns=\"http://www.w3.org/2000/svg\"> \n"
            + "<circle id=\"A\" cx=\"50\" cy=\"50\" r=\"20\" fill=\"rgb(100.0%,0.0%,0.0%)\""
            + " visibility=\"hidden\" >\n"
            + "  <set attributeName=\"visibility\" attributeType=\"CSS\" to=\"visible\""
            + " begin=\"500.0ms\" duration=\"4500.0ms\" fill=\"freeze\" />\n"
            + "  <animate attributeName=\"cx\" attributeType=\"CSS\" from=\"50\" to=\"100\""
            + " begin=\"1000.0ms\" dur=\"3500.0ms\" fill=\"freeze\" />\n"
            + "  <animate attributeName=\"cy\" attributeType=\"CSS\" from=\"50\" to=\"50\""
            + " begin=\"1000.0ms\" dur=\"3500.0ms\" fill=\"freeze\" />\n"
            + "</circle>\n"
            + "<ellipse id=\"B\" cx=\"100\" cy=\"100\" rx=\"30\" ry=\"30\""
            + " fill=\"rgb(0.0%,100.0%,0.0%)\" visibility=\"hidden\" >\n"
            + "  <set attributeName=\"visibility\" attributeType=\"CSS\" to=\"visible\""
            + " begin=\"1000.0ms\" duration=\"4000.0ms\" fill=\"freeze\" />\n"
            + "  <animate attributeName=\"rx\" attributeType=\"CSS\" from=\"30\" to=\"60\""
            + " begin=\"1500.0ms\" dur=\"3000.0ms\" fill=\"freeze\" />\n"
            + "  <animate attributeName=\"ry\" attributeType=\"CSS\" from=\"30\" to=\"60\""
            + " begin=\"1500.0ms\" dur=\"3000.0ms\" fill=\"freeze\" />\n"
            + "</ellipse>\n"
            + "<rect id=\"C\" x=\"200\" y=\"200\" width=\"40\" height=\"40\""
            + " fill=\"rgb(0.0%,0.0%,100.0%)\" visibility=\"hidden\" >\n"
            + "  <set attributeName=\"visibility\" attributeType=\"CSS\" to=\"visible\""
            + " begin=\"2500.0ms\" duration=\"5000.0ms\" fill=\"freeze\" />\n"
            + "  <animate attributeName=\"fill\" attributeType=\"CSS\""
            + " from=\"rgb(0.0%,0.0%,100.0%)\" to=\"rgb(100.0%,0.0%,0.0%)\""
            + " begin=\"3000.0ms\" dur=\"4000.0ms\" fill=\"freeze\" />\n"
            + "</rect>\n"
            + "</svg>", testAnimation.generate(OutputType.SVG, 2));
  }

  /**
   * generate() must be called with a number greater than zero.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testSpeedNotGreaterZero() {
    testAnimation.addTransformation(move);
    testAnimation.addTransformation(scale);
    testAnimation.addTransformation(color);
    testAnimation.generate(OutputType.SVG, -1);
  }

  /**
   * generateFrame() should properly handle Transformations. Transformations are "sticky", and
   * should persist after they end. If two Transformations of the same type affect the same shape,
   * the most recent should apply.
   */
  @Test
  public void testGenerateFrameWithTransformations() {
    Shape circle1 = ShapeFactory.makeShapeCircle("A",
        new Color(20f, 30f, 40f),
        1, 10,
        new Double(25, 50),
        20d);
    Shape oval1 = ShapeFactory.makeShapeOval("B",
        new Color(10f, 20f, 30f),
        2, 18,
        new Double(50d, 100),
        15d, 30d);
    Shape rectangle1 = ShapeFactory.makeShapeRectangle("C",
        new Color(70f, 30f, 10f),
        5, 15,
        new Double(100, 200),
        20d, 40d);
    IAnimation thisAnimation = new Animation();
    thisAnimation.addShape(circle1);
    thisAnimation.addShape(oval1);
    thisAnimation.addShape(rectangle1);
    ITransformation colorA = TransformFactory.makeTransformColor(4, 8, "A",
        new Color(20f, 30f, 40f),
        new Color(40f, 60f, 80f));
    ITransformation scaleB = TransformFactory.makeTransformScale(15, 30,
        30, 60,
        7, 9,
        "B",
        ShapeType.OVAL);
    ITransformation scaleBAgain = TransformFactory.makeTransformScale(30, 60,
        60, 120,
        14, 16,
        "B",
        ShapeType.OVAL);
    ITransformation moveC = TransformFactory.makeTransformMove(
        new Double(100, 200),
        new Double(150, 250),
        10, 14,
        "C",
        ShapeType.RECTANGLE);
    thisAnimation.addTransformation(colorA);
    thisAnimation.addTransformation(scaleB);
    thisAnimation.addTransformation(scaleBAgain);
    thisAnimation.addTransformation(moveC);
    assertEquals("[Shape 25,50, w 20.0, h 20.0, color (20.0,30.0,40.0)\n"
        + ", Shape 50,100, w 15.0, h 30.0, color (10.0,20.0,30.0)\n"
        + "]", thisAnimation.generateFrame(3).toString());
    assertEquals("[Shape 25,50, w 20.0, h 20.0, color (30.0,45.0,60.0)\n"
        + ", Shape 50,100, w 15.0, h 30.0, color (10.0,20.0,30.0)\n"
        + ", Shape 100,200, w 20.0, h 40.0, color (70.0,30.0,10.0)\n"
        + "]", thisAnimation.generateFrame(6).toString());
    assertEquals("[Shape 25,50, w 20.0, h 20.0, color (40.0,60.0,80.0)\n"
        + ", Shape 50,100, w 22.5, h 45.0, color (10.0,20.0,30.0)\n"
        + ", Shape 100,200, w 20.0, h 40.0, color (70.0,30.0,10.0)\n"
        + "]", thisAnimation.generateFrame(8).toString());
    assertEquals("[Shape 50,100, w 30.0, h 60.0, color (10.0,20.0,30.0)\n"
        + ", Shape 125,225, w 20.0, h 40.0, color (70.0,30.0,10.0)\n"
        + "]", thisAnimation.generateFrame(12).toString());
    assertEquals("[Shape 50,100, w 30.0, h 60.0, color (10.0,20.0,30.0)\n"
        + ", Shape 150,250, w 20.0, h 40.0, color (70.0,30.0,10.0)\n"
        + "]", thisAnimation.generateFrame(14).toString());
    assertEquals("[Shape 50,100, w 45.0, h 90.0, color (10.0,20.0,30.0)\n"
            + "]",
        thisAnimation.generateFrame(15).toString());
  }

  /**
   * generateFrame() should generate a frame with proper tweening for movements, scales, and
   * colors.
   */
  @Test
  public void testGenerateFrameNoTransformations() {
    Shape circle1 = ShapeFactory.makeShapeCircle("A",
        new Color(20f, 30f, 40f),
        1, 10,
        new Double(25, 50),
        20d);
    Shape oval1 = ShapeFactory.makeShapeOval("B",
        new Color(10f, 20f, 30f),
        2, 10,
        new Double(50d, 100),
        15d, 30d);
    Shape rectangle1 = ShapeFactory.makeShapeRectangle("C",
        new Color(70f, 30f, 10f),
        5, 15,
        new Double(100, 200),
        20d, 40d);
    IAnimation thisAnimation = new Animation();
    thisAnimation.addShape(circle1);
    thisAnimation.addShape(oval1);
    thisAnimation.addShape(rectangle1);
    assertEquals("[]", thisAnimation.generateFrame(0).toString());
    assertEquals("[Shape 25,50, w 20.0, h 20.0, color (20.0,30.0,40.0)\n"
            + "]",
        thisAnimation.generateFrame(1).toString());
    assertEquals("[Shape 25,50, w 20.0, h 20.0, color (20.0,30.0,40.0)\n"
        + ", Shape 50,100, w 15.0, h 30.0, color (10.0,20.0,30.0)\n"
        + "]", thisAnimation.generateFrame(2).toString());
    assertEquals("[Shape 25,50, w 20.0, h 20.0, color (20.0,30.0,40.0)\n"
        + ", Shape 50,100, w 15.0, h 30.0, color (10.0,20.0,30.0)\n"
        + ", Shape 100,200, w 20.0, h 40.0, color (70.0,30.0,10.0)\n"
        + "]", thisAnimation.generateFrame(5).toString());
    assertEquals("[Shape 100,200, w 20.0, h 40.0, color (70.0,30.0,10.0)\n"
        + "]", thisAnimation.generateFrame(10).toString());
  }

  /**
   * getMaxX() should work properly.
   */
  @Test
  public void getMaxX() {
    testAnimation.addTransformation(scale);
    testAnimation.addTransformation(color);
    ITransformation move2 = TransformFactory
        .makeTransformMove(new Double(50d, 50d),
            new Double(350d, 50d), 2, 9, "A", ShapeType.CIRCLE);
    testAnimation.addTransformation(move2);
    assertEquals(370, testAnimation.getMaxX(), .001);
  }

  /**
   * getMaxY() should work properly.
   */
  @Test
  public void getMaxY() {
    testAnimation.addTransformation(scale);
    testAnimation.addTransformation(color);
    ITransformation move2 = TransformFactory
        .makeTransformMove(new Double(50d, 50d),
            new Double(30d, 300d), 2, 9, "B", ShapeType.OVAL);
    testAnimation.addTransformation(move2);
    assertEquals(330, testAnimation.getMaxY(), .001);
  }
}