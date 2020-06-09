//<summary>
//Class: FileHandler
//Author: BitNak - Reuben Wilson
//Date Authored: 04.08.14
//Last Modified: 10.08.14
//Description: The FileHandler class is used to parse the required information from a specified file.
//</summary>

using System;
using System.IO;
using System.Text;

	public class FileHandler
	{
//				<remarks>
//				Two fields declared:
//				_file as a String - used to store the name of the file being parsed
//				_fileReader as a StreamReader - used to read the information within the file
//				</remarks>
		private String _file;
		private StreamReader _fileReader;

//				<remarks>
//				Constructor for class, FileHandler
//				Takes one argument:
//				1. String
//				</remarks>
		public FileHandler (String fileToHandle)
		{
			if (File.Exists (fileToHandle)) {
				_file = fileToHandle;
			} else {
				throw new System.Exception(fileToHandle + " is not a valid file.");
			}
		}

//				<remarks>
//				Method GetAlgorithmName - returns a String:
//				Returns the name of the algorithm represented by the file being parsed
//				</remarks>
		public String GetAlgorithmName(){
			using (_fileReader = new StreamReader (_file)) {
				string result;
				while ((result = _fileReader.ReadLine ()) != null) {
					if (result.StartsWith ("algo_name=", StringComparison.OrdinalIgnoreCase)) {
						_fileReader.Close ();
						return result.Replace ("algo_name=", "");
					}
				}
				throw new System.Exception("Algorithm is nameless.");
			}
		}

//				<remarks>
//				Method GetDataSet - returns a StringBuilder:
//				Returns a StringBuilder comprised of all the vector data that the algorithm file represents
//				</remarks>
		public StringBuilder GetDataSet(){
			using (_fileReader = new StreamReader (_file)) {
				string data;
				StringBuilder result = new StringBuilder ();
				while ((data = _fileReader.ReadLine ()) != null) {
					if (data.StartsWith ("complete population", StringComparison.OrdinalIgnoreCase)) {
						while ((data = _fileReader.ReadLine ()) != string.Empty && data != null) {
							result.AppendLine (data);
						}
						_fileReader.Close ();
						return result;
					}
				}
				throw new System.Exception ("There is no data.");
			}
		}
}