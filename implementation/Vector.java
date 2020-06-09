/**
 * Class: Vector
 * Author: BitNak - Reuben Wilson
 * Date Authored: 04.08.14
 * Last Modified: 10.08.14
 * Description: The Vector class is used to catalog the required data for each vector that appears
 * Within abstract dataset.
 */
import java.util.ArrayList;

public class Vector {
	/**
	* Three fields declared:
	* _vectorValues as a List of double - used to store the values that represent a vector
	* _standardisedValues as a List of double - used to store the standardized values for the vector
	* _comparionValue as a double - comparison result used to compare against other vector entries
	*/
	private ArrayList<Double> _vectorValues;
	private ArrayList<Double> _standardisedValues;
	private Double _comparisonValue;

	/**
	* Constructor for class, Vector
	* Takes one argument:
	* 1. List of double
	*/
	public Vector(ArrayList<Double> values) {
		_vectorValues = values;
		_standardisedValues = new ArrayList<Double>();
		_comparisonValue = 0.0;
	}
	
	/**
	* Getter vectorValues for field _vectorValues
	*/
	public ArrayList<Double> vectorValues() {
		return _vectorValues;
	}
	
	/**
	* Getter standardisedValues for field _standardisedValues
	*/
	public ArrayList<Double> standardisedValues() {
		return _standardisedValues;
	}

	/**
	 * Getter comparisonValue for field _comparisonValue
	 */
	public Double comparisonValue() {
		return _comparisonValue;
	}

	/**
	 * Method incrementComparisonValue:
	 * Takes one argument:
	 * 1. Double
	 * Increments field _comparisonValue by the Double argument
	 */
	public void incrementComparisonValue(Double incrementation) {
		_comparisonValue += incrementation;
	}
}
