package edu.gatech.cx4230.projectone.backend.random;

/**
 * RNG using xorshift.
 * @author telliott8
 *
 */
public class CustomRNG extends AbstractRNG {
	
	public CustomRNG() {
		
	}
	
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
		return d;
	}

	@Override
	public double nextDouble() {
		long x = RNG();
		return (double) x % Double.MAX_VALUE;
	}

	@Override
	public boolean nextBoolean() {
		long x = RNG();
		if (x % 2 == 0)
			return true;
		else return false;
	}

	@Override
	public int nextInt() {
		long x = RNG();
		return (int) x % 2147483647;
	}

	@Override
	public int nextInt(int n) {
		long x = RNG();
		return (int) x % n;
	}

}
