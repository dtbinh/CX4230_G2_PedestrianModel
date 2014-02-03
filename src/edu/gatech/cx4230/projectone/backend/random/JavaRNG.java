package edu.gatech.cx4230.projectone.backend.random;

import java.util.Random;

/**
 * Random number generator which uses Java's built in Random object.  This
 * object is not for use with the final project, but is instead meant to be
 * used as a test RNG during development.
 * @author tbowling3
 *
 */
public class JavaRNG implements RNGInterface {
	private Random rand;
	
	public JavaRNG() {
		rand = new Random();
	}

	@Override
	public double nextDouble() {
		return rand.nextDouble();
	}

	@Override
	public boolean nextBoolean() {
		return rand.nextBoolean();
	}

	@Override
	public int nextInt() {
		return rand.nextInt();
	}

	@Override
	public int nextInt(int n) {
		return rand.nextInt(n);
	}

}
