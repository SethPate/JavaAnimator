import static org.junit.Assert.assertEquals;

import cs5004.animator.model.Animation;
import cs5004.animator.model.Color;
import cs5004.animator.model.IAnimation;
import cs5004.animator.model.IRenderableShape;
import cs5004.animator.model.OutputType;
import cs5004.animator.model.RenderableFactory;
import cs5004.animator.model.ShapeCircle;
import cs5004.animator.view.IAnimationView;
import cs5004.animator.view.IAnimationViewFactory;
import cs5004.animator.view.ViewFile;
import java.awt.geom.Point2D;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.io.FileUtils;
import org.junit.Before;
import org.junit.Test;

public class IAnimationViewTest {

  private IAnimationView view;

  /**
   * Creates test cases for testing IAnimationView.
   */
  @Before
  public void setUp() {
    List<IRenderableShape> shapeList = new ArrayList<>();
    shapeList
        .add(RenderableFactory.createRenderableRect(50, 60, 40, 80, new Color(100.0f, 0f, 0f)));
  }

  /**
   * IAnimationView should be able to take SVG output and write it to a File.
   */
  @Test
  public void testViewAsSVG() {
    IAnimation animation = new Animation();
    animation.addShape(
        new ShapeCircle("A", new Color(0f, 0f, 100f), 1, 10, new Point2D.Double(20f, 20f), 40));
    view = new ViewFile("output.svg");
    view.render(animation.generate(OutputType.SVG, 1));
    File file = new File("output.svg");
    try {
      assertEquals(
          "<svg width=\"60.0\" height=\"60.0\" version=\"1.1\""
              + " xmlns=\"http://www.w3.org/2000/svg\"> \n"
              + "<circle id=\"A\" cx=\"20\" cy=\"20\" r=\"40\" fill=\"rgb(0.0%,0.0%,100.0%)\""
              + " visibility=\"hidden\" >\n"
              + "  <set attributeName=\"visibility\" attributeType=\"CSS\" to=\"visible\""
              + " begin=\"1000.0ms\" duration=\"9000.0ms\" fill=\"freeze\" />\n"
              + "</circle>\n"
              + "</svg>", FileUtils.readFileToString(file));
    } catch (Exception e) {
      System.err.println(e.getMessage());
    }
  }

  /**
   * IAnimationView should be able to print a String to a file.
   */
  @Test
  public void renderFile() {
    view = IAnimationViewFactory.makeViewFile("output.txt");
    view.render("print this to output.txt");
    File file = new File("output.txt");
    try {
      assertEquals("print this to output.txt", FileUtils.readFileToString(file));
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }
  }

  /**
   * IAnimationView should be able to print a String to System.out (can't test)
   */

}