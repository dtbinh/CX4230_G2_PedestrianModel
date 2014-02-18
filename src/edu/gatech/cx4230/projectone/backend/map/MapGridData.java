package edu.gatech.cx4230.projectone.backend.map;

import java.awt.Point;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import de.fhpotsdam.unfolding.data.Feature;
import de.fhpotsdam.unfolding.data.MarkerFactory;
import de.fhpotsdam.unfolding.data.ShapeFeature;
import de.fhpotsdam.unfolding.geo.Location;
import de.fhpotsdam.unfolding.marker.Marker;
import edu.gatech.cx4230.projectone.backend.abstraction.Cell;
import edu.gatech.cx4230.projectone.backend.abstraction.CellManager;
import edu.gatech.cx4230.projectone.backend.utilities.CSVReader;
import edu.gatech.cx4230.projectone.backend.utilities.CSVRow;
import edu.gatech.cx4230.projectone.backend.utilities.ListHelper;
import edu.gatech.cx4230.projectone.visualization.abstraction.CellMarker;
import edu.gatech.cx4230.projectone.visualization.map.VisualizationMain;

/**
 * Class holds information for the map grid (buildings, sidewalks, cross walks, streets, etc.)
 * @author tbowling3
 *
 */
public class MapGridData {
	private static String filename = "../res/Model_Roster.csv";
	public static final String NAME = "name";
	public static final String TYPE = "type";
	private int width, height;
	private CellManager cm;
	private List<Marker> cellMarkers;

	public MapGridData() {
		File file = new File(filename);
		String absolutePath = file.getAbsolutePath();

		CSVReader cReader = new CSVReader(absolutePath);
		ArrayList<CSVRow> mapObjects = cReader.getRows();

		// Get overall dimensions
		if(mapObjects.size() > 0) {
			CSVRow overall = mapObjects.get(0);
			width = overall.getWidthInd();
			height = overall.getHeightInd();

			Cell[][] cells = create2DArray(width, height);
			cm = new CellManager();

			int cellModCount = 0;
			List<Feature> cellsMap = new ArrayList<Feature>();

			// Go through each of the mapObjects and modify cells in cells
			for(int k = 1; k < mapObjects.size(); k++) {
				CSVRow mapObject = mapObjects.get(k);
				int x = mapObject.getTopLeftXInd();
				int y = mapObject.getTopLeftYInd();
				int w = mapObject.getWidthInd();
				int h = mapObject.getHeightInd();
				String name = mapObject.getName();
				char cellType = mapObject.getCellType();
				int csvLine = mapObject.getCsvRow();

				// Updates the relevant cells in the 2D Array simulation model
				for(int j = y; j < (y+h); j++) {
					for(int i = x; i < (x+w); i++) {
						cells[j][i].setProperties(i, j, name, cellType, csvLine);
						cells[j][i].setCm(cm);
						cellModCount++;

					}
				}


				// Adds Feature to list for map
				double lon = VisualizationMain.TOP_LEFT_LON + x * VisualizationMain.CELL_FACTOR_X;
				double lat = VisualizationMain.TOP_LEFT_LAT - y * VisualizationMain.CELL_FACTOR_Y;
				double width = w * VisualizationMain.CELL_FACTOR_X;
				double height = h * VisualizationMain.CELL_FACTOR_Y;

				Location tl = new Location(lat, lon);
				Location tr = new Location(lat, lon + width);
				Location br = new Location(lat - height, lon + width);
				Location bl = new Location(lat - height, lon);

				ShapeFeature sf = new ShapeFeature(Feature.FeatureType.POLYGON);
				sf.addProperty(NAME, name);
				sf.addProperty(TYPE, cellType);
				sf.addLocation(tl);
				sf.addLocation(tr);
				sf.addLocation(br);
				sf.addLocation(bl);

				cellsMap.add(sf);

			} // close for
			System.out.println("Cells updated: " + cellModCount);

			MarkerFactory mf = new MarkerFactory();
			mf.setPolygonClass(CellMarker.class);

			cellMarkers = new ArrayList<Marker>();
			cellMarkers = mf.createMarkers(cellsMap);

			cm.setCells(cells);
		} else {
			System.err.println(file.getAbsolutePath() + " not found!");
		}
	} // close constructor

	private Cell[][] create2DArray(int width, int height) {
		Cell[][] cells = new Cell[height][];

		for(int j = 0; j < height; j++) {
			cells[j] = new Cell[width];

			for(int i = 0; i < width; i++) {
				cells[j][i] = new Cell(i,j);
			}
		}

		return cells;
	}

	/**
	 * @return the cellMarkers
	 */
	public List<Marker> getCellMarkers() {
		return cellMarkers;
	}

	/**
	 * @param cellMarkers the cellMarkers to set
	 */
	public void setCellMarkers(List<Marker> cellMarkers) {
		this.cellMarkers = cellMarkers;
	}

	public static void main(String[] args) {
		MapGridData mgd = new MapGridData();
		CellManager cm = mgd.getCellManager();
		List<Point> nullCells = cm.getNullLocations();
		System.out.println("Null cell Locations:");
		System.out.println(ListHelper.listToString(nullCells));
		System.out.println("Rows: " + mgd.getCellManager().getCells().length);
	}

	public CellManager getCellManager() {
		return cm;
	}

}
