package edu.gatech.cx4230.projectone.backend.random;

import java.util.Arrays;

/**
 * Interface for the Random Number Generator
 * @author tbowling3
 *
 */
public abstract class AbstractRNG {
	
	/**
	 * Generates a random number between 0 and 1, exclusive
	 * @return
	 */
	public abstract double nextDouble();
	
	/**
	 * Generates a random boolean
	 * @return
	 */
	public abstract boolean nextBoolean();
	
	/**
	 * Generates an int from the generator's random sequence
	 * @return
	 */
	public abstract int nextInt();
	
	/**
	 * Returns the next pseudo-random, uniformly distributed int value from 
	 * this random number generator's sequence.
	 * @param n the bound on the random number to be returned. Must be positive.
	 * @return
	 */
	public abstract int nextInt(int n);
	
	/**
	 * Generates the next double in the sequence with a range [min, max)
	 * @param min
	 * @param max
	 * @return the next double in the sequence with a range [min, max)
	 */
	public double nextDoubleInRange(double min, double max) {
		return min + nextDouble() * ((max - min) + 1);
	}
	
	/**
	 * Returns a sorted array of a specific length of the next doubles in the sequence
	 * @param n the length of the array to be returned
	 * @return a sorted array of a specific length of the next doubles in the sequence
	 */
	public double[] nextDoublesArraySorted(int n) {
		double[] a = nextDoublesArray(n);
		Arrays.sort(a);
		
		return a;
	}
	
	/**
	 * Returns an array of a specific length of the next doubles in the sequence
	 * @param n the length of the array to be returned
	 * @return an array of a specific length of the next doubles in the sequence
	 */
	public double[] nextDoublesArray(int n) {
		double[] out = new double[n];
		
		for(int i = 0; i < out.length; i++) {
			out[i] = nextDouble();
		}
		
		return out;
	}
	
	/**
	 * 
	 * @param n
	 * @param min
	 * @param max
	 * @return
	 */
	public double[] nextDoublesArray(int n, double min, double max) {
		double[] out = new double[n];
		
		for(int i = 0; i < out.length; i++) {
			out[i] = nextDoubleInRange(min, max);
		}
		
		return out;
	}
	
	/**
	 * 
	 * @param n
	 * @param min
	 * @param max
	 * @return
	 */
	public double[] nextDoublesArraySorted(int n, double min, double max) {
		double[] a = nextDoublesArray(n, min, max);
		Arrays.sort(a);
		
		return a;
	}
	
	/**
	 * Generates the next int in the sequence with a range [min, max)
	 * @param min
	 * @param max
	 * @return the next int in the sequence with a range [min, max)
	 */
	public int nextIntInRange(int min, int max) {
		return (int) (min + nextDouble() * ((max - min) + 1));
	}
	
	/**
	 * Returns a sorted array of a specific length of the next ints in the sequence
	 * @param n the length of the array to be returned
	 * @return a sorted array of a specific length of the next ints in the sequence
	 */
	public int[] nextIntArraySorted(int n) {
		int[] a = nextIntsArray(n);
		Arrays.sort(a);
		
		return a;
	}
	
	/**
	 * Returns an array of a specific length of the next ints in the sequence
	 * @param n the length of the array to be returned
	 * @return an array of a specific length of the next doubles in the sequence
	 */
	public int[] nextIntsArray(int n) {
		int[] out = new int[n];
		
		for(int i = 0; i < out.length; i++) {
			out[i] = nextInt();
		}
		
		return out;
	}
	
	/**
	 * 
	 * @param n
	 * @param min
	 * @param max
	 * @return
	 */
	public int[] nextIntsArray(int n, int min, int max) {
		int[] out = new int[n];
		
		for(int i = 0; i < out.length; i++) {
			out[i] = nextIntInRange(min, max);
		}
		
		return out;
	}
	
	/**
	 * 
	 * @param n
	 * @param min
	 * @param max
	 * @return
	 */
	public int[] nextIntsArraySorted(int n, int min, int max) {
		int[] a = nextIntsArray(n, min, max);
		Arrays.sort(a);
		
		return a;
	}
	
	
	

}
