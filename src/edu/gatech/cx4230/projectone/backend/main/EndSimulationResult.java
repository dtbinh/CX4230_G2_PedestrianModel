package edu.gatech.cx4230.projectone.backend.main;

public class EndSimulationResult {
	private int termCondition;
	private int totalPeople, peopleFinished, peopleStillInSim;
	private int timeSteps;
	private long time;
	private int score;
	
	public EndSimulationResult(int termCondition, int totalPeople, int peopleFinished, int peopleStillInSim, int x) {
		this.totalPeople = totalPeople;
		this.peopleFinished = peopleFinished;
		this.peopleStillInSim = peopleStillInSim;
		this.termCondition = termCondition;
		
		switch(termCondition) {
		case PedestrianSimulation.TERM_CON_1:
			this.score = x;
			break;
		case PedestrianSimulation.TERM_CON_2:
			this.timeSteps = x;
			break;
		}
	}
	
	public int getValue() {
		int out = -1;
		switch(termCondition) {
		case PedestrianSimulation.TERM_CON_1:
			out = score;
			break;
		case PedestrianSimulation.TERM_CON_2:
			out = timeSteps;
			break;
		}
		return out;
	}
	
	public String toString() {
		String out = "TC: " + termCondition + " People: " + totalPeople + " Var: ";
		switch(termCondition) {
		case PedestrianSimulation.TERM_CON_1:
			out += score;
			break;
		case PedestrianSimulation.TERM_CON_2:
			out += timeSteps;
			break;
		}
		out += " in " + time + " ms";
		return out;
	}

	/**
	 * @return the termCondition
	 */
	public int getTermCondition() {
		return termCondition;
	}

	/**
	 * @param termCondition the termCondition to set
	 */
	public void setTermCondition(int termCondition) {
		this.termCondition = termCondition;
	}

	/**
	 * @return the timeSteps
	 */
	public int getTimeSteps() {
		return timeSteps;
	}

	/**
	 * @param timeSteps the timeSteps to set
	 */
	public void setTimeSteps(int timeSteps) {
		this.timeSteps = timeSteps;
	}

	/**
	 * @return the score
	 */
	public int getScore() {
		return score;
	}

	/**
	 * @param score the score to set
	 */
	public void setScore(int score) {
		this.score = score;
	}

	/**
	 * @return the time
	 */
	public long getTime() {
		return time;
	}

	/**
	 * @param time the time to set
	 */
	public void setTime(long time) {
		this.time = time;
	}

	/**
	 * @return the totalPeople
	 */
	public int getTotalPeople() {
		return totalPeople;
	}

	/**
	 * @param totalPeople the totalPeople to set
	 */
	public void setTotalPeople(int totalPeople) {
		this.totalPeople = totalPeople;
	}

	/**
	 * @return the peopleFinished
	 */
	public int getPeopleFinished() {
		return peopleFinished;
	}

	/**
	 * @param peopleFinished the peopleFinished to set
	 */
	public void setPeopleFinished(int peopleFinished) {
		this.peopleFinished = peopleFinished;
	}

	/**
	 * @return the peopleStillInSim
	 */
	public int getPeopleStillInSim() {
		return peopleStillInSim;
	}

	/**
	 * @param peopleStillInSim the peopleStillInSim to set
	 */
	public void setPeopleStillInSim(int peopleStillInSim) {
		this.peopleStillInSim = peopleStillInSim;
	}

}
