package edu.gatech.cx4230.projectone.backend.utilities;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Instead of printing to the console, printing to this object will write everything to a file at the end.
 * @author tbowling3
 *
 */
public class Logger {
	private static List<String> lines = new ArrayList<String>();
	public static boolean PRINT = true;
	private static String partialLine;
	
	public static void logLine(String line) {
		if(line != null) {
			if(PRINT) System.out.println(line);
			if(partialLine != null && !partialLine.isEmpty()) {
				line += partialLine;
				partialLine = null;
			}
			lines.add(line + "\n");
			
		}
	}
	
	public static void log(String partial) {
		if(partial != null) {
			partialLine += partial;
			System.out.print(partial);
		}
	}
	
	public static void clear() {
		lines.clear();
	}
	
	public static void save(String filename, boolean clear) {
		String workingDirectory = System.getProperty("user.dir");
		int index = workingDirectory.indexOf("CX4230_G2_PedestrianModel");
		String localPath = workingDirectory.substring(0,index);
		String out = localPath + "CX4230_G2_PedestrianModel/Output/"  + filename;
		
		if(!out.endsWith(".txt")) {
			out = out + ".txt";
		}
		try {
		FileWriter fw = new FileWriter(out);
		for(String line: lines) {
			
			fw.write(line);
		} // close for i
		
		fw.close();
		} catch(IOException e) {
			System.err.println("There was a problem writing Log to txt file");
			e.printStackTrace();
		}
		System.out.println("Log saved");
		if(clear) clear();
	}

}
