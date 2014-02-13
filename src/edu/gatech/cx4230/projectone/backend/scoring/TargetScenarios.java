package edu.gatech.cx4230.projectone.backend.scoring;

import java.util.ArrayList;
import java.util.List;

import edu.gatech.cx4230.projectone.backend.abstraction.Cell;

/**
 * Contains several different scoring scenarios.
 * @author tbowling3
 *
 */
public class TargetScenarios {
	private Cell[][] cells;
	public static final int MAXIMIZE_DIST = 4325634;
	public static final int SPEC_TARGET_1 = 6547465;

	public TargetScenarios(Cell[][] cells) {
		this.cells = cells;
	}

	private List<Cell> specificTargetPoints1() {
		ArrayList<Cell> out = new ArrayList<Cell>();

		return out;
	}

	/**
	 * Scenario where a Pedestrian's goal is to get as far away from the
	 * Model Building as possible.
	 * @return A list of target cells for the simulation
	 */
	private List<Cell> maximizeDistance() {
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
	
	public List<Cell> getTargetScenario(int in) {
		List<Cell> out = null;
		switch(in) {
		case MAXIMIZE_DIST:
			out = maximizeDistance();
			break;
		case SPEC_TARGET_1:
			out = specificTargetPoints1();
			break;
		}
		return out;
	}

}
