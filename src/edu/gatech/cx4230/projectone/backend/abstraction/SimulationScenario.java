package edu.gatech.cx4230.projectone.backend.abstraction;

import java.awt.Point;
import java.util.List;

public class SimulationScenario {
	private int peopleInBuilding;
	private boolean debug;
	private int terminiatingCondition;
	private int boundaryLine;
	private int endingTimeStep;
	private int trials;
	private List<Point> doors;
	
	public SimulationScenario(int peeps, boolean d, int termCon, int bound, int ending, List<Point>doors, int trials) {
		this.peopleInBuilding = peeps;
		this.debug = d;
		this.terminiatingCondition = termCon;
		this.boundaryLine = bound;
		this.endingTimeStep = ending;
		this.doors = doors;
		this.trials = trials;
	}
	
	/**
	 * @return the peopleInBuilding
	 */
	public int getPeopleInBuilding() {
		return peopleInBuilding;
	}
	/**
	 * @param peopleInBuilding the peopleInBuilding to set
	 */
	public void setPeopleInBuilding(int peopleInBuilding) {
		this.peopleInBuilding = peopleInBuilding;
	}
	/**
	 * @return the debug
	 */
	public boolean isDebug() {
		return debug;
	}
	/**
	 * @param debug the debug to set
	 */
	public void setDebug(boolean debug) {
		this.debug = debug;
	}
	/**
	 * @return the terminiatingCondition
	 */
	public int getTerminiatingCondition() {
		return terminiatingCondition;
	}
	/**
	 * @param terminiatingCondition the terminiatingCondition to set
	 */
	public void setTerminiatingCondition(int terminiatingCondition) {
		this.terminiatingCondition = terminiatingCondition;
	}
	/**
	 * @return the boundaryLine
	 */
	public int getBoundaryLine() {
		return boundaryLine;
	}
	/**
	 * @param boundaryLine the boundaryLine to set
	 */
	public void setBoundaryLine(int boundaryLine) {
		this.boundaryLine = boundaryLine;
	}
	/**
	 * @return the endingTimeStep
	 */
	public int getEndingTimeStep() {
		return endingTimeStep;
	}
	/**
	 * @param endingTimeStep the endingTimeStep to set
	 */
	public void setEndingTimeStep(int endingTimeStep) {
		this.endingTimeStep = endingTimeStep;
	}

	/**
	 * @return the doors
	 */
	public List<Point> getDoors() {
		return doors;
	}

	/**
	 * @param doors the doors to set
	 */
	public void setDoors(List<Point> doors) {
		this.doors = doors;
	}

	/**
	 * @return the trials
	 */
	public int getTrials() {
		return trials;
	}

	/**
	 * @param trials the trials to set
	 */
	public void setTrials(int trials) {
		this.trials = trials;
	}

}
