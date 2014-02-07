package edu.gatech.cx4230.projectone.backend.errorchecking;

import java.util.ArrayList;
import java.util.List;

import edu.gatech.cx4230.projectone.backend.abstraction.Cell;
import edu.gatech.cx4230.projectone.backend.abstraction.CellManager;
import edu.gatech.cx4230.projectone.backend.map.MapGridData;
import edu.gatech.cx4230.projectone.backend.utilities.ListHelper;

public class MapGridCheck {
	private CellManager cm;

	public MapGridCheck(CellManager cm) {
		this.cm = cm;
	}

	/**
	 * Prints out all the Cells that haven't been updated (cellType == ERROR)
	 */
	public void runCheck1() {
		Cell[][] cells = cm.getCells();
		List<Cell> errorCells = new ArrayList<Cell>();

		System.out.print("Running Check 1");
		for(int j = 0; j < cells.length; j++) {
			System.out.println("Running row " + j);
			for(int i = 0; i < cells[j].length; i++) {
				Cell c = cells[j][i];

				if(c.getType() == Cell.ERROR) {
					errorCells.add(c);
				}
			}
		}
		System.out.println("\nErrors: " + errorCells.size());
		ListHelper.writeListToCSV(errorCells, "/Users/ducttapeboro/Desktop/ErrorCells.csv");
		System.out.println("Done!");
	}

	public static void main(String[] args) {
		MapGridData mgd = new MapGridData();
		MapGridCheck check = new MapGridCheck(mgd.getCellManager());
		check.runCheck1();
	}

}
