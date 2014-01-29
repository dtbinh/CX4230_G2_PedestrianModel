package edu.gatech.cx4230.projectone.backend.abstraction;


public class Cell {

	private static int idNextCell;
	public static final char BUILDING = 'B';
	public static final char SIDEWALK = 'S';
	public static final char STREET = 'T';
	public static final char CROSSWALK = 'C';
	public static final char ERROR = 'E';
	
	private int id;
	private int csvRow;
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
		this(ERROR);
	}
	
	public Cell(char type) {
		id = idNextCell++;
		cellType = type; // cellType must be from a defined set of types (w-wall, s-sidewalk, etc)
		person = null; // initially, the cell is unoccupied
	}
	
	public void setProperties(int x, int y, String name, char type, int csvRow) {
		this.x = x;
		this.y = y;
		this.name = name;
		this.cellType = type;
		this.csvRow = csvRow;
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
	
	public String toString() {
		return "[(" + x + ", " + y + ")\t" + "ID: " + id + "\t" + cellType + ": " + name + "]";
	}

	/**
	 * @return the csvRow
	 */
	public int getCsvRow() {
		return csvRow;
	}

	/**
	 * @param csvRow the csvRow to set
	 */
	public void setCsvRow(int csvRow) {
		this.csvRow = csvRow;
	}
}
