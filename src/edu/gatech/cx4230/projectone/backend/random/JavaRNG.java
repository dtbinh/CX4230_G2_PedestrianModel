package edu.gatech.cx4230.projectone.backend.random;

import java.util.Random;

/**
 * Random number generator which uses Java's built in Random object.  This
- * object is not for use with the final project, but is instead meant to be
- * used as a test RNG during development.
- * @author tbowling3
 *
 */
public class JavaRNG extends AbstractRNG {
	private Random rand;


	public JavaRNG() {
		rand = new Random();
	}

	@Override
	/**
	 * Returns random integer across entire range of doubles.
	 */
	public double nextDouble() {
		return rand.nextDouble();
	}

	@Override
	/**
	 * Returns true if randomly generated number is even.
	 */
	public boolean nextBoolean() {
		return rand.nextBoolean();
	}

	@Override
	/**
	 * Returns random integer across entire range of ints.
	 */
	public int nextInt() {
		return rand.nextInt();
	}

	@Override
	/**
	 * Returns random int between 0 (inclusive) and n (exclusive).
	 */
	public int nextInt(int n) {
		return rand.nextInt(n);
	}

	@Override
	public double nextInNormal(double mean, double sd) {
		// TODO Auto-generated method stub
		return 0;
	}

}
