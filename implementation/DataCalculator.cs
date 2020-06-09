//<summary>
//Class: DataCalculator
//Author: BitNak - Reuben Wilson
//Date Authored: 04.08.14
//Last Modified: 10.08.14
//Description: The DataCalculator class is used to perform the required calculations on all the data imported at runtime. It uses
//a list of DataSet to do such; there is one DataSet object for each file.
//</summary>

using System;
using System.Text;
using System.IO;
using System.Linq;
using System.Collections.Generic;

	public class DataCalculator
	{
//				<remarks>
//				One field declared:
//				_sets as a List of DataSet - used to store the required DataSet objects involved in data calculation
//				</remarks>
		private List<DataSet> _sets;

//				<remarks>
//				Constructor for class, DataCalculator
//				Takes one argument:
//				1. List of DataSet
//				</remarks>
		public DataCalculator (List<DataSet> setsToProcess)
		{
			_sets = setsToProcess;
			for (int i = 0; i < _sets.Count; i++) {
				for (int j = 0; j < _sets [i].Vectors.Count (); j++) {
					this.SetStandardisedValuesForSetAndRow(i, j);
					this.SetComparitiveValueForSetAndRow(i, j);
				}
			}
		}

//				<remarks>
//				Getter Sets for field _sets
//				</remarks>
		public List<DataSet> Sets {
			get { return _sets; }
		}
//
//				<remarks>
//				Method SetStandardisedValuesForSetAndRow:
//				Takes two arguments:
//				1. int
//				2. int
//				The first parameter specifies the DataSet, the second specified the row (Vector) within that DataSet
//				The standardized values for that row (Vector) within the set are then calculated and assigned appropriately
//				</remarks>
		private void SetStandardisedValuesForSetAndRow(int setNumber, int rowNumber){
			if (setNumber >= _sets.Count ()) {
				throw new System.Exception ("There is no such set...");
			}
			for (int i = 0; i < _sets [setNumber].GetDimensionCount (); i++) {

				_sets [setNumber].Vectors [rowNumber].StandardisedValues.Add((_sets [setNumber].Vectors [rowNumber].VectorValues [i] - this.GetAverageForDimension(i)) / this.GetStandardDeviationForDimension(i, this.GetAverageForDimension(i)));
			}
		}

//				<remarks>
//				Method SetComparitiveValueForSetAndRow:
//				Takes two arguments:
//				1. int
//				2. int
//				The first parameter specifies the DataSet, the second specified the row (Vector) within that DataSet
//				The comparison value for that row (Vector) within the set is then calculated and assigned appropriately
//				</remarks>
		private void SetComparitiveValueForSetAndRow(int setNumber, int rowNumber){
			if (setNumber >= _sets.Count ()) {
				throw new System.Exception ("There is no such set...");
			}
			for (int i = 0; i < _sets [setNumber].GetDimensionCount (); i++) {

				_sets [setNumber].Vectors [rowNumber].ComparisonValue += (_sets [setNumber].Vectors [rowNumber].VectorValues [i] - this.GetAverageForDimension(i)) / this.GetStandardDeviationForDimension(i, this.GetAverageForDimension(i));
			}
		}

//				<remarks>
//				Method GetCountOfResultsCompared - returns and integer:
//				Returns an integer representing the total number of vectors across all DataSet objects in _sets
//				</remarks>
		public int GetCountOfVectors(){
			int result = 0;
			foreach (DataSet dataSet in _sets) {
				result += dataSet.Vectors.Count ();
			}
			return result;
		}

//				<remarks>
//				Method GetCountOfTotalValuesCompared - returns and integer:
//				Returns an integer representing the total number of values compared
//				</remarks>
		public int GetCountOfTotalValuesCompared(){
			return this.GetCountOfVectors() * _sets[0].GetDimensionCount();
		}

//				<remarks>
//				Method GetAverageForDimension - returns a double:
//				Takes one argument:
//				1. int
//				The parameter passed specifies the dimension for which the average should be calculated
//				The average is then calculated and returned
//				</remarks>
		public double GetAverageForDimension(int dimension){
			if (dimension > _sets [0].GetDimensionCount ()) {
				throw new System.Exception ("Dimension is out of bounds...");
			}
			double result = 0;
			foreach (DataSet dataSet in _sets) {
				foreach (Vector v in dataSet.Vectors) {
					result += v.VectorValues.ElementAt (dimension);
				}
			}
			result = result / this.GetCountOfVectors();
			return result;
		}

//				Method GetStandardDeviationForDimension - returns a double:
//				Takes two arguments:
//				1. int
//				2. double
//				The first parameter specifies the dimension for which the standard deviation is being calculated
//				The second parameter is the average of that dimension specified (first argument)
//				The standard deviation is then calculated and returned
		public double GetStandardDeviationForDimension(int dimension, double averageOfDimension){
			if (dimension > _sets [0].GetDimensionCount ()) {
				throw new System.Exception ("Dimension is out of bounds...");
			}
			double result = 0;
			foreach (DataSet dataSet in _sets) {
				foreach (Vector v in dataSet.Vectors) {
					result +=  Math.Pow((v.VectorValues.ElementAt (dimension) - averageOfDimension), 2);
				}
			}
			result /= this.GetCountOfVectors();
			result = Math.Sqrt (result);
			return result;
		}
}