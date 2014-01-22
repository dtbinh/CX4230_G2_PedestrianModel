
public class Cell {

	private static int idNextCell;
	
	private int id;
	private char cellType;
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
