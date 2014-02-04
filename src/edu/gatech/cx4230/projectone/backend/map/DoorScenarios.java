package edu.gatech.cx4230.projectone.backend.map;

import java.util.ArrayList;
import java.util.List;

import edu.gatech.cx4230.projectone.backend.abstraction.Cell;

public class DoorScenarios {
	private Cell[][] cells;
	public static final int DOOR_SCENARIO = 1;
	
	/**
	 * Dimensions of the model building
	 */
	private static final int BX = 120, BY = 390, BW = 257, BH = 81;

	public DoorScenarios(Cell[][] cells) {
		this.cells = cells;
	}


	private List<Cell> scenario1() {
		List<Cell> out = new ArrayList<Cell>();
		
		// Door 1: Single
		int door1X = BX;
		int door1Y = BY + 5;
		int door1W = 1;
		int door1H = 1;
		out.addAll(getCellsInArea(door1X, door1Y, door1W, door1H));

		// Door 2: Single
		int door2X = BX + BW;
		int door2Y = BY + 5;
		int door2W = 1;
		int door2H = 1;
		out.addAll(getCellsInArea(door2X, door2Y, door2W, door2H));
		
		// Door 3: Single
		int door3X = BX;
		int door3Y = BY + BH - 5;
		int door3W = 1;
		int door3H = 1;
		out.addAll(getCellsInArea(door3X, door3Y, door3W, door3H));
		
		// Door 4: Single
		int door4X = BX + BW;
		int door4Y = BY + BH - 5;
		int door4W = 1;
		int door4H = 1;
		out.addAll(getCellsInArea(door4X, door4Y, door4W, door4H));
		
		// Door 5: Double
		int door5X = (BX + BW) / 2;
		int door5Y = BY + BH;
		int door5W = 2;
		int door5H = 1;
		out.addAll(getCellsInArea(door5X, door5Y, door5W, door5H));
		
		// Door 6: Quad
		int door6X = (BX + BW) / 2;
		int door6Y = BY;
		int door6W = 4;
		int door6H = 1;
		out.addAll(getCellsInArea(door6X, door6Y, door6W, door6H));
		
		return out;
	}
	
	private List<Cell> scenario2() {
		List<Cell> out = new ArrayList<Cell>();
		// TODO Create door scenario
		return out;
	}
	
	private List<Cell> scenario3() {
		List<Cell> out = new ArrayList<Cell>();
		// TODO Create door scenario
		return out;
	}
	
	public List<Cell> getScenarios() {
		return getScenario(DOOR_SCENARIO);
	}
	

	public List<Cell> getScenario(int s) {
		List<Cell> out = null;

		switch(s) {
		case 1:
			out = scenario1();
			break;
		case 2:
			out = scenario2();
			break;
		case 3:
			out = scenario3();
			break;
		}
		return out;
	}
	
	private List<Cell> getCellsInArea(int x, int y, int width, int height) {
		List<Cell> out = new ArrayList<Cell>();
		
		for(int j = y; j < (y + height); j++) {
			for(int i = x; i < (x + width); i++) {
				if(0 <= j && j < cells.length) { // Indexing check
					if(0 <= i && i < cells[j].length) { // Indexing check
						out.add(cells[j][i]);
					} // close i-if
				} // close j-if
			} // close i-for
		} // close j-for
		return out;
	} // close getCellsInArea()
} // close DoorScenarios class
