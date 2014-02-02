package edu.gatech.cx4230.projectone.backend.abstraction;


public class Person {

	private static int idNextPerson;
	
	private int id;
	private Cell location;
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
	
	public double getCurrSpeed() {
		return currSpeed;
	}
	
	public void setCurrSpeed(double newSpeed) {
		currSpeed = newSpeed;
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
}
