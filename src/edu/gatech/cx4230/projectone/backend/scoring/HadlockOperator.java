package edu.gatech.cx4230.projectone.backend.scoring;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Set;

import edu.gatech.cx4230.projectone.backend.abstraction.Cell;
import edu.gatech.cx4230.projectone.backend.abstraction.CellManager;
import edu.gatech.cx4230.projectone.backend.map.MapGridData;
import edu.gatech.cx4230.projectone.backend.utilities.ArrayManipulation;

/**
 * Performs Hadlock's Algorithm on the cell array
 * @author tbowling3
 *
 */
public class HadlockOperator {
	private CellManager cm;
	private HashMap<Integer, List<Cell>> map;


	public HadlockOperator(CellManager cake) {
		this.cm = cake;
	}

	public void cellFilling(Cell here, Cell dest) {
		PriorityQueue<Cell> visitedCells = new PriorityQueue<Cell>();
		PriorityQueue<Cell> waitingCells = new PriorityQueue<Cell>();
		map = new HashMap<Integer, List<Cell>>();
		
		here.setDetourNumber(0);
//		waitingCells.add(here);
		addCellToMap(here, 0);
		int step = 0;
		int stepLimit = Integer.MAX_VALUE;
		Cell c = null;
		
		while(!waitingCells.isEmpty() && step < stepLimit) {
			// Progress printing
			if(step % 250 == 0) {
				System.out.println("Step: " + step + "\tWaiting: " + waitingCells.size());
			}

//			c = waitingCells.poll();
			c = removeHighestPriorityCell();
			List<Cell> cNeighbors = cm.getCardinalTraversableNeighbors(c);
			int cMD = (int) c.getManhattanDistance(dest);

			Cell dnMin = getMinimumDetourNumberCell(cNeighbors);
			if(dnMin != null) {
				int pMD = (int) dnMin.getManhattanDistance(dest);

				int detNum = dnMin.getDetourNumber();
				if(cMD > pMD) {
					detNum++;
				}
				c.setDetourNumber(detNum);
				// TODO System.out.println("(" + c.getX() + ", " + c.getY() + ")\tDN: " + c.getDetourNumber());
				cm.setCellSmart(c);
			}
			for(Cell d: cNeighbors) {
				if(!visitedCells.contains(d) && !waitingCells.contains(d)) {
					waitingCells.add(d);
				}
			} // close for
			if(c.equals(dest)) {
				System.out.println("Dest found");
				break;
			}
			visitedCells.add(c);

			step++;
		} // close while

		// So as it indicate Here
		here.setDetourNumber(-2);
		dest.setDetourNumber(-2);
		cm.setCellSmart(here);
		cm.setCellSmart(dest);
		System.out.println("Cell Filling complete after: " + step);
	} // close cellFilling(...)

	private Cell getMinimumDetourNumberCell(List<Cell> list) {
		Cell out = null;
		if(list != null && !list.isEmpty()) {

			for(Cell c: list) {
				if(c.isVisited()) {
					if(out != null && c.getDetourNumber() < out.getDetourNumber()) {
						out = c;
					} else if(out == null){
						out = c;
					}
				}
			}
		}

		return out;
	}
	
	private void addCellToMap(Cell c, int dn) {
		if(map.containsKey(dn)) {
			List<Cell> l = map.remove(dn);
			l.add(c);
			map.put(dn, l);
		} else {
			List<Cell> l = new ArrayList<Cell>();
			l.add(c);
			map.put(dn, l);
			
		}
	}
	
	private Cell removeHighestPriorityCell() {
		Cell out = null;
		Set<Integer> keys = map.keySet();
		List<Integer> l = new ArrayList<Integer>(keys);
		if(!l.isEmpty()) {
			for(Integer i: l) {
				List<Cell> row = map.get(i);
				if(!row.isEmpty()) {
					out = row.remove(0);
					map.put(i, row);
					break;
				}
			}
		}
		return out;
	}

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
		Cell dest = cm.getCell(325, 315);
		HadlockOperator ho = new HadlockOperator(cm);
		ho.cellFilling(source, dest);
		cm = ho.getCm();
		int[][] dNs = ho.createDetourNumberMatrix(cm.getCells());
		int[][] dNs2 = ArrayManipulation.getSubMatrix(dNs, 275, 280, 150, 50);
		ArrayManipulation.writeIntArrayToCSV(dNs2, "/Users/ducttapeboro/Desktop/HadlockTest.csv");
		System.out.println("Main DONE");

	}

}
