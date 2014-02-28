package edu.gatech.cx4230.projectone.backend.utilities;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Contains many useful methods for when working with arrays
 * @author tg2
 *
 */
public class ArrayManipulation {

	/**
	 * Finds the minimum value from a 2D array
	 * @param array The array to find the minimum value of
	 * @return The minimum value of a 2D array
	 */
	public static double findMinIn2D(double[][] array) {
		double out = array[0][0];

		for(int i = 0; i < array.length; i++) {
			for(int j = 0; j < array[i].length; j++) {
				if(out > array[i][j]) out = array[i][j];
			}
		}	
		return out;
	}

	/**
	 * Finds the maximum value from a 2D array
	 * @param array The array to find the maximum value of
	 * @return The maximum value of a 2D array
	 */
	public static double findMaxIn2D(double[][] array) {
		double out = array[0][0];

		for(int i = 0; i < array.length; i++) {
			for(int j = 0; j < array[i].length; j++) {
				if(out < array[i][j]) out = array[i][j];
			}
		}

		return out;
	}

	public static int findIndexMin(double[] array) {
		int ind = -1;
		if(array != null && array.length > 0) {
			double val = array[0];
			for(int i = 0; i < array.length; i++) {
				if(array[i] < val) {
					val = array[i];
					ind = i;
				}
			}
		}
		return ind;
	}

	public static int findIndexMax(double[] array) {
		int ind = -1;
		if(array != null && array.length > 0) {
			ind = 0;
			double val = array[ind];
			for(int i = 0; i < array.length; i++) {
				if(array[i] > val) {
					val = array[i];
					ind = i;
				}
			}
		}
		return ind;
	}

	public static int findIndexMin(int[] array) {
		int ind = -1;
		if(array != null && array.length > 0) {
			int val = array[0];
			for(int i = 0; i < array.length; i++) {
				if(array[i] < val) {
					val = array[i];
					ind = i;
				}
			}
		}
		return ind;
	}

	public static int findIndexMax(int[] array) {
		int ind = -1;
		if(array != null && array.length > 0) {
			int val = array[0];
			for(int i = 0; i < array.length; i++) {
				if(array[i] > val) {
					val = array[i];
					ind = i;
				}
			}
		}
		return ind;
	}

	/**
	 * Takes in a 3D array and consolidates array into a 2D array by
	 * merging along the 3rd dimension
	 * @param array
	 * @return A consolidated 2D boolean array
	 */
	public static boolean[][] consolidateBoolArray(boolean[][][] array) {
		boolean[][] out = new boolean[array.length][];

		for(int i = 0; i < array.length; i++) {
			out[i] = new boolean[array[i].length];

			for(int j = 0; j < array[i].length; j++) {
				out[i][j] = false;

				for(int k = 0; k < array[i][j].length; k++) {
					if(array[i][j][k]) out[i][j] = true;
				}
			}
		}

		return out;
	}

	/**
	 * Takes in an ArrayList of 2D booleans and consolidates array into a 2D array by
	 * merging along the ArrayList
	 * @param array
	 * @return A consolidated 2D boolean array
	 */
	public static boolean[][] consolidateBoolArray(ArrayList<boolean[][]> array) {
		boolean[][] out = null;
		if(array != null && !array.isEmpty()) {
			if(array.size() == 1) {
				out = array.get(0);
			} else {
				int width = array.get(0)[0].length;
				int height = array.get(0).length;
				out = new boolean[height][];

				for(int i = 0; i < height; i++) {
					out[i] = new boolean[width];

					for(int j = 0; j < width; j++) {
						out[i][j] = false;

						for(boolean[][] cur: array) {
							if(cur[i][j]) out[i][j] = true;
						} // close for
					} // close j for
				} // close i for
			} // close else
		} // close null if
		return out;
	} // close function

	/**
	 * Writes an array to a comma-separated-variable (CSV) file to the directory of this application
	 * @param array Array to be saved to the file
	 * @param filename Name of the csv File
	 */
	public static void writeDoubleArrayToCSV(double[][] array, String filename) {
		try {
			if(!filename.endsWith(".csv")) {
				filename = filename + ".csv";
			}
			if(array != null) {
				FileWriter fw = new FileWriter(filename);
				for(int i = 0; i < array.length; i++) {
					String line = "";

					for(int j = 0; j < array[i].length; j++) {
						if(j != 0) line += ", ";
						line += array[i][j];
					} // close for j
					line += "\n";
					fw.write(line);
				} // close for i

				fw.close();
			} // close if null
		} catch (IOException e) {
			System.err.println("There was a problem writing Array to CSV file");
			e.printStackTrace();
		} // close catch
	} // close method

	/**
	 * Writes an array to a comma-separated-variable (CSV) file to the directory of this application
	 * @param array Array to be saved to the file
	 * @param filename Name of the csv File
	 */
	public static void writeIntArrayToCSV(int[][] array, String filename) {
		try {
			if(!filename.endsWith(".csv")) {
				filename = filename + ".csv";
			}
			if(array != null) {
				FileWriter fw = new FileWriter(filename);
				for(int i = 0; i < array.length; i++) {
					String line = "";

					for(int j = 0; j < array[i].length; j++) {
						if(j != 0) line += ", ";
						line += array[i][j];
					} // close for j
					line += "\n";
					fw.write(line);
				} // close for i

				fw.close();
			} // close if null
		} catch (IOException e) {
			System.err.println("There was a problem writing Array to CSV file");
			e.printStackTrace();
		} // close catch
	} // close method

	/**
	 * Converts a 2D array of booleans to a 2D array of doubles where
	 * 1 is true and 0 is false
	 * @param in 2D boolean array to be converted to an array of doubles
	 * @return 2D double array of the converted input array
	 */
	public static double[][] convertBoolArrayToDouble(boolean[][] in) {
		double[][] out = new double[in.length][];

		for(int i = 0; i < in.length; i++) {
			out[i] = new double[in[i].length];

			for(int j = 0; j < in[i].length; j++) {
				out[i][j] = 0;
				if(in[i][j]) out[i][j] = 1;
			} // close for
		} // close for
		return out;
	}

	/**
	 * Returns the specific third dimension of the 3D array.
	 * @param data 3D array of doubles
	 * @param dimension The desired dimension of the 3D array to be returned
	 * @return The appropriate 2D array taken from the parameter 3D array
	 */
	public static double[][] getArrayDim(double[][][] data, int dimension) {
		double[][] out = new double[data.length][];

		if(0 <= dimension && dimension < data[0][0].length) {
			for(int i = 0; i < data.length; i++) {
				out[i] = new double[data[i].length];

				for(int j = 0; j < data[i].length; j++) {
					out[i][j] = data[i][j][dimension];
				}
			}
		}

		return out;
	}

	/**
	 * Returns the specific third dimension of the 3D array.
	 * @param data 3D array of ints
	 * @param dimension The desired dimension of the 3D array to be returned
	 * @return The appropriate 2D array taken from the parameter 3D array
	 */
	public static int[][] getArrayDim(int[][][] data, int dimension) {
		int[][] out = new int[data.length][];

		if(0 <= dimension && dimension < data[0][0].length) {
			for(int i = 0; i < data.length; i++) {
				out[i] = new int[data[i].length];

				for(int j = 0; j < data[i].length; j++) {
					out[i][j] = data[i][j][dimension];
				}
			}
		}

		return out;
	}

	/**
	 * Prints out a 2D array of doubles to the Console
	 * @param array 2D array of doubles to be printed
	 */
	public static void print2DArray(double[][] array) {		
		int height = array.length;
		int width = array[0].length;

		for(int i = 0; i < height; i++) {
			String line = "Line " + i;
			for(int j = 0; j < width; j++) {
				line += "\t" + array[i][j];
			}
			System.out.println(line);
		}
	} // close method

	/**
	 * Prints out a 2D array of ints to the Console
	 * @param array 2D array of ints to be printed
	 */
	public static void print2DArray(int[][] array) {
		int height = array.length;
		int width = array[0].length;

		for(int i = 0; i < height; i++) {
			String line = "Line " + i;
			for(int j = 0; j < width; j++) {
				line += "\t" + array[i][j];
			}
			System.out.println(line);
		}
	}

	/**
	 * Prints out a 2D array of Objects to the Console
	 * @param array 2D array of Objects to be printed
	 */
	public static void print2DArray(Object[][] array) {
		int height = array.length;
		int width = array[0].length;

		for(int i = 0; i < height; i++) {
			String line = "Line " + i;
			for(int j = 0; j < width; j++) {
				line += "\t" + array[i][j].toString();
			}
			System.out.println(line);
		}
	}

	/**
	 * Prints out a 2D array of booleans to the Console
	 * @param array 2D array of booleans to be printed
	 */
	public static void print2DArray(boolean[][] array) {
		int height = array.length;
		int width = array[0].length;

		for(int i = 0; i < height; i++) {
			String line = "Line " + i;
			for(int j = 0; j < width; j++) {
				line += "\t" + array[i][j];
			}
			System.out.println(line);
		}

	}

	/**
	 * Doubles the size of an array. Array {{1}} becomes {{1, 1}, {1, 1}}
	 * @param arrayIn Array to be doubled in size
	 * @return Array that has been doubled in size
	 */
	public static int[][] double2DArraySize(int[][] arrayIn) {
		int height = arrayIn.length;
		int width = arrayIn[0].length;

		int[][] arrayOut = new int[2*height][];

		for(int i = 0; i < 2*height; i++) {
			arrayOut[i] = new int[width*2];

			for(int j = 0; j < 2*width; j++) {
				int oldHeight = i / 2;
				int oldWidth = j / 2;

				arrayOut[i][j] = arrayIn[oldHeight][oldWidth];
			}
		}
		return arrayOut;
	} // close method

	/**
	 * Doubles the size of an array. Array {{1}} becomes {{1, 1}, {1, 1}}
	 * @param arrayIn Array to be doubled in size
	 * @return Array that has been doubled in size
	 */
	public static double[][] double2DArraySize(double[][] arrayIn) {
		int height = arrayIn.length;
		int width = arrayIn[0].length;

		double[][] arrayOut = new double[2*height][];

		for(int i = 0; i < 2*height; i++) {
			arrayOut[i] = new double[width*2];

			for(int j = 0; j < 2*width; j++) {
				int oldHeight = i / 2;
				int oldWidth = j / 2;

				arrayOut[i][j] = arrayIn[oldHeight][oldWidth];
			}
		}	
		return arrayOut;
	} // close method

	/**
	 * Reduces the size of a 2D boolean array by half.
	 * Example:
	 * 	half2DArraySize({{true, true}, {false, true}}) returns {{true}}
	 * @param arrayIn
	 * @return an array created which is half of the provided matrix's size
	 */
	public static boolean[][] half2DArraySize(boolean[][] arrayIn) {
		boolean[][] arrayOut = new boolean[arrayIn.length/2][];

		int oldHeight = arrayIn.length;
		int oldWidth = arrayIn[0].length;

		for(int i = 0; i < oldHeight / 2; i++) {
			arrayOut[i] = new boolean[oldWidth / 2];

			for(int j = 0; j < oldWidth / 2; j++) {
				arrayOut[i][j] = arrayIn[i * 2][j*2];
			}
		}
		return arrayOut;
	} // close method

	public static int[][] getSubMatrix(int[][] big, int x, int y, int width, int height) {
		int w = width;
		int h = height;
		if(x + width >= big[0].length) {
			w = big[0].length - x;
		}
		if(y + height >= big.length) {
			h = big.length - y;
		}
		int[][] out = new int[h][];

		for(int j = y; (j-y) < out.length; j++) {
			out[j - y] = new int[w];
			for(int i = x; (i-x) < out[j - y].length; i++) {
				out[j - y][i - x] = big[j][i];
			}
		}

		return out;
	} // close getSubMatrix(...)

	public static int[][] createMatrix(int w, int h) {
		int[][] out = new int[h][];

		int count = 0;
		for(int j = 0; j < out.length; j++) {
			out[j] = new int[w];

			for(int i = 0; i < out[j].length; i++) {
				out[j][i] = count;
				count++;
			}
		}

		return out;
	}

} // close class
