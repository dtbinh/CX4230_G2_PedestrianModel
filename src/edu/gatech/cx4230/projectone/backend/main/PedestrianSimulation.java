package edu.gatech.cx4230.projectone.backend.main;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import edu.gatech.cx4230.projectone.backend.abstraction.Cell;
import edu.gatech.cx4230.projectone.backend.abstraction.CellManager;
import edu.gatech.cx4230.projectone.backend.abstraction.Person;
import edu.gatech.cx4230.projectone.backend.abstraction.TargetScenarios;
import edu.gatech.cx4230.projectone.backend.map.CrossWalkWaypoints;
import edu.gatech.cx4230.projectone.backend.map.DoorScenarios;
import edu.gatech.cx4230.projectone.backend.map.MapGridData;
import edu.gatech.cx4230.projectone.backend.random.AbstractRNG;
import edu.gatech.cx4230.projectone.backend.random.JavaRNG;
import edu.gatech.cx4230.projectone.visualization.map.VisualizationMain;


public class PedestrianSimulation {

	public static final int BUILDING_CAPACITY = 500;

	private int totalPeople;
	private int countPeopleInBuilding;

	// list of people currently in simulation 
	private ArrayList<Person> people;

	// list of people to move at each time step
	private List<Person> peopleToMove;

	// Manager for the 2-D grid of cells in the simulation
	private CellManager cm;

	// list of the door cells
	private List<Cell> doors;

	// Visualization of the simulation
	private VisualizationMain vis;

	// Simulation scenarios
	public static final int TARGET_SCENARIO = TargetScenarios.MAXIMIZE_DIST;
	public static final int DOOR_SCENARIO = 1;

	// Random Number Generator
	private AbstractRNG rng;

	private SimulationThread simThread;
	private boolean peopleAvailable = false;
	private boolean timeChanged = false;

	public PedestrianSimulation(VisualizationMain vis) {
		this.vis = vis;

		totalPeople = BUILDING_CAPACITY; // this may be variable for each simulation, with BUILDING_CAPACITY as the max
		countPeopleInBuilding = totalPeople;
		people = new ArrayList<Person>();
		peopleToMove = new ArrayList<Person>();
		rng = new JavaRNG(); // TODO Change to our RNG

		// Read in and create the map grid
		MapGridData mgd = new MapGridData();
		vis.setMarkers(mgd.getCellMarkers());
		cm = mgd.getCellManager();
		vis.setCellsHeight(cm.getCellsHeight());
		vis.setCellsWidth(cm.getCellsWidth());

		// Load and Set target cells
		TargetScenarios ts = new TargetScenarios(cm.getCells());
		List<Cell> targets = ts.getTargetScenario(TARGET_SCENARIO);
		targets.addAll(CrossWalkWaypoints.getCrosswalkWaypoints(cm));
		cm.setCellsScores(targets);

		DoorScenarios ds = new DoorScenarios(cm.getCells());
		doors = ds.getScenario(DOOR_SCENARIO);
		cm.setCells(doors);
		// TODO Send doors to vis

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
		Person p = null;
		// TODO Improve this method
		List<Cell> openDoors = getAvailableDoors();
		if(openDoors.size() > 0) {
			int doorI = rng.nextInt(openDoors.size());
			Cell door = openDoors.get(doorI);
			int[] speeds = rng.nextIntsArraySorted(3, Person.MIN_SPEED, Person.MAX_SPEED);
			int stress = rng.nextIntInRange(Person.MIN_STRESS, Person.MAX_STRESS);
			p = new Person(door, speeds[1], speeds[0], speeds[2], stress, simThread.getCurrTimeStep());
			cm.addPerson(p);
			countPeopleInBuilding--;
			System.out.println("Person added: " + p.toString());
		}
		return p;
	}

	/**
	 * Returns a subset of the door Cells which are not currently occupied
	 * @return
	 */
	private List<Cell> getAvailableDoors() {
		List<Cell> out = new ArrayList<Cell>();
		for(Cell c: doors) {
			if(!c.isOccupied()) {
				out.add(c);
			}
		}
		return out;
	}

	/**
	 * spawnPeople
	 * 
	 * spawns new Person objects and adds them to the people ArrayList
	 * 
	 * @param numPeople the number of new Person objects to spawn
	 */
	public void spawnPeople(int numPeople) {
		int num = 0;
		int numCreated = 0;
		if(countPeopleInBuilding - numPeople >= 0) {
			num = numPeople;
		} else {
			num = countPeopleInBuilding;
		}
		for(int i = 0; i < num; i++) {
			Person p = spawnPerson();
			if(p != null) {
				people.add(p);
				numCreated++;
			}
		}
		if(numCreated > 0) peopleAvailable = true;
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
		//for(Person p : people) {
		for(Iterator<Person> it = people.iterator(); it.hasNext();) {
			Person p = it.next();
			if(p.isMoveable(currStep)) {
				calculateNextMove(p);
				peopleToMove.add(p);
			}
		}
		//while(!peopleToMove.isEmpty()) {	// implement this if person must find alternate move instead of waiting until next time step
		//for(Person p : peopleToMove) {
		for(Iterator<Person> it = peopleToMove.iterator(); it.hasNext();) {
			Person p = it.next();
			// handle movement of people, potential collisions with other people, etc
			Cell nextCell = p.getNextLocation();
			if(nextCell == null) {
				// TODO If the next Cell hasn't been specified
			} else {
				if(nextCell.getTargeted().size() == 1) {

					// Update the CellManager
					movePerson(p, nextCell, currStep);

					nextCell.setPerson(p); // TODO is this line necessary?
					//peopleToMove.remove(p); // Causes ConcurrentModificationException
					it.remove();
				}
				else {
					List<Person> targeted = nextCell.getTargeted();
					if(targeted != null && !targeted.isEmpty()) {
						Person winner = targeted.get(0);
						for(Person t : targeted) {
							if(t.getCurrSpeed() > winner.getCurrSpeed())
								winner = t;
						}

						// Update the CellManager
						movePerson(winner, nextCell, currStep);
						nextCell.setPerson(winner); // TODO is this line necessary?

						peopleToMove.remove(targeted); // this assumes "losers" will wait until next time step
						//it.remove();
					}
				}
				nextCell.clearTargeted();
				// TODO What happens to the nextCell object after this line? If the nextCell
				// isn't stored in the CellManager, then Line 215 is useless.
			}
		} // close Person for

		peopleAvailable = true;
	} // close movePeople()
	
	private void movePerson(Person p, Cell nextCell, int currStep) {
		// Update the CellManager
		int oldX = p.getLocation().getX();
		int oldY = p.getLocation().getY();
		int newX = nextCell.getX();
		int newY = nextCell.getY();
		p.move(currStep, nextCell);
		cm.movePerson(p, oldX, oldY, newX, newY);
	}

	public void calculateNextMove(Person p) {
		// determine Person's most desirable next move
		Cell currCell = p.getLocation();
		if(currCell != null) {
			ArrayList<Cell> neighbors = cm.getNeighborAll(currCell);
			if(neighbors.size() > 0) {
				int i = getIndexOfFirstTraversable(neighbors);
				if(0 <= i && i < neighbors.size()) {
					Cell nextCell = neighbors.get(i);
					for(Cell c: neighbors) {
						if(c.getScore() > nextCell.getScore() && c.isTraversable() && !c.isOccupied())
							nextCell = c;
					} // close for

					p.setNextLocation(nextCell);
					nextCell.addToTargeted(p);
				}
			} // close if
		} // close null if
	} // close calculateNextMove()

	private int getIndexOfFirstTraversable(List<Cell> cells) {
		int out = -1;
		for(int i = 0; i < cells.size(); i++) {
			if(cells.get(i).isTraversable() && !cells.get(i).isOccupied()) {
				out = i;
				break;
			}
		}
		return out;
	}

	public void infoForCell(int cellX, int cellY) {
		Cell c = cm.getCell(cellX, cellY);
		if(c != null) {
			String line0 = "(" + cellX + ", " + cellY + ")\n";
			String line1 = "Type: " + c.getTypeName() + "\n";
			String line2 = "Name: " + c.getName() + "\n";
			String line3 = "Score: " + c.getScore() + "\n";
			String line4 = "";
			if(c.isOccupied()) {
				line4 = "Person id: " + c.getPerson().toString();
			}
			String text = line0 + line1 + line2 + line3 + line4;
			vis.setTooltipText(text);
		}
	}

	public int getTimeStep() {
		timeChanged = false;
		return simThread.getCurrTimeStep();
	}

	public boolean timeChanged() {
		return timeChanged;
	}

	public void triggerThread() {
		simThread.setRunning(!simThread.isRunning());
	}


	/**
	 * Tests if the simulation has completed
	 * @return True if the simulation is should continue, false otherwise
	 */
	public boolean continueSim() {
		return true;
		//return (countPeopleInBuilding > 0 || people.size() < totalPeople);
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
