package edu.gatech.cx4230.projectone.visualization.abstraction;

import java.util.HashMap;
import java.util.List;

import processing.core.PConstants;
import processing.core.PGraphics;
import de.fhpotsdam.unfolding.geo.Location;
import de.fhpotsdam.unfolding.marker.SimplePolygonMarker;
import de.fhpotsdam.unfolding.utils.MapPosition;
import edu.gatech.cx4230.projectone.backend.abstraction.Cell;
import edu.gatech.cx4230.projectone.backend.map.MapGridData;

public class CellMarker extends SimplePolygonMarker {


	public CellMarker() {
		super();
	}

	public CellMarker(List<Location> locations) {
		super(locations);
	}

	public CellMarker(List<Location> locations, HashMap<String, Object> properties) {
		super(locations, properties);
	}

	/* (non-Javadoc)
	 * @see de.fhpotsdam.unfolding.marker.SimplePolygonMarker#draw(processing.core.PGraphics, java.util.List)
	 */
	@Override
	public void draw(PGraphics pg, List<MapPosition> mapPositions) {
		// TODO Auto-generated method stub
		if (mapPositions.isEmpty() || isHidden())
			return;

		pg.pushStyle();
		pg.strokeWeight(strokeWeight);
		if (isSelected()) {
			pg.fill(highlightColor);
			pg.stroke(highlightStrokeColor);
		} else {
			pg.fill(color);
			pg.stroke(strokeColor);
		}

		String cellType = getProperty(MapGridData.TYPE).toString();
		char type = cellType.charAt(0);

		pg.color(50);
		if(cellType != null) {
			if(type == Cell.BUILDING) {
				pg.fill(0,0,100);
				pg.stroke(0,0,100);
			} else if(type == Cell.CROSSWALK) {
				pg.fill(200, 200, 200);
				pg.stroke(200, 200, 200);
			} else if(type == Cell.ERROR) {
				pg.fill(255, 0, 0);
				pg.stroke(255, 0, 0);
			} else if(type == Cell.SIDEWALK) {
				pg.fill(255,255,0);
				pg.stroke(255,255,0);
			} else if(type == Cell.STREET) {
				pg.fill(40, 40, 40);
				pg.stroke(40, 40, 40);
			}
		}

		pg.beginShape();
		for (MapPosition pos : mapPositions) {
			pg.vertex(pos.x, pos.y);
		}
		pg.endShape(PConstants.CLOSE);
		pg.popStyle();
	}

}
