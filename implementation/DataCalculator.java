/**
 * Class: DataCalculator
 * Author: BitNak - Reuben Wilson
 * Date Authored: 04.08.14
 * Last Modified: 10.08.14
 * Description: The DataCalculator class is used to perform the required calculations on all the data imported at runtime. It uses
 * a list of DataSet to do such; there is one DataSet object for each file.
 */

import java.util.ArrayList;
import java.util.List;

public class DataCalculator {
	/**
	 * One field declared:
	 * _sets as a List of DataSet - used to store the required DataSet objects involved in data calculation
	 */
	private ArrayList<DataSet> _sets;
	
	/**
	 * Constructor for class, DataCalculator
	 * Takes one argument:
	 * 1. List of DataSet
	 * Calls DataSet method createMap which uses Collections to sort comparison results (comparator)
	 */
	public DataCalculator(ArrayList<DataSet> setsToProcess) throws Exception {
		_sets = setsToProcess;
		for (int i = 0; i < _sets.size(); i++) {
			DataSet set = _sets.get(i);
			for (int j = 0; j < set.vectors().size(); j++) {
				this.setStandardisedValuesForSetAndRow(i, j);
				this.setComparitiveValueForSetAndRow(i, j);
			}
			set.createResultMap();
		}
	}

	/**
	 * Getter Sets for field _sets
	 */
	public List<DataSet> sets() {
		return _sets;
	}

	/**
	 * Method setStandardisedValuesForSetAndRow:
	 * Takes two arguments:
	 * 1. int
	 * 2. int
	 * The first parameter specifies the DataSet, the second specified the row (Vector) within that DataSet
	 * The standardized values for that row (Vector) within the set are then calculated and assigned appropriately
	 */
	private void setStandardisedValuesForSetAndRow(int setNumber, int rowNumber) throws Exception {
		if (setNumber >= _sets.size()) {
			throw new Exception("There is no such set...");
		}
		for (int i = 0; i < _sets.get(setNumber).getDimensionCount(); i++) {

			_sets.get(setNumber).vectors().get(rowNumber).standardisedValues().add((_sets.get(setNumber).vectors().get(rowNumber).vectorValues().get(i) - this.getAverageForDimension(i)) / this.getStandardDeviationForDimension(i, this.getAverageForDimension(i)));
		}
	}

	/**
	 * Method setComparitiveValueForSetAndRow:
	 * Takes two arguments:
	 * 1. int
	 * 2. int
	 * The first parameter specifies the DataSet, the second specified the row (Vector) within that DataSet
	 * The comparison value for that row (Vector) within the set is then calculated and assigned appropriately
	 */
	private void setComparitiveValueForSetAndRow(int setNumber, int rowNumber) throws Exception {
		if (setNumber >= _sets.size()) {
			throw new Exception("There is no such set...");
		}
		for (int i = 0; i < _sets.get(setNumber).getDimensionCount(); i++) {

			_sets.get(setNumber).vectors().get(rowNumber).incrementComparisonValue((_sets.get(setNumber).vectors().get(rowNumber).vectorValues().get(i) - this.getAverageForDimension(i)) / this.getStandardDeviationForDimension(i, this.getAverageForDimension(i)));
		}
	}
	
	/**
	 * Method getCountOfVectors - returns and integer:
	 * Returns an integer representing the total number of vectors across all DataSet objects in _sets
	 */
	public int getCountOfVectors() {
		int result = 0;
		for (DataSet dataSet : _sets) {
			result += dataSet.vectors().size();
		}
		return result;
	}
	
	/**
	 * Method getCountOfVectors - returns and integer:
	 * Returns an integer representing the total number of values compared
	 */
	public int getCountOfTotalValuesCompared() {
		return this.getCountOfVectors() * _sets.get(0).getDimensionCount();
	}

	/**
	 * Method getAverageForDimension - returns a double:
	 * Takes one argument:
	 * 1. int
	 * The parameter passed specifies the dimension for which the average should be calculated
	 * The average is then calculated and returned
	 */
	public Double getAverageForDimension(int dimension) throws Exception {
		if (dimension > _sets.get(0).getDimensionCount()) {
			throw new Exception("Dimension is out of bounds...");
		}
		Double result = 0.0;
		for (DataSet dataSet : _sets) {
			for (Vector v : dataSet.vectors()) {
				result += v.vectorValues().get(dimension);
			}
		}
		result = result / this.getCountOfVectors();
		return result;
	}

	/**
	 * Method getStandardDeviationForDimension - returns a double:
	 * Takes two arguments:
	 * 1. int
	 * 2. double
	 * The first parameter specifies the dimension for which the standard deviation is being calculated
	 * The second parameter is the average of that dimension specified (first argument)
	 * The standard deviation is then calculated and returned
	 */
	public Double getStandardDeviationForDimension(int dimension, double averageOfDimension) throws Exception {
		if (dimension > _sets.get(0).getDimensionCount()) {
			throw new Exception("Dimension is out of bounds...");
		}
		double result = 0;
		for (DataSet dataSet : _sets) {
			for (Vector v : dataSet.vectors()) {
				result += Math.pow((v.vectorValues().get(dimension) - averageOfDimension), 2);
			}
		}
		result /= this.getCountOfVectors();
		result = Math.sqrt(result);
		return result;
	}
}
