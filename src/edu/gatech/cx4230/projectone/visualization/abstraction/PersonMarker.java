package edu.gatech.cx4230.projectone.visualization.abstraction;

import processing.core.PConstants;
import processing.core.PGraphics;
import de.fhpotsdam.unfolding.geo.Location;
import de.fhpotsdam.unfolding.marker.SimplePointMarker;
import edu.gatech.cx4230.projectone.backend.abstraction.Person;
import edu.gatech.cx4230.projectone.backend.utilities.MathHelper;

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
		pg.ellipse((int) x, (int) y, radius, radius);
		pg.popStyle();
	}
	
	private void setColorForStress(double stress) {
		int maxStress = Person.MAX_STRESS;
		int minStress = Person.MIN_STRESS;
		int maxR = 255, minR = 0;
		int maxB = 255, minB = 0;
		
		colorR = (int) MathHelper.linearInterp(stress, minStress, minR, maxStress, maxR);
		colorG = 0;
		colorB = (int) MathHelper.linearInterp(stress, minStress, maxB, maxStress, minB);
		
	}
	
	

}
