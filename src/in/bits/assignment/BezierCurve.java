package in.bits.assignment;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.CubicCurve2D;

import javax.swing.JOptionPane;

/**
 * @author kinkuraj
 *
 */
/**
 * Form a bezier curve if there are three control points are given as below.
 * Point2D.Double P1 = new Point2D.Double(50, 75); // Start Point
 * Point2D.Double P2 = new Point2D.Double(150, 75); // End Point
 * Point2D.Double ctrl1 = new Point2D.Double(80, 25); // Control Point 1
 * Point2D.Double ctrl2 = new Point2D.Double(160, 100); // Control Point 2
 * 
 */
public class BezierCurve extends ApplicationFrame {

	public BezierCurve() {
		super("First Bezier Curve");
	}

	private Point[] pointsGiven;
	
	public Point[] getPointsGiven() {
		return pointsGiven;
	}

	public void setPointsGiven(Point[] pointsGiven) {
		this.pointsGiven = pointsGiven;
	}

	public static void main(String... args) {
		new BezierCurve();

	}

	@Override
	public void paint(Graphics g) {
		int noOfControlPoints = takePointsFromUser();
		if (noOfControlPoints == 0) {
			JOptionPane.showMessageDialog(mainFrame, "This is not a valid point. Points reset. Tray again!");
		} else {
			Point[] pointsToBePlotted = calculateNewCurvePoints(noOfControlPoints);
			
			//Draw curve using new evaluated points using Bernstien formula
			CubicCurve2D.Double cubicCurve;
			cubicCurve = new CubicCurve2D.Double();
			cubicCurve.setCurve(getTheCurvePoints(pointsToBePlotted), 0);
			Graphics2D g2 = (Graphics2D) g;
			g2.setColor(new Color(000));
			g2.draw(cubicCurve);
			
			//Draw curve using given control points
			CubicCurve2D.Double cubicCurve2;
			cubicCurve2 = new CubicCurve2D.Double();
			cubicCurve2.setCurve(getTheCurvePoints(getPointsGiven()), 0);
			g2.setColor(new Color(222));
			g2.draw(cubicCurve2);
		}
	}
	
	/**
	 * @return
	 */
	private int takePointsFromUser() {
		String noOfCtrPointsInput = JOptionPane.showInputDialog("How many points required?");
		if (noOfCtrPointsInput == null) {
			return 0;
		}
		int noOfControlPoints = Integer.parseInt(noOfCtrPointsInput);
		pointsGiven = new Point[noOfControlPoints];
		for (int index = 0; index < noOfControlPoints; index++) {
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
	 * @param noOfControlPoints
	 * @return
	 */
	private Point[] calculateNewCurvePoints(int noOfControlPoints) {
		int newSize = noOfControlPoints + 1;
		Point[] newPoints = new Point[newSize + 1];
		double len = (1.0 / newSize);
		int index = 0;
		newPoints[index] = pointsGiven[index];
		index++;
		for (double u = len; u < 1; u += len) {
			newPoints[index] = calculatePoint(pointsGiven, u);
			index++;
		}
		newPoints[index] = pointsGiven[noOfControlPoints - 1];
		return newPoints;
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

	/**
	 * @param pointsGiven
	 * @param u
	 * @return
	 * @desc Points calculated using Bernstien formula
	 */
	private Point calculatePoint(Point[] pointsGiven, double u) {
		double xResultPoint = 0;
		double yResultPoint = 0;
		int noOfControlPoints = pointsGiven.length - 1;
		for (int k = 0; k <= noOfControlPoints; k++) {
			double bezResult = calculateBez(k, u, noOfControlPoints);
			xResultPoint += (pointsGiven[k].getX()) * bezResult;
			yResultPoint += (pointsGiven[k].y) * bezResult;
		}
		return new Point((int) Math.ceil(xResultPoint), (int) Math.ceil(yResultPoint));
	}

	/**
	 * @param k
	 * @param u
	 * @param noOfControlPoints
	 * @return
	 */
	private double calculateBez(int k, double u, int noOfControlPoints) {
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

	/**
	 * @param n
	 * @param k
	 * @return
	 */
	private double calculateCombination(int n, int k) {
		return fact(n) / (fact(k) * fact(n - k));
	}

	/**
	 * @param num
	 * @return
	 */
	public int fact(int num) {
		int fact = 1, i;
		for (i = 1; i <= num; i++)
			fact = fact * i;
		return fact;
	}

	
}
