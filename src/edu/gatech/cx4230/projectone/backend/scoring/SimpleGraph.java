package edu.gatech.cx4230.projectone.backend.scoring;

import java.util.ArrayList;
import java.util.List;

import edu.gatech.cx4230.projectone.backend.abstraction.Cell;
import edu.gatech.cx4230.projectone.backend.abstraction.CellManager;

public class SimpleGraph {
	private List<Cell> nodes;
	private List<Edge> edges;
	private List<Cell> sinks;


	public SimpleGraph(CellManager cm) {
		nodes = new ArrayList<Cell>();
		edges = new ArrayList<Edge>();
		sinks = new ArrayList<Cell>();

		// Sinks
		Cell nw5 = cm.getCell(0, 55);
		Cell sw5 = cm.getCell(0, 99);
		Cell nwS = cm.getCell(81, 0);
		Cell neS = cm.getCell(116, 0);
		Cell ne5 = cm.getCell(477, 180);
		Cell se5 = cm.getCell(477, 206);
		Cell neP = cm.getCell(418, 0);
		Cell nwP = cm.getCell(375, 0);
		sinks.add(nw5);
		sinks.add(sw5);
		sinks.add(nwS);
		sinks.add(neS);
		sinks.add(ne5);
		sinks.add(se5);
		sinks.add(neP);
		sinks.add(nwP);

		
		// Spring & 5th - North
		Cell cS5NW = cm.getCell(86, 55); 
		Cell cS5NE = cm.getCell(114, 55);

		// - West
		Cell cS5WN = cm.getCell(81, 66);
		Cell cS5WS = cm.getCell(81, 87);

		// - South
		Cell cS5SW = cm.getCell(86, 92);
		Cell cS5SE = cm.getCell(114, 92);


		// ARMSTEAD C - North
		Cell cANW = cm.getCell(86, 289);
		Cell cANE = cm.getCell(113, 289);

		// - West
		Cell cAWN = cm.getCell(118, 294);
		Cell cAWS = cm.getCell(118, 312);

		// - East
		Cell cAEN = cm.getCell(372, 294);
		Cell cAES = cm.getCell(372, 312);


		// FIVE & FIVE - South
		Cell cFFSW = cm.getCell(379, 214);
		Cell cFFSE = cm.getCell(410, 214);

		// - East
		Cell cFFES = cm.getCell(421, 203);
		Cell cFFEN = cm.getCell(421, 183);

		// - North
		Cell cFFNE = cm.getCell(410, 93);
		Cell cFFNW = cm.getCell(379, 93);

		// - West
		Cell cFFWN = cm.getCell(375, 66);
		Cell cFFWS = cm.getCell(375, 89);
		
		// Add all nodes
		nodes.add(nw5);
		nodes.add(sw5);
		nodes.add(nwS);
		nodes.add(neS);
		nodes.add(ne5);
		nodes.add(se5);
		nodes.add(neP);
		nodes.add(nwP);
		nodes.add(cS5NW);
		nodes.add(cS5NE);
		nodes.add(cS5WN);
		nodes.add(cS5WS);
		nodes.add(cS5SW);
		nodes.add(cS5SE);
		nodes.add(cANW);
		nodes.add(cANE);
		nodes.add(cAWN);
		nodes.add(cAWS);
		nodes.add(cAEN);
		nodes.add(cAES);
		nodes.add(cFFSW);
		nodes.add(cFFSE);
		nodes.add(cFFES);
		nodes.add(cFFEN);
		nodes.add(cFFNE);
		nodes.add(cFFNW);
		nodes.add(cFFWN);
		nodes.add(cFFWS);
		
		edges.add(new Edge(nwS, cS5NW)); // 1
		edges.add(new Edge(cS5NW, nwS)); // 1
		edges.add(new Edge(ne5, cS5NE)); // 2
		edges.add(new Edge(cS5NE, ne5)); // 2
		edges.add(new Edge(cS5NW, cS5NE)); // 3
		edges.add(new Edge(cS5NE, cS5NW)); // 3
		edges.add(new Edge(cS5NW, cS5WN)); // 4
		edges.add(new Edge(cS5WN, cS5NW)); // 4
		edges.add(new Edge(nw5, cS5WN)); // 5
		edges.add(new Edge(cS5WN, nw5)); // 5
		
		edges.add(new Edge(cS5WN, cS5WS)); // 6
		edges.add(new Edge(cS5WS, cS5WN)); // 6
		edges.add(new Edge(sw5, cS5WS)); // 7
		edges.add(new Edge(cS5WS, sw5)); // 7
		edges.add(new Edge(cS5WS, cS5SW)); // 8
		edges.add(new Edge(cS5SW, cS5WS)); // 8
		edges.add(new Edge(cS5SW, cANW)); // 9
		edges.add(new Edge(cANW, cS5SW)); // 9
		edges.add(new Edge(cS5SW, cS5SE)); // 10
		edges.add(new Edge( cS5SE, cS5SW)); // 10
		
		edges.add(new Edge(cS5NE, cFFWN)); // 11
		edges.add(new Edge(cFFWN, cS5NE)); // 11
		edges.add(new Edge(nwP, cFFWN)); // 12
		edges.add(new Edge(cFFWN, nwP)); // 12
		edges.add(new Edge(cFFWN, cFFWS)); // 13
		edges.add(new Edge(cFFWS, cFFWN)); // 13
		edges.add(new Edge(cFFWS, cFFNW)); // 14
		edges.add(new Edge(cFFNW, cFFWS)); // 14
		edges.add(new Edge(neP, cFFNE)); // 15
		edges.add(new Edge(cFFNE, neP)); // 15
		
		edges.add(new Edge(cFFNW, cFFNE)); // 16
		edges.add(new Edge(cFFNE, cFFNW)); // 16
		edges.add(new Edge(cFFNE, cFFEN)); // 17
		edges.add(new Edge(cFFEN, cFFNE)); // 17
		edges.add(new Edge(cFFEN, ne5)); // 18
		edges.add(new Edge(ne5, cFFEN)); // 18
		edges.add(new Edge(cFFEN, cFFES)); // 19
		edges.add(new Edge(cFFES, cFFEN)); // 19
		edges.add(new Edge(se5, cFFES)); // 20
		edges.add(new Edge(cFFES, se5)); // 20
		
		edges.add(new Edge(cFFES, cFFSE)); // 21
		edges.add(new Edge(cFFSE, cFFES)); // 21
		edges.add(new Edge(cFFSE, cFFSW)); // 22
		edges.add(new Edge(cFFSW, cFFSE)); // 22
		edges.add(new Edge(cFFNW, cFFSW)); // 23
		edges.add(new Edge(cFFSW, cFFNW)); // 23
		edges.add(new Edge(cFFSW, cAEN)); // 24
		edges.add(new Edge(cAEN, cFFSW)); // 24
		edges.add(new Edge(cAEN, cAES)); // 25
		edges.add(new Edge(cAES, cAEN)); // 25
		
		edges.add(new Edge(cAWN, cAEN)); // 26
		edges.add(new Edge(cAEN, cAWN)); // 26
		edges.add(new Edge(cAES, cAWS)); // 27
		edges.add(new Edge(cAWS, cAES)); // 27
		edges.add(new Edge(cAWS, cAWN)); // 28
		edges.add(new Edge(cAWN, cAWS)); // 28
		edges.add(new Edge(cAWN, cANE)); // 29
		edges.add(new Edge(cANE, cAWN)); // 29
		edges.add(new Edge(cANW, cANE)); // 30
		edges.add(new Edge(cANE, cANW)); // 30
		
		edges.add(new Edge(cS5SE, cANE)); // 31
		edges.add(new Edge(cANE, cS5SE)); // 31
		edges.add(new Edge(cS5SE, cFFWS)); // 32
		edges.add(new Edge(cFFWS, cS5SE)); // 32
		
	}


	/**
	 * @return the nodes
	 */
	public List<Cell> getNodes() {
		return nodes;
	}


	/**
	 * @param nodes the nodes to set
	 */
	public void setNodes(List<Cell> nodes) {
		this.nodes = nodes;
	}


	/**
	 * @return the edges
	 */
	public List<Edge> getEdges() {
		return edges;
	}


	/**
	 * @param edges the edges to set
	 */
	public void setEdges(List<Edge> edges) {
		this.edges = edges;
	}


	/**
	 * @return the sinks
	 */
	public List<Cell> getSinks() {
		return sinks;
	}

}
