/**
 * Class: DataSet
 * Author: BitNak - Reuben Wilson
 * Date Authored: 04.08.14
 * Last Modified: 10.08.14
 * Description: The DataSet class represents all of the data thatg belongs to a single algorithm.
 * I.e. It houses a List of Vector objects, one for each entry for a given file and also accomodates for
 * Housing the name of the algoritm for which it represents
 */

import java.io.BufferedReader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class DataSet {
	/**
	 * Two fields declared:
	 * _algorithmName as a String - used to store the algorithm name for the data of which it represents
	 * _vectors as a List of Vector - used to store a List of Vector objects (one Vector object for each data line in a file)
	 * _results as a List of Double - used to store the sorted comparison results
	 * _index as a Map<Double, Integer> - used to store comparison results with a key (index)	
	 */
	private String _algorithmName;
	private ArrayList<Vector> _vectors;
	private ArrayList<Double> _results;
	private Map<Double, Integer> _index;

	/**
	 * Constructor for class, DataSet
	 * Takes two arguments:
	 * 1. String
	 * 2. StringBuilder
	 */
	public DataSet(String algorithmName, StringBuilder dataSet) throws Exception {
		_algorithmName = algorithmName;
		_vectors = new ArrayList<Vector>();
		_results = new ArrayList<Double>();
		_index = new HashMap<Double, Integer>();
		this.populateVectors(dataSet);
	}

	/**
	 * Method createMap:
	 * Uses Collections to sort the comparison values for each vector
	 * Comparator that ranks the comparison values
	 */
	public void createResultMap() {
		for (int i = 0; i < _vectors.size(); i++) {
			Double result = _vectors.get(i).comparisonValue();
			_index.put(result, i + 1);
			_results.add(result);
		}
		Collections.sort(_results, new Comparator<Double>() {
			public int compare(Double a, Double b) {
				if (a < b) return +1;
				if (a > b) return -1;
				return 0;
			}
		});
	}

	/**
	 * Getter algorithmName for field _algorithmName
	 */
	public String algorithmName() {
		return _algorithmName;
	}
	
	/**
	 * Getter vectors for field _vectors
	 */
	public List<Vector> vectors() {
		return _vectors;
	}

	/**
	 * Method getDimensionCount - returns an integer:
	 * Returns an integer representing the number of dimensions that each of the Vector objects are comprised of
	 */
	public int getDimensionCount() {
		return _vectors.get(0).vectorValues().size();
	}

	/**
	 * Method populateVectors:
	 * Takes one argument:
	 * 1. StringBuilder
	 * Converts the data contained within the StringBuilder into Vector objects
	 */
	private void populateVectors(StringBuilder dataSet) throws Exception {
		BufferedReader reader = new BufferedReader(new StringReader(dataSet.toString()));
		String data = null;
		while ((data = reader.readLine()) != "" && !data.isEmpty()) {
			String[] parts = data.split(" ");
			ArrayList<Double> result = new ArrayList<Double>();
			for (int i = 0; i < parts.length; i++) {
				result.add(Double.parseDouble(parts[i]));
			}
			_vectors.add(new Vector(result));
		}
		reader.close();
	}

	/**
	 * Method returnTopThreeComparisonResults - Returns a Map<Double, Integer>:
	 * Returns a Map with Key Value Pair in relation to the top three sorted comparison values and their index
	 */
	public Map<Double, Integer> returnTopThreeComparisonResults() {
		Map<Double, Integer> result = new TreeMap<Double, Integer>();
		for (int i = 0; i < 3; i++) {
			result.put(_results.get(i), _index.get(_results.get(i)));
		}
		return result;
	}
}
