package edu.gatech.cx4230.projectone.backend.main;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import edu.gatech.cx4230.projectone.backend.abstraction.ExperimentResult;
import edu.gatech.cx4230.projectone.backend.abstraction.SimulationScenario;

public class DoorScenarioSimulator {
	/**
	 * Dimensions of the model building
	 */
	private static final int BX = 120, BY = 319, BW = 257, BH = 81;
	
	// Simulation Inputs
	private int peopleToUse = 100;
	private boolean debug = false;
	private int termCon = PedestrianSimulation.TERM_CON_1;
	private int boundary = 200;
	private int endingTimeSteps = 500;
	private List<Point> doorPoints;
	private SimulationScenario scen;
	private int doorTrials = 3;

	public DoorScenarioSimulator() {
		
	}

	public void determineQuadDoorLocation() {
		// First default location

		// Door 6: Quad
		int door6X = (BX + BW) / 2;
		int door6Y = BY - 1;
		int door6W = 4;
		int door6H = 1;
		doorPoints = new ArrayList<Point>();
		doorPoints.addAll(getLocalesInArea(door6X, door6Y, door6W, door6H));
		
		scen = new SimulationScenario(peopleToUse, debug, termCon, boundary, endingTimeSteps, doorPoints, doorTrials);
		
		NoVisualizationMain experiment = new NoVisualizationMain(scen);
		ExperimentResult result = experiment.processResults();
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
		DoorScenarioSimulator dss = new DoorScenarioSimulator();
		dss.determineQuadDoorLocation();
	}

}
