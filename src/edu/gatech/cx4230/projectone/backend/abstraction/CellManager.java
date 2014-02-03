package edu.gatech.cx4230.projectone.backend.abstraction;

import java.util.ArrayList;
import java.util.List;

import edu.gatech.cx4230.projectone.backend.utilities.MathHelper;

/**
 * Object that will hold the 2D-array of cells.  Each cell will have a reference to this object.  Contains
 * methods for cells to communicate with other neighboring cells.
 * @author tbowling3
 *
 */
public class CellManager {
	private Cell[][] cells;
	
	public CellManager(Cell[][] cells) {
		this.cells = cells;
	}
	
	public CellManager() {
		
	}
	
	/**
	 * Returns the cell located above a given cell
	 * @param here
	 * @return
	 */
	public Cell getNeighborTop(Cell here) {
		return getNeighborCell(here, -1, 0);
	}
	
	/**
	 * Returns the cell located above and to the right of a given cell
	 * @param here
	 * @return
	 */
	public Cell getNeighborTopRight(Cell here) {
		return getNeighborCell(here, -1, 1);
	}
	
	/**
	 * Returns the cell located to the left of a given cell
	 * @param here
	 * @return
	 */
	public Cell getNeighborLeft(Cell here) {
		return getNeighborCell(here, 0, -1);
	}
	
	/**
	 * Returns the cell located to the bottom left of a given cell
	 * @param here
	 * @return
	 */
	public Cell getNeighborBottomLeft(Cell here) {
		return getNeighborCell(here, 1, -1);
	}
	
	/**
	 * Returns the cell located below a given cell
	 * @param here
	 * @return
	 */
	public Cell getNeighborBottom(Cell here) {
		return getNeighborCell(here, 1, 0);
	}
	
	/**
	 * Returns the cell located to the bottom left of a given cell
	 * @param here
	 * @return
	 */
	public Cell getNeighborBottomRight(Cell here) {
		return getNeighborCell(here, 1, 1);
	}
	
	/**
	 * Returns the cell located to the right of a given cell
	 * @param here
	 * @return
	 */
	public Cell getNeighborRight(Cell here) {
		return getNeighborCell(here, 0, 1);
	}
	
	/**
	 * Returns the cell located to the top left of a given cell
	 * @param here
	 * @return
	 */
	public Cell getNeighborTopLeft(Cell here) {
		return getNeighborCell(here, -1, -1);
	}
	
	public ArrayList<Cell> getNeighborAll(Cell here) {
		ArrayList<Cell> out = new ArrayList<Cell>();
		Cell t0 = getNeighborTop(here);
		if(t0 != null) out.add(t0);
		
		Cell tr = getNeighborTopRight(here);
		if(tr != null) out.add(tr);
		
		Cell r0 = getNeighborRight(here);
		if(r0 != null) out.add(r0);
		
		Cell br = getNeighborBottomRight(here);
		if(br != null) out.add(br);
		
		Cell b0 = getNeighborBottom(here);
		if(b0 != null) out.add(b0);
		
		Cell bl = getNeighborBottomLeft(here);
		if(bl != null) out.add(bl);
		
		Cell l0 = getNeighborLeft(here);
		if(l0 != null) out.add(l0);
		
		Cell tl = getNeighborTopLeft(here);
		if(tl != null) out.add(tl);
		
		return out;
	}
	
	/**
	 * Returns the cell located a given distance from a given cell.  Ex - if the given cell is located 
	 * at (50,24) and tb=-1 and lf = 1, then the cell at (51,23) is returned.
	 * @param here
	 * @param tb the top/bottom distance to the desired cell
	 * @param lr the left/right distance to the desired cell
	 * @return The given cell or null otherwise
	 */
	private Cell getNeighborCell(Cell here, int tb, int lr) {
		int hereX = here.getX();
		int hereY = here.getY();
		int oY = tb + hereY;
		int oX = lr + hereX;
		Cell out = null;
		
		if(0 <= oY && oY < cells.length) {
			if(0 <= oX && oX < cells[oY].length) {
				out = cells[oY][oX];
			}
		}
		
		return out;
	}
	
	/**
	 * Iterates over all the cells in Cell[][] and sets the score for each cell.  
	 * @param targets
	 */
	public void setTargetCells(List<Cell> targets) {
		for(int j = 0; j < cells.length; j++) {
			for(int i = 0; i < cells[j].length; i++) {
				double score = Double.MIN_VALUE;
				//Cell targetCell = null;
				for(Cell c: targets) {
					
					// Higher score means closer to cell
					double curScore = calculateScore(cells[j][i], c);
					if(curScore > score) {
						score = curScore;
						//targetCell = c;
					}
				} // close target for
				
				// set the score for the cell
				cells[j][i].setScore(score);
				
			} // close width for
		} // close height for
	} // close method
	
	public double calculateScore(Cell here, Cell target) {
		double maxScore = 1000000;
		double minScore = 0;
		double maxDist = 1500;
		double minDist = 0;
		double dist = here.getDistanceToCell(target);
	
		// TODO only for Sidewalk and Crosswalk
		double score = MathHelper.linearInterp(dist, minDist, maxScore, maxDist, minScore);
		return score;
	}
	
	public void setCells(Cell[][] c) {
		this.cells = c;
	}
	
	public Cell[][] getCells() {
		return cells;
	}

}
