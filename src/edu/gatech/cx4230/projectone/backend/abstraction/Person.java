package edu.gatech.cx4230.projectone.backend.abstraction;

import java.util.ArrayList;
import java.util.List;


public class Person {

	private static int idNextPerson;
	public static final int MAX_STRESS = 1000;
	public static final int MIN_STRESS = 0;
	public static final int MAX_SPEED = 1; // move every x time step
	public static final int MIN_SPEED = 20; // move every x time steps
	
	private int id;
	private Cell location;
	private Cell nextLocation;
	private int currSpeed;
	private int minSpeed;
	private int maxSpeed;
	private int stressLevel;
	private int timeLastMove;
	private List<Cell> visitedTargets;
	private Cell nextTarget;
	
	/**
	 * Person()
	 * 
	 * default constructor. will use default values for new Person.
	 */
	public Person() {
		this(null, 0, 0, 0, 0, 0);
	}
	
	public Person(Cell startCell, int startSpeed, int minS, int maxS, int startStress, int startTime) {
		id = idNextPerson++;
		location = startCell;
		currSpeed = startSpeed;
		minSpeed = minS;
		maxSpeed = maxS;
		stressLevel = startStress;
		timeLastMove = startTime;
		visitedTargets = new ArrayList<Cell>();
	}
	
	public Cell getLocation() {
		return location;
	}
	
	private void setLocation(Cell newLocation) {
		this.location = newLocation;
		if(location.equals(nextTarget)) {
			visitedTargets.add(nextTarget);
			nextTarget = null;
		}
	}
	
	public Cell getNextLocation() {
		return nextLocation;
	}
	
	public void setNextLocation(Cell nextLocation) {
		this.nextLocation = nextLocation;
	}
	
	public double getCurrSpeed() {
		return currSpeed;
	}
	
	public void setCurrSpeed(int newSpeed) {
		if(minSpeed <= newSpeed && newSpeed <= maxSpeed) {
			currSpeed = newSpeed;
		} else if(newSpeed < minSpeed) {
			currSpeed = minSpeed;
		} else if(maxSpeed < newSpeed) {
			currSpeed = maxSpeed;
		}
		
	}
	
	public int getStressLevel() {
		return stressLevel;
	}
	
	public void setStressLevel(int newStressLevel) {
		stressLevel = newStressLevel;
	}

	public int getTimeLastMove() {
		return timeLastMove;
	}
	
	public void setTimeLastMove(int newTime) {
		timeLastMove = newTime;
	}
	
	/**
	 * @return the minSpeed
	 */
	public int getMinSpeed() {
		return minSpeed;
	}

	/**
	 * @param minSpeed the minSpeed to set
	 */
	public void setMinSpeed(int minSpeed) {
		this.minSpeed = minSpeed;
	}

	/**
	 * @return the maxSpeed
	 */
	public int getMaxSpeed() {
		return maxSpeed;
	}

	/**
	 * @param maxSpeed the maxSpeed to set
	 */
	public void setMaxSpeed(int maxSpeed) {
		this.maxSpeed = maxSpeed;
	}

	/**
	 * @return the visitedTargets
	 */
	public List<Cell> getVisitedTargets() {
		return visitedTargets;
	}

	/**
	 * @param visitedTargets the visitedTargets to set
	 */
	public void setVisitedTargets(List<Cell> visitedTargets) {
		this.visitedTargets = visitedTargets;
	}
	
	public void addVisitedTarget(Cell c) {
		if(c != null) visitedTargets.add(c);
	}

	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * @return the nextTarget
	 */
	public Cell getNextTarget() {
		return nextTarget;
	}

	/**
	 * @param nextTarget the nextTarget to set
	 */
	public void setNextTarget(Cell nextTarget) {
		this.nextTarget = nextTarget;
	}

	public boolean isMoveable(int time) {
		return (time - timeLastMove) >= currSpeed;
	}
	
	public void move(int time, Cell newLocation) {
		setTimeLastMove(time);
		setLocation(newLocation);
	}
	
	public String toString() {
		String out = "(" + id;
		if(location != null) {
			out += " (" + location.getX() + "," + location.getY() + ")";
		}
		out += "\tSpeed: " + currSpeed + ")";
		return out;
	}
	
	public boolean hasVisitedTarget(Cell t) {
		return visitedTargets.contains(t);
	}
}
