package cs5004.animator.view;

/**
 * A Factory class for IAnimationView. Has a single static method parameterized over a String with
 * the desired output type. This was required by the instructions, but I don't think I would have
 * chosen this method otherwise.
 */
public class IAnimationViewFactory {

  /**
   * A Factory method for creating a ViewFile. Prints information to the specified file.
   *
   * @param fileName the name of the File to print to.
   */
  public static IAnimationView makeViewFile(String fileName) {
    return new ViewFile(fileName);
  }

  /**
   * A Factory method for creating a new ViewGUI object. It requires a width and a height for the
   * animation panel of the GUI.
   *
   * @param panelWidth the width size to display the animation.
   * @param panelHeight the height size to display the animation.
   * @return a new ViewGUI.
   */
  public static IAnimationView makeViewGUI(int panelWidth, int panelHeight) {
    return new ViewGUI(panelWidth, panelHeight);
  }

  /**
   * A Factory method for creating a View that will return information to the System.out.
   *
   * @return a new ViewSystemOut.
   */
  public static IAnimationView makeViewSystemOut() {
    return new ViewSystemOut();
  }
}
