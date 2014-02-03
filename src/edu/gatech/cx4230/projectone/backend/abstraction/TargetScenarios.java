package edu.gatech.cx4230.projectone.backend.abstraction;

import java.util.ArrayList;
import java.util.List;

/**
 * Contains several different scoring scenarios.
 * @author tbowling3
 *
 */
public class TargetScenarios {
	private Cell[][] cells;

	public TargetScenarios(Cell[][] cells) {
		this.cells = cells;
	}

	public List<Cell> specificTargetPoints1() {
		ArrayList<Cell> out = new ArrayList<Cell>();

		return out;
	}

	/**
	 * Scenario where a Pedestrian's goal is to get as far away from the
	 * Model Building as possible.
	 * @return A list of target cells for the simulation
	 */
	public List<Cell> maximizeDistance() {
		ArrayList<Cell> out = new ArrayList<Cell>();

		for(int i = 50; i < 105; i++) { // 5th Street going West
			out.add(cells[i][0]);
		}
		
		for(int i = 76; i < 118; i++) { // Spring Street going North
			out.add(cells[0][i]);
		}
		
		for(int i = 372; i < 422; i++) { // W Peachtree going North
			out.add(cells[0][i]);
		}
		
		for(int i = 179; i < 212; i++) { // 5th Street going East
			out.add(cells[i][cells[i].length - 1]);
		}
		
		
		return out;
	}

}
