package edu.gatech.cx4230.projectone.backend.map;

import java.util.List;

import processing.core.PApplet;
import de.fhpotsdam.unfolding.UnfoldingMap;
import de.fhpotsdam.unfolding.geo.Location;
import de.fhpotsdam.unfolding.marker.Marker;
import de.fhpotsdam.unfolding.utils.MapUtils;
import edu.gatech.cx4230.projectone.backend.main.PedestrianSimulation;

/**
 * Object that will be the main driver for the visualization of the simulation
 * @author tbowling3
 *
 */
public class VisualizationMain extends PApplet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1551910428456184440L;
	
	public static final double CELL_FACTOR = 0.05;
	private UnfoldingMap map;
	private static final int WIDTH = 1000, HEIGHT = 800;
	public static final double TOP_LEFT_LAT = 50;
	public static final double TOP_LEFT_LON = -35;
	private PedestrianSimulation ps;
	private List<Marker> markers;
	
	
	public void setup() {
		size(WIDTH, HEIGHT);
		
		map = new UnfoldingMap(this);

		MapUtils.createDefaultEventDispatcher(this, map);
		map.zoomAndPanTo(new Location(TOP_LEFT_LAT, TOP_LEFT_LON), 5);
		
		//noLoop();
		
		ps = new PedestrianSimulation();
		ps.mainLoop(this);
		
		redraw();
		
	}
	
	/**
	 * Draw operation called by the PApplet
	 */
	public void draw() {
		background(175);
		map.updateMap();
		
		if(markers != null && !markers.isEmpty()) {
			for(Marker m: markers) {
				m.draw(map);
			}
		}
		//map.draw();
	}

	/**
	 * @return the markers
	 */
	public List<Marker> getMarkers() {
		return markers;
	}

	/**
	 * @param markers the markers to set
	 */
	public void setMarkers(List<Marker> markers) {
		this.markers = markers;
	}


}
