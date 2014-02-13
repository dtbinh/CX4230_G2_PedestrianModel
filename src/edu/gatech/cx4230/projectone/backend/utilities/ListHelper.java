package edu.gatech.cx4230.projectone.backend.utilities;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import edu.gatech.cx4230.projectone.backend.abstraction.Cell;

public class ListHelper {
	
	public static void writeListToCSV(List<Cell> list, String filename) {
		try {
			if(!filename.endsWith(".csv")) {
				filename = filename + ".csv";
			}
			if(list != null && !list.isEmpty()) {
				FileWriter fw = new FileWriter(filename);
				String header = "X,Y,ID,Cell Type, Name\n";
				fw.write(header);
				for(int i = 0; i < list.size(); i++) {
					String line = "";

					line += list.get(i).csvInfo();
					line += "\n";
					fw.write(line);
				} // close for i

				fw.close();
			} // close if null
		} catch (IOException e) {
			System.err.println("There was a problem writing Array to CSV file");
			e.printStackTrace();
		} // close catch
	} // close method
	

	
	public static String listToString(List<?> list) {
		String out = "";
		if(list != null && !list.isEmpty()) {
			for(int i = 0; i < list.size(); i++) {
				out += i + "\t" + list.get(i).toString() + "\n";
			}
		}
		
		return out;
	}
	
	public static int[] convertSetToArray(Set<Integer> set) {
		int[] out = new int[set.size()];
		List<Integer> l = new ArrayList<Integer>(set);
		for(int i = 0; i < set.size(); i++) {
			out[i] = l.get(i);
		}
		return out;
	}

}
