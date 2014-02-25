package edu.gatech.cx4230.projectone.backend.abstraction;

import java.util.ArrayList;
import java.util.List;

import edu.gatech.cx4230.projectone.backend.scoring.Path;


public class Person {

	private static int idNextPerson;
	public static final int MAX_STRESS = 1000;
	public static final int MIN_STRESS = 0;
	public static final int MAX_SPEED = 1; // move every x time step
	public static final int MIN_SPEED = 20; // move every x time steps
	public static final double WAIT_INCREASE_STRESS = 0.05;
	public static final int PANIC_THRESHOLD = 500;

	
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
	private Path nextTargets;
	public static final boolean DEBUG = true;
	
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
	
	/**
	 * Increase the stress level by a specific percent.
	 * @param percent
	 */
	public void increaseStress(double percent) {
		int newStress =(int) (stressLevel * (1 + percent));
		if(MIN_STRESS <= newStress && newStress <= MAX_STRESS) {
			stressLevel = newStress;
		} else if(MIN_STRESS > newStress) {
			stressLevel = MIN_STRESS;
		} else if(MAX_STRESS < newStress) {
			stressLevel = MAX_STRESS;
		}
	}
	
	public Cell getLocation() {
		return location;
	}
	
	private void setLocation(Cell newLocation) {
		this.location = newLocation;
		if(location.equals(nextTarget)) {
			setToNextTarget();
		}
	}
	
	public void setToNextTarget() {
		visitedTargets.add(nextTarget);
		if(nextTargets.hasNext()) {
			nextTarget = nextTargets.removeSource();
		} else {
			if(DEBUG) System.out.println(this.toString() + ": No NEXT Target");
			// TODO Person has reached last target
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
	
	public boolean equals(Person p) {
		return (this.id == p.getId());
	}
	
	public boolean hasVisitedTarget(Cell t) {
		return visitedTargets.contains(t);
	}

	/**
	 * @return the nextTargets
	 */
	public Path getNextTargets() {
		return nextTargets;
	}

	/**
	 * @param nextTargets the nextTargets to set
	 */
	public void setNextTargets(Path nt) {
		this.nextTargets = nt;
		if(nt != null) {
			setNextTarget(nextTargets.removeSource());
			if(DEBUG) System.out.println("Person(Ln 215): Next Target: " + nextTarget.toString());
		} else {
			System.err.println("setNextTargets path is null");
		}
	}
}
