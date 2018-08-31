package cs5004.animator.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Map;
import java.util.Scanner;

/**
 * This class represents a file reader for the animation file. This reads in the file in the
 * prescribed file format, and relies on a model builder interface. The user of this class should
 * create a model builder that implements this interface.
 */

public class AnimationFileReader {

  /**
   * Read the animation file and use the builder to build a model.
   *
   * @param fileName the path of the file to be read
   * @param builder the builder used to build the model
   * @param <IAnimation> the type of model
   * @return the model
   * @throws FileNotFoundException if the specified file cannot be read
   * @throws InputMismatchException if some data value is not of the expected type
   * @throws IllegalStateException if an illegal token is read from the file
   */
  public <IAnimation> IAnimation readFile(String fileName, TweenModelBuilder<IAnimation> builder)
      throws
      FileNotFoundException, IllegalStateException, InputMismatchException {
    Scanner sc;

    sc = new Scanner(new FileInputStream(fileName));

    while (sc.hasNext()) {
      String command = sc.next();
      ShapeInfo shapeInfo;
      switch (command) {
        case "rectangle":
          RectangleInfo rinfo = readRectangleInfo(sc);
          builder.addRectangle(
              rinfo.getName(),
              rinfo.getX(), rinfo.getY(),
              rinfo.getWidth(), rinfo.getHeight(),
              rinfo.getR(), rinfo.getG(), rinfo.getB(),
              rinfo.getStart(), rinfo.getEnd());
          break;
        case "oval":
          OvalInfo cinfo = readOvalInfo(sc);
          builder.addOval(
              cinfo.getName(),
              cinfo.getX(), cinfo.getY(),
              cinfo.getXRadius(), cinfo.getYRadius(),
              cinfo.getR(), cinfo.getG(), cinfo.getB(),
              cinfo.getStart(), cinfo.getEnd());
          break;
        case "move":
          MoveInfo minfo = readMoveInfo(sc);
          builder.addMove(
              minfo.getName(),
              minfo.getFromX(),
              minfo.getFromY(),
              minfo.getToX(),
              minfo.getToY(),
              minfo.getStart(),
              minfo.getEnd());
          break;
        case "change-color":
          ChangeColorInfo colorInfo = readChangeColorInfo(sc);
          builder.addColorChange(colorInfo.name,
              colorInfo.getFromR(),
              colorInfo.getFromG(),
              colorInfo.getFromB(),
              colorInfo.getToR(),
              colorInfo.getToG(),
              colorInfo.getToB(),
              colorInfo.getStart(),
              colorInfo.getEnd());
          break;
        case "scale":
          ScaleByInfo scaleByInfo = readScaleByInfo(sc);
          builder.addScaleToChange(scaleByInfo.name,
              scaleByInfo.getFromXScale(),
              scaleByInfo.getFromYScale(),
              scaleByInfo.getToXScale(),
              scaleByInfo.getToYScale(),
              scaleByInfo.getStart(),
              scaleByInfo.getEnd());
          break;
        default:
          throw new IllegalStateException("Unidentified token " + command + " "
              + "read from file");

      }
    }
    return builder.build();
  }

  /**
   * Reads the parameters for a Rect object from the input text.
   *
   * @param sc a Scanner for the input text.
   * @return Information for a new Rect object.
   * @throws IllegalStateException if incorrect text is passed to it.
   * @throws InputMismatchException if error in parsing.
   */
  private RectangleInfo readRectangleInfo(Scanner sc) throws
      IllegalStateException, InputMismatchException {
    RectangleInfo info = new RectangleInfo();

    while (!info.isAllInitialized()) {
      String command = sc.next();
      switch (command) {
        case "min-x":
          info.setX(sc.nextFloat());
          break;
        case "min-y":
          info.setY(sc.nextFloat());
          break;
        case "width":
          info.setWidth(sc.nextFloat());
          break;
        case "height":
          info.setHeight(sc.nextFloat());
          break;
        case "color":
          info.setR(sc.nextFloat());
          info.setG(sc.nextFloat());
          info.setB(sc.nextFloat());
          break;
        case "name":
          info.setName(sc.next());
          break;
        case "from":
          info.setStart(sc.nextInt());
          break;
        case "to":
          info.setEnd(sc.nextInt());
          break;
        default:
          throw new IllegalStateException("Invalid attribute " + command + " for "
              + "rectangle");
      }
    }

    return info;
  }

  /**
   * Reads the parameters for a ShapeOval object from the input text.
   *
   * @param sc a Scanner for the input text.
   * @return Information for a new ShapeOval object.
   * @throws IllegalStateException if incorrect text is passed to it.
   * @throws InputMismatchException if error in parsing.
   */
  private OvalInfo readOvalInfo(Scanner sc) throws
      IllegalStateException, InputMismatchException {
    OvalInfo info = new OvalInfo();

    while (!info.isAllInitialized()) {
      String command = sc.next();
      switch (command) {
        case "center-x":
          info.setX(sc.nextFloat());
          break;
        case "center-y":
          info.setY(sc.nextFloat());
          break;
        case "x-radius":
          info.setXRadius(sc.nextFloat());
          break;
        case "y-radius":
          info.setYRadius(sc.nextFloat());
          break;
        case "color":
          info.setR(sc.nextFloat());
          info.setG(sc.nextFloat());
          info.setB(sc.nextFloat());
          break;
        case "name":
          info.setName(sc.next());
          break;
        case "from":
          info.setStart(sc.nextInt());
          break;
        case "to":
          info.setEnd(sc.nextInt());
          break;
        default:
          throw new IllegalStateException("Invalid attribute " + command + " for "
              + "oval");
      }
    }

    return info;
  }

  /**
   * Reads the parameters for a TransformMove object from the input text.
   *
   * @param sc a Scanner for the input text.
   * @return Information for a new TransfromMove object.
   * @throws IllegalStateException if incorrect text is passed to it.
   * @throws InputMismatchException if error in parsing.
   */
  private MoveInfo readMoveInfo(Scanner sc) throws
      IllegalStateException, InputMismatchException {
    MoveInfo info = new MoveInfo();

    while (!info.isAllInitialized()) {
      String command = sc.next();
      switch (command) {
        case "moveto":
          info.setFromX(sc.nextFloat());
          info.setFromY(sc.nextFloat());
          info.setToX(sc.nextFloat());
          info.setToY(sc.nextFloat());
          break;
        case "name":
          info.setName(sc.next());
          break;
        case "from":
          info.setStart(sc.nextInt());
          break;
        case "to":
          info.setEnd(sc.nextInt());
          break;
        default:
          throw new IllegalStateException("Invalid attribute " + command + " for "
              + "move");
      }
    }

    return info;
  }

  /**
   * Reads the parameters for a TransformColor object from the input text.
   *
   * @param sc a Scanner for the input text.
   * @return Information for a new TransformColor object.
   * @throws IllegalStateException if incorrect text is passed to it.
   * @throws InputMismatchException if error in parsing.
   */
  private ChangeColorInfo readChangeColorInfo(Scanner sc) throws
      IllegalStateException, InputMismatchException {
    ChangeColorInfo info = new ChangeColorInfo();

    while (!info.isAllInitialized()) {
      String command = sc.next();
      switch (command) {
        case "colorto":
          info.setFromR(sc.nextFloat());
          info.setFromG(sc.nextFloat());
          info.setFromB(sc.nextFloat());
          info.setToR(sc.nextFloat());
          info.setToG(sc.nextFloat());
          info.setToB(sc.nextFloat());
          break;
        case "name":
          info.setName(sc.next());
          break;
        case "from":
          info.setStart(sc.nextInt());
          break;
        case "to":
          info.setEnd(sc.nextInt());
          break;
        default:
          throw new IllegalStateException("Invalid attribute " + command + " for "
              + "change-color");
      }
    }

    return info;
  }

  /**
   * Reads the parameters for a TransfromScale(ShapeType) object from the input text.
   *
   * @param sc a Scanner for the input text.
   * @return Information for a new TransformScale(ShapeType) object.
   * @throws IllegalStateException if incorrect text is passed to it.
   * @throws InputMismatchException if error in parsing.
   */
  private ScaleByInfo readScaleByInfo(Scanner sc) throws
      IllegalStateException, InputMismatchException {
    ScaleByInfo info = new ScaleByInfo();

    while (!info.isAllInitialized()) {
      String command = sc.next();
      switch (command) {
        case "scaleto":
          info.setFromXScale(sc.nextFloat());
          info.setFromYScale(sc.nextFloat());
          info.setToXScale(sc.nextFloat());
          info.setToYScale(sc.nextFloat());
          break;
        case "name":
          info.setName(sc.next());
          break;
        case "from":
          info.setStart(sc.nextInt());
          break;
        case "to":
          info.setEnd(sc.nextInt());
          break;
        default:
          throw new IllegalStateException("Invalid attribute " + command + " for "
              + "scale-to");
      }
    }

    return info;
  }

  /**
   * A private class that I do not really understand. I think Shapes and Transformations are
   * Inputables.
   */
  class Inputable {

    protected Map<String, Boolean> valueFlags;

    /**
     * Constructs a new instance of the Inputable class.
     */
    public Inputable() {
      valueFlags = new HashMap<>();

    }

    /**
     * Determines whether every entry in the map has been set.
     *
     * @return true if all have been set, false otherwise.
     */
    public boolean isAllInitialized() {
      for (Map.Entry<String, Boolean> entry : valueFlags.entrySet()) {
        if (!entry.getValue()) {
          return false;
        }
      }
      return true;
    }
  }

  /**
   * A subclass of Inputable representing the information of a Shape.
   */
  class ShapeInfo extends Inputable {

    private String name;
    private float r;
    private float g;
    private float b;
    private int start;
    private int end;

    /**
     * Creates a new instance of the ShapeInfo class.
     */
    ShapeInfo() {
      super();
      valueFlags.put("name", false);
      valueFlags.put("r", false);
      valueFlags.put("g", false);
      valueFlags.put("b", false);
      valueFlags.put("start", false);
      valueFlags.put("end", false);
    }

    /**
     * Sets the name of the ShapeInfo.
     *
     * @param name the name of the IShape.
     */
    void setName(String name) {
      this.name = name;
      valueFlags.replace("name", true);
    }

    /**
     * Sets the r value of the ShapeInfo.
     *
     * @param r The r value of the IShape's Color.
     */
    void setR(float r) {
      this.r = r;
      valueFlags.replace("r", true);
    }

    /**
     * Sets the g value of the ShapeInfo.
     *
     * @param g The g value of the IShape's Color.
     */
    void setG(float g) {
      this.g = g;
      valueFlags.replace("g", true);
    }

    /**
     * Sets the b value of the ShapeInfo.
     *
     * @param b The b value of the IShape's Color.
     */
    void setB(float b) {
      this.b = b;
      valueFlags.replace("b", true);
    }

    /**
     * Sets the start time of the IShape.
     *
     * @param start the time in ticks for the IShape to start.
     */
    void setStart(int start) {
      this.start = start;
      valueFlags.replace("start", true);
    }

    /**
     * Sets the end time of the IShape.
     *
     * @param end the time in ticks for the IShape to end.
     */
    void setEnd(int end) {
      this.end = end;
      valueFlags.replace("end", true);
    }

    /**
     * Gets the r value of the IShape's color.
     *
     * @return the r value of the IShape's color.
     */
    float getR() {
      return r;
    }

    /**
     * Gets the g value of the IShape's color.
     *
     * @return the g value of the IShape's color.
     */
    float getG() {
      return g;
    }

    /**
     * Gets the b value of the IShape's color.
     *
     * @return the b value of the IShape's color.
     */
    float getB() {
      return b;
    }

    /**
     * Gets the name of the IShape.
     *
     * @return a String with the name of the IShape.
     */
    String getName() {
      return name;
    }

    /**
     * Gets the time at which the IShape starts.
     *
     * @return the time in ticks.
     */
    public int getStart() {
      return start;
    }

    /**
     * Gets the time at which the IShape ends.
     *
     * @return the time in ticks.
     */
    public int getEnd() {
      return end;
    }
  }

  /**
   * A subclass of ShapeInfo storing formation about Rectangles.
   */
  class RectangleInfo extends ShapeInfo {

    private float x;
    private float y;
    private float width;
    private float height;

    /**
     * Creates a new instance of this class.
     */
    RectangleInfo() {
      super();
      valueFlags.put("x", false);
      valueFlags.put("y", false);
      valueFlags.put("width", false);
      valueFlags.put("height", false);
    }

    /**
     * Sets the X coordinate of the Rect.
     *
     * @param x the x coordinate.
     */
    void setX(float x) {
      this.x = x;
      valueFlags.replace("x", true);
    }

    /**
     * Sets the y coordinate of the Rect.
     *
     * @param y the y coordinate.
     */
    void setY(float y) {
      this.y = y;
      valueFlags.replace("y", true);
    }

    /**
     * Sets the width of the Rect.
     *
     * @param width the width.
     */
    void setWidth(float width) {
      this.width = width;
      valueFlags.replace("width", true);
    }

    /**
     * Sets the height of the Rect.
     *
     * @param height the height.
     */
    void setHeight(float height) {
      this.height = height;
      valueFlags.replace("height", true);
    }

    /**
     * Gets the x coordinate.
     *
     * @return the x coordinate.
     */
    float getX() {
      return x;
    }

    /**
     * Gets the y coordinate.
     *
     * @return the y coordinate.
     */
    float getY() {
      return y;
    }

    /**
     * gets the width.
     *
     * @return the width.
     */
    float getWidth() {
      return width;
    }

    /**
     * Gets the height.
     *
     * @return the height.
     */
    float getHeight() {
      return height;
    }
  }

  /**
   * A Subclass of ShapeInfo storing information about an Oal.
   */
  class OvalInfo extends ShapeInfo {

    private float cx;
    private float cy;
    private float xradius;
    private float yradius;

    /**
     * Creates a new instance of the class.
     */
    OvalInfo() {
      super();
      valueFlags.put("cx", false);
      valueFlags.put("cy", false);
      valueFlags.put("xradius", false);
      valueFlags.put("yradius", false);
    }

    /**
     * Sets the x coordinate of the center.
     *
     * @param x the x coord.
     */
    void setX(float x) {
      this.cx = x;
      valueFlags.replace("cx", true);
    }

    /**
     * Sets the y coordinate of the center.
     *
     * @param y the y coordinate.
     */
    void setY(float y) {
      this.cy = y;
      valueFlags.replace("cy", true);
    }

    /**
     * Sets the x radius of the oval.
     *
     * @param radius the x radius.
     */
    void setXRadius(float radius) {
      this.xradius = radius;
      valueFlags.replace("xradius", true);
    }

    /**
     * Sets the y radius of the oval.
     *
     * @param radius the y radius.
     */
    void setYRadius(float radius) {
      this.yradius = radius;
      valueFlags.replace("yradius", true);
    }

    /**
     * Gets the x coordinate of the center.
     *
     * @return the x coordinate.
     */
    float getX() {
      return cx;
    }

    /**
     * Gets the y coordinate of the center.
     *
     * @return the y coordinate.
     */
    float getY() {
      return cy;
    }

    /**
     * Gets the x radius of the oval.
     *
     * @return the x radius.
     */
    float getXRadius() {
      return xradius;
    }

    /**
     * Gets the y radius of the oval.
     *
     * @return the y radius.
     */
    float getYRadius() {
      return yradius;
    }

  }

  /**
   * A subclass of Inputable storing information about a TransformMove.
   */
  class MoveInfo extends Inputable {

    private String name;
    private float fromX;
    private float fromY;
    private float toX;
    private float toY;
    private int start;
    private int end;

    /**
     * Construt a new instance of this class.
     */
    MoveInfo() {
      super();

      valueFlags.put("name", false);
      valueFlags.put("fromx", false);
      valueFlags.put("fromy", false);
      valueFlags.put("tox", false);
      valueFlags.put("toy", false);
      valueFlags.put("start", false);
      valueFlags.put("end", false);

    }

    /**
     * Sets the name of the TransformMove.
     *
     * @param name the name.
     */
    void setName(String name) {
      this.name = name;
      valueFlags.replace("name", true);
    }

    /**
     * Sets the x of the from coordinate.
     *
     * @param x the x coord.
     */
    void setFromX(float x) {
      this.fromX = x;
      valueFlags.replace("fromx", true);
    }

    /**
     * Sets the y coord of the from position.
     *
     * @param y the y coord.
     */
    void setFromY(float y) {
      this.fromY = y;
      valueFlags.replace("fromy", true);
    }

    /**
     * Sets the x coord of the to position.
     *
     * @param x the x coord.
     */
    void setToX(float x) {
      this.toX = x;
      valueFlags.replace("tox", true);
    }

    /**
     * Sets the y coordof the to position.
     *
     * @param y the y coord.
     */
    void setToY(float y) {
      this.toY = y;
      valueFlags.replace("toy", true);
    }

    /**
     * Sets the start time of the TransformMove.
     *
     * @param start the start.
     */
    void setStart(int start) {
      this.start = start;
      valueFlags.replace("start", true);
    }

    /**
     * Sets the end time of the Transformmove.
     *
     * @param end the end time.
     */
    void setEnd(int end) {
      this.end = end;
      valueFlags.replace("end", true);
    }

    /**
     * Gets the shape name.
     *
     * @return the shape name.
     */
    String getName() {
      return name;
    }

    /**
     * Gets the x coordinate of the from position..
     *
     * @return the x coord.
     */
    float getFromX() {
      return fromX;
    }

    /**
     * Gets the y coordinate of the from position.
     *
     * @return the y coordinate.
     */
    float getFromY() {
      return fromY;
    }

    /**
     * Gets the x coordinate of the t position.
     *
     * @return the x coordinate.
     */
    float getToX() {
      return toX;
    }

    /**
     * Gets the y coordinate of the to position.
     *
     * @return the y coordinate.
     */
    float getToY() {
      return toY;
    }

    /**
     * Gets the time at which this Transform starts.
     *
     * @return the start time.
     */
    int getStart() {
      return start;
    }

    /**
     * Gets the time at which this Transform ends.
     *
     * @return the end time.
     */
    int getEnd() {
      return end;
    }
  }

  /**
   * A subclass of inputable representing a new Transform Color.
   */
  class ChangeColorInfo extends Inputable {

    private String name;
    private float fromR;
    private float fromG;
    private float fromB;
    private float toR;
    private float toG;
    private float toB;
    private int start;
    private int end;

    /**
     * Creates a new instance of this class.
     */
    ChangeColorInfo() {
      super();

      valueFlags.put("name", false);
      valueFlags.put("tor", false);
      valueFlags.put("tog", false);
      valueFlags.put("tob", false);
      valueFlags.put("fromr", false);
      valueFlags.put("fromg", false);
      valueFlags.put("fromb", false);
      valueFlags.put("start", false);
      valueFlags.put("end", false);

    }

    /**
     * Sets the name of the Transform.
     *
     * @param name the name.
     */
    void setName(String name) {
      this.name = name;
      valueFlags.replace("name", true);
    }

    /**
     * Sets the r value of the color.
     *
     * @param r the r value.
     */
    void setFromR(float r) {
      this.fromR = r;
      valueFlags.replace("fromr", true);
    }

    /**
     * Sets the g value of the color.
     *
     * @param g the g value.
     */
    void setFromG(float g) {
      this.fromG = g;
      valueFlags.replace("fromg", true);
    }

    /**
     * Sets the b value of the color.
     *
     * @param b the b value.
     */
    void setFromB(float b) {
      this.fromB = b;
      valueFlags.replace("fromb", true);
    }

    /**
     * Sets the r value of the color.
     *
     * @param r the r value.
     */
    void setToR(float r) {
      this.toR = r;
      valueFlags.replace("tor", true);
    }

    /**
     * Sets the g value of the to color.
     *
     * @param g the g value.
     */
    void setToG(float g) {
      this.toG = g;
      valueFlags.replace("tog", true);
    }

    /**
     * Sets the b value of the to color.
     *
     * @param b the b value.
     */
    void setToB(float b) {
      this.toB = b;
      valueFlags.replace("tob", true);
    }

    /**
     * Sets the start time of the TransformMove.
     *
     * @param start the start.
     */
    void setStart(int start) {
      this.start = start;
      valueFlags.replace("start", true);
    }

    /**
     * Sets the end time of the Transform.
     *
     * @param end the end time.
     */
    void setEnd(int end) {
      this.end = end;
      valueFlags.replace("end", true);
    }

    /**
     * Gets the name of the sape.
     *
     * @return the name of the shape.
     */
    String getName() {
      return name;
    }

    /**
     * Gets the r value of from color.
     *
     * @return the r value.
     */
    float getFromR() {
      return fromR;
    }

    /**
     * Gets the g value of the from color.
     *
     * @return the g value.
     */
    float getFromG() {
      return fromG;
    }

    /**
     * Gets the b value of the from color.
     *
     * @return the b value.
     */
    float getFromB() {
      return fromB;
    }

    /**
     * Gets the r value of the to color.
     *
     * @return the r value.
     */
    float getToR() {
      return toR;
    }

    /**
     * Gets the g value of the to color.
     *
     * @return the g value.
     */
    float getToG() {
      return toG;
    }

    /**
     * Gets the b value of the to color.
     *
     * @return the b value.
     */
    float getToB() {
      return toB;
    }

    /**
     * Gets the time at which this Transform starts.
     *
     * @return the start time.
     */
    int getStart() {
      return start;
    }

    /**
     * Gets the time at which this Transform ends.
     *
     * @return the end time.
     */
    int getEnd() {
      return end;
    }
  }

  /**
   * A subclass of the inputable class representing a TransfromScale.
   */
  class ScaleByInfo extends Inputable {

    private String name;
    private float fromSx;
    private float fromSy;
    private float toSx;
    private float toSy;
    private int start;
    private int end;

    /**
     * Constructs a new instance of this class.
     */
    ScaleByInfo() {
      super();

      valueFlags.put("name", false);
      valueFlags.put("fromsx", false);
      valueFlags.put("fromsy", false);
      valueFlags.put("tosx", false);
      valueFlags.put("tosy", false);
      valueFlags.put("start", false);
      valueFlags.put("end", false);

    }

    /**
     * Sets the name of the Transform.
     *
     * @param name the name.
     */
    void setName(String name) {
      this.name = name;
      valueFlags.replace("name", true);
    }

    /**
     * Sets the x value to start the scale from.
     *
     * @param sx the x value.
     */
    void setFromXScale(float sx) {
      this.fromSx = sx;
      valueFlags.replace("fromsx", true);
    }

    /**
     * Sets the y value to start the scale from.
     *
     * @param sy the y value.
     */
    void setFromYScale(float sy) {
      this.fromSy = sy;
      valueFlags.replace("fromsy", true);
    }

    /**
     * Sets the x value to end the scale at.
     *
     * @param sx the x value.
     */
    void setToXScale(float sx) {
      this.toSx = sx;
      valueFlags.replace("tosx", true);
    }

    /**
     * Sets the y value to end the scale at.
     *
     * @param sy the y value.
     */
    void setToYScale(float sy) {
      this.toSy = sy;
      valueFlags.replace("tosy", true);
    }

    /**
     * Sets the start time of the Transform.
     *
     * @param start the start.
     */
    void setStart(int start) {
      this.start = start;
      valueFlags.replace("start", true);
    }

    /**
     * Sets the end time of the Transform.
     *
     * @param end the end time.
     */
    void setEnd(int end) {
      this.end = end;
      valueFlags.replace("end", true);
    }

    /**
     * Gets the name of the sape.
     *
     * @return the name of the shape.
     */
    String getName() {
      return name;
    }

    /**
     * Gets the x value to start the scale from.
     *
     * @return the x value.
     */
    float getFromXScale() {
      return fromSx;
    }

    /**
     * Gets the y value to start the scale from.
     *
     * @return the y value.
     */
    float getFromYScale() {
      return fromSy;
    }

    /**
     * Gets the x value to end the scale at.
     *
     * @return the x value.
     */
    float getToXScale() {
      return toSx;
    }

    /**
     * Gets the y value to end the scale at.
     *
     * @return the y value.
     */
    float getToYScale() {
      return toSy;
    }

    /**
     * Gets the time at which this Transform starts.
     *
     * @return the start time.
     */
    int getStart() {
      return start;
    }

    /**
     * Gets the time at which this Transform ends.
     *
     * @return the end time.
     */
    int getEnd() {
      return end;
    }
  }
}
