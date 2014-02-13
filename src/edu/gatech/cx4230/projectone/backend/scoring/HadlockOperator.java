package edu.gatech.cx4230.projectone.backend.scoring;

import java.util.ArrayList;
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


	public HadlockOperator(CellManager cake) {
		this.cm = cake;
		cpq = new CustomPriorityQueue();
	}

	public List<Cell> findPath(Cell here, Cell dest) {
		List<Cell> out = new ArrayList<Cell>();
		if(here.isTraversable() && dest.isTraversable()) {
			PriorityQueue<Cell> visitedCells = new PriorityQueue<Cell>();

			here.setDetourNumber(0);
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
				List<Cell> cNeighbors = cm.getCardinalTraversableNeighbors(c);
				int cMD = (int) c.getManhattanDistance(dest);

				Cell dnMin = getManhattanDistanceCell(cNeighbors);
				if(dnMin != null) {
					int pMD = (int) dnMin.getManhattanDistance(dest);

					int detNum = dnMin.getDetourNumber();
					if(cMD > pMD) {
						detNum++;
					}
					c.setDetourNumber(detNum);			
					cm.setCellSmart(c);
				}
				if(DEBUG) System.out.println("(" + c.getX() + ", " + c.getY() + ")\tDN: " + c.getDetourNumber());
				for(Cell d: cNeighbors) {
					if(!visitedCells.contains(d) && !cpq.contains(d)) {
						d.setDistTodestination((int) d.getManhattanDistance(dest));
						d.setPrevious(c);
						cm.setCellSmart(d);
						cpq.add(d);
					}
				} // close for
				if(c.equals(dest)) {
					dest.setDetourNumber(c.getDetourNumber());
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
				out[j][i] = cells[j][i].getDetourNumber();
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
