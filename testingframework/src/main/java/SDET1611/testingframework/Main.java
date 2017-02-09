package SDET1611.testingframework;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.ServiceConfigurationError;

import SDET1611.gui.GUI;

/**
 * Allows the testing framework to be run headless using the commands:
 * mvn package
 * mvn exec:java -Dexec.args="arg0 arg1 ..."
 * Warning: using backslashes in the file path will throw FileNotFound exception unless you escape them first
 */

public class Main {
	// args are excelFilePath, keyword
	public static void main(String args[]) throws Exception {
		// 2 is the number of arguments necessary to have drivers
		if (args.length == 0){
			GUI gui = new GUI();
		}
		else if (args.length == 1){
			throw new NullPointerException("Error: Invalid number of arguments");
		}
		else {
			String path = args[0];
			File f = new File(path);
			List<String> drivers = new ArrayList<String>(); 
			for(int i = 1; i < args.length; i++){
				drivers.add(args[i]);
			}
			System.out.println("drivers used are " + drivers);
			
			if (!verifyDrivers(drivers)){
				throw new Exception("Error: Driver not recognized");
			}
			else if(!f.exists()){
				throw new FileNotFoundException("Error: File not found");
			}
			else if(!path.substring(path.indexOf(".")).equals(".xlsx") && !path.substring(path.indexOf(".")).equals(".xls")){
				throw new FileNotFoundException("Error: Invalid file format");
			}
			else {
				String OS;
				String bit;
				if (System.getProperty("os.name").contains("Windows")) {
					OS = "WINDOWS";
				} else if (System.getProperty("os.name").contains("Mac")) {
					OS = "MAC";
				} else {
					OS = "LINUX";
				}
				
				if (System.getProperty("os.arch").contains("64")){
					bit = "64";
				}
				else if (System.getProperty("os.arch").contains("86")){
					bit = "32";
				}
				else {
					bit = "Unsupported Processor";
					throw new ServiceConfigurationError("Error: Unsupported Processor");
				}

				PropObj.tryToCreateInstance(path, path, "Keywords", "Data", OS, bit, drivers.toArray(new String[drivers.size()]));
	
				for (int i = 0; i < drivers.size(); i++) {
					TestThread T = new TestThread(drivers.get(i));
					T.setName(drivers.get(i));
					T.start();
				}
			}
			System.gc();

		} 
	}
	
	private static boolean verifyDrivers(List<String> drivers){
		for(String driver : drivers){
			System.out.println("validating " + driver);
			switch(driver.toLowerCase()){
			case "chrome":
			case "firefox":
			case "ie":
			case "internetexplorer":
			case "internet_explorer":
			case "internet explorer":
			case "edge":
			case "safari":
				break;
			default:
				return false;
			}
		}
		return true;
	}
	
}
