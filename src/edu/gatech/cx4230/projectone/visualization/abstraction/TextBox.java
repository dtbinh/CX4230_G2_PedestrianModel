package edu.gatech.cx4230.projectone.visualization.abstraction;

import processing.core.PApplet;

public class TextBox {
	private int x, y;
	private int width, height;
	private String text;
	
	public TextBox(String text, int x, int y, int width, int height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}
	
	public void draw(PApplet p) {
		p.color(0, 0, 0);
		p.fill(125);
		p.rect(x, y, width, height);
		p.text(text, x + 5, y + 5);
	}
	
	public void setLocation(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public void setText(String in) {
		this.text = in;
	}

}
