package edu.gatech.cx4230.projectone.backend.main;

import java.util.ArrayList;
import java.util.List;

import edu.gatech.cx4230.projectone.backend.abstraction.Cell;
import edu.gatech.cx4230.projectone.backend.abstraction.CellManager;
import edu.gatech.cx4230.projectone.backend.abstraction.Person;
import edu.gatech.cx4230.projectone.backend.abstraction.TargetScenarios;
import edu.gatech.cx4230.projectone.backend.map.MapGridData;
import edu.gatech.cx4230.projectone.backend.map.VisualizationMain;


public class PedestrianSimulation {

	public static final int BUILDING_CAPACITY = 1500;

	private int totalPeople;
	private int countPeopleInBuilding;

	// list of people currently in simulation 
	private ArrayList<Person> people;

	// list of people to move at each time step
	private List<Person> peopleToMove;

	// Manager for the 2-D grid of cells in the simulation
	private CellManager cm;

	// Visualization of the simulation
	private VisualizationMain vis;

	// Simulation scenario
	public static final int SCENARIO = 1;
	
	private SimulationThread simThread;
	private boolean peopleAvailable = false;
	private boolean timeChanged = false;
	
	public PedestrianSimulation(VisualizationMain vis) {
		this.vis = vis;
		
		totalPeople = BUILDING_CAPACITY; // this may be variable for each simulation, with BUILDING_CAPACITY as the max
		countPeopleInBuilding = totalPeople;
		people = new ArrayList<Person>();
		peopleToMove = new ArrayList<Person>();

		// Read in and create the map grid
		MapGridData mgd = new MapGridData();
		vis.setMarkers(mgd.getCellMarkers());
		cm = mgd.getCellManager();
		
		// Load and Set target cells
		TargetScenarios ts = new TargetScenarios(cm.getCells());
		List<Cell> targets = null;
		switch(SCENARIO) {
		case 1: // Maximize Distance
			targets = ts.maximizeDistance();
		case 2: // specificTargetPoints1
			targets = ts.specificTargetPoints1();
		}
		cm.setCellsScores(targets);
		
		// TODO Load and Set Door locations on Model Building
		
		
		peopleAvailable = false;
		simThread = new SimulationThread(PedestrianSimulation.this, 50, "Ped Sim Thread");
		simThread.start();
	} // close constructor

	/**
	 * spawnPerson
	 * 
	 * spawns a new Person
	 * uses random number generation to determine start location, start speed
	 * 
	 * @return a new Person object
	 */
	public Person spawnPerson() {
		return new Person(); // right now this uses the default constructor, but it should take in start values for the new Person
	}

	/**
	 * spawnPeople
	 * 
	 * spawns new Person objects and adds them to the people ArrayList
	 * 
	 * @param numPeople the number of new Person objects to spawn
	 */
	public void spawnPeople(int numPeople) {
		for(int i = 0; i < numPeople; i++) {
			Person p = spawnPerson();
			people.add(p);
		}
	}

	/**
	 * movePeople
	 * 
	 * handles moving active People objects in the simulation.
	 * this method is called at each timeStep()
	 */
	public void movePeople() {
		int currStep = simThread.getCurrTimeStep();
		// iterate over all people currently in the simulation
		for(Person p : people) {
			if(p.isMoveable(currStep)) {
				calculateNextMove(p);
				peopleToMove.add(p);
			}
		}
		//while(!peopleToMove.isEmpty()) {	// implement this if person must find alternate move instead of waiting until next time step
		for(Person p : peopleToMove) {
			// handle movement of people, potential collisions with other people, etc
			Cell nextCell = p.getNextLocation();
			if(nextCell == null) {
				// TODO If the next Cell hasn't been specified
			} else {
				if(nextCell.getTargeted().size() == 1) {
					p.move(currStep, nextCell);
					nextCell.setPerson(p);
					peopleToMove.remove(p);
				}
				else {
					List<Person> targeted = nextCell.getTargeted();
					Person winner = targeted.get(0);
					for(Person t : targeted) {
						if(t.getCurrSpeed() > winner.getCurrSpeed())
							winner = t;
					}
					winner.move(currStep, nextCell);
					nextCell.setPerson(winner);
					peopleToMove.remove(targeted); // this assumes "losers" will wait until next time step
				}
				nextCell.clearTargeted();
			}
		} // close Person for
		
		peopleAvailable = true;
	} // close movePeople()

	public void calculateNextMove(Person p) {
		// determine Person's most desirable next move
		Cell currCell = p.getLocation();
		if(currCell != null) {
			ArrayList<Cell> neighbors = currCell.getCm().getNeighborAll(currCell);
			if(neighbors.size() > 0) {
				Cell nextCell = neighbors.get(0);
				for(Cell c: neighbors) {
					if(c.getScore() > nextCell.getScore())
						nextCell = c;
				} // close for

				p.setNextLocation(nextCell);
				nextCell.addToTargeted(p);
			} // close if
		} // close null if
	} // close calculateNextMove()
	
	public int getTimeStep() {
		timeChanged = false;
		return simThread.getCurrTimeStep();
	}
	
	public boolean timeChanged() {
		return timeChanged;
	}


	/**
	 * Tests if the simulation has completed
	 * @return True if the simulation is should continue, false otherwise
	 */
	public boolean continueSim() {
		return (countPeopleInBuilding > 0 || people.size() < totalPeople);
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
	 * @return the people
	 */
	public ArrayList<Person> getPeople() {
		peopleAvailable = false;
		return people;
	}

	/**
	 * @param people the people to set
	 */
	public void setPeople(ArrayList<Person> people) {
		this.people = people;
		peopleAvailable = true;
	}
	
	public boolean peopleAvailable() {
		return peopleAvailable;
	}
	
	public void setTimeChanged(boolean in) {
		this.timeChanged = in;
	}

}
