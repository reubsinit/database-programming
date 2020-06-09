/**
 * Class: FileHandler
 * Author: BitNak - Reuben Wilson
 * Date Authored: 04.08.14
 * Last Modified: 10.08.14
 * Description: The FileHandler class is used to parse the required information from a specified file.
 */

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;

import java.io.InputStreamReader;

public class FileHandler {
	/**
	 * Two fields declared:
	 * _file as a String - used to store the name of the file being parsed
	 * _fileReader as a StreamReader - used to read the information within the file
	 */
	private String _file;
	private BufferedReader _fileReader;

	/**
	 * Constructor for class, FileHandler
	 * Takes one argument:
	 * 1. String
	 */
	public FileHandler(String fileToHandle) throws Exception {
		File temp = new File(fileToHandle);
		if (temp.exists()) {
			_file = fileToHandle;
		} else {
			throw new Exception(fileToHandle + " is not a valid file.");
		}
	}

	/**
	 * Method GetAlgorithmName - returns a String:
	 * Returns the name of the algorithm represented by the file being parsed
	 */
	public String GetAlgorithmName() throws Exception {
		_fileReader = new BufferedReader(new InputStreamReader(new FileInputStream(_file)));
		String result;
		while ((result = _fileReader.readLine()) != null) {
			if (result.startsWith("algo_name=")) {
				_fileReader.close();
				return result.replace("algo_name=", "");
			}
		}
		throw new Exception("Algorithm is nameless.");
	}

	/**
	 * Method GetDataSet - returns a StringBuilder:
	 * Returns a StringBuilder comprised of all the vector data that the algorithm file represents
	 */
	public StringBuilder GetDataSet() throws Exception {
		_fileReader = new BufferedReader(new InputStreamReader(new FileInputStream(_file)));
		String data;
		StringBuilder result = new StringBuilder();
		while ((data = _fileReader.readLine()) != null) {
			if (data.toLowerCase().startsWith("complete population")) {
				while ((data = _fileReader.readLine()) != "" && data != null) {
					result.append(data);
					result.append(System.getProperty("line.separator"));
				}
				_fileReader.close();
				return result;
			}
		}
		throw new Exception("There is no data.");
	}

}
