package edu.gatech.cx4230.projectone.visualization.abstraction;

import processing.core.PApplet;
import processing.core.PFont;

public class CustomTooltip {
	private int x, y;
	private int width, height;
	private String text;
	
	public CustomTooltip(String text, int x, int y, int width, int height) {
		this.text = text;
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}
	
	public void draw(PApplet p) {
		PFont f = p.createFont("Arial", 14, true);
		p.textFont(f);
		p.color(0, 0, 0);
		p.fill(125);
		p.rect(x, y, width, height);
		p.fill(0);
		p.text(text, x + 15, y + 15);
	}
	
	public void setLocation(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public void setText(String in) {
		this.text = in;
	}

}
