package edu.gatech.cx4230.projectone.backend.abstraction;

public class ExperimentResult {
	private double average;
	private double standardDeviation;
	private int[] scores;
	
	public ExperimentResult(double ave, double stDev, int[] scores) {
		this.average = ave;
		this.standardDeviation = stDev;
		this.scores = scores;
	}
	
	public String toString() {
		return "Scores Average: " + average + " St Dev: " + standardDeviation;
	}

	/**
	 * @return the average
	 */
	public double getAverage() {
		return average;
	}

	/**
	 * @param average the average to set
	 */
	public void setAverage(double average) {
		this.average = average;
	}

	/**
	 * @return the standardDeviation
	 */
	public double getStandardDeviation() {
		return standardDeviation;
	}

	/**
	 * @param standardDeviation the standardDeviation to set
	 */
	public void setStandardDeviation(double standardDeviation) {
		this.standardDeviation = standardDeviation;
	}

	/**
	 * @return the scores
	 */
	public int[] getScores() {
		return scores;
	}

	/**
	 * @param scores the scores to set
	 */
	public void setScores(int[] scores) {
		this.scores = scores;
	}

}
