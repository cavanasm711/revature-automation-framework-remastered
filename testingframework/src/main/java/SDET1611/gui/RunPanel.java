package SDET1611.gui;

import SDET1611.testingframework.TestThread;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JPanel;

import SDET1611.testingframework.MultithreadTests;
import SDET1611.testingframework.PropObj;
import SDET1611.testingframework.TestThread;

import javax.swing.JOptionPane;

public class RunPanel extends JPanel implements ActionListener {

	static private File keywordExcelFile;
	static private File dataExcelFile;

	static private String keywordPath;
	static private String dataDrivenPath;

	static private boolean chromeCheckboxValue;
	static private boolean ieCheckboxValue;
	static private boolean firefoxCheckboxValue;
	static private boolean edgeCheckboxValue;
	private static boolean safariCheckboxValue;

	static private String keywordSheetText;
	static private String dataSheetText;

	private static String keyExist;
	private static String dataExist;

	public RunPanel() {

		JButton runTestButton = new JButton("Run Test");
		add(runTestButton);
		runTestButton.addActionListener(this);

	}

	// Keyword file and sheet methods
	public void setKeywordExcelFile(File file) {
		keywordPath = file.getName();
		keywordExcelFile = file;
	}

	public File getKeywordExcelFile() {
		return keywordExcelFile;
	}

	public String getKeywordPath() {
		// System.out.println(keywordPath);
		return keywordPath;
	}

	public void setKeywordSheetText(String kst) {
		keywordSheetText = kst;
	}

	public String getKeywordSheetText() {
		return keywordSheetText;
	}

	// Data file and sheet methods
	public void setDataExcelFile(File file) {
		dataExcelFile = file;
		dataDrivenPath = file.getName();
	}

	public String getDataDrivenPath() {
		return dataDrivenPath;
	}

	public File getDataExcelFile() {
		return dataExcelFile;
	}

	public void setDataSheetText(String dst) {
		dataSheetText = dst;
	}

	public String getDataSheetText() {
		return dataSheetText;
	}

	// Property file methods
	/*public void setPropertiesFile(File file) {
		propertiesFile = file;
		popertiesPath = file.getName();
	}

	public String getPropertiesFilePath() {
		return popertiesPath;
	}

	public File getPropertiesFile() {
		return propertiesFile;
	}*/

	// Check box methods
	public void setChromeCheckValue(boolean isChecked) {
		chromeCheckboxValue = isChecked;
	}

	public boolean getChromeCheckboxValue() {
		return chromeCheckboxValue;
	}

	public void setIECheckValue(boolean isChecked) {
		ieCheckboxValue = isChecked;
	}

	public boolean getIECheckboxValue() {
		return ieCheckboxValue;
	}

	public void setFirefoxCheckValue(boolean isChecked) {
		firefoxCheckboxValue = isChecked;
	}

	public boolean getFirefoxCheckboxValue() {
		return firefoxCheckboxValue;
	}

	public void setSafariCheckboxValue(boolean isChecked) {
		safariCheckboxValue = isChecked;
	}

	public boolean getSafariCheckValue() {
		return safariCheckboxValue;
	}

	public void setKeySheetExists(String exists) {
		keyExist = exists;
	}

	public String getKeySheetExists() {
		return keyExist;
	}

	public void setDataSheetExists(String exists) {
		dataExist = exists;
	}

	public String getDataSheetExists() {
		return dataExist;
	}

	public void setEdgeCheckValue(boolean isChecked) {
		edgeCheckboxValue = isChecked;
	}

	public boolean getEdgeCheckValue() {
		return edgeCheckboxValue;
	}

	// Run Test action
	public void actionPerformed(ActionEvent e) {
		String OS;
		keywordExcelFile = getKeywordExcelFile();
		dataExcelFile = getDataExcelFile();
		//propertiesFile = getPropertiesFile();

		String keywordSheet = getKeywordSheetText();
		String dataSheet = getDataSheetText();

		String temp = System.getProperty("os.name");

		if (temp.contains("Windows")) {
			OS = "WINDOWS";
		} else if (temp.contains("Mac")) {
			OS = "MAC";
		} else {
			OS = "LINUX";
		}

		String bit = "64";

		chromeCheckboxValue = getChromeCheckboxValue();
		ieCheckboxValue = getIECheckboxValue();
		firefoxCheckboxValue = getFirefoxCheckboxValue();
		edgeCheckboxValue = getEdgeCheckValue();
		safariCheckboxValue = getSafariCheckValue();

		List<String> drivers = new ArrayList<String>();

		if (chromeCheckboxValue)
			drivers.add("Chrome");

		if (ieCheckboxValue)
			drivers.add("IE");

		if (firefoxCheckboxValue)
			drivers.add("Firefox");

		if (edgeCheckboxValue)
			drivers.add("Edge");

		if (safariCheckboxValue)
			drivers.add("Safari");

		// System.out.println(chromeCheckboxValue);
		// System.out.println(ieCheckboxValue);
		// System.out.println(firefoxCheckboxValue);
		// System.out.println(edgeCheckboxValue);

		System.out.println(drivers);

		if (!drivers.isEmpty()) {
			PropObj testProperties = PropObj.tryToCreateInstance(dataExcelFile.toString().replace("\\", "/"),
					keywordExcelFile.toString().replace("\\", "/"),
					keywordSheet, dataSheet, OS, bit, drivers.toArray(new String[drivers.size()]));

			for (int i = 0; i < drivers.size(); i++) {
				TestThread T = new TestThread(drivers.get(i));
				T.setName(drivers.get(i));
				T.start();
			}
			System.gc();

		} else {
			JOptionPane.showMessageDialog(null, "No Browsers were selected. Please select a browser to continue.");
		}

	}
}