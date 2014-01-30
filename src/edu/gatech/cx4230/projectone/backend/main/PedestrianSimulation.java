package edu.gatech.cx4230.projectone.backend.main;

import java.util.ArrayList;

import edu.gatech.cx4230.projectone.backend.abstraction.CellManager;
import edu.gatech.cx4230.projectone.backend.abstraction.Person;
import edu.gatech.cx4230.projectone.backend.map.MapGridData;
import edu.gatech.cx4230.projectone.backend.map.VisualizationMain;


public class PedestrianSimulation {

	public final int BUILDING_CAPACITY = 1500;
	
	public int countPeopleInBuilding;
	public int countPeopleSpawned;
	public int currTimeStep;
	
	// list of people currently in simulation 
	public ArrayList<Person> people;
	
	// Manager for the 2-D grid of cells in the simulation
	private CellManager cm;
	
	// Visualization of the simulation
	private VisualizationMain vis;
	
	
	public void initializeSimulation(VisualizationMain vis) {
		this.vis = vis;
		
		countPeopleInBuilding = BUILDING_CAPACITY;
		countPeopleSpawned = 0;
		currTimeStep = 0;
		people = new ArrayList<Person>();
		
		// Read in and create the map grid
		MapGridData mgd = new MapGridData();
		vis.setMarkers(mgd.getCellMarkers());
		cm = mgd.getCellManager();
	}
	
	public void timeStep() {
		// TODO traffic light maintenance
		// TODO spawn people
		// TODO move people
		
		vis.redraw();
	}
	
	public Person spawnPerson() {
		countPeopleSpawned++;
		countPeopleInBuilding++;
		return new Person(); // right now this uses the default constructor, but it should take in start values for the new Person
	}
	
	public void movePeople() {
		// iterate over all people currently in the simulation
		for(Person p : people) {
			if(p.isMoveable()) {
				// TODO calculate probabilities of movement into neighbor cells
			}
		}
		for(Person p : people) {
			if(p.isMoveable()) {
				// TODO handle movement of people, potential collisions with other people, etc
				// if a person is moved, call p.move(currTime); to reset their ability to move
			}
		}
	}
	
	public void mainLoop(VisualizationMain vis) {
		initializeSimulation(vis);
		
		//while(countPeopleInBuilding > 0 || countPeopleSpawned < BUILDING_CAPACITY) {
			// TODO wait specified amount of time for each time step, then call timeStep()
			timeStep();
		//}
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

}
