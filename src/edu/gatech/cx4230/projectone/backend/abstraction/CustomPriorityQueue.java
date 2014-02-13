package edu.gatech.cx4230.projectone.backend.abstraction;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

import edu.gatech.cx4230.projectone.backend.utilities.ListHelper;

public class CustomPriorityQueue {
	private HashMap<Integer, Queue<Cell>> data;
	public static final int DIST_TO_DEST = 31435;
	public static final int DETOUR_NUMBER = 365753;
	private int simCase = DIST_TO_DEST;


	public CustomPriorityQueue() {
		data = new HashMap<Integer, Queue<Cell>>();
	}

	public void add(Cell c) {
		if(c != null) {
			int i = getSimCase(c);

			Queue<Cell> queue;
			if(data.get(i) == null) {
				queue = new LinkedList<Cell>();

			} else {
				queue = data.get(i);
			} // close else

			queue.add(c);
			data.put(i, queue);
		}
	}

	public Cell poll() {
		Cell out = null;
		int row = getFirstRowNotEmpty();
		if(row >= 0) {
			Queue<Cell> queue = data.remove(row);
			out = queue.poll();
			if(!queue.isEmpty()) {
				data.put(row, queue);
			}
		}
		return out;
	}

	public Cell peek() {
		Cell out = null;
		int row = getFirstRowNotEmpty();
		if(row >= 0) {
			Queue<Cell> queue = data.get(row);
			out = queue.peek();
			data.put(row, queue);
		}
		return out;
	}

	private int getFirstRowNotEmpty() {
		int out = -1;
		Set<Integer> keys = data.keySet();
		int[] array = ListHelper.convertSetToArray(keys);
		Arrays.sort(array);
		for(int i: array) {
			Queue<Cell> q = data.get(i);
			if(q != null && !q.isEmpty()) {
				out = i;
				break;
			}
		}

		return out;
	}

	public boolean isEmpty() {
		boolean out = true;
		Collection<Queue<Cell>> collection = data.values();

		for(Queue<Cell> q: collection) {
			if(!q.isEmpty()) {
				out = false;
				break;
			}
		}

		return out;
	}

	public int size() {
		int count = 0;

		Collection<Queue<Cell>> collection = data.values();
		for(Queue<Cell> q: collection) {
			count += q.size();
		}

		return count;
	}

	public boolean contains(Cell c) {
		boolean out = false;
		if(c != null) {
			int i = getSimCase(c);
			Queue<Cell> q = data.get(i);
			if(q != null && !q.isEmpty()) {
				out = q.contains(c);
			}
		}

		return out;
	} // close contains(...)
	
	private int getSimCase(Cell c) {
		int out = -1;
		switch(simCase) {
		case DIST_TO_DEST:
			out = c.getDistTodestination();
			break;
		case DETOUR_NUMBER:
			out = c.getDetourNumber();
			break;
		}
		return out;
	}
}
