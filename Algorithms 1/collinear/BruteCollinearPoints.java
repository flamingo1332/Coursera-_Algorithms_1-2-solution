/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import java.util.ArrayList;
import java.util.Arrays;

public class BruteCollinearPoints {
    private ArrayList<LineSegment> lineSegments;

    // finds all line segments containing 4 points
    public BruteCollinearPoints(Point[] points) {

        // check for null
        if (nullPoints(points)) {
            throw new NullPointerException("Invalid");
        }

        // clone and sort
        Arrays.sort(points);

        // check for duplicate
        if (repeatedPoints(points)) {
            throw new IllegalArgumentException("Has Duplicate");
        }

        this.lineSegments = new ArrayList<>();

        // make segments
        for (int a = 0; a < points.length - 3; a++) {
            Point pa = points[a];
            for (int b = a + 1; b < points.length - 2; b++) {
                Point pb = points[b];
                double papb = pa.slopeTo(pb);
                for (int c = b + 1; c < points.length - 1; c++) {
                    Point pc = points[c];
                    double pbpc = pb.slopeTo(pc);
                    if (papb == pbpc) {

                        for (int d = c + 1; d < points.length; d++) {
                            Point pd = points[d];
                            double pcpd = pc.slopeTo(pd);
                            if (papb == pcpd) {
                                this.lineSegments.add(new LineSegment(pa, pd));
                            }
                        }
                    }
                }
            }


        }
    }

    private boolean repeatedPoints(Point[] p) {
        for (int i = 0; i < p.length - 1; i++) {
            if (p[i] == p[i + 1]) {
                return true;
            }
        }
        return false;
    }

    private boolean nullPoints(Point[] p) {
        for (int i = 0; i < p.length - 1; i++) {
            if (p[i] == p[i + 1]) {
                return true;
            }
        }
        return false;
    }

    // the number of line segments
    public int numberOfSegments() {
        return lineSegments.size();
    }

    // the line segments
    public LineSegment[] segments() {
        return lineSegments.toArray(new LineSegment[lineSegments.size()]);
    }
}