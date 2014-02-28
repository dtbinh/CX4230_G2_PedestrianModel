package edu.gatech.cx4230.projectone.backend.main;

import java.awt.Point;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import edu.gatech.cx4230.projectone.backend.abstraction.Cell;
import edu.gatech.cx4230.projectone.backend.abstraction.CellManager;
import edu.gatech.cx4230.projectone.backend.abstraction.Person;
import edu.gatech.cx4230.projectone.backend.abstraction.SimulationScenario;
import edu.gatech.cx4230.projectone.backend.map.DoorScenarios;
import edu.gatech.cx4230.projectone.backend.map.MapGridData;
import edu.gatech.cx4230.projectone.backend.random.AbstractRNG;
import edu.gatech.cx4230.projectone.backend.random.CustomRNG;
import edu.gatech.cx4230.projectone.backend.scoring.DjikstraOperator;
import edu.gatech.cx4230.projectone.backend.scoring.Edge;
import edu.gatech.cx4230.projectone.backend.scoring.HadlockOperator;
import edu.gatech.cx4230.projectone.backend.scoring.Path;
import edu.gatech.cx4230.projectone.backend.scoring.PathOrganizer;
import edu.gatech.cx4230.projectone.backend.scoring.SimpleGraph;
import edu.gatech.cx4230.projectone.backend.utilities.ListHelper;
import edu.gatech.cx4230.projectone.visualization.map.VisualizationMain;


public class PedestrianSimulation {
	
	private int totalPeople;
	private int countPeopleInBuilding;

	// list of people currently in simulation 
	private ArrayList<Person> people;

	// list of people to move at each time step
	private List<Person> peopleToMove;
	
	// list of people who have exited the simulation
	private ArrayList<Person> finishedPeople;

	// Manager for the 2-D grid of cells in the simulation
	private CellManager cm;

	// list of the door cells
	private List<Cell> doors;


	// Simulation scenarios
	public static final int DOOR_SCENARIO = 1;
	public static final int TERM_CON_1 = 1; // Stop after set time steps
	public static final int TERM_CON_2 = 2; // Stop after people cross above line
	public static final int TERM_CON_3 = 3; // Stop when building is empty
	private int terminatingCondition = TERM_CON_1;
	private int endingTimeStep = 500;
	private int boundaryLine = 200;

	// Random Number Generator
	private AbstractRNG rng;

	private SimulationThread simThread;
	private boolean peopleAvailable = false;
	private boolean timeChanged = false;

	// Changes for Proposed Targeting
	private List<Cell> targets, sources, nodes;
	private PathOrganizer pathOrganizer;
	private HadlockOperator hadlock;
	private DjikstraOperator dOp;
	public static final boolean oldImplementation = false;

	public static boolean DEBUG = false;

	private boolean useVisualization;


	public PedestrianSimulation(VisualizationMain vis, SimulationScenario scen) {
		this.useVisualization = vis != null;
		
		// Get data from SimulationScenario
		boolean debug = scen.isDebug();
		PedestrianSimulation.DEBUG = debug;
		Person.DEBUG = debug;
		MapGridData.DEBUG = debug;
		SimulationThread.DEBUG = debug;
		this.terminatingCondition = scen.getTerminiatingCondition();
		this.boundaryLine = scen.getBoundaryLine();
		this.endingTimeStep = scen.getEndingTimeStep();
		List<Point> doorPoints = scen.getDoors();

		totalPeople = scen.getPeopleInBuilding(); // this may be variable for each simulation, with BUILDING_CAPACITY as the max
		countPeopleInBuilding = totalPeople;
		people = new ArrayList<Person>();
		peopleToMove = new ArrayList<Person>();
		finishedPeople = new ArrayList<Person>();
		rng = new CustomRNG();

		// Read in and create the map grid
		MapGridData mgd = new MapGridData();
		cm = mgd.getCellManager();
		
		if(useVisualization) {
			vis.setMarkers(mgd.getCellMarkers());
			vis.setCellsHeight(cm.getCellsHeight());
			vis.setCellsWidth(cm.getCellsWidth());
		}

		// Load and Set target cells
		SimpleGraph graph = new SimpleGraph(cm);
		// crosswalks = graph.getCrosswalkWaypoints();
		targets = graph.getSinks();
		sources = graph.getSources();
		nodes = graph.getNodes();

		dOp = new DjikstraOperator(graph);
		pathOrganizer = new PathOrganizer();
		for(Cell here: sources) {
			dOp.execute(here);
			for(Cell t: targets) {
				pathOrganizer.addPath(dOp.getPath(t));
			}
		}

		if(doorPoints == null) {
			DoorScenarios ds = new DoorScenarios(cm.getCells());
			doorPoints = ds.getScenarios();
		}
		doors = cm.getCellsForPoints(doorPoints);
		cm.setCells(doors);
		hadlock = new HadlockOperator(cm);
		
		
		if(oldImplementation) {
			//cm.setCellsScoresAlternateMethod(targets);
			setCellsScoresAlternateMethod();
		}

		peopleAvailable = false;
		int wait = 0;
		if(useVisualization) {
			wait = 50;
		} else {
			wait = 0;
		}
		simThread = new SimulationThread(PedestrianSimulation.this, wait, "Ped Sim Thread");
		simThread.start();
	} // close constructor
	
	public PedestrianSimulation(SimulationScenario scen) {
		this(null, scen);
	}

	private void setCellsScoresAlternateMethod() {
		Cell[][] cells = cm.getCells();
		for(int j = 0; j < cells.length; j++) {
			for(int i = 0; i < cells[j].length; i++) {
				cells[j][i].setScore(Double.MAX_VALUE); // Lower score means more desirable
			}
		}
		
		LinkedList<Cell> cellsToBeScored = new LinkedList<Cell>();
		HashSet<Cell> cellsScored = new HashSet<Cell>();
		ArrayList<Cell> neighbors = new ArrayList<Cell>();
		
		for(Cell t : targets) {
			cellsToBeScored.add(t);
			while(!cellsToBeScored.isEmpty()) {
				Cell c = cellsToBeScored.removeFirst();
				if(c != null && !cellsScored.contains(c)) {
					cellsScored.add(c);
					double score = getTraversibleDistance(c,t);
					
					if(score < c.getScore()) {
						// set cell score
						c.setScore(score);
					}
					neighbors = cm.getAllTraversableNeighbors(c);
					cellsToBeScored.addAll(neighbors);
				}
			}
			cellsScored.clear();
			cellsToBeScored.clear();
		}
	}
	
	public double getTraversibleDistance(Cell here, Cell target) {
		double distance = here.getDistanceToCell(target);
		//double distance = Double.MAX_VALUE;
		
		return distance;
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
		Person p = null;
		
		List<Cell> openDoors = getAvailableDoors();
		if(openDoors.size() > 0) {
			int doorI = rng.nextInt(openDoors.size());
			Cell door = openDoors.get(doorI);
			//int[] speeds = rng.nextIntsArraySorted(3, Person.MIN_SPEED, Person.MAX_SPEED);
			int speed = (int) Math.round(rng.nextInNormal(Person.MEAN_SPEED, Person.SPEED_ST_DEV));
			//int stress = rng.nextIntInRange(Person.MIN_STRESS, Person.MAX_STRESS);
			int stress = rng.nextIntInRange(100,400);
			p = new Person(door, speed, stress, simThread.getCurrTimeStep());

			p = findNextTargetPathForPerson(p);


			cm.addPerson(p);
			countPeopleInBuilding--;
		}
		return p;
	}

	private Person findNextTargetPathForPerson(Person p) {
		if(p != null) {
			Cell t = getClosestNodeNotVisited(p);
			Path path = null;
			if(sources.contains(t)) {
				path = pathOrganizer.getMinimumPath(t);
				if(path == null) {
					dOp.execute(t);
					PathOrganizer pathO = new PathOrganizer();

					for(Cell tar: targets) {
						pathO.addPath(dOp.getPath(tar));
					}
					path = pathO.getMinimumPath(t);
				}
			} else {
				if(DEBUG) System.err.println("PS.fNTPFP() Line 144 - Calculating path");
				dOp.execute(t);
				PathOrganizer pathO = new PathOrganizer();

				for(Cell tar: targets) {
					pathO.addPath(dOp.getPath(tar));
				}
				path = pathO.getMinimumPath(t);
			}

			p.setNextTargets(path);
		}
		return p;
	}

	private Cell getClosestNodeNotVisited(Person p) {
		Cell out = null;
		Cell pCell = p.getLocation();
		for(Cell n: nodes) {
			if(out == null || n.getManhattanDistance(pCell) < out.getManhattanDistance(pCell) && !p.hasVisitedTarget(n)) {
				out = n;
			}
		}
		return out;
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
				int panic = rng.nextInt(Person.MAX_STRESS);
				if(p.getStressLevel() > panic) {
					calculatePanicMove(p);
					p.increaseStress(Person.WAIT_INCREASE_STRESS);
				}
				else {
					calculateNextMove(p);
				}
				//p = calculateNextMove(p);
				peopleToMove.add(p);
			}
		}
		
		ArrayList<Person> peopleInConflict = new ArrayList<Person>();
		
		for(int i = 0; i < 2; i++) { // max 2 attempts for a person to move in this time step
			for(Iterator<Person> it = peopleToMove.iterator(); it.hasNext();) {
				Person p = it.next();
				// handle movement of people, potential collisions with other people, etc
				Cell nextCell = p.getNextLocation();
				if(nextCell == null) {
					//if(DEBUG) System.err.println("PS.movePerson() Line 234 - nextCell is null");
					// TODO If the next Cell hasn't been specified
				} else {
					nextCell = cm.getCell(nextCell.getX(), nextCell.getY());
					
					if(nextCell.getTargeted().size() == 1) { // if no conflicts
						movePerson(p, nextCell, currStep);
						it.remove();
					}
					else { // Multiple people want same Cell
						List<Person> targeted = nextCell.getTargeted();
						if(DEBUG) System.out.println("Conflicts for:" + nextCell );
						
						if(targeted != null && !targeted.isEmpty()) {
							if(DEBUG) System.out.println(ListHelper.listToString(targeted));
							if(DEBUG) System.out.println();
							
							Person winner = targeted.get(0);
							for(Person t : targeted) {
								if(t.getCurrSpeed() > winner.getCurrSpeed()) {
									winner = t;
								}
							} // close for
	
							// Update the CellManager
							movePerson(winner, nextCell, currStep);
							
							peopleToMove.remove(targeted); // this assumes "losers" will wait until next time step
							//peopleToMove.remove(winner);
							targeted.remove(winner);
							peopleInConflict.addAll(targeted);
							//it.remove();
						} // close targeted if
					} // close else
				} // close else
			} // close Person for
			
			// calculate alternative moves for people who had conflicts
			for(Person p : peopleInConflict) { // people that couldn't move because of conflict
				calculateAlternativeMove(p, p.getNextLocation());
			}
			peopleToMove = peopleInConflict;
		}
			
		// For the people still waiting to move:
		for(Person p: peopleToMove) {
			p.increaseStress(Person.WAIT_INCREASE_STRESS);
		}
					
		peopleToMove.clear();
		peopleInConflict.clear();

		peopleAvailable = true;
	} // close movePeople()

	private void movePerson(Person p, Cell nextCell, int currStep) {
		// Update the CellManager
		int oldX = p.getLocation().getX();
		int oldY = p.getLocation().getY();
		int newX = nextCell.getX();
		int newY = nextCell.getY();
		p.move(currStep, nextCell);
		p.decreaseStress(Person.MOVE_DECREASE_STRESS);
		cm.movePerson(p, oldX, oldY, newX, newY);
		
		if(p.isFinished()) {
			people.remove(p);
			finishedPeople.add(p);
		}
	}

	private void calculatePanicMove(Person p) {
		Cell currCell = p.getLocation();
		Cell nextCell = currCell;
		ArrayList<Cell> neighbors = cm.getAllTraversableNeighbors(currCell);
		int choice = rng.nextInt(9);
		if(choice < neighbors.size()) {
			nextCell = neighbors.get(choice);
			nextCell.addToTargeted(p);
		}
		p.setNextLocation(nextCell);
	}

	public Person calculateNextMove(Person p) {
		// determine Person's most desirable next move
		Cell currCell = p.getLocation();
		if(currCell != null) {
			if (!oldImplementation) {
				// TODO Calculate path to nextTarget using Hadlock
				Cell nextTarget = p.getNextTarget();
				hadlock.setCm(cm);
				if (nextTarget == null) {
					if (DEBUG)
						System.err
								.println("PS.calcNextMove() Line 286 - Person.nextTarget is null");
				} else {
					List<Cell> personClosePath = hadlock.findPath(currCell,
							nextTarget);

					if (personClosePath != null && personClosePath.size() >= 2) {
						// The first cell in personClosePath should be the person's current cell
						Cell nextCellInPath = personClosePath.get(1);
						int nextX = nextCellInPath.getX();
						int nextY = nextCellInPath.getY();
						Cell nextFromCM = cm.getCell(nextX, nextY);
						if (currCell.getManhattanDistance(nextFromCM) == 1) {
							if (nextFromCM.isOccupied()) { // Try to move to different neighbor
								if (DEBUG)
									System.out.println("Person " + p
											+ " moving around " + nextFromCM);
								// TODO Apply some probability to deviating from path
								int dx = nextFromCM.getX() - currCell.getX();
								int dy = nextFromCM.getY() - currCell.getY();
								if (dx == 0) { // Wanting to move up/down
									Cell left = cm.getNeighborLeft(currCell);
									Cell right = cm.getNeighborRight(currCell);
									boolean bLeft = left != null
											&& left.isTraversable()
											&& !left.isTraversable();
									boolean bRight = right != null
											&& right.isTraversable()
											&& !right.isOccupied();
									if (bLeft && bRight) {

									} else if (bLeft) {
										left.addToTargeted(p);
										p.setNextLocation(left);
										cm.setCellSmart(left);
									} else if (bRight) {
										right.addToTargeted(p);
										p.setNextLocation(right);
										cm.setCellSmart(right);
									}

								} else if (dy == 0) { // Wanting to move left/right
									Cell up = cm.getNeighborTop(currCell);
									Cell down = cm.getNeighborBottom(currCell);
									boolean bUp = up != null
											&& up.isTraversable()
											&& !up.isOccupied();
									boolean bDown = down != null
											&& down.isTraversable()
											&& !down.isOccupied();
									if (bUp && bDown) {

									} else if (bUp) {
										up.addToTargeted(p);
										p.setNextLocation(up);
										cm.setCellSmart(up);
									} else if (bDown) {
										down.addToTargeted(p);
										p.setNextLocation(down);
										cm.setCellSmart(down);
									}
								} // close if(dy==0)
									// TODO Something clever with conflicts
									// Maybe test if the person is within X cells of the targetCell,
									// and if so, maybe move on to the next target
							} else { // The next cell is empty, person can move (probably)
								nextFromCM.addToTargeted(p);
								p.setNextLocation(nextFromCM);
								cm.setCellSmart(nextFromCM);
							}
						} else {
							if (DEBUG)
								System.err
										.println("PS.calcNextMove() - Manhattan Distance != 1 for "
												+ p);
							// TODO There is some error
						}

					} else {
						if (DEBUG)
							System.out
									.println("PersonClosePath is either null or of size 1 for "
											+ p);
					}
				} // close else
			}// close oldImplementation if
			
			else if(oldImplementation) {
				// Old Implementation
				//ArrayList<Cell> neighbors = cm.getNeighborAll(currCell);
				ArrayList<Cell> neighbors = cm.getAllTraversableNeighbors(currCell);
				Cell nextCell = null;
				if(neighbors.size() > 0) {
					int i = getIndexOfFirstTraversable(neighbors);
					if(0 <= i && i < neighbors.size()) {
						nextCell = neighbors.get(i);
						for(Cell c: neighbors) {
							if(c != null
									&& c.isTraversable() 
									&& !c.isOccupied()
									&& c.getScore() < nextCell.getScore()) // Lower score is more desirable
								nextCell = c;
						} // close for

						p.setNextLocation(nextCell);
						nextCell.addToTargeted(p);
						cm.setCellSmart(nextCell);
					}
				} // close if
				else {
					p.setNextLocation(currCell);
				}
			} // close oldImplementation else if
		} // close null if
		
		return p;
	} // close calculateNextMove()

	/**
	 * calculateAlternativeMove finds a "sway" cell (to the left or right of the desired cell) if it is available.
	 * this method assumes that the desired movement was in the N,S,E, or W direction,
	 * and gives a "sway" cell the would produce diagonal movement (NW, NE, SW, SE).
	 * 
	 * @param p the Person that is trying to move
	 * @param nextCell the preferred nextCell that is unavailable due to conflict
	 */
	private void calculateAlternativeMove(Person p, Cell nextCell) {
		Cell currCell = p.getLocation();
		Cell alternateNextCell = currCell;
		int direction = cm.getMovementDirection(currCell, nextCell);
		Cell[] swayCells = new Cell[2];
		
		// find "sway" cells 
		if(direction == CellManager.NORTH) {
			 swayCells[0] = cm.getNeighborTopRight(currCell);
			 swayCells[1] = cm.getNeighborTopLeft(currCell);
		}
		else if(direction == CellManager.SOUTH) {
			 swayCells[0] = cm.getNeighborBottomRight(currCell);
			 swayCells[1] = cm.getNeighborBottomLeft(currCell);
		}
		else if(direction == CellManager.EAST) {
			 swayCells[0] = cm.getNeighborTopRight(currCell);
			 swayCells[1] = cm.getNeighborBottomRight(currCell);
		}				
		else if(direction == CellManager.WEST) {
			 swayCells[0] = cm.getNeighborTopLeft(currCell);
			 swayCells[1] = cm.getNeighborBottomLeft(currCell);
		}
		else {
			 swayCells[0] = currCell;
			 swayCells[1] = currCell;
		}
		
		// choose which way to "sway" based on which cell gets person closer to next target
		if(!swayCells[0].isOccupied() && 
				(swayCells[0].getDistanceToCell(p.getNextTarget()) < 
				swayCells[1].getDistanceToCell(p.getNextTarget()))) {
			alternateNextCell = swayCells[0];
		}
		else if (!swayCells[1].isOccupied()){
			alternateNextCell = swayCells[1];
		}
		
		p.setNextLocation(alternateNextCell);
		if(!alternateNextCell.equals(currCell)) {
			alternateNextCell.addToTargeted(p);
		}
	}
	
	
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

	public String infoForCell(int cellX, int cellY) {
		String out = "";
		Cell c = cm.getCell(cellX, cellY);
		if(c != null) {
			String line0 = "(" + cellX + ", " + cellY + ")\n";
			String line1 = "Type: " + c.getTypeName() + "\n";
			String line2 = "Name: " + c.getName() + "\n";
			String line3 = "";
			if(c.isOccupied()) {
				line3 = "Person id: " + c.getPerson().toString() + "\n";
				line3 += "NextTarget: " + c.getPerson().getNextTarget() + "\n";
				line3 += "NextLocation: " + c.getPerson().getNextLocation() + "\n";
			}
			out = line0 + line1 + line2 + line3;
		} // close null
		return out;
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
		boolean out = true;
		switch(terminatingCondition) {
		case TERM_CON_1:
			out = terminatingConditionOne();
			break;
		case TERM_CON_2:
			out = terminatingConditionTwo();
			break;
		case TERM_CON_3:
			out = terminatingConditionThree();
		} // close switch
		return out;
	}
	
	/**
	 * 
	 * @return True if the simulation should continue
	 */
	private boolean terminatingConditionOne() {
//		out = (countPeopleInBuilding > 0 || people.size() < totalPeople);
		return (simThread.getCurrTimeStep() < endingTimeStep);
	}
	
	/**
	 * Checks to see if all the people have moved above (y-location) a line at
	 * y-value = boundaryLine.
	 * 
	 * @return True if all the people are above the line and false otherwise
	 */
	private boolean terminatingConditionTwo() {
		boolean out = true;
		
		for(Person p : people) {
			Cell c = p.getLocation();
			if(c != null && c.getY() < boundaryLine) {
				out = false;
				break;
			}
		}
		return out;
	}
	
	/**
	 * 
	 * @return True if the simulation should continue
	 */
	private boolean terminatingConditionThree() {
		return (countPeopleInBuilding > 0);
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

	/**
	 * @return the rng
	 */
	public AbstractRNG getRng() {
		return rng;
	}

	/**
	 * @param rng the rng to set
	 */
	public void setRng(AbstractRNG rng) {
		this.rng = rng;
	}

	/**
	 * @return the totalPeople
	 */
	public int getTotalPeople() {
		return totalPeople;
	}

	/**
	 * @return the countPeopleInBuilding
	 */
	public int getCountPeopleInBuilding() {
		return countPeopleInBuilding;
	}

	public List<Cell> getDoors() {
		return doors;
	}
	
	/**
	 * @return the simThread
	 */
	public SimulationThread getSimThread() {
		return simThread;
	}

	private int getEndSimulationScore() {
		double out = 0;
		
		switch(terminatingCondition) {
		case TERM_CON_1:
		case TERM_CON_3:
			int scoreFinished = 100;
			int scoreOutOfBuilding = 25;
			
			out += scoreFinished * finishedPeople.size();
			out += scoreOutOfBuilding * people.size();
			
			for(Person p: people) {
				List<Edge> edges = p.getNextTargets().getList();
				if(edges != null && !edges.isEmpty()) {
					Cell finalDest = edges.get(edges.size() - 1).getDestination();
					Cell first = p.getMyDoor();
					double totalDist = first.getManhattanDistance(finalDest);
					double myDist = p.getLocation().getManhattanDistance(finalDest);
					out += scoreFinished * (1 - myDist / totalDist);
				} // close if
			}
			break;
		case TERM_CON_2:
			out = simThread.getCurrTimeStep();
			break;
		}
		return (int) out;
	}
	
	public EndSimulationResult getEndSimulationResult() {
		EndSimulationResult out = null;
		if(!continueSim()) {
			out = new EndSimulationResult(terminatingCondition, totalPeople, finishedPeople.size(), people.size(), getEndSimulationScore());
		}
		return out;
	}

	/**
	 * @return the terminatingCondition
	 */
	public int getTerminatingCondition() {
		return terminatingCondition;
	}

	/**
	 * @param terminatingCondition the terminatingCondition to set
	 */
	public void setTerminatingCondition(int terminatingCondition) {
		this.terminatingCondition = terminatingCondition;
	}

}
