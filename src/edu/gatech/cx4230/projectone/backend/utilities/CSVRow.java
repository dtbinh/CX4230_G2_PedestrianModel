package edu.gatech.cx4230.projectone.backend.utilities;

import edu.gatech.cx4230.projectone.backend.abstraction.Cell;

public class CSVRow {
	private double topLeftXFt, topLeftYFt, widthFt, heightFt;
	private int topLeftXInd, topLeftYInd, widthInd, heightInd;
	private int csvRow;
	private String name;
	private char cellType;
	
	
	public CSVRow(double tlxft, double tlyft, double wft, double hft, String name, String type, int topLeftXInd, int topLeftYInd, int wInd, int hInd, int row) {
		this.topLeftXFt = tlxft;
		this.topLeftYFt = tlyft;
		this.widthFt = wft;
		this.heightFt = hft;
		this.name = name;
		cellType = findCellType(type);
		this.topLeftXInd = topLeftXInd;
		this.topLeftYInd = topLeftYInd;
		this.widthInd = wInd;
		this.heightInd = hInd;
		this.csvRow = row;
	}
	
	private char findCellType(String in) {
		char out = Cell.ERROR;
		if(in != null && !in.isEmpty()) {
			if(in.equals("BUILDING")) {
				out = Cell.BUILDING;
			} else if(in.equals("SIDEWALK")) {
				out = Cell.SIDEWALK;
			} else if(in.equals("STREET")) {
				out = Cell.STREET;
			} else if(in.equals("CROSSWALK")) {
				out = Cell.CROSSWALK;
			}
		}
		return out;
	}

	/**
	 * @return the topLeftXInd
	 */
	public int getTopLeftXInd() {
		return topLeftXInd;
	}

	/**
	 * @param topLeftXInd the topLeftXInd to set
	 */
	public void setTopLeftXInd(int topLeftXInd) {
		this.topLeftXInd = topLeftXInd;
	}

	/**
	 * @return the topLeftYInd
	 */
	public int getTopLeftYInd() {
		return topLeftYInd;
	}

	/**
	 * @param topLeftYInd the topLeftYInd to set
	 */
	public void setTopLeftYInd(int topLeftYInd) {
		this.topLeftYInd = topLeftYInd;
	}

	/**
	 * @return the widthInd
	 */
	public int getWidthInd() {
		return widthInd;
	}

	/**
	 * @param widthInd the widthInd to set
	 */
	public void setWidthInd(int widthInd) {
		this.widthInd = widthInd;
	}

	/**
	 * @return the heightInd
	 */
	public int getHeightInd() {
		return heightInd;
	}

	/**
	 * @param heightInd the heightInd to set
	 */
	public void setHeightInd(int heightInd) {
		this.heightInd = heightInd;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the cellType
	 */
	public char getCellType() {
		return cellType;
	}

	/**
	 * @param cellType the cellType to set
	 */
	public void setCellType(char cellType) {
		this.cellType = cellType;
	}

	/**
	 * @return the topLeftXFt
	 */
	public double getTopLeftXFt() {
		return topLeftXFt;
	}

	/**
	 * @param topLeftXFt the topLeftXFt to set
	 */
	public void setTopLeftXFt(double topLeftXFt) {
		this.topLeftXFt = topLeftXFt;
	}

	/**
	 * @return the topLeftYFt
	 */
	public double getTopLeftYFt() {
		return topLeftYFt;
	}

	/**
	 * @param topLeftYFt the topLeftYFt to set
	 */
	public void setTopLeftYFt(double topLeftYFt) {
		this.topLeftYFt = topLeftYFt;
	}

	/**
	 * @return the widthFt
	 */
	public double getWidthFt() {
		return widthFt;
	}

	/**
	 * @param widthFt the widthFt to set
	 */
	public void setWidthFt(double widthFt) {
		this.widthFt = widthFt;
	}

	/**
	 * @return the heightFt
	 */
	public double getHeightFt() {
		return heightFt;
	}

	/**
	 * @param heightFt the heightFt to set
	 */
	public void setHeightFt(double heightFt) {
		this.heightFt = heightFt;
	}
	
	public String toString() {
		return name + " x: " + topLeftXInd + " y: " + topLeftYInd + " widthInd: " + widthInd + " heightInd: " + heightInd;
	}

	/**
	 * @return the csvRow
	 */
	public int getCsvRow() {
		return csvRow;
	}

	/**
	 * @param csvRow the csvRow to set
	 */
	public void setCsvRow(int csvRow) {
		this.csvRow = csvRow;
	}

}
