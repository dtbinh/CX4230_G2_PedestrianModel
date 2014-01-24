package edu.gatech.cx4230.projectone.backend.utilities;

public class CSVRow {
	private double topLeftXFt, topLeftYFt, widthFt, heightFt;
	private int topLeftXInd, topLeftYInd, widthInd, heightInd;
	private String name;
	private char cellType;
	public static final char BUILDING = 'B';
	public static final char SIDEWALK = 'S';
	public static final char STREET = 'T';
	public static final char CROSSWALK = 'C';
	public static final char ERROR = 'E';
	
	public CSVRow(double tlxft, double tlyft, double wft, double hft, String name, String type, int topLeftXInd, int topLeftYInd, int wInd, int hInd) {
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
	}
	
	private char findCellType(String in) {
		char out = ERROR;
		if(in != null && !in.isEmpty()) {
			if(in.equals("BUILDING")) {
				out = BUILDING;
			} else if(in.equals("SIDEWALK")) {
				out = SIDEWALK;
			} else if(in.equals("STREET")) {
				out = STREET;
			} else if(in.equals("CROSSWALK")) {
				out = CROSSWALK;
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

}
