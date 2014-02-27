package edu.gatech.cx4230.projectone.backend.main;

import java.util.ArrayList;
import java.util.List;

import edu.gatech.cx4230.projectone.backend.abstraction.Cell;
import edu.gatech.cx4230.projectone.backend.abstraction.CellManager;

public class DoorScenarioSimulator {
	/**
	 * Dimensions of the model building
	 */
	private static final int BX = 120, BY = 319, BW = 257, BH = 81;
	private CellManager cm;

	public DoorScenarioSimulator() {
		
	}

	private void determineQuadDoorLocation() {
		// First default location

		// Door 6: Quad
		int door6X = (BX + BW) / 2;
		int door6Y = BY - 1;
		int door6W = 4;
		int door6H = 1;
		List<Cell> doors = new ArrayList<Cell>();
		doors.addAll(cm.getCellsInArea(door6X, door6Y, door6W, door6H));
	}


	public static void main(String[] args) {

	}

}
