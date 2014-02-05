package edu.gatech.cx4230.projectone.backend.abstraction;


public class Person {

	private static int idNextPerson;
	public static final int MAX_STRESS = 1000;
	public static final int MIN_STRESS = 0;
	public static final double MAX_SPEED = 1; // move every x time step
	public static final double MIN_SPEED = 20; // move every x time steps
	
	private int id;
	private Cell location;
	private Cell nextLocation;
	private double currSpeed;
	private double minSpeed;
	private double maxSpeed;
	private int stressLevel;
	private int timeLastMove;
	
	/**
	 * Person()
	 * 
	 * default constructor. will use default values for new Person.
	 */
	public Person() {
		this(null, 0, 0, 0, 0, 0);
	}
	
	public Person(Cell startCell, double startSpeed, double minS, double maxS, int startStress, int startTime) {
		id = idNextPerson++;
		location = startCell;
		currSpeed = startSpeed;
		minSpeed = minS;
		maxSpeed = maxS;
		stressLevel = startStress;
		timeLastMove = startTime;
	}
	
	public Cell getLocation() {
		return location;
	}
	
	private void setLocation(Cell newLocation) {
		location = newLocation;
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
	
	public void setCurrSpeed(double newSpeed) {
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
	
	public boolean isMoveable(int time) {
		return (time - timeLastMove) >= currSpeed;
	}
	
	public void move(int time, Cell newLocation) {
		setTimeLastMove(time);
		setLocation(newLocation);
	}
	
	public String toString() {
		return "(" + id + "\tSpeed: " + currSpeed + ")";
	}
}
