import java.util.ArrayList;


public class PedestrianSimulation {

	public final int CELL_COUNT_WIDTH = 500;
	public final int CELL_COUNT_HEIGHT = 500;
	public final int BUILDING_CAPACITY = 1500;
	
	public int countPeopleInBuilding;
	public int countPeopleSpawned;
	public int currTimeStep;
	
	// list of people currently in simulation 
	public ArrayList<Person> people;
	
	// 2-D grid of cells in the simulation
	public ArrayList< ArrayList<Cell> > cells;
	
	public void initializeSimulation() {
		countPeopleInBuilding = BUILDING_CAPACITY;
		countPeopleSpawned = 0;
		currTimeStep = 0;
		people = new ArrayList<Person>();
		cells = new ArrayList< ArrayList<Cell> >();
		for(int i = 0; i < CELL_COUNT_HEIGHT; i++) {
			ArrayList<Cell> list = cells.get(i);
			for(int j = 0; j < CELL_COUNT_WIDTH; j++) {
				list.add(new Cell());
			}
		}
	}
	
	public void timeStep() {
		// TODO traffic light maintenance
		// TODO spawn people
		// TODO move people
	}
	
	public Person spawnPerson() {
		return new Person();
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
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		PedestrianSimulation simulation = new PedestrianSimulation();
		
		simulation.initializeSimulation();
		
		while(simulation.countPeopleInBuilding > 0 || simulation.countPeopleSpawned < simulation.BUILDING_CAPACITY) {
			// TODO wait specified amount of time for each time step, then call timeStep()
			simulation.timeStep();
		}
	}

}
