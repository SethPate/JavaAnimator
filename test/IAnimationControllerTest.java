import static org.junit.Assert.assertEquals;

import cs5004.animator.controller.AnimationController;
import cs5004.animator.controller.AnimationControllerGUI;
import cs5004.animator.controller.IAnimationController;
import cs5004.animator.model.Animation;
import cs5004.animator.model.Color;
import cs5004.animator.model.IAnimation;
import cs5004.animator.model.OutputType;
import cs5004.animator.model.ShapeCircle;
import cs5004.animator.view.ViewFile;
import cs5004.animator.view.IAnimationView;
import cs5004.animator.view.ViewSystemOut;
import java.awt.geom.Point2D;
import java.io.File;
import org.apache.commons.io.FileUtils;
import org.junit.Before;
import org.junit.Test;

/**
 * A Junit test for the IAnimationController class. The AnimationController doesn't do much except
 * run the program without hitting an error.
 */

public class IAnimationControllerTest {

  private IAnimationController controller;
  private IAnimation animation;

  /**
   * Creates test cases for the IAnimationController test.
   */
  @Before
  public void setUp() {
    animation = new Animation();
  }

  /**
   * Should be able to create an empty IAnimationController.
   */
  @Test
  public void createEmpty() {
    IAnimationView view = new ViewFile("");
    controller = new AnimationController(animation, view, 1, OutputType.TEXT);
    assertEquals("Shapes:\n"
        + "\n"
        + "\n"
        + "1\n"
        + "TEXT\n", controller.toString());
  }

  /**
   * Should hit an exception if attempting to initialize an IAnimationController with a null
   * object.
   */
  @Test(expected = NullPointerException.class)
  public void constructWithNull() {
    controller = new AnimationController(null, null, 1, null);
  }

  /**
   * Should be able to make the request for format to a model, and then send that information to a
   * ViewFile output.
   */
  @Test
  public void run() {
    animation.addShape(
        new ShapeCircle("A", new Color(0f, 0f, 0f), 1, 10,
            new Point2D.Double(40d, 40d), 10d));

    IAnimationView view = new ViewFile("output.txt");

    controller = new AnimationController(animation, view, 1, OutputType.TEXT);

    controller.run();

    File file = new File("output.txt");
    try {
      assertEquals("Shapes:\n"
              + "Name: A\n"
              + "Type: circle\n"
              + "Center: (40.0,40.0), Radius: 10.0, Color: (0.0,0.0,0.0)\n"
              + "Appears at t=1.0s\n"
              + "Disappears at t=10.0s\n"
              + "\n",
          FileUtils.readFileToString(file));
    } catch (Exception e) {
      throw new IllegalArgumentException();
    }
  }

  /**
   * saveSVG should save an SVG to a file at a given speed.
   */
  @Test
  public void testSaveSVG() {
    animation.addShape(
        new ShapeCircle("A", new Color(0f, 0f, 0f), 1, 10,
            new Point2D.Double(40d, 40d), 10d));
    IAnimationView view = new ViewSystemOut();
    AnimationControllerGUI controllerGUI = new AnimationControllerGUI(animation, view, 1,
        OutputType.SVG);
    controllerGUI.saveSVG("savefile.txt");
    File file = new File("savefile.txt");
    try {
      assertEquals(
          "<svg width=\"50.0\" height=\"50.0\" version=\"1.1\""
              + " xmlns=\"http://www.w3.org/2000/svg\"> \n"
              + "<circle id=\"A\" cx=\"40\" cy=\"40\" r=\"10\" fill=\"rgb(0.0%,0.0%,0.0%)\""
              + " visibility=\"hidden\" >\n"
              + "  <set attributeName=\"visibility\" attributeType=\"CSS\" to=\"visible\""
              + " begin=\"1000.0ms\" duration=\"9000.0ms\" fill=\"freeze\" />\n"
              + "</circle>\n"
              + "</svg>", FileUtils.readFileToString(file));
    } catch (Exception e) {
      System.err.println((e.getMessage()));
    }
  }

}