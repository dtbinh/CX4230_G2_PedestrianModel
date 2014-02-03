package edu.gatech.cx4230.projectone.backend.main;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.Timer;

import edu.gatech.cx4230.projectone.backend.abstraction.Cell;
import edu.gatech.cx4230.projectone.backend.abstraction.CellManager;
import edu.gatech.cx4230.projectone.backend.abstraction.Person;
import edu.gatech.cx4230.projectone.backend.map.MapGridData;
import edu.gatech.cx4230.projectone.backend.map.VisualizationMain;


public class PedestrianSimulation {

	public final int BUILDING_CAPACITY = 1500;
	
	public int totalPeople;
	public int countPeopleInBuilding;
	public int currTimeStep;
	
	// list of people currently in simulation 
	public ArrayList<Person> people;
	
	// list of people to move at each time step
	public List<Person> peopleToMove;
	
	// Manager for the 2-D grid of cells in the simulation
	private CellManager cm;
	
	// Visualization of the simulation
	private VisualizationMain vis;
	
	/**
	 * initializes simulation variables
	 * 
	 * @param vis
	 */
	public void initializeSimulation(VisualizationMain vis) {
		this.vis = vis;
		
		totalPeople = BUILDING_CAPACITY; // this may be variable for each simulation, with BUILDING_CAPACITY as the max
		countPeopleInBuilding = totalPeople;
		currTimeStep = 0;
		people = new ArrayList<Person>();
		
		// Read in and create the map grid
		MapGridData mgd = new MapGridData();
		vis.setMarkers(mgd.getCellMarkers());
		cm = mgd.getCellManager();
	}
	
	/**
	 * handles tasks that need to be done at each time step
	 * maintains crosswalk timing, spawns new people into simulation, moves active people in simulation
	 */
	public void timeStep() {
		currTimeStep++;
		
		// TODO traffic light maintenance
		
		// TODO spawn people
		int numPeople = 10; // use random here to decide how many ppl to spawn at each time step?
		spawnPeople(numPeople);
		
		// TODO move people
		movePeople();
		
		vis.redraw();
	}
	
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
		// iterate over all people currently in the simulation
		for(Person p : people) {
			if(p.isMoveable(currTimeStep)) {
				calculateNextMove(p);
				peopleToMove.add(p);
			}
		}
		//while(!peopleToMove.isEmpty()) {	// implement this if person must find alternate move instead of waiting until next time step
			for(Person p : peopleToMove) {
				// handle movement of people, potential collisions with other people, etc
				Cell nextCell = p.getNextLocation();
				if(nextCell.getTargeted().size() == 1) {
					p.move(currTimeStep, nextCell);
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
					winner.move(currTimeStep, nextCell);
					nextCell.setPerson(winner);
					peopleToMove.remove(targeted); // this assumes "losers" will wait until next time step
				}
				nextCell.clearTargeted();
			}
		//}
	
		vis.updatePeopleMarkers(people);
	}
	
	public void calculateNextMove(Person p) {
		// determine Person's most desirable next move
		Cell currCell = p.getLocation();
		Cell[] neighbors = currCell.getCm().getNeighborAll(currCell);
		Cell nextCell = neighbors[0];
		for(Cell c: neighbors) {
			if(c.getScore() > nextCell.getScore())
				nextCell = c;
		}
		p.setNextLocation(nextCell);
		nextCell.addToTargeted(p);
	}
	
	/**
	 * initializes the simulation variables, starts the timer, and checks for stopping conditions
	 * 
	 * @param vis
	 */
	public void mainLoop(VisualizationMain vis) {
		initializeSimulation(vis);

		int delay = 50; // 50 milliseconds = 0.05 seconds per time step
				
		ActionListener timeStepper = new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
					timeStep();
			}
		};
		
		Timer timer = new Timer(delay, timeStepper);
		timer.start();
		// check for stop conditions here
		while(countPeopleInBuilding > 0 || people.size() < totalPeople);
		timer.stop();

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
