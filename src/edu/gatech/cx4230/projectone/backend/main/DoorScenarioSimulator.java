package edu.gatech.cx4230.projectone.backend.main;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import edu.gatech.cx4230.projectone.backend.abstraction.Door;
import edu.gatech.cx4230.projectone.backend.abstraction.ExperimentResult;
import edu.gatech.cx4230.projectone.backend.abstraction.SimulationScenario;
import edu.gatech.cx4230.projectone.backend.utilities.ArrayManipulation;
import edu.gatech.cx4230.projectone.backend.utilities.DateTimeHelper;
import edu.gatech.cx4230.projectone.backend.utilities.Logger;

public class DoorScenarioSimulator {
	/**
	 * Dimensions of the model building
	 */
	@SuppressWarnings("unused")
	private static final int BX = 120, BY = 319, BW = 257, BH = 81;

	// Simulation Inputs
	private int peopleToUse = 1500;
	private int endingTimeSteps = 1000;
	private int doorTrials = 30; // Trials of each Door Arrangement
	private boolean debug = false;
	private int termCon = PedestrianSimulation.TERM_CON_1;
	private int boundary = 200;
	private SimulationScenario scen;
	

	public DoorScenarioSimulator() {
		Logger.logLine("Determining Door Positions");
		Logger.logLine(DateTimeHelper.getDateTime());
		Logger.logLine("PeopleToUse: " + peopleToUse);
		Logger.logLine("EndingTimeSteps: " + endingTimeSteps);
		Logger.logLine("Trials of each door: " + doorTrials);
		Logger.logLine("\n");
	}

	public Door determineQuadDoorLocation() {
		Logger.logLine("Determining Quad Door Location");
		List<Door> quadTrials = getQuadTrials();
		return findWinningCell(quadTrials, null);
	}

	private List<Door> getQuadTrials() {
		List<Door> out = new ArrayList<Door>();

		int offset = 5;  // distance from a building corner
		out.add(new Door(Door.QUAD, 1, BX - 1, BY + offset, 1, 4)); // 1
		out.add(new Door(Door.QUAD, 2, BX + offset, BY - 1, 4, 1)); // 2
		out.add(new Door(Door.QUAD, 3, (BX + BW) / 2, BY - 1, 4, 1)); // 3
		out.add(new Door(Door.QUAD, 4, BX +BW - offset, BY - 1, 4, 1)); // 4
		out.add(new Door(Door.QUAD, 5, BX + BW + 1, BY + offset, 1, 4)); // 5		
		return out;
	}

	public Door determineDoubleDoorLocation(Door quad) {
		Logger.logLine("Determining Double Door Location");
		List<Door> doubleTrials = getDoubleTrials(quad);
		List<Door> setDoors = new ArrayList<Door>();
		setDoors.add(quad);

		return findWinningCell(doubleTrials, setDoors);
	}

	private Door findWinningCell(List<Door> trialDoors, List<Door> setDoors) {
		List<ExperimentResult> results = new ArrayList<ExperimentResult>();
		double[] averages = new double[trialDoors.size()];
		double[] stDevs = new double[trialDoors.size()];

		for(int i = 0; i < trialDoors.size(); i++) {
			Door d = trialDoors.get(i);

			Logger.logLine("Door Trial " + d.getId());
			List<Point> doorPoints = d.getPoints();

			if(setDoors != null && !setDoors.isEmpty()) {
				for(Door dSet: setDoors) {
					doorPoints.addAll(dSet.getPoints());
				}
			}

			scen = new SimulationScenario(peopleToUse, debug, termCon, boundary, endingTimeSteps, doorPoints, doorTrials);
			NoVisualizationMain experiment = new NoVisualizationMain(scen);
			ExperimentResult result = experiment.processResults();
			averages[i] = result.getAverage();
			stDevs[i] = result.getStandardDeviation();

			System.out.println(result + "\n");
			results.add(result);
		} // close for

		int winningIndex = ArrayManipulation.findIndexMax(averages);
		Door dWinner = trialDoors.get(winningIndex);
		return dWinner;
	}

	private List<Door> getDoubleTrials(Door quad) {
		List<Door> out = new ArrayList<Door>();
		int doubleTrialsCount = 0;
		int offset = 5;
		int x, y, w, h;

		// 1 - 4
		x = BX - 1;
		w = 1;
		h = 2;
		if(quad.getId() == 1) {
			y = quad.getY() + quad.getHeight() + offset;
		} else {
			y = BY + offset;
		}
		for(int i = 0; i < 4; i++) {
			Door d = new Door(Door.DOUBLE, doubleTrialsCount, x, y, w, h);
			doubleTrialsCount++;
			y += offset;
			out.add(d);
		}

		// 5 - 8
		y = BY - 1;
		w = 2;
		h = 1;
		if(quad.getId() == 2) {
			x = quad.getX() + quad.getWidth() + offset;
		} else {
			x = BX + offset;
		}
		for(int i = 0; i < 4; i++) {
			Door d = new Door(Door.DOUBLE, doubleTrialsCount, x, y, w, h);
			doubleTrialsCount++;
			x += offset;
			out.add(d);
		}

		// 9 - 12
		y = BY - 1;
		w = 2;
		h = 1;
		if(quad.getId() == 3) {
			x = quad.getX() + quad.getWidth() + offset;
		} else {
			x = (BX + BW) / 2;
		}
		for(int i = 0; i < 4; i++) {
			Door d = new Door(Door.DOUBLE, doubleTrialsCount, x, y, w, h);
			doubleTrialsCount++;
			x += offset;
			out.add(d);
		}

		// 13 - 16
		y = BY - 1;
		w = 2;
		h = 1;
		if(quad.getId() == 4) {
			x = quad.getX() + quad.getWidth() + offset;
		} else {
			x = BX + BW - offset;
		}
		for(int i = 0; i < 4; i++) {
			Door d = new Door(Door.DOUBLE, doubleTrialsCount, x, y, w, h);
			doubleTrialsCount++;
			x -= offset;
			out.add(d);
		}

		// 17 - 20
		x = BX + BW + 1;
		w = 1;
		h = 2;
		if(quad.getId() == 5) {
			y = quad.getY() + quad.getHeight() + offset;
		} else {
			y = BY + offset;
		}
		for(int i = 0; i < 4; i++) {
			Door d = new Door(Door.DOUBLE, doubleTrialsCount, x, y, w, h);
			doubleTrialsCount++;
			y += offset;
			out.add(d);
		}

		return out;
	}

	private List<Door> getSingleTrials(List<Door> setDoors) {
		List<Door> out = new ArrayList<Door>();
		int offset = 3;
		int doorCount = 0;
		
		int x, y, w = 1, h = 1;
		
		// 1 - 8
		x = BX - 1;
		y = BY + offset;
		for(int i = 0; i < 8; i++) {
			if(!areDoorsInRange(x, y, setDoors)) {
				Door d = new Door(Door.SINGLE, doorCount, x, y, w, h);
				doorCount++;
				out.add(d);
			}
			y += offset;
		}
		
		
		// 9 - 28
		y = BY - 1;
		x = BX + offset;
		int lim = 12;
		int dx = (BW - 2 * offset) / lim;
		for(int i = 0; i < lim; i++) {
			if(!areDoorsInRange(x, y, setDoors)) {
				Door d = new Door(Door.SINGLE, doorCount, x, y, w, h);
				doorCount++;
				out.add(d);
			}
			x += dx;
		}
		
		
		// 29 - 36
		x = BX + BW + 1;
		y = BY + offset;
		for(int i = 0; i < 8; i++) {
			if(!areDoorsInRange(x, y, setDoors)) {
				Door d = new Door(Door.SINGLE, doorCount, x, y, w, h);
				doorCount++;
				out.add(d);
			}
			y += offset;
		}

		return out;
	}

	public Door determineSingleDoorLocation(List<Door> setDoors) {
		Logger.logLine("Determining Single Door Location");
		List<Door> trialDoors = getSingleTrials(setDoors);
		return findWinningCell(trialDoors, setDoors);
	}
	
	private boolean areDoorsInRange(int x, int y, List<Door> doors) {
		boolean out = false;
		
		for(Door d: doors) {
			if(d.isPointInRange(x, y)) {
				out = true;
				break;
			}
		}
		
		return out;
	}

	public static void main(String[] args) {
		DoorScenarioSimulator dss = new DoorScenarioSimulator();
		Door quadLocation = dss.determineQuadDoorLocation();
		Door doubleLocation = dss.determineDoubleDoorLocation(quadLocation);

		List<Door> setDoors = new ArrayList<Door>();
		setDoors.add(quadLocation);
		setDoors.add(doubleLocation);

		int singleDoorCount = 4;
		for(int i = 0; i < singleDoorCount; i++) {
			Door single = dss.determineSingleDoorLocation(setDoors);
			setDoors.add(single);
		}

		Logger.logLine("\n\n");
		Logger.logLine("Door Locations Set to be:");
		for(Door d: setDoors) {
			Logger.logLine(d.toString());
		}
		
		
		Logger.save("DoorSimulator" + DateTimeHelper.getDateTime());
	} // close Main

}
