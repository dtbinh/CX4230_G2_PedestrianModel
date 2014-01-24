package edu.gatech.cx4230.projectone.backend.abstraction;

public class Cell {

	private static int idNextCell;
	
	private int id;
	private int x, y;
	private char cellType;
	private String name;
	private Person person;
	
	/**
	 * Cell()
	 * 
	 * default constructor. will use default values for new Cell.
	 */
	public Cell() {
		this('w');
	}
	
	public Cell(char type) {
		id = idNextCell++;
		cellType = type; // cellType must be from a defined set of types (w-wall, s-sidewalk, etc)
		person = null; // initially, the cell is unoccupied
	}
	
	public void setProperties(int x, int y, String name, char type) {
		this.x = x;
		this.y = y;
		this.name = name;
		this.cellType = type;
		// x, y, type, name
	}
	
	public char getType() {
		return cellType;
	}
	
	public void setType(char newType) {
		cellType = newType;
	}
	
	public Person getPerson() {
		return person;
	}
	
	public void setPerson(Person newPerson) {
		person = newPerson;
	}
	
	public boolean isOccupied() {
		return (person != null);
	}
}
