package graphit;

import javax.swing.*;

import java.awt.*;
import java.awt.event.*;
import java.util.List;
import java.awt.Point;

public class NGraphitGraphPane extends JPanel {

  private static final long serialVersionUID = -8637712318103083791L;

  private int lineSpacing;
  private GraphController controller;
  private Function f;

  public NGraphitGraphPane(GraphController controller) {

    this.controller = controller;
    lineSpacing = controller.getPixelsBetweenNumbers();

    this.addMouseMotionListener(new MouseHandler());
    this.addMouseListener(new MouseHandler());

    this.addKeyListener(new KeyHandler());
    setFocusable(true);
  }

  private static Graphics2D g2;
  private static final int GRAPH_WIDTH = 2000;
  private static final int GRAPH_HEIGHT = 2000;
  private Point origin = new Point(GRAPH_WIDTH / 2, GRAPH_HEIGHT / 2);
  private Point mouseCenter = new Point(0, 0);
  private List<GraphPoint> points;

  private int drawCircleX;
  private int drawCircleY;
  private double mouseGraphX;
  private double mouseGraphY;
  private boolean drawCircle = false;

  @Override
  public void paintComponent(Graphics g) {

    super.paintComponent(g);

    g2 = (Graphics2D) g;

    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
    // line thickness width
    g2.setStroke(new BasicStroke(2));

    // draw the y and x axis
    g2.drawLine(0, origin.y, getWidth(), origin.y);

    g2.drawLine(origin.x, 0, origin.x, getHeight());

    g2.setStroke(new BasicStroke(1));

    drawGreyLines(g2);
    drawNumbers(g2);
    if (points == null) {
      return;
    }

    // Draw the points by connecting i to i+1
    List<Point> viewPoints = controller.convertToPanelCoordinate(points, origin);
    g2.setColor(Color.RED);

    for (int i = 0; i < viewPoints.size() - 1; i++) {

      Point p1 = viewPoints.get(i);
      Point p2 = viewPoints.get(i + 1);

      g2.drawLine((int) p1.getX(), (int) p1.getY(), (int) p2.getX(), (int) p2.getY());

    }

    g2.setColor(Color.GRAY);

    // Draw function circle
    if (drawCircle) {
      g2.setColor(Color.BLACK);
      g2.drawOval(drawCircleX - 3, drawCircleY - 3, 6, 6);
      g2.drawString(mouseGraphX + ", " + mouseGraphY, drawCircleX - 3, drawCircleY - 6);
      g2.setColor(Color.GRAY);
    }
  }

  private void drawGreyLines(Graphics2D g2) {
    g2.setColor(Color.LIGHT_GRAY);

    for (int i = 0; i < getWidth(); i += lineSpacing) {
      g2.drawLine(i, 0, i, getHeight());
    }

    for (int i = 0; i < getHeight(); i += lineSpacing) {
      g2.drawLine(0, i, getWidth(), i);
    }

  }

  private void drawNumbers(Graphics2D g2) {
    g2.setColor(Color.BLUE);

    int numbersToDraw = getWidth() / lineSpacing;
    int j = 0;

    for (int i = controller.getStart(getWidth()); j < numbersToDraw; j++, i++) {

      if (i == 0) {
        continue;
      }
      FontMetrics metrics = g2.getFontMetrics();
      String as_i = Integer.toString(i);

      int x = j * lineSpacing + (lineSpacing - metrics.stringWidth(as_i)) / 2 - lineSpacing / 2 + 1;
      g2.drawString(as_i, x, origin.y + 20);

    }
    j = 0;
    numbersToDraw = getHeight() / lineSpacing;
    for (int i = controller.getStart(getHeight()); j < numbersToDraw; j++, i++) {

      if (i == 0) {
        continue;
      }
      FontMetrics metrics = g2.getFontMetrics();
      String as_i = Integer.toString(-i);

      int y = j * lineSpacing + ((lineSpacing - metrics.getHeight()) / 2) + metrics.getAscent() - lineSpacing / 2 + 1;
      g2.drawString(as_i, origin.x - 20, y);

    }

    g2.setColor(Color.GRAY);
  }

  public void graph(String s) {
    requestFocusInWindow();

    f = Function.parse(s);
    this.points = controller.getPoints(f, getWidth());

    repaint();

  }

  private class KeyHandler implements KeyListener {

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {

      if (e.isShiftDown()) {
        Point me = mouseCenter;
        int x = (int) me.getX();

        if (f == null) {
          return;
        }

        double relx = (double) (x - origin.x) / lineSpacing;

        double res = f.evaluate(relx);
        Point p = controller.convert(new GraphPoint(relx, res), origin);

        mouseGraphX = relx;
        mouseGraphY = res;
        drawCircle = true;
        drawCircleX = p.x;
        drawCircleY = p.y;

        repaint();
      }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
  }

  private class MouseHandler implements MouseMotionListener, MouseListener {

    public void mouseDragged(MouseEvent me) {

      JViewport viewPort = (JViewport) SwingUtilities.getAncestorOfClass(JViewport.class, NGraphitGraphPane.this);

      if (viewPort != null) {
        int deltaX = mouseCenter.x - me.getX();
        int deltaY = mouseCenter.y - me.getY();

        Rectangle view = viewPort.getViewRect();
        view.x += deltaX;
        view.y += deltaY;

        NGraphitGraphPane.this.scrollRectToVisible(view);

      }
    }

    public void mouseMoved(MouseEvent me) {
      mouseCenter = new Point(me.getPoint());
    }

    public void mouseClicked(MouseEvent evt) {
      requestFocusInWindow();
    }

    public void mouseEntered(MouseEvent evt) {
    }

    public void mouseReleased(MouseEvent evt) {
    }

    public void mousePressed(MouseEvent me) {

      mouseCenter = new Point(me.getPoint());
      System.out.println(mouseCenter.x + " " + mouseCenter.y);

    }

    public void mouseExited(MouseEvent evt) {
    }

  }

  @Override
  public Dimension getPreferredSize() {
    return new Dimension(GRAPH_WIDTH, GRAPH_HEIGHT);
  }

}