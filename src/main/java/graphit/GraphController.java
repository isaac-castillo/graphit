package graphit;

import java.util.ArrayList;
import java.util.List;
import java.awt.Point;

public class GraphController {

    private int pixelsBetweenNumbers;
    private int plotSize;

    GraphController(int pixelsBetweenNumbers, int plotSize) {
        this.pixelsBetweenNumbers = pixelsBetweenNumbers;
        this.plotSize = plotSize;
    }

    public void setPlotSize(int plotSize) {
        this.plotSize = plotSize;
    }

    public void setPixelsBetweenNumbers(int pixelsBetweenNumbers) {
        this.pixelsBetweenNumbers = pixelsBetweenNumbers;
    }

    public int getPlotSize() {
        return this.plotSize;
    }

    public int getPixelsBetweenNumbers() {
        return this.pixelsBetweenNumbers;
    }

    public int getStart(int width) {

        int x = -width / (pixelsBetweenNumbers * 2);

        return x;
    }

    public List<Point> convertToPanelCoordinate(List<GraphPoint> relations, Point origin) {

        List<Point> points = new ArrayList<>(relations.size());

        for (GraphPoint p : relations) {

            points.add(convert(p, origin));

        }

        return points;
    }

    public Point convert(GraphPoint relation, Point origin) {
        int x = (int) (origin.x + pixelsBetweenNumbers * relation.x);
        int y = (int) (origin.y - pixelsBetweenNumbers * relation.y);
        return new Point(x, y);
    }

    public List<GraphPoint> getPoints(Function f, int width) {

        double start = getStart(width);
        double increment = (double) (width / plotSize) / pixelsBetweenNumbers;
        int j = 0;

        List<GraphPoint> points = new ArrayList<GraphPoint>();

        for (double i = start; j < plotSize; i += increment, j++) {
            GraphPoint p = new GraphPoint(i, f.evaluate(i));
            points.add(p);
        }

        return points;
    }

}
