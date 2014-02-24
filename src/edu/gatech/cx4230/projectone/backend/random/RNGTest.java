package edu.gatech.cx4230.projectone.backend.random;

/**
 * Object to test perform some simple tests on the RNG
 * @author tbowling3
 *
 */
public class RNGTest {

	public static void main(String[] args) {
		RNGTest test1 = new RNGTest();
		test1.testIntInRange();

	}

	public void testIntInRange() {
		AbstractRNG rng = new CustomRNG();

		int simRuns = 100000;
		int randomLimit = 6;
		int[] counts = new int[randomLimit + 1];

		for(int i = 0; i < simRuns; i++) {
			int rand = rng.nextInt(randomLimit);
			if(0 <= rand && rand < counts.length) {
				counts[rand]++;
			}
		}

		for(int i = 0; i < counts.length; i++) {
			System.out.println(i + ": " + counts[i]);
		}
	}

}
