package edu.gatech.cx4230.projectone.backend.utilities;

/**
 * Object that contains some useful methods for statistics
 * @author tbowling3
 *
 */
public class StatsHelper {


	public static double findAverage(int[] array) {
		int sum = findSum(array);
		return sum / array.length;
	}

	public static  double findAverage(double[] array) {
		double sum = findSum(array);
		return sum / array.length;
	}

	public static  int findSum(int[] array) {
		int sum = 0;

		for(int i = 0; i < array.length; i++) {
			sum += array[i];
		}
		return sum;
	}

	public static  double findSum(double[] array) {
		int sum = 0;

		for(int i = 0; i < array.length; i++) {
			sum += array[i];
		}
		return sum;
	}

	public static  double findStandardDeviation(int[] array) {
		int n = array.length;
		double average = findAverage(array);
		
		double[] errors = new double[n];
		for(int i = 0; i < n; i++) {
			errors[i] = Math.pow((array[i] - average), 2);
		}
		
		double sumErrors = findSum(errors);
		return Math.sqrt(sumErrors / (n-1));
	}
	
	public static  double findStandardDeviation(double[] array) {
		int n = array.length;
		double average = findAverage(array);
		
		double[] errors = new double[n];
		for(int i = 0; i < n; i++) {
			errors[i] = Math.pow((array[i] - average), 2);
		}
		
		double sumErrors = findSum(errors);
		return Math.sqrt(sumErrors / (n-1));
	}




}
