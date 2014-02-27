package edu.gatech.cx4230.projectone.backend.main;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

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
		List<Point> doors = new ArrayList<Point>();
		doors.addAll(getLocalesInArea(door6X, door6Y, door6W, door6H));
	}
	
	
	private List<Point> getLocalesInArea(int x, int y, int w, int h) {
		List<Point> out = new ArrayList<Point>();
		
		for(int j = y; j < (y + h); j++) {
			for(int i = x; i < (x + w); i++) {
				out.add(new Point(i,j));
			} // close i-for
		} // close j-for
		return out;
	}


	public static void main(String[] args) {

	}

}
