package edu.gatech.cx4230.projectone.backend.abstraction;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import edu.gatech.cx4230.projectone.backend.utilities.MathHelper;

public class Door {
	private int id, type;
	private int x, y, width, height;
	public static final int QUAD = 4;
	public static final int DOUBLE = 2;
	public static final int SINGLE = 1;
	public static final int RANGE = 3;
	
	public Door(int type, int id, int x, int y, int width, int height) {
		this.type = type;
		this.id = id;
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}
	
	public List<Point> getPoints() {
		List<Point> out = new ArrayList<Point>();
		
		for(int j = y; j < (y + height); j++) {
			for(int i = x; i < (x + width); i++) {
				out.add(new Point(i,j));
			} // close i-for
		} // close j-for
		
		return out;
	}
	
	public String toString() {
		return "Type: " + type + "\tID: " + id + "\tx: " + x + "\ty: " + y + "\tWidth: " + width + "\tHeight: " + height;
	}
	
	public boolean isPointInRange(int x2, int y2) {
		double dist1 = MathHelper.getCartesianDistance(x, y, x2, y2);
		boolean out = false;
		if(dist1 < RANGE) {
			out = true;
		} else {
			if(width != 1) {
				dist1 = MathHelper.getCartesianDistance(x + width, y, x2, y2);
			}
			if(height != 1) {
				dist1 = MathHelper.getCartesianDistance(x, y + height, x2, y2);
			}
			out = dist1 < RANGE;
		}
		
		return out;
	}

	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * @return the type
	 */
	public int getType() {
		return type;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(int type) {
		this.type = type;
	}

	/**
	 * @return the x
	 */
	public int getX() {
		return x;
	}

	/**
	 * @param x the x to set
	 */
	public void setX(int x) {
		this.x = x;
	}

	/**
	 * @return the y
	 */
	public int getY() {
		return y;
	}

	/**
	 * @param y the y to set
	 */
	public void setY(int y) {
		this.y = y;
	}

	/**
	 * @return the width
	 */
	public int getWidth() {
		return width;
	}

	/**
	 * @param width the width to set
	 */
	public void setWidth(int width) {
		this.width = width;
	}

	/**
	 * @return the height
	 */
	public int getHeight() {
		return height;
	}

	/**
	 * @param height the height to set
	 */
	public void setHeight(int height) {
		this.height = height;
	}
	

}
