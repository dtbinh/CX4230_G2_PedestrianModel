package edu.gatech.cx4230.projectone.backend.main;

public class SimulationThread extends Thread {
	private PedestrianSimulation ps;
	private boolean running;
	private boolean changed;
	private int wait;
	private int currTimeStep;
	private String id;


	public SimulationThread(PedestrianSimulation ps, int w, String s) {
		this.ps = ps;
		this.wait = w;
		this.id = s;

	}


	/**
	 * handles tasks that need to be done at each time step
	 * maintains crosswalk timing, spawns new people into simulation, moves active people in simulation
	 */
	public void run() {
		if(running) {
			currTimeStep++;

			// TODO traffic light maintenance

			// TODO spawn people
			int numPeople = 10; // use random here to decide how many ppl to spawn at each time step?
			ps.spawnPeople(numPeople);

			ps.movePeople();

			try {
				sleep((long) wait);
			} catch(Exception e) {
				e.printStackTrace();
			}
			if(!ps.continueSim()) {
				quit();
			}
		}
	}

	@Override
	public synchronized void start() {
		currTimeStep = 0;

		running = true;
		super.start();
	}

	public void quit() {
		System.out.println("Quitting Thread...");
		running = false;
		interrupt();
	}

	public String toString() {
		return "(" + id + "\tRunning: " + running + ")";
	}


	/**
	 * @return the running
	 */
	public boolean isRunning() {
		return running;
	}


	/**
	 * @param running the running to set
	 */
	public void setRunning(boolean running) {
		this.running = running;
	}


	/**
	 * @return the changed
	 */
	public boolean isChanged() {
		return changed;
	}


	/**
	 * @param changed the changed to set
	 */
	public void setChanged(boolean changed) {
		this.changed = changed;
	}


	/**
	 * @return the currTimeStep
	 */
	public int getCurrTimeStep() {
		return currTimeStep;
	}


	/**
	 * @param currTimeStep the currTimeStep to set
	 */
	public void setCurrTimeStep(int currTimeStep) {
		this.currTimeStep = currTimeStep;
	}

}
