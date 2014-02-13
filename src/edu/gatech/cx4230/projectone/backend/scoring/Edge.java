package edu.gatech.cx4230.projectone.backend.scoring;

import edu.gatech.cx4230.projectone.backend.abstraction.Cell;

public class Edge {
	private int id;
	private static int nextId;
	private String name;
	private Cell source;
	private Cell destination;
	private int weight;
	
	public Edge(String name, Cell source, Cell destination, int weight) {
		this.name = name;
		this.source = source;
		this.destination = destination;
		this.weight = weight;
		id = nextId++;
	}
	
	public Edge(Cell source, Cell destination, int weight) {
		this("", source, destination, weight);
	}
	
	public Edge(Cell source, Cell destination) {
		this(source, destination, 1);
	}
	
	public Cell getSource() {
		return source;
	}
	
	public Cell getDestination() {
		return destination;
	}
	
	public int getWeight() {
		return weight;
	}
	
	public int getId() {
		return id;
	}
	
	public String getName() {
		return name;
	}

}
