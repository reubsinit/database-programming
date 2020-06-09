//<summary>
//Class: Program
//Author: BitNak - Reuben Wilson
//Date Authored: 04.08.14
//Last Modified: 10.08.14
//Description: The Program class is the main class, instantiating the required objects to undertake the task
//Specified in the hurdle criteria
//</summary>

using System;
using System.IO;
using System.Collections.Generic;

	class MainClass
	{
//				<remarks>
//				Main method
//				</remarks>
		public static void Main (string[] args)
		{
//					<remarks>
//					Covert the single command line argument I.e. result_5_a.txt,result_5_b.txt,result_5_c.xt into a string arrary
//					Containing the file names I.e. result_5_a.txt result_5_b.txt result_5_c.txt
//					If there are no arguments, throw and exception
//					</remarks>
		try{
			string[] fileArguments;
			if (args != null && args.Length > 0) {
				fileArguments = args [0].Split (new char[] { ',' }, StringSplitOptions.RemoveEmptyEntries);
			} else {

				Console.Error.WriteLine ("No arguments were supplied. You need to supply them in format: result_5_a.txt,result_5_b.txt,result_5_c.txt etc");
				return;
			}
	//					<remarks>
	//					Check to see if at least two arguments have been supplied and no more than ten, if not, throw an exception
	//			 		For each argument, parse required data from files using a FileHandler
	//					Return the data from FileHandler and use that data to instantiate a DataSet
	//					Add that DataSet to a temporary List of DataSet
	//			 		After each DataSet has been instantiated (one for each file argument)
	//					Use the List of DataSet to instantiate an instance of DataCalculator
	//					For each DataSet in DataCalculator, print the algorithm name and the top three results from each algorithm
	//					</remarks>
			if (fileArguments != null && fileArguments.Length > 1 && fileArguments.Length <= 10) {
				List<DataSet> tempSet = new List<DataSet>();
				for (int i = 0; i < fileArguments.Length; i++) {
					FileHandler tempFileHandler = new FileHandler (fileArguments [i]);
					DataSet tempDataSet = new DataSet (tempFileHandler.GetAlgorithmName(), tempFileHandler.GetDataSet() );
					tempSet.Add (tempDataSet);
				}
				DataCalculator data = new DataCalculator (tempSet);
				Console.WriteLine ("Number of results compared: " + data.GetCountOfTotalValuesCompared());
				for (int i = 0; i < data.Sets.Count; i++) {
					Console.WriteLine (data.Sets [i].AlgorithmName);
					foreach (KeyValuePair<string, double> entry in data.Sets[i].ReturnTopThreeComparisonResults())
					{
						Console.WriteLine (entry.Key + " " + Math.Round(entry.Value, 2));
					}
				}
			} 
			else {
				Console.Error.WriteLine ("You have not specified the correct number of arguments. Please specify at least two!");
				return;
			}
		}
		catch(Exception e) {
			Console.Error.WriteLine (e);
		}
	}

}
