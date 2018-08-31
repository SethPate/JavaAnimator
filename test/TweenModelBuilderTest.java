import static org.junit.Assert.assertEquals;

import cs5004.animator.model.IAnimation;
import cs5004.animator.model.OutputType;
import cs5004.animator.util.TweenModelBuilderImpl;
import org.junit.Before;
import org.junit.Test;

/**
 * A junit test for the TweenModelBuilder. This Class should be able to translate from the
 * AnimationFileReader class to the model we use here, IAnimation.
 */
public class TweenModelBuilderTest {

  private TweenModelBuilderImpl builder;
  private IAnimation animation;

  /**
   * Creates an empty TweenModelBuilder for testing.
   */
  @Before
  public void setUp() {
    builder = new TweenModelBuilderImpl();
  }

  /**
   * Should be able to create an empty Object.
   */
  @Test
  public void testEmpty() {
    assertEquals("", builder.toString());
  }

  /**
   * Should add a valid ShapeOval to the IAnimation.
   */
  @Test
  public void addOval() {
    builder.addOval("A", 50f, 60f, 20f, 30f,
        1f, 0f, 0f, 1, 10);
    animation = builder.build();
    assertEquals("Shapes:\n"
            + "Name: A\n"
            + "Type: oval\n"
            + "Center: (50.0,60.0), X radius: 20.0, Y radius: 30.0, Color: (100.0,0.0,0.0)\n"
            + "Appears at t=1.0s\n"
            + "Disappears at t=10.0s\n"
            + "\n",
        animation.generate(OutputType.TEXT, 1));
  }

  /**
   * Should add a valid ShapeCircle to the IAnimation.
   */
  @Test
  public void addCircle() {
    builder.addOval("A", 50f, 60f, 20f, 20f,
        .2f, .4f, .6f, 1, 10);
    animation = builder.build();
    assertEquals("Shapes:\n"
            + "Name: A\n"
            + "Type: circle\n"
            + "Center: (50.0,60.0), Radius: 20.0, Color: (20.0,40.0,60.0)\n"
            + "Appears at t=1.0s\n"
            + "Disappears at t=10.0s\n"
            + "\n",
        animation.generate(OutputType.TEXT, 1));
  }

  /**
   * Should add a valid ShapeRectangle to the IAnimation.
   */
  @Test
  public void addRectangle() {
    builder.addRectangle("A", 50f, 60f, 20f, 30f,
        0f, 0f, 1f, 1, 10);
    animation = builder.build();
    assertEquals("Shapes:\n"
            + "Name: A\n"
            + "Type: rectangle\n"
            + "Min corner: (50.0,60.0), Width: 20.0, Height: 30.0, Color: (0.0,0.0,100.0)\n"
            + "Appears at t=1.0s\n"
            + "Disappears at t=10.0s\n"
            + "\n",
        animation.generate(OutputType.TEXT, 1));
  }

  /**
   * Should fail to add an IShape if an IShape of that name has already been added.
   */
  @Test
  public void addIShapeDuplicateName() {
    builder.addOval("A", 50f, 60f, 20f, 30f,
        1f, 0f, 0f, 1, 10);
    builder.addOval("A", 50f, 60f, 20f, 30f,
        1f, 0f, 0f, 1, 10);
    animation = builder.build();
    assertEquals("Shapes:\n"
            + "Name: A\n"
            + "Type: oval\n"
            + "Center: (50.0,60.0), X radius: 20.0, Y radius: 30.0, Color: (100.0,0.0,0.0)\n"
            + "Appears at t=1.0s\n"
            + "Disappears at t=10.0s\n"
            + "\n",
        animation.generate(OutputType.TEXT, 1));
  }

  /**
   * Should add a valid TransfromMove to the IAnimation.
   */
  @Test
  public void addMove() {
    builder.addOval("A", 50f, 60f, 20f, 30f, 0f, 1f, 0f, 1, 10);
    builder.addMove("A", 50f, 60f, 60f, 70f, 2, 10);
    animation = builder.build();
    assertEquals("Shapes:\n"
            + "Name: A\n"
            + "Type: oval\n"
            + "Center: (50.0,60.0), X radius: 20.0, Y radius: 30.0, Color: (0.0,100.0,0.0)\n"
            + "Appears at t=1.0s\n"
            + "Disappears at t=10.0s\n"
            + "\n"
            + "Shape A moves from (50.0,60.0) to (60.0,70.0) from t=2.0s to t=10.0s\n",
        animation.generate(OutputType.TEXT, 1));
  }

  /**
   * Should add a valid TransformColor to the IAnimation.
   */
  @Test
  public void addColorChange() {
    builder.addOval("A", 50f, 60f, 20f, 30f, .2f, .4f, .6f, 1, 10);
    builder.addColorChange("A", .2f, .4f, .6f, .3f, .5f, .7f, 2, 10);
    animation = builder.build();
    assertEquals("Shapes:\n"
            + "Name: A\n"
            + "Type: oval\n"
            + "Center: (50.0,60.0), X radius: 20.0, Y radius: 30.0, Color: (20.0,40.0,60.0)\n"
            + "Appears at t=1.0s\n"
            + "Disappears at t=10.0s\n"
            + "\n"
            + "Shape A changes color from (20.0,40.0,60.0) to (30.0,50.0,70.0)"
            + " from t=2.0s to t=10.0s\n",
        animation.generate(OutputType.TEXT, 1));
  }

  /**
   * Should add a valid TransformScale for a ShapeCircle to the IAnimation.
   */
  @Test
  public void addScaleToChangeCircle() {
    builder.addOval("A", 50f, 60f, 20f, 20f, 0f, .5f, .2f, 1, 10);
    builder.addScaleToChange("A", 50f, 60f, 60f, 70f, 2, 10);
    animation = builder.build();
    assertEquals("Shapes:\n"
            + "Name: A\n"
            + "Type: circle\n"
            + "Center: (50.0,60.0), Radius: 20.0, Color: (0.0,50.0,20.0)\n"
            + "Appears at t=1.0s\n"
            + "Disappears at t=10.0s\n"
            + "\n"
            + "Shape A scales from Radius: 50.0 to Radius: 60.0 from t=2.0s to t=10.0s\n",
        animation.generate(OutputType.TEXT, 1));
  }

  /**
   * Should add a valid TransformScaleOval to the IAnimation.
   */
  @Test
  public void addScaleToChangeOval() {
    builder.addOval("A", 50f, 60f, 20f, 30f, 0f, 1f, 0f, 1, 10);
    builder.addScaleToChange("A", 50f, 60f, 60f, 70f, 2, 10);
    animation = builder.build();
    assertEquals("Shapes:\n"
            + "Name: A\n"
            + "Type: oval\n"
            + "Center: (50.0,60.0), X radius: 20.0, Y radius: 30.0, Color: (0.0,100.0,0.0)\n"
            + "Appears at t=1.0s\n"
            + "Disappears at t=10.0s\n"
            + "\n"
            + "Shape A scales from X radius: 50.0, Y radius: 60.0 to X radius: 60.0, Y radius: 70.0"
            + " from t=2.0s to t=10.0s\n",
        animation.generate(OutputType.TEXT, 1));
  }

  /**
   * Should add a valid TransformScaleRect to the IAnimation.
   */
  @Test
  public void addScaleToChangeRect() {
    builder.addRectangle("A", 50f, 60f, 20f, 30f, 0f, 1f, 0f, 1, 10);
    builder.addScaleToChange("A", 50f, 60f, 60f, 70f, 2, 10);
    animation = builder.build();
    assertEquals("Shapes:\n"
            + "Name: A\n"
            + "Type: rectangle\n"
            + "Min corner: (50.0,60.0), Width: 20.0, Height: 30.0, Color: (0.0,100.0,0.0)\n"
            + "Appears at t=1.0s\n"
            + "Disappears at t=10.0s\n"
            + "\n"
            + "Shape A scales from Width: 50.0, Height: 60.0 to Width: 60.0, Height: 70.0"
            + " from t=2.0s to t=10.0s\n",
        animation.generate(OutputType.TEXT, 1));
  }

  /**
   * Should add a valid ITransformation even if the name case is different.
   */
  @Test
  public void addTransformationNameCase() {
    builder.addRectangle("A", 50f, 60f, 20f, 30f, 0f, 1f, 0f, 1, 10);
    builder.addScaleToChange("a", 50f, 60f, 60f, 70f, 2, 10);
    animation = builder.build();
    assertEquals("Shapes:\n"
            + "Name: A\n"
            + "Type: rectangle\n"
            + "Min corner: (50.0,60.0), Width: 20.0, Height: 30.0, Color: (0.0,100.0,0.0)\n"
            + "Appears at t=1.0s\n"
            + "Disappears at t=10.0s\n"
            + "\n"
            + "Shape A scales from Width: 50.0, Height: 60.0 to Width: 60.0, Height: 70.0"
            + " from t=2.0s to t=10.0s\n",
        animation.generate(OutputType.TEXT, 1));
  }

  /**
   * Should fail to add an ITransformation if the Shape does not exist.
   */
  @Test
  public void addTransformationDoesNotExist() {
    builder.addRectangle("A", 50f, 60f, 20f, 30f, 0f, 1f, 0f, 1, 10);
    builder.addScaleToChange("B", 50f, 60f, 60f, 70f, 2, 10);
    animation = builder.build();
    assertEquals("Shapes:\n"
            + "Name: A\n"
            + "Type: rectangle\n"
            + "Min corner: (50.0,60.0), Width: 20.0, Height: 30.0, Color: (0.0,100.0,0.0)\n"
            + "Appears at t=1.0s\n"
            + "Disappears at t=10.0s\n"
            + "\n",
        animation.generate(OutputType.TEXT, 1));
  }
}