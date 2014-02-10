package edu.gatech.cx4230.projectone.backend.abstraction;

import java.util.ArrayList;
import java.util.List;



public class Cell implements Comparable<Cell> {

	private static int idNextCell; // current value of idNextCell is the number of cells that have been instantiated
	private CellManager cm;
	public static final char BUILDING = 'B';
	public static final String BUILDING_NAME = "BUILDING";
	public static final char MODEL_BUILDING = 'M';
	public static final String MODEL_BUILDING_NAME = "MODEL BUILDING";
	public static final char SIDEWALK = 'S';
	public static final String SIDEWALK_NAME = "SIDEWALK";
	public static final char STREET = 'T';
	public static final String STREET_NAME = "ROAD";
	public static final char CROSSWALK = 'C';
	public static final String CROSSWALK_NAME = "CROSSWALK";
	public static final char ERROR = 'E';
	public static final String ERROR_NAME = "ERROR";
	public static final char DOOR = 'D';
	public static final String DOOR_NAME = "DOOR";
	
	private int id; // unique
	private int csvRow;
	private int x, y;
	private char cellType;
	private String name;
	private Person person;
	private double score;
	private List<Person> targeted; // list of people who want to move to this cell
	
	/**
	 * Cell()
	 * 
	 * default constructor. will use default values for new Cell.
	 */
	public Cell() {
		
		this(0, 0, "default", ERROR, 0, 0);
	}
	
	public Cell(int x, int y) {
		this(x, y, "default", ERROR, 0, 0);
	}
	
	public Cell(int x, int y, String name, char type, int csvRow, int score) {
		id = idNextCell++;
		person = null; // initially, the cell is unoccupied
		//targeted = new List<Person>();
		
		this.x = x;
		this.y = y;
		this.name = name;
		this.cellType = type; // cellType must be from a defined set of types (w-wall, s-sidewalk, etc)
		this.csvRow = csvRow;
		this.score = score;
		
		targeted = new ArrayList<Person>();
	}
	
	public void setProperties(int x, int y, String name, char type, int csvRow) {
		this.x = x;
		this.y = y;
		this.name = name;
		this.cellType = type;
		this.csvRow = csvRow;
		// x, y, type, name
	}
	
	public int getID() {
		return id;
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
	
	public String csvInfo() {
		return x + "," + y + "," + id + "," + cellType + "," + name;
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

	/**
	 * @return the x
	 */
	public int getX() {
		return x;
	}

	/**
	 * @param x the x to set
	 */
	public void setX(int x) {
		this.x = x;
	}

	/**
	 * @return the y
	 */
	public int getY() {
		return y;
	}

	/**
	 * @param y the y to set
	 */
	public void setY(int y) {
		this.y = y;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the cm
	 */
	public CellManager getCm() {
		return cm;
	}

	/**
	 * @param cm the cm to set
	 */
	public void setCm(CellManager cm) {
		this.cm = cm;
	}
	
	/**
	 * 
	 * @return the cell's score
	 */
	public double getScore() {
		return score;		
	}
	
	/**
	 * 
	 * @param score the score to set
	 */
	public void setScore(double score) {
		this.score = score;
	}
	
	public List<Person> getTargeted() {
		return targeted;
	}
	
	public void addToTargeted(Person p) {
		targeted.add(p);
	}
	
	public void clearTargeted() {
		targeted.clear();
	}
	
	/**
	 * compares two Cell objects to determine if they are the same instance of Cell
	 * 
	 * @return true if they are the same Cell, false otherwise
	 */
	public boolean equals(Object o) {
		if(o == null) {
			return false;
		}
		else if(!(o instanceof Cell)) {
			return false;
		}
		else {
			return (this.id == ((Cell) o).getID());
		}
	}
	
	public double getDistanceToCell(Cell c) {
		double dx = this.x - c.getX();
		double dy = this.y - c.getY();
		
		return Math.sqrt(dx*dx + dy*dy);
	}
	
	public boolean isTraversable() {
		boolean out = false;
		if(cellType == CROSSWALK || cellType == DOOR || cellType == SIDEWALK) {
			out = true;
		}
		return out;
	}
	
	public String getTypeName() {
		String out = "";
		switch(cellType) {
		case BUILDING:
			out = BUILDING_NAME;
			break;
		case CROSSWALK:
			out = CROSSWALK_NAME;
			break;
		case DOOR:
			out = DOOR_NAME;
			break;
		case ERROR:
			out = ERROR_NAME;
			break;
		case MODEL_BUILDING:
			out = MODEL_BUILDING_NAME;
			break;
		case SIDEWALK:
			out = SIDEWALK_NAME;
			break;
		case STREET:
			out = STREET_NAME;
			break;
		}
		return out;
	}

	@Override
	public int compareTo(Cell arg0) {
		return (int) (this.score - arg0.getScore());
	}
}
