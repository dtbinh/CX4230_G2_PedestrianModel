package edu.gatech.cx4230.projectone.backend.abstraction;

import edu.gatech.cx4230.projectone.backend.map.VisualizationMain;
import processing.core.PApplet;

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
	
	public void draw(PApplet p) {
		p.color(50);
		if(cellType == BUILDING) {
			p.fill(0,0,100);
		} else if(cellType == CROSSWALK) {
			p.fill(200, 200, 200);
		} else if(cellType == ERROR) {
			p.fill(255, 0, 0);
		} else if(cellType == SIDEWALK) {
			p.fill(255,255,0);
		} else if(cellType == STREET) {
			p.fill(40, 40, 40);
		}
		
		p.rect(x * VisualizationMain.CELL_SIZE, y * VisualizationMain.CELL_SIZE, VisualizationMain.CELL_SIZE, VisualizationMain.CELL_SIZE);
		if(person != null) {
			person.draw(p, x, y);
		}
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
		return "(" + x + ", " + y + ")\t" + "ID: " + id + "\t" + name;
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