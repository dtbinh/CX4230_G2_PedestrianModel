package edu.gatech.cx4230.projectone.visualization.abstraction;

import processing.core.PConstants;
import processing.core.PGraphics;
import de.fhpotsdam.unfolding.geo.Location;
import de.fhpotsdam.unfolding.marker.SimplePointMarker;

public class PersonMarker extends SimplePointMarker {
	private int colorR, colorG, colorB;
	
	public PersonMarker(Location loc, double stress) {
		super(loc);
		setColorForStress(stress);
	}

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
			pg.fill(colorR, colorG, colorB);
			pg.stroke(colorR, colorG, colorB);
		} else {
			pg.fill(colorR, colorG, colorB);
			pg.stroke(colorR, colorG, colorB);
		}
		pg.ellipseMode(PConstants.CENTER);
		pg.ellipse((int) x, (int) y, radius, radius); // TODO use radius in km and convert to px
		pg.popStyle();
	}
	
	private void setColorForStress(double stress) {
		
	}
	
	

}
