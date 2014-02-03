package edu.gatech.cx4230.projectone.backend.random;

/**
 * Interface for the Random Number Generator
 * @author tbowling3
 *
 */
public interface RNGInterface {
	
	/**
	 * Generates a random number between 0 and 1, exclusive
	 * @return
	 */
	public double nextDouble();
	
	/**
	 * Generates a random boolean
	 * @return
	 */
	public boolean nextBoolean();
	
	/**
	 * Generates an int from the generator's random sequence
	 * @return
	 */
	public int nextInt();
	
	/**
	 * Returns the next pseudorandom, uniformly distributed int value from 
	 * this random number generator's sequence.
	 * @param n the bound on the random number to be returned. Must be positive.
	 * @return
	 */
	public int nextInt(int n);
	
	
	

}
