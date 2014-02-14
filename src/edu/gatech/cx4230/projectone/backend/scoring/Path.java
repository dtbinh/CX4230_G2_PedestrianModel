package edu.gatech.cx4230.projectone.backend.scoring;
import java.util.ArrayList;
import java.util.List;

import edu.gatech.cx4230.projectone.backend.abstraction.Cell;

/**
 * This class is an Immutable List that allows us to represent paths between
 * vertices in a weighted graph. These paths are comparable based on their total
 * weight and the time they were created
 */
public class Path implements Comparable<Path> {

	/**
	 * This is the total weight of the path
	 */
	private int weight;

	/**
	 * This is a list of all of the vertices in the path, it should contain a
	 * pair for start that has weight 0, and it should never be null -- this
	 * means make sure that the list is initialized in EVERY constructor
	 */
	private List<Edge> list;

	public Path() {
		list = new ArrayList<Edge>();
		weight = 0;
	}

	public Path(Edge e) {
		this();
		list.add(e);
		weight = e.getWeight();
	}

	public Path(Path p, Edge e) {
		this();
		list.addAll(p.getList());
		weight = p.getWeight() + e.getWeight();
	}

	public Path(List<Cell> l) {
		this();
		if(l != null && l.size() >= 2) {
			Cell prev = l.get(0);

			for(int i = 1; i < l.size(); i++) {
				Edge e = new Edge(prev, l.get(i));
				addEdge(e);
				prev = l.get(i);
			}

		}
	}

	public void addEdge(Edge e) {
		list.add(e);
		weight += e.getWeight();
	}

	@Override
	public int compareTo(Path o) {
		return this.weight - o.weight;
	}

	/**
	 * @return the weight
	 */
	public int getWeight() {
		return weight;
	}

	/**
	 * @param weight the weight to set
	 */
	public void setWeight(int weight) {
		this.weight = weight;
	}

	/**
	 * @return the list
	 */
	public List<Edge> getList() {
		return list;
	}

	/**
	 * @param list the list to set
	 */
	public void setList(List<Edge> list) {
		this.list = list;
	}

	public Cell getSource() {
		Cell out = null;
		if(list != null && !list.isEmpty()) {
			out = list.get(0).getSource();
		}
		return out;
	}

	public Cell removeSource() {
		Cell out = null;
		if(list != null && !list.isEmpty()) {
			Edge e = list.remove(0);
			out = e.getSource();
			weight -= e.getWeight();
		}
		return out;
	}

	public boolean hasNext() {
		return !(getSource() == null);
	}


}
