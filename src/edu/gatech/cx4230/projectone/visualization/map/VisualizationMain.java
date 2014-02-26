package edu.gatech.cx4230.projectone.visualization.map;

import java.util.ArrayList;
import java.util.Iterator;
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
import edu.gatech.cx4230.projectone.visualization.abstraction.CustomTooltip;
import edu.gatech.cx4230.projectone.visualization.abstraction.DoorMarker;
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
	private CustomTooltip cTooltip;
	private static final int WIDTH = 1000, HEIGHT = 800;
	private int cellsWidth, cellsHeight;
	public static final double TOP_LEFT_LAT = 50;
	public static final double TOP_LEFT_LON = -35;
	private PedestrianSimulation ps;
	private List<Marker> markers;
	private List<DoorMarker> doorMarkers;
	private List<Marker> peopleMarkers;
	private int timeStep = 0;
	private PFont f;


	public void setup() {
		size(WIDTH, HEIGHT);

		map = new UnfoldingMap(this);

		MapUtils.createDefaultEventDispatcher(this, map);
		map.zoomAndPanTo(new Location(TOP_LEFT_LAT, TOP_LEFT_LON), 5);
		peopleMarkers = new ArrayList<Marker>();
		doorMarkers = new ArrayList<DoorMarker>();
		
		f = createFont("Arial", 40, true);
		cTooltip = new CustomTooltip("Cell: ()", 20, 20, 250, 120);

		timeStep = 0;
		ps = new PedestrianSimulation(this, true);
		
		updateDoorMarkers();
		
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

		if(doorMarkers != null && !doorMarkers.isEmpty()) {
			for(DoorMarker m: doorMarkers) {
				m.draw(this.g);
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
		cTooltip.draw(this);
	} // close draw
	
	private void updateTimeStep() {
		if(ps.timeChanged()) {
			timeStep = ps.getTimeStep();
		}
	}

	public void updateDoorMarkers() {
		if(doorMarkers == null) {
			doorMarkers = new ArrayList<DoorMarker>();
		}
		doorMarkers.clear();
		List<Cell> doors = ps.getDoors();
		for(Cell d : doors) {
			Location loc = getMapLocationForCellLocation(d.getX(), d.getY());
			float size = Math.abs(loc.getLat() - getMapLocationForCellLocation(d.getX()+1, d.getY()).getLat());
			DoorMarker dm = new DoorMarker(loc, size);
			doorMarkers.add(dm);
		}
	}
	
	private void updatePeopleMarkers() {
		if(ps.peopleAvailable()) {
			List<Person> people = ps.getPeople();

			if(people != null && !people.isEmpty()) {
				peopleMarkers.clear();

				//for(Person p: people) {
				for(Iterator<Person> it = people.iterator(); it.hasNext();) {
					Person p = it.next();
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
	
	public void mouseClicked() {
		Location location = map.getLocation(mouseX, mouseY);
		double lon = location.getLon();
		double lat = location.getLat();
		int cellX = (int) ((lon - TOP_LEFT_LON) / CELL_FACTOR_X);
		int cellY = (int) ((-lat + TOP_LEFT_LAT) / CELL_FACTOR_Y);
		
		if(0 <= cellX && cellX < cellsWidth) {
			if(0 <= cellY && cellY < cellsHeight) {
				setTooltipText(ps.infoForCell(cellX, cellY));
			}
		}
	} // close mouseClicked()
	
	public void keyPressed() {
		switch(key) {
		case ' ':
			ps.triggerThread();
			break;
		}
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

	/**
	 * @return the cellsWidth
	 */
	public int getCellsWidth() {
		return cellsWidth;
	}


	/**
	 * @param cellsWidth the cellsWidth to set
	 */
	public void setCellsWidth(int cellsWidth) {
		this.cellsWidth = cellsWidth;
	}


	/**
	 * @return the cellsHeight
	 */
	public int getCellsHeight() {
		return cellsHeight;
	}


	/**
	 * @param cellsHeight the cellsHeight to set
	 */
	public void setCellsHeight(int cellsHeight) {
		this.cellsHeight = cellsHeight;
	}
	
	public void setTooltipText(String in) {
		if(in != null && !in.isEmpty()) {
			cTooltip.setText(in);
		}
	}


	public static void main(String[] args) {
		PApplet.main(new String[] {"edu.gatech.cx4230.projectone.visualization.map.VisualizationMain"});
	}


}
