package cs5004.animator.view;

import cs5004.animator.controller.AnimationControllerGUI;
import cs5004.animator.model.IRenderableShape;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

/**
 * An implementation of IAnimationView that can render shapes with a JFrame. Uses a JPanel called
 * DrawingWindow to show the Shapes. Listens to the actions of its own buttons.
 */

public class ViewGUI extends JFrame implements IAnimationView, ActionListener {

  private int panelWidth;
  private int panelHeight;
  private DrawingPanel drawingPanel;
  private JPanel mainPanel;
  private JLabel speedIndicator;
  private AnimationControllerGUI controller;

  /**
   * Creates a new instance of the ViewGUI. Note that many buttons depend on the controller being
   * set, so the item cannot be used until setController() is called with a valid object.
   *
   * @param panelWidth the eventual width of the animation panel in pixels.
   * @param panelHeight the eventual height of the animation panel in pixels.
   */
  public ViewGUI(int panelWidth, int panelHeight) {
    this.panelWidth = panelWidth;
    this.panelHeight = panelHeight;

    setSize(650, 850); // overall frame size

    // create main panel and add to ViewGUI
    mainPanel = new JPanel();
    setContentPane(mainPanel);

    setTitle("Mediocre Animation Viewer");
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setVisible(true);
  }

  /**
   * Allows the main function to set this viewer's controller. This allows the IAnimationView to
   * make requests of the IAnimationController, for example, when a button is pushed.
   *
   * @param controller the controller to link this viewer to.
   */
  public void setController(AnimationControllerGUI controller) {
    this.controller = controller;
    this.setMainPanel(); // set buttons, labels, etc.
    this.revalidate(); // necessary to reorient main panel
  }

  /**
   * Takes a String of animation instructions that the model has formatted to the desired spec (for
   * example, text or SVG). Render() writes this String to the desired location (for example,
   * System.out) in the desired format.
   *
   * @param shapeList read-only shapes to be drawn in a GUI window.
   */
  public void render(Object shapeList) {
    drawingPanel.updateShapes((List<IRenderableShape>) shapeList);
    drawingPanel.repaint();
  }

  /**
   * Creates and adds all buttons, labels, etc. to this ViewGUI. The ViewGUI must have a controller
   * set.
   */
  public void setMainPanel() {
    JButton playButton = new JButton("play"); // starts, resumes, and restarts
    playButton.setActionCommand("play");
    playButton.addActionListener(this);
    mainPanel.add(playButton, BorderLayout.PAGE_START);

    JButton pauseButton = new JButton("pause");
    pauseButton.setActionCommand("pause");
    pauseButton.addActionListener(this);
    mainPanel.add(pauseButton, BorderLayout.PAGE_START);

    JButton speedUpButton = new JButton("speed +");
    speedUpButton.setActionCommand("speedUp");
    speedUpButton.addActionListener(this);
    mainPanel.add(speedUpButton, BorderLayout.PAGE_START);

    JButton speedDownButton = new JButton("speed -");
    speedDownButton.setActionCommand("speedDown");
    speedDownButton.addActionListener(this);
    mainPanel.add(speedDownButton, BorderLayout.PAGE_START);

    JButton saveButton = new JButton("save as SVG");
    saveButton.setActionCommand("save");
    saveButton.addActionListener(this);
    mainPanel.add(saveButton, BorderLayout.PAGE_START);

    // create place for the animation
    drawingPanel = new DrawingPanel(this.panelWidth, this.panelHeight);

    // wrap animation pane into scrollpane
    JScrollPane drawingScrollPane = new JScrollPane(drawingPanel);
    drawingScrollPane.setViewportBorder(
        BorderFactory.createLineBorder(Color.black));
    mainPanel.add(drawingScrollPane, BorderLayout.CENTER);

    // create label for speed
    speedIndicator = new JLabel();
    speedIndicator.setText(String.format("Speed: %d", controller.getTicksPerSecond()));
    mainPanel.add(speedIndicator, BorderLayout.PAGE_END);
  }

  /**
   * Handles button presses in this ViewGUI. The ViewGUI is its own action listener.
   *
   * @param e an event resulting from a button press.
   */
  @Override
  public void actionPerformed(ActionEvent e) {
    String command = e.getActionCommand();
    if ("pause".equals(command)) {
      this.controller.stopTimer();
    } else if ("play".equals(command)) {
      this.controller.startTimer();
    } else if ("speedUp".equals(command)) {
      this.controller.speedUp();
      speedIndicator.setText(String.format("Speed: %d", controller.getTicksPerSecond()));
    } else if ("speedDown".equals(command)) {
      this.controller.speedDown();
      speedIndicator.setText(String.format("Speed: %d", controller.getTicksPerSecond()));
    } else if ("save".equals(e.getActionCommand())) {
      String filename = JOptionPane.showInputDialog("Please enter filename",
          "filename (without extension)");
      if ((filename == null) || filename.equals("")) {
        JOptionPane.showMessageDialog(null,
            "I could not understand your filename."
                + " If you want to save, please press the button again.");
      } else {
        controller.saveSVG(filename + ".svg");
      }
    }
  }
}
