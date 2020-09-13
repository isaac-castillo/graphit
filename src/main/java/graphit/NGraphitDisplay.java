package graphit;

/* This file handles all the displaying and managing of Events. */
import javax.swing.*;

import java.awt.*;
import java.awt.event.*;

public class NGraphitDisplay implements ActionListener {

  /* NEW AGE CONSTANTS */
  private static final int CANVAS_WIDTH = 600;
  private static final int CANVAS_HEIGHT = 600;

  private static final String PROGRAM_NAME = "NGraphit";
  private static final String GRAPH_STRING = "Graphit!";
  private static final String ORIGIN_STRING = "Origin!";

  /* END NEW AGE CONSTANTS */

  private JScrollPane scroll;
  private JTextField enterFunctionField;
  private JPanel bottomGraphPanel;

  private NGraphitGraphPane graphPane;

  public NGraphitDisplay() {

    JFrame mainFrame = new JFrame(PROGRAM_NAME);
    mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    mainFrame.setSize(CANVAS_WIDTH, CANVAS_HEIGHT);
    createUI(mainFrame);

    mainFrame.pack();
    mainFrame.setLocationRelativeTo(null);
    mainFrame.setVisible(true);

  }

  private void createUI(Container pane) {

    if (!(pane.getLayout() instanceof BorderLayout)) {
      pane.add(new JLabel("Container doesn't use BorderLayout!"));
      return;
    }

    GraphController controller = new GraphController(40, 200);

    graphPane = new NGraphitGraphPane(controller);
    scroll = new JScrollPane(graphPane);
    pane.add(scroll, BorderLayout.CENTER);
    scroll.setPreferredSize(new Dimension(CANVAS_WIDTH, CANVAS_HEIGHT));

    JPanel bottomPanel = new JPanel();
    pane.add(bottomPanel, BorderLayout.SOUTH);

    JButton graphBtn = new JButton(GRAPH_STRING);
    JButton originBtn = new JButton(ORIGIN_STRING);

    JLabel enterFunction = new JLabel("y=");
    enterFunctionField = new JTextField();
    enterFunctionField.setPreferredSize(new Dimension(600, 48));

    Font font = new Font("Sans-Serif", Font.BOLD, 40);

    enterFunction.setFont(font);
    enterFunctionField.setFont(font);

    GridLayout layout = new GridLayout(2, 0);
    bottomGraphPanel = new JPanel();
    JPanel bottomButtonPanel = new JPanel();

    bottomGraphPanel.setLayout(new FlowLayout(FlowLayout.LEADING));
    bottomPanel.setLayout(layout);
    bottomPanel.add(bottomGraphPanel);
    bottomPanel.add(bottomButtonPanel);
    bottomGraphPanel.add(enterFunction);
    bottomGraphPanel.add(enterFunctionField);
    bottomButtonPanel.add(graphBtn);
    bottomButtonPanel.add(originBtn);

    Rectangle bounds = scroll.getViewport().getViewRect();
    Dimension size = scroll.getViewport().getViewSize();
    int x = (size.width - bounds.width) / 2 - pane.getWidth() / 2;
    int y = (size.height - bounds.height) / 2 - pane.getHeight() / 2;
    scroll.getViewport().setViewPosition(new java.awt.Point(x, y));

    graphBtn.addActionListener(this);
    originBtn.addActionListener(this);

  }

  public void actionPerformed(ActionEvent evt) {

    switch (evt.getActionCommand()) {

      case GRAPH_STRING:
        graph();
        break;
      case ORIGIN_STRING:
        origin();
        break;

      default:

    }

  }

  public void graph() {

    graphPane.graph("y=" + enterFunctionField.getText());
    System.out.println("Graphin!");
  }

  public void origin() {

    Rectangle bounds = scroll.getViewport().getViewRect();
    Dimension size = scroll.getViewport().getViewSize();
    int x = (size.width - bounds.width) / 2;
    int y = (size.height - bounds.height) / 2;
    scroll.getViewport().setViewPosition(new java.awt.Point(x, y));
  }

  public Dimension getPreferredSize() {
    return new Dimension(CANVAS_WIDTH, CANVAS_HEIGHT);
  }

}