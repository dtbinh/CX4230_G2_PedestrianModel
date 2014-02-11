package edu.gatech.cx4230.projectone.backend.map;

import java.util.ArrayList;
import java.util.List;

import edu.gatech.cx4230.projectone.backend.abstraction.Cell;
import edu.gatech.cx4230.projectone.backend.abstraction.CellManager;

/**
 * Object that returns the cells to be set as waypoints at the edges of crosswalks
 * @author tbowling3
 *
 */
public class CrossWalkWaypoints {
	
	public static List<Cell> getCrosswalkWaypoints(CellManager cm) {
		List<Cell> out = new ArrayList<Cell>();

		// Spring & 5th
		out.add(cm.getCell(86, 55)); 
		out.add(cm.getCell(114, 55));
		
		out.add(cm.getCell(81, 66));
		out.add(cm.getCell(81, 87));
		
		out.add(cm.getCell(86, 92));
		out.add(cm.getCell(114, 92));
		
		
		// ARMSTEAD C
		out.add(cm.getCell(86, 289));
		out.add(cm.getCell(113, 289));
		
		out.add(cm.getCell(118, 294));
		out.add(cm.getCell(118, 312));
		
		out.add(cm.getCell(372, 294));
		out.add(cm.getCell(372, 312));
		
		
		// FIVE & FIVE
		out.add(cm.getCell(379, 214));
		out.add(cm.getCell(410, 214));
		
		out.add(cm.getCell(421, 203));
		out.add(cm.getCell(421, 183));
		
		out.add(cm.getCell(410, 93));
		out.add(cm.getCell(379, 93));
		
		out.add(cm.getCell(375, 66));
		out.add(cm.getCell(375, 89));
		
		return out;
	}

}
