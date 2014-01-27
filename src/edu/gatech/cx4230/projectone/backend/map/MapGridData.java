package edu.gatech.cx4230.projectone.backend.map;

import java.io.File;
import java.util.ArrayList;

import edu.gatech.cx4230.projectone.backend.abstraction.Cell;
import edu.gatech.cx4230.projectone.backend.utilities.CSVReader;
import edu.gatech.cx4230.projectone.backend.utilities.CSVRow;

/**
 * Class holds information for the map grid (buildings, sidewalks, cross walks, streets, etc.)
 * @author tbowling3
 *
 */
public class MapGridData {
	private static String filename = "Model_Roster.csv";
	private int width, height;
	private Cell[][] cells;

	public MapGridData() {
		File file = new File("res/" + filename);
		String absolutePath = file.getAbsolutePath();
		
		CSVReader cReader = new CSVReader(absolutePath);
		ArrayList<CSVRow> mapObjects = cReader.getRows();
		
		// Get overall dimensions
		CSVRow overall = mapObjects.get(0);
		width = overall.getWidthInd();
		height = overall.getHeightInd();
		
		create2DArray(width,height);
		
		int cellModCount = 0;
		// Go through each of the mapObjects and modify cells in cells
		for(int k = 1; k < mapObjects.size(); k++) {
			CSVRow mapObject = mapObjects.get(k);
			int x = mapObject.getTopLeftXInd();
			int y = mapObject.getTopLeftYInd();
			int w = mapObject.getWidthInd();
			int h = mapObject.getHeightInd();

			for(int j = y; j < (y+h - 1); j++) {
				for(int i = x; i < (x+w - 1); i++) {
					cells[j][i].setProperties(i, j, mapObject.getName(), mapObject.getCellType(), mapObject.getCsvRow());
					cellModCount++;
				}
			}

			
		}
		System.out.println("Cells updated: " + cellModCount);
	}
	
	private void create2DArray(int width, int height) {
		cells = new Cell[height][];
		
		for(int j = 0; j < height; j++) {
			cells[j] = new Cell[width];
			
			for(int i = 0; i < width; i++) {
				cells[j][i] = new Cell();
			}
		}
	}
	
	public Cell[][] getCells() {
		return cells;
	}
	
	public static void main(String[] args) {
		MapGridData mgd = new MapGridData();
		System.out.println("Rows: " + mgd.getCells().length);
	}

}
