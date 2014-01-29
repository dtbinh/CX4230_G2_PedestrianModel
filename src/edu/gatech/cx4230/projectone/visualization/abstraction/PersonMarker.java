package edu.gatech.cx4230.projectone.visualization.abstraction;

import processing.core.PGraphics;
import de.fhpotsdam.unfolding.marker.SimplePointMarker;

public class PersonMarker extends SimplePointMarker {

	/* (non-Javadoc)
	 * @see de.fhpotsdam.unfolding.marker.SimplePointMarker#draw(processing.core.PGraphics, float, float)
	 */
	@Override
	public void draw(PGraphics pg, float x, float y) {
		// TODO Auto-generated method stub
		if (isHidden())
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
		pg.ellipse((int) x, (int) y, radius, radius); // TODO use radius in km and convert to px
		pg.popStyle();
	}
	
	

}
