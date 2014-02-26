package edu.gatech.cx4230.projectone.visualization.abstraction;

import processing.core.PGraphics;
import de.fhpotsdam.unfolding.geo.Location;

public class DoorMarker {
	private int colorR, colorG, colorB;
	private float xPos, yPos, size;
	
	public DoorMarker(Location loc, float size) {
		colorR = 230;
		colorG = 21;
		colorB = 230;
		xPos = loc.getLat();
		yPos = loc.getLon();
		this.size = size;
	}


	public void draw(PGraphics pg) {
		// TODO Auto-generated method stub
		pg.pushStyle();
		
		pg.fill(colorR, colorG, colorB);
		pg.stroke(colorR, colorG, colorB);
		
		pg.rect(xPos, yPos, size, size);
		pg.popStyle();
	}

}
