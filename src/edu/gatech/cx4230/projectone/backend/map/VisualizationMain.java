package edu.gatech.cx4230.projectone.backend.map;

import processing.core.PApplet;
import edu.gatech.cx4230.projectone.backend.abstraction.Cell;
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
	
	public static final int CELL_SIZE = 5;
	private int viewTLX, viewTLY; // in pixels
	private int viewWidth, viewHeight; // in pixels (to be used for zoom)
	private int mousePressX, mousePressY;
	private boolean mouseEngaged = false;
	private static final int WIDTH = 1000, HEIGHT = 800;
	private PedestrianSimulation ps;
	
	
	public void setup() {
		size(WIDTH, HEIGHT);
		
		noLoop();
		viewTLX = 0;
		viewTLY = 0;
		
		ps = new PedestrianSimulation();
		ps.mainLoop(this);
		redraw();
	}
	
	/**
	 * Draw operation called by the PApplet
	 */
	public void draw() {
		System.out.println("Drawing");
		Cell[][] cells = ps.getCells();
		int cellWidth = cells[0].length;
		int cellHeight = cells.length;
		int drawX = viewTLX * CELL_SIZE; // index
		int drawY = viewTLY * CELL_SIZE; // index
		int drawWidth = WIDTH / CELL_SIZE; // index count 
		int drawHeight = HEIGHT / CELL_SIZE; // index count
		
		int limX = Math.min(cellWidth, drawX + drawWidth);
		int limY = Math.min(cellHeight, drawY + drawHeight);
		
		for(int j = drawY; j < (limX); j++) {
			for(int i = drawX; i < (limY); i++) {
				cells[j][i].draw(this);
			}
		}
		// should only draw visible cells.  Vis will not display entire model space.  
		// Zoom required to view parts of the map
	}
	
	public void mousePressed() {
		mouseEngaged = true;
		mousePressX = mouseX;
		mousePressY = mouseY;
	}
	
	public void mouseDragged() {
		if(mouseEngaged) {
			viewTLX += mousePressX - mouseX;
			viewTLY += mousePressY - mouseY;
		}
	}
	
	public void mouseRealeased() {
		mouseEngaged = false;
	}
	
	public void mouseMoved() {
		
	}

}
