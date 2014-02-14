package edu.gatech.cx4230.projectone.backend.scoring;

import java.util.ArrayList;
import java.util.List;

import edu.gatech.cx4230.projectone.backend.abstraction.Cell;

public class PathOrganizer {
	private List<Path> paths;
	
	public PathOrganizer() {
		paths = new ArrayList<Path>();
	}
	
	public PathOrganizer(List<Path> list) {
		this.paths = list;
	}
	
	public void addPath(Path p) {
		paths.add(p);
	}
	
	public Path getMinimumPath(Cell source) {
		Path out = null;
		
		for(Path p: paths) {
			if(p.getSource().equals(source)) {
				if(out == null || p.getWeight() < out.getWeight()) {
					out = p;
				}
			}
		}
		return out;
	}

}
