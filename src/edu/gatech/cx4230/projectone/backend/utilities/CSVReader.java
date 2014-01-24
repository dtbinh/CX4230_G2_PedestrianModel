package edu.gatech.cx4230.projectone.backend.utilities;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Reads in a CSV file
 * @author tbowling3
 *
 */
public class CSVReader {
	private ArrayList<CSVRow> rows;
	
	public CSVReader(String filename) {
		rows = new ArrayList<CSVRow>();
		FileHelper fh = new FileHelper();
		if(fh.fileExists(filename)) {
			BufferedReader br = null;
			String line = "";
			int lineCount = 0;
			String csvSplitBy = ",";
			
			try {
				 
				br = new BufferedReader(new FileReader(filename));
				while ((line = br.readLine()) != null) {
		 
				    // use comma as separator
					String[] lineSplit = line.split(csvSplitBy);
		 
					// Get values from lineSplit array
					if(lineCount != 0) { // Ignore the Header row
						if(lineSplit.length == 10) {
							String stlxFt = lineSplit[0];
							String stlyFt = lineSplit[1];
							String swidFt = lineSplit[2];
							String sHgtFt = lineSplit[3];
							String name   = lineSplit[4];
							String type   = lineSplit[5];
							String stlxIn = lineSplit[6];
							String stlyIn = lineSplit[7];
							String swidIn = lineSplit[8];
							String sHgtIn = lineSplit[9];
							
							// Convert data types
							double dtlxFt = Double.parseDouble(stlxFt);
							double dtlyFt = Double.parseDouble(stlyFt);
							double dWidFt = Double.parseDouble(swidFt);
							double dHgtFt = Double.parseDouble(sHgtFt);
							int itlxIn = Integer.parseInt(stlxIn);
							int itlyIn = Integer.parseInt(stlyIn);
							int iwidIn = Integer.parseInt(swidIn);
							int iHgtIn = Integer.parseInt(sHgtIn);
							
							CSVRow r = new CSVRow(dtlxFt, dtlyFt, dWidFt, dHgtFt, name, type, itlxIn, itlyIn, iwidIn, iHgtIn);
							rows.add(r);
							
						}
					}
					lineCount++;
		 
				}
		 
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				if (br != null) {
					try {
						br.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}
	}
	
	public ArrayList<CSVRow> getRows() {
		return rows;
	}
	

}
