package edu.gatech.cx4230.projectone.backend.random;

import java.util.Random;

/**
 * RNG using xorshift.
 * @author telliott8
 *
 */
public class JavaRNG extends AbstractRNG {
	private Random rand;
	
	
	public JavaRNG() {}
	
	private long RNG() {
		long a = System.nanoTime() % 100000;
		long b = System.nanoTime() % 200000;
		long c = System.nanoTime() % 400000;
		long d = System.nanoTime() % 800000;
		
		long t = a ^ (a << 11);
		a = b;
		b = c;
		c = d;
		d = (d ^ (d >>> 19) ^ (t ^ (t >>> 8)));
		
	}

	@Override
	/**
	 * Returns random integer across entire range of doubles.
	 */
	public double nextDouble() {
		public int nextInt() {
			long x = RNG();
			return (double) x % Double.MAX_VALUE;
	}

	@Override
	/**
	 * Returns true if randomly generated number is even.
	 */
	public boolean nextBoolean() {
		long x = RNG();
		if (x % 2 = 0)
			return true;
		else return false;
		
	}

	@Override
	/**
	 * Returns random integer across entire range of ints.
	 */
	public int nextInt() {
		long x = RNG();
		return (int) x % 2147483647;
	}

	@Override
	/**
	 * Returns random int between 0 (inclusive) and n (exclusive).
	 */
	public int nextInt(int n) {
		long x = RNG();
		return (int) x & n;
	}

}
