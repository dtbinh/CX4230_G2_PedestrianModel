package edu.gatech.cx4230.projectone.backend.scoring;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.PriorityQueue;

import edu.gatech.cx4230.projectone.backend.abstraction.Cell;
import edu.gatech.cx4230.projectone.backend.abstraction.CellManager;
import edu.gatech.cx4230.projectone.backend.abstraction.CustomPriorityQueue;
import edu.gatech.cx4230.projectone.backend.map.MapGridData;
import edu.gatech.cx4230.projectone.backend.utilities.ArrayManipulation;

/**
 * Performs Hadlock's Algorithm on the cell array
 * @author tbowling3
 *
 */
public class HadlockOperator {
	private CellManager cm;
	private CustomPriorityQueue cpq;
	public static boolean DEBUG = false;
	private int[] detourNumbers; // index in array maps to Cell.id


	public HadlockOperator(CellManager cake) {
		this.cm = cake;
	}
	
	private void resetDetourNumbers() {
		detourNumbers = new int[cm.getCellsHeight() * cm.getCellsWidth()];
		for(int i = 0; i < detourNumbers.length; i++) {
			detourNumbers[i] = Integer.MAX_VALUE / 1000;
		}
	}

	public List<Cell> findPath(Cell here, Cell dest) {
		resetDetourNumbers();
		List<Cell> out = new ArrayList<Cell>();
		if(here.isTraversable() && dest.isTraversable()) {
			cpq = new CustomPriorityQueue();
			CellManager cmThis = cm;
			
			PriorityQueue<Cell> visitedCells = new PriorityQueue<Cell>();

			detourNumbers[here.getID()] = 0;
			here.setVisited(true);
			here.setDistTodestination((int) here.getManhattanDistance(dest));
			cpq.add(here);
			int step = 0;
			int stepLimit = Integer.MAX_VALUE;
			Cell c = null;

			while(!cpq.isEmpty() && step < stepLimit) {
				// Progress printing
				if(DEBUG && step % 250 == 0) {
					System.out.println("Step: " + step + "\tWaiting: " + cpq.size());
				}

				c = cpq.poll();
				List<Cell> cNeighbors = cmThis.getCardinalTraversableNeighbors(c);
				int cMD = (int) c.getManhattanDistance(dest);

				Cell dnMin = getManhattanDistanceCell(cNeighbors);
				if(dnMin != null) {
					int pMD = (int) dnMin.getManhattanDistance(dest);

					int detNum = detourNumbers[dnMin.getID()];
					if(cMD > pMD) {
						detNum++;
					}
					detourNumbers[c.getID()] = detNum;
					c.setVisited(true);
					cmThis.setCellSmart(c);
				}
				if(DEBUG) System.out.println("(" + c.getX() + ", " + c.getY() + ")\tDN: " + detourNumbers[c.getID()]);
				for(Cell d: cNeighbors) {
					if(!visitedCells.contains(d) && !cpq.contains(d)) {
						d.setDistTodestination((int) d.getManhattanDistance(dest));
						d.setPrevious(c);
						cmThis.setCellSmart(d);
						cpq.add(d);
					}
				} // close for
				if(c.equals(dest)) {
					detourNumbers[dest.getID()] = detourNumbers[c.getID()];
					break;
				}
				visitedCells.add(c);

				step++;
			} // close while
			if(DEBUG) System.out.println("Cell Filling complete after: " + step);
			out = retrace(here, dest);
		} // close traversable if
		return out;
	} // close cellFilling(...)

	private List<Cell> retrace(Cell source, Cell dest) {
		List<Cell> out = new ArrayList<Cell>();
		Cell c = dest;

		do {
			out.add(c);
			c = c.getPrevious();
		} while(!c.equals(source));
		out.add(source);
		
		Collections.reverse(out);
		return out;
	}

	private Cell getManhattanDistanceCell(List<Cell> list) {
		Cell out = null;
		if(list != null && !list.isEmpty()) {
			for(Cell c: list) {
				if(c.isVisited()) {
					if(out != null && c.getDistTodestination() < out.getDistTodestination()) {
						out = c;
					} else if(out == null){
						out = c;
					}
				}
			} // close for
		} // close null/empty if

		return out;
	} // close getManhattanDistanceCell(...)

	public int[][] createDetourNumberMatrix(Cell[][] cells) {
		int[][] out = new int[cells.length][];

		for(int j = 0; j < out.length; j++) {
			out[j] = new int[cells[j].length];

			for(int i = 0; i < out[j].length; i++) {
				out[j][i] = detourNumbers[cells[j][i].getID()];
			}
		}

		return out;
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

	public static void main(String[] args) {
		MapGridData mgd = new MapGridData();
		CellManager cm = mgd.getCellManager();
		System.out.println("Traversable cells count: " + cm.getAllTraversableCells().size());

		Cell source = cm.getCell(300, 315);
		Cell dest = cm.getCell(325, 318);
		HadlockOperator ho = new HadlockOperator(cm);
		ho.findPath(source, dest);
		cm = ho.getCm();
		int[][] dNs = ho.createDetourNumberMatrix(cm.getCells());
		int[][] dNs2 = ArrayManipulation.getSubMatrix(dNs, 275, 280, 150, 50);
		ArrayManipulation.writeIntArrayToCSV(dNs2, "/Users/ducttapeboro/Desktop/HadlockTest.csv");
		System.out.println("Main DONE");

	}

}
