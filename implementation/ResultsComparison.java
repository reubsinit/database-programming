/**
 * Class: Program
 * Author: BitNak - Reuben Wilson
 * Date Authored: 04.08.14
 * Last Modified: 10.08.14
 * Description: The Program class is the main class, instantiating the required objects to undertake the task
 * Specified in the hurdle criteria
 */

import java.util.ArrayList;
import java.util.Map;

public class ResultsComparison {

	/**
	 * Main method
	 */
	public static void main(String[] args) throws Exception {
		/**
		 * Covert the single command line argument I.e. result_5_a.txt,result_5_b.txt,result_5_c.xt into a string arrary
		 * Containing the file names I.e. result_5_a.txt result_5_b.txt result_5_c.txt
		 * If there are no arguments, throw and exception
		 */
		try{
		String[] fileArguments;
		if (args != null && args.length > 0) {
			fileArguments = args[0].split(",");
		}
		else{
			throw new Exception("No arguments were supplied. You need to supply them in format: result_5_a.txt,result_5_b.txt,result_5_c.txt etc");
		}
		/**
		 * Check to see if at least two arguments have been supplied and no more than ten, if not, throw an exception
		 * For each argument, parse required data from files using a FileHandler
		 * Return the data from FileHandler and use that data to instantiate a DataSet
		 * Add that DataSet to a temporary List of DataSet
		 * After each DataSet has been instantiated (one for each file argument)
		 * Use the List of DataSet to instantiate an instance of DataCalculator
		 * For each DataSet in DataCalculator, print the algorithm name and the top three results from each algorithm
		 */
		if (fileArguments != null && fileArguments.length > 1 && fileArguments.length <= 10) {
			ArrayList<DataSet> tempSet = new ArrayList<DataSet>();
			for (int i = 0; i < fileArguments.length; i++) {
				FileHandler tempFileHandler = new FileHandler(fileArguments[i]);
				DataSet tempDataSet = new DataSet(tempFileHandler.GetAlgorithmName(), tempFileHandler.GetDataSet());
				tempSet.add(tempDataSet);
			}
			DataCalculator data = new DataCalculator(tempSet);
			System.out.println("Total number of results compared: " + data.getCountOfTotalValuesCompared());
			for (int i = 0; i < data.sets().size(); i++) {
				System.out.println(data.sets().get(i).algorithmName());
				ArrayList<Map.Entry<Double, Integer>> results = new ArrayList<Map.Entry<Double, Integer>>(data.sets().get(i).returnTopThreeComparisonResults().entrySet());
				for (int j = results.size(); j > 0; j--) {
					System.out.println("[" + results.get(j - 1).getValue() + "]" + " " + (double)Math.round((results.get(j - 1).getKey()) * 100.0) / 100.0);
				}
			}
		} else {
			throw new Exception("You have not specified the correct number of arguments. Please specify at least two!");
		}
		}catch(Exception e){
			System.err.println(e.getMessage());
		}
	}

}
