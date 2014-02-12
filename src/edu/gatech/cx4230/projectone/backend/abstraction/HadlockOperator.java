package edu.gatech.cx4230.projectone.backend.abstraction;

import java.util.List;
import java.util.PriorityQueue;

import edu.gatech.cx4230.projectone.backend.map.MapGridData;
import edu.gatech.cx4230.projectone.backend.utilities.ArrayManipulation;

/**
 * Performs Hadlock's Algorithm on the cell array
 * @author tbowling3
 *
 */
public class HadlockOperator {
	private CellManager cm;
	private PriorityQueue<Cell> visitedCells;
	private PriorityQueue<Cell> waitingCells;
	
	
	public HadlockOperator(CellManager cake) {
		this.cm = cake;
		visitedCells = new PriorityQueue<Cell>();
		waitingCells = new PriorityQueue<Cell>();
	}
	
	public void cellFilling(Cell here, Cell dest) {
		here.setDetourNumber(0);
		waitingCells.add(here);
		int step = 0;
		
		while(!waitingCells.isEmpty() && step < 25000) {
			// Progress printing
			if(step % 250 == 0) {
				System.out.println("Step: " + step + "\tWaiting: " + waitingCells.size());
			}
			
			Cell c = waitingCells.poll();
			int cx = Math.abs(c.getX() - dest.getX());
			int cy = Math.abs(c.getY() - dest.getY());
			
			Cell prev = c.getPrevious();
			if(prev != null) {
				int px = Math.abs(c.getX() - prev.getX());
				int py = Math.abs(c.getY() - prev.getY());
				
				if(cx <= px || cy <= py) {
					c.setDetourNumber(prev.getDetourNumber());
				} else {
					c.setDetourNumber(prev.getDetourNumber() + 1);
				}
				cm.setCellSmart(c);
			} else {
				System.out.println("prev is null");
			}
			
			
			List<Cell> cNeighbors = cm.getAllTraversableNeighbors(c);
			for(Cell d: cNeighbors) {
				d.setPrevious(c);
				cm.setCellSmart(d);
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
		cm.setCellSmart(here);
		System.out.println("Cell Filling complete after: " + step);
	} // close cellFilling(...)
	
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
		int[][] dNs2 = ArrayManipulation.getSubMatrix(dNs, 275, 300, 100, 25);
		ArrayManipulation.writeIntArrayToCSV(dNs2, "/Users/ducttapeboro/Desktop/HadlockTest.csv");
		System.out.println("Main DONE");
		
	}

}
