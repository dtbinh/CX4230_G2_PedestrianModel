package edu.gatech.cx4230.projectone.backend.abstraction;

import java.util.List;

import edu.gatech.cx4230.projectone.backend.map.MapGridData;

/**
 * Performs Hadlock's Algorithm on the cell array
 * @author tbowling3
 *
 */
public class HadlockOperator {
	private CellManager cm;
	private List<Cell> visitedCells;
	
	
	public HadlockOperator(CellManager cake) {
		this.cm = cake;
	}
	
	public void cellFilling(Cell here, Cell dest) {
		
	}
	
	public static void main(String[] args) {
		MapGridData mgd = new MapGridData();
		CellManager cm = mgd.getCellManager();
		System.out.println("Traversable cells count: " + cm.getAllTraversableCells().size());
		
		Cell source = cm.getCell(122, 315);
		Cell dest = cm.getCell(372, 315);
		
		
	}

}
