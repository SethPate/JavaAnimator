package cs5004.animator;

import cs5004.animator.controller.AnimationController;
import cs5004.animator.controller.AnimationControllerGUI;
import cs5004.animator.controller.IAnimationController;
import cs5004.animator.model.IAnimation;
import cs5004.animator.model.OutputType;
import cs5004.animator.util.AnimationFileReader;
import cs5004.animator.util.TweenModelBuilder;
import cs5004.animator.util.TweenModelBuilderImpl;
import cs5004.animator.view.IAnimationViewFactory;
import cs5004.animator.view.IAnimationView;
import cs5004.animator.view.ViewGUI;
import javax.swing.JOptionPane;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.ParseException;

/**
 * The EasyAnimator main class calls two subordinate functions, to initialize the Options object of
 * the commandLine object, and also to build the IAnimation from a file string, using an
 * AnimationFileReader and a TweenModelBuilder. It queries the commandLine to set values for key
 * variables, then builds a IAnimationView and an IAnimationController. Finally, it calls run() on
 * the IAnimationController.
 */

public final class EasyAnimator {

  /**
   * Reads from an input file and generates an animation in a variety of formats.
   *
   * @param args a string array containing configuration information for this function.
   */
  public static void main(String[] args) {
    //////// Create Options for CommandLine

    Options options = null;

    try {
      options = makeDefaultOptions(); // generate options for this command line
    } catch (Exception e) {
      System.err.println("failed during initialization: " + e.getMessage());
      System.exit(1);
    }

    //////// Create and execute the CommandLineParser

    CommandLine commandLine = null;
    CommandLineParser parser = new DefaultParser();

    try {
      commandLine = parser.parse(options, args);
    } catch (ParseException e) {
      System.err.println(e.getMessage());
      JOptionPane.showMessageDialog(null, "Error parsing command line.");
      System.exit(1);
    }

    //////// create all values to run EasyAnimator

    String inputFileString;
    OutputType outputType;
    String outputDestination;
    String fileName = null; // optional String to hold desired output file name
    int speed = -1;

    //////// interrogate commandLine to set values

    // set input file value (required)

    inputFileString = commandLine.getOptionValue("if");

    // set output type value (required)

    String outputTypeString = commandLine.getOptionValue("iv").toLowerCase();

    switch (outputTypeString) {
      case "text":
        outputType = OutputType.TEXT;
        break;
      case "svg":
        outputType = OutputType.SVG;
        break;
      case "visual":
        outputType = OutputType.VISUAL;
        break;
      default:
        JOptionPane
            .showMessageDialog(null, "output type must be \"text\", \"svg\", or \"visual\".");
        System.exit(1);
        throw new IllegalArgumentException("did not understand output type");
    }

    // set output destination value (optional)

    if (commandLine.hasOption("o")) {
      outputDestination = commandLine.getOptionValue("o");
      if (!outputDestination.equals("out")) {
        fileName = outputDestination;
        outputDestination = "file";
      }
    } else {
      outputDestination = "out";
    }

    // set animation speed (optional)

    if (commandLine.hasOption("speed")) {
      try {
        speed = Integer.parseInt(commandLine.getOptionValue("speed"));
        if (speed < 1) {
          JOptionPane.showMessageDialog(null, "speed must be a number greater than zero.");
          System.exit(1);
        }
      } catch (Exception e) {
        JOptionPane.showMessageDialog(null, "speed must be a number greater than zero.");
        System.exit(1);
      }
    } else {
      speed = 1;
    }

    //////// create the model, viewer, and controller

    // create the animation (model)

    IAnimation animation = null;
    try {
      animation = makeDefaultAnimation(inputFileString);
    } catch (Exception e) {
      System.err.println("Failed initializing model: " + e.getMessage());
      System.exit(1);
    }

    // create the AnimationView

    IAnimationView animationView = null;

    try {
      if (outputType == OutputType.VISUAL) {
        int panelWidth = (int) animation.getMaxX();
        int panelHeight = (int) animation.getMaxY();
        animationView = IAnimationViewFactory.makeViewGUI(panelWidth, panelHeight);
      } else if (outputDestination.equals("file")) {
        animationView = IAnimationViewFactory.makeViewFile(fileName);
      } else if (outputDestination.equals("out")) {
        animationView = IAnimationViewFactory.makeViewSystemOut();
      }
    } catch (Exception e) {
      System.err.println("Failed initializing view: " + e.getMessage());
      System.exit(1);
    }

    // create the AnimationController.
    // Note that in visual mode, both the Controller and View must be GUI types.

    IAnimationController animationController = null;
    try {
      if (outputType == OutputType.VISUAL) {
        animationController = new AnimationControllerGUI(animation, animationView,
            speed, outputType);
        ((ViewGUI) animationView).setController(((AnimationControllerGUI)
            animationController));
      } else {
        animationController = new AnimationController(animation, animationView,
            speed, outputType);
      }
    } catch (Exception e) {
      System.err.println("Failed initializing controller: " + e.getMessage());
      System.exit(1);
    }

    //////// pass control to the controller

    animationController.run();
  }

  /**
   * Constructs a default range of Options for use with the CommandLine interface.
   *
   * @return An Options object with all options initialized.
   */
  private static Options makeDefaultOptions() {
    // input file
    Option inputFileOption = new Option("if", true, "input file");
    inputFileOption.setRequired(true);

    // output format
    Option outputFormatOption = new Option("iv", true, "file format to output");
    outputFormatOption.setRequired(true);

    // output destination
    Option outputDestinationOption = new Option("o", true, "output destination");
    outputDestinationOption.setRequired(false);

    // speed (ticks per second)
    Option speedOption = new Option("speed", true, "speed of animation in ticks per second");
    speedOption.setRequired(false);

    Options options = new Options();

    options.addOption(inputFileOption);
    options.addOption(outputFormatOption);
    options.addOption(outputDestinationOption);
    options.addOption(speedOption);

    return options;
  }

  /**
   * Constructs an IAnimation from an input file string using default settings. Uses the
   * AnimationFileReader and TweenModelBuilder classes as intermediaries.
   *
   * @return an initialized IAnimation.
   * @throws IllegalArgumentException if could not read from input file String.
   */
  private static IAnimation makeDefaultAnimation(String inputFileString)
      throws IllegalArgumentException {
    AnimationFileReader fileReader = new AnimationFileReader();
    TweenModelBuilder<IAnimation> tweenModelBuilder = new TweenModelBuilderImpl();
    try {
      return fileReader.readFile(inputFileString, tweenModelBuilder);
    } catch (Exception e) {
      System.err.println(e.getMessage());
      throw new IllegalArgumentException("Could not read from input file string.");
    }
  }
}
