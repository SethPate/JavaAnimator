package cs5004.animator.view;

import java.io.File;
import java.util.Objects;
import org.apache.commons.io.FileUtils;

/**
 * An implementation of the IAnimationView interface. ViewFile will print to a fileName, as defined
 * by a String.
 */

public class ViewFile implements IAnimationView {

  private String fileName;

  /**
   * Creates a new ViewFile associated with a fileName.
   *
   * @param fileName a String holding the value of the output location.
   */
  public ViewFile(String fileName) {
    Objects.requireNonNull(fileName);
    this.fileName = fileName;
  }

  /**
   * Takes a String of animation instructions that the model has formatted to the desired spec (for
   * example, text or SVG). Render() writes this String to a File named fileName.
   *
   * @param animateThis a String in the correct format to be animated.
   * @throws IllegalArgumentException if called with null parameter.
   */
  public void render(Object animateThis) {
    Objects.requireNonNull(animateThis);
    File file = new File(fileName);
    try {
      FileUtils.writeStringToFile(file, (String) animateThis, "utf-8");
    } catch (Exception e) {
      System.err.print("error writing animation to file");
    }
  }

  /**
   * Returns this view as a String. Only prints its fileName.
   *
   * @return a String of the fileName.
   */
  @Override
  public String toString() {
    return new String(this.fileName);
  }
}