package edu.gatech.cx4230.projectone.backend.main;

import java.util.ArrayList;
import java.util.List;

import edu.gatech.cx4230.projectone.backend.abstraction.Cell;
import edu.gatech.cx4230.projectone.backend.abstraction.Person;
import edu.gatech.cx4230.projectone.backend.utilities.StatsHelper;

/**
 * Class to create and run the experiments
 * @author tbowling3
 *
 */
public class NoVisualizationMain {
	private List<EndSimulationResult> results;

	public NoVisualizationMain(int trials) {
		results = new ArrayList<EndSimulationResult>();

		for(int i = 1; i <= trials; i++) {
			System.out.println("Running trial: " + i + " of " + trials + "...");
			run();
		}
	}

	public void run() {
		Person.resetCount();
		Cell.resetCount();
		PedestrianSimulation ps = new PedestrianSimulation(null, false, false);

		while(ps.continueSim()) {
			try {
				Thread.sleep(50);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		results.add(ps.getEndSimulationResult());

	}

	public List<EndSimulationResult> getResults() {
		return results;
	}

	public void processResults() {
		if(results != null && !results.isEmpty()) {
			int trials = results.size();
			int[] scores = new int[trials];

			for(int i = 0; i < trials; i++) {
				scores[i] = results.get(i).getScore();
			}

			double scoreAverage = StatsHelper.findAverage(scores);
			double scoreSSD = StatsHelper.findStandardDeviation(scores);

			System.out.println("Trials run: " + trials);
			System.out.println("Average Score: " + scoreAverage);
			System.out.println("Sample Standard Deviation Score: " + scoreSSD);
		}
	}


	public static void main(String[] args) {
		int trials = 3;
		NoVisualizationMain experiment = new NoVisualizationMain(trials);
		experiment.processResults();
	}
}
