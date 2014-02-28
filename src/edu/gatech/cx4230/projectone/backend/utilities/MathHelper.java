package edu.gatech.cx4230.projectone.backend.utilities;

/**
 * Class provides useful mathematical methods
 * @author tbowling3
 *
 */
public class MathHelper {
	
	public MathHelper() {
		
	}
	
	/**
	 * Performs linear interpretation using the points (x1, y1) and (x2, y2) to
	 * determine the value of y given x.  If f(x) is a line described by points
	 * (x1, y1) and (x2, y2), then interp(x) returns f(x)
	 * @param x
	 * @param x1
	 * @param y1
	 * @param x2
	 * @param y2
	 * @return f(x) where f is the function described by the given coordinates
	 */
	public static double linearInterp(double x, double x1, double y1, double x2, double y2) {
		return y1 + (y2 - y1) / (x2 - x1) * (x - x1);
	}
	
	public static double getCartesianDistance(double x1, double y1, double x2, double y2) {
		double dx = x2 - x1;
		double dy = y2 - y1;
		return Math.sqrt(dx*dx + dy*dy);
	}

}
