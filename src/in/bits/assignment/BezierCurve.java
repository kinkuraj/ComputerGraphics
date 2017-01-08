package in.bits.assignment;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.CubicCurve2D;

import javax.swing.JOptionPane;

/**
 * @author kinkuraj
 *
 */
public class BezierCurve extends ApplicationFrame {

	public BezierCurve() {
		super("First Bezier Curve");
	}

	double points[];
	Point[] pointsGiven;

	/**
	 * Form a bezier curve if there are three control points are given as below.
	 * Point2D.Double P1 = new Point2D.Double(50, 75); // Start Point
	 * Point2D.Double P2 = new Point2D.Double(150, 75); // End Point
	 * Point2D.Double ctrl1 = new Point2D.Double(80, 25); // Control Point 1
	 * Point2D.Double ctrl2 = new Point2D.Double(160, 100); // Control Point 2
	 * 
	 */
	public static void main(String... args) {
		new BezierCurve();

	}

	/**
	 * @param noOfControlPoints
	 * @return
	 */
	private Point[] calculateCurvePoints(int noOfControlPoints) {

		int newSize = noOfControlPoints + 1;
		Point[] pointsToBePlotted = new Point[newSize + 1];
		double len = (1.0 / newSize);
		int index = 0;
		pointsToBePlotted[index] = pointsGiven[index];
		index++;
		for (double u = len; u < 1; u += len) {
			pointsToBePlotted[index] = calculatePoint(pointsGiven, u);

			System.out.println(pointsToBePlotted[index]);
			index++;
		}
		pointsToBePlotted[index] = pointsGiven[noOfControlPoints - 1];
		return pointsToBePlotted;
	}

	/**
	 * @return
	 */
	private int takePointsFromUser() {
		String noOfCtrPointsInput = JOptionPane.showInputDialog("How many control points required?");
		if (noOfCtrPointsInput == null) {
			return 0;
		}
		int noOfControlPoints = Integer.parseInt(noOfCtrPointsInput);

		pointsGiven = new Point[noOfControlPoints];
		for (int index = 0; index < noOfControlPoints; index++) {
			System.out.println("Enter the point in x,y  format");

			String point = JOptionPane.showInputDialog("Please Enter Next Point in x,y format");
			if (point != null) {
				pointsGiven[index] = new Point(Integer.valueOf(point.split(",")[0]),
						Integer.parseInt(point.split(",")[1]));
			} else {
				return 0;
			}

		}
		return noOfControlPoints;
	}

	/**
	 * @param pointsToBeplotted
	 * @return
	 */
	private double[] getTheCurvePoints(Point[] pointsToBeplotted) {
		int index = 0;
		double[] points = new double[(pointsToBeplotted.length * 2)];
		for (Point point : pointsToBeplotted) {
			points[index] = point.getX();
			index++;
			points[index] = point.getY();
			index++;
		}
		return points;
	}

	private Point calculatePoint(Point[] pointsGiven, double u) {
		double xResultPoint = 0;
		double yResultPoint = 0;
		int noOfControlPoints = pointsGiven.length - 1;
		for (int k = 0; k <= noOfControlPoints; k++) {
			double bezResult = calculatePoint(k, u, noOfControlPoints);
			xResultPoint += (pointsGiven[k].getX()) * bezResult;
			System.out.println("x:: " + xResultPoint);
			yResultPoint += (pointsGiven[k].y) * bezResult;
			System.out.println("y:: " + yResultPoint);
		}
		return new Point((int) Math.ceil(xResultPoint), (int) Math.ceil(yResultPoint));
	}

	private double calculatePoint(int k, double u, int noOfControlPoints) {
		if (k == noOfControlPoints) {
			return Math.pow(u, k);
		}
		if (k == 0) {
			return Math.pow(1 - u, noOfControlPoints);
		}
		if (k != noOfControlPoints) {
			return Math.pow(1 - u, noOfControlPoints - k) * Math.pow(u, k) * calculateCombination(noOfControlPoints, k);
		}
		return 0;
	}

	private double calculateCombination(int n, int k) {
		return fact(n) / (fact(k) * fact(n - k));
	}

	public int fact(int num) {
		int fact = 1, i;
		for (i = 1; i <= num; i++)
			fact = fact * i;
		return fact;
	}

	@Override
	public void paint(Graphics g) {
		int noOfControlPoints = takePointsFromUser();
		if (noOfControlPoints == 0) {
			JOptionPane.showMessageDialog(mainFrame, "This is not a valid point. Points reset. Tray again!");
		} else {
			Point[] pointsToBePlotted = calculateCurvePoints(noOfControlPoints);
			CubicCurve2D.Double cubicCurve; // Cubic curve
			cubicCurve = new CubicCurve2D.Double();
			cubicCurve.setCurve(getTheCurvePoints(pointsToBePlotted), 0);
			Graphics2D g2 = (Graphics2D) g;
			g2.draw(cubicCurve);
			CubicCurve2D.Double cubicCurve2; // Cubic curve
			cubicCurve2 = new CubicCurve2D.Double();
			cubicCurve2.setCurve(getTheCurvePoints(pointsGiven), 0);
			g2.draw(cubicCurve2);
		}
	}
}
