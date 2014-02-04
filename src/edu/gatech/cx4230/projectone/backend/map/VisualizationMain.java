package edu.gatech.cx4230.projectone.backend.map;

import java.util.ArrayList;
import java.util.List;

import processing.core.PApplet;
import processing.core.PFont;
import de.fhpotsdam.unfolding.UnfoldingMap;
import de.fhpotsdam.unfolding.geo.Location;
import de.fhpotsdam.unfolding.marker.Marker;
import de.fhpotsdam.unfolding.utils.MapUtils;
import edu.gatech.cx4230.projectone.backend.abstraction.Cell;
import edu.gatech.cx4230.projectone.backend.abstraction.Person;
import edu.gatech.cx4230.projectone.backend.main.PedestrianSimulation;
import edu.gatech.cx4230.projectone.visualization.abstraction.PersonMarker;

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

	public static final double CELL_FACTOR_X = 0.05, CELL_FACTOR_Y = 0.04;
	private UnfoldingMap map;
	private static final int WIDTH = 1000, HEIGHT = 800;
	public static final double TOP_LEFT_LAT = 50;
	public static final double TOP_LEFT_LON = -35;
	private PedestrianSimulation ps;
	private List<Marker> markers;
	private List<Marker> peopleMarkers;
	private int timeStep = 0;
	private PFont f;


	public void setup() {
		size(WIDTH, HEIGHT);

		map = new UnfoldingMap(this);

		MapUtils.createDefaultEventDispatcher(this, map);
		map.zoomAndPanTo(new Location(TOP_LEFT_LAT, TOP_LEFT_LON), 5);
		peopleMarkers = new ArrayList<Marker>();
		
		f = createFont("Arial", 40, true);

		timeStep = 0;
		ps = new PedestrianSimulation(this);
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

		updatePeopleMarkers();
		if(peopleMarkers != null && !peopleMarkers.isEmpty()) {
			for(Marker m: peopleMarkers) {
				m.draw(map);
			}
		} // close peopleMarkers if
		
		fill(207, 14, 14);
		textFont(f);
		updateTimeStep();
		text(timeStep, WIDTH - 100, 75);
	} // close draw
	
	private void updateTimeStep() {
		if(ps.timeChanged()) {
			timeStep = ps.getTimeStep();
		}
	}

	private void updatePeopleMarkers() {
		if(ps.peopleAvailable()) {
			List<Person> people = ps.getPeople();

			if(people != null && !people.isEmpty()) {
				peopleMarkers.clear();

				for(Person p: people) {
					Cell location = p.getLocation();
					if(location != null) {
						double x = location.getX() + 0.5;
						double y = location.getY() + 0.5;
						Location loc = getMapLocationForCellLocation(x, y);
						PersonMarker pm = new PersonMarker(loc, p.getStressLevel());
						peopleMarkers.add(pm);
					} // close if
				} // close people for
			} // close null if
		} // close people if
	} // close updatePeopleMarkers()

	/**
	 * Returns the map position of the top left corner of a given cell index (x,y)
	 * @param x
	 * @param y
	 * @return
	 */
	public Location getMapLocationForCellLocation(double x, double y) {
		double lon = TOP_LEFT_LON + x * CELL_FACTOR_X;
		double lat = TOP_LEFT_LAT - y * CELL_FACTOR_Y;
		return new Location(lat, lon);
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

	/**
	 * @return the peopleMarkers
	 */
	public List<Marker> getPeopleMarkers() {
		return peopleMarkers;
	}

	/**
	 * @param peopleMarkers the peopleMarkers to set
	 */
	public void setPeopleMarkers(List<Marker> peopleMarkers) {
		this.peopleMarkers = peopleMarkers;
	}

	public static void main(String[] args) {
		PApplet.main(new String[] {"edu.gatech.cx4230.projectone.backend.map.VisualizationMain"});
	}


}
