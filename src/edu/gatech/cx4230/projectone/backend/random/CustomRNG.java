package edu.gatech.cx4230.projectone.backend.random;

/**
 * RNG using xorshift.
 * @author telliott8
 *
 */
public class CustomRNG extends AbstractRNG {
	
	public CustomRNG() {
		
	}
	
	/**
	 * Generates random seed based on system time. Variant of xorshift.
	 */
	private double RNG() {
		long a = System.nanoTime() % 100000;
		long b = System.nanoTime() % 200000;
		long c = System.nanoTime() % 400000;
		long d = System.nanoTime() % 800000;
		
		long t = a ^ (a << 11);
		a = b;
		b = c;
		c = d;
		d = (d ^ (d >>> 19) ^ (t ^ (t >>> 8)));
		return d;
	}

	/**
	 * Returns random integer within range of possible doubles.
	 */
	public double nextDouble() {
		long x = RNG();
		return x % Double.MAX_VALUE;
	}

	/**
	 * Returns true if random seed is even, else false.
	 */
	public boolean nextBoolean() {
		long x = RNG();
		if (x % 2 == 0)
			return true;
		else return false;
	}

	/**
	 * Returns random integer within range of integers.
	 */
	public int nextInt() {
		long x = RNG();
		return (int) x % 2147483647;
	}

	/**
	 * Returns random integer between 0 (inclusive) and n (exclusive).
	 */
	public int nextInt(int n) {
		long x = RNG();
		return (int) x % n;
	}
	
	/**
	 * Returns member of normal distribution of specified mean and standard
	 * deviation. Based on Box-Muller transform.
	 */
	public double nextInNormal(double mean, double sd) {
		double u = 2 * ((RNG() % 100000) / 100000) - 1;
		double v = 2 * ((RNG() % 100000) / 100000) - 1;
		double r = u * u + v * v;
		
		//reject
		if (r == 0 || r > 1) return nextInNormal(mean, sd);
		
		//accept
		double c = Math.sqrt(-2 * Math.log(r)/r);
		return u * c;
	}

}
