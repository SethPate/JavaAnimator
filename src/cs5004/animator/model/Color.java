package cs5004.animator.model;

/**
 * A class representing a Color. Contains three float values for red, green, and blue, ranging from
 * 0-100.
 */
public class Color {

  private final float red;
  private final float green;
  private final float blue;

  /**
   * Creates a new instance of the Color class. Stores values as percentages from 0 to 100%.
   *
   * @param red A float from 0-100.
   * @param green A float from 0-100.
   * @param blue A float from 0-100.
   * @throws IllegalArgumentException if a color variable is < 0 or > 100.
   */
  public Color(float red, float green, float blue) throws IllegalArgumentException {
    if (red < 0 || red > 100) {
      throw new IllegalArgumentException("red value must be between 0 and 100.");
    }
    if (green < 0 || green > 100) {
      throw new IllegalArgumentException("green value must be between 0 and 100.");
    }
    if (blue < 0 || blue > 100) {
      throw new IllegalArgumentException("blue value must be between 0 and 100.");
    }
    this.red = red;
    this.green = green;
    this.blue = blue;
  }

  /**
   * Gets the value of the red variable of this Color.
   *
   * @return an integer containing the value.
   */
  public float getRed() {
    return this.red;
  }

  /**
   * Gets the value of the green variable of this Color.
   *
   * @return an integer containing the value.
   */
  public float getGreen() {
    return this.green;
  }

  /**
   * Gets the value of the blue variable of this Color.
   *
   * @return an integer containing the value.
   */
  public float getBlue() {
    return this.blue;
  }

  /**
   * Displays the values of the Color as a String.
   *
   * @return a String containing all three values of this Color.
   */
  @Override
  public String toString() {
    return String.format("(%.1f,%.1f,%.1f)", red, green, blue);
  }
}