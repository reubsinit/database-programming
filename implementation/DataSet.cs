//<summary>
//Class: DataSet
//Author: BitNak - Reuben Wilson
//Date Authored: 04.08.14
//Last Modified: 10.08.14
//Description: The DataSet class represents all of the data thatg belongs to a single algorithm.
//I.e. It houses a List of Vector objects, one for each entry for a given file and also accomodates for
//Housing the name of the algoritm for which it represents.
//</summary>

using System;
using System.Text;
using System.IO;
using System.Linq;
using System.Collections.Generic;

	public class DataSet
	{
//				<remarks>
//				Two fields declared:
//				_algorithmName as a String - used to store the algorithm name for the data of which it represents
//				_vectors as a List of Vector - used to store a List of Vector objects (one Vector object for each data line in a file)
//				</remarks>
		private String _algorithmName;
		private List<Vector> _vectors;

//				<remarks>
//				Constructor for class, DataSet
//				Takes two arguments:
//				1. String
//				2. StringBuilder
//				</remarks>
		public DataSet (String algorithmName, StringBuilder dataSet)
		{
			_algorithmName = algorithmName;
			_vectors = new List<Vector> ();
			this.PopulateVectors(dataSet);
		}

//				<remarks>
//				Getter AlgorithmName for field _algorithmName
//				</remarks>
		public String AlgorithmName
		{
			get {return _algorithmName;}
		}

//				<remarks>
//				Getter Vectors for field _vectors
//				</remarks>
		public List<Vector> Vectors {
			get { return _vectors; }
		}

//				<remarks>
//				Method GetDimensionCount - returns an integer:
//				Returns an integer representing the number of dimensions that each of the Vector objects are comprised of
//				</remarks>
		public int GetDimensionCount()
		{
			return _vectors.ElementAt (0).VectorValues.Count ();
		}

//				<remarks>
//				Method PopulateVectors:
//				Takes one argument:
//				1. StringBuilder
//				Converts the data contained within the StringBuilder into Vector objects
//				</remarks>
		private void PopulateVectors(StringBuilder withDataSet){
			StreamReader reader = new StreamReader (new MemoryStream (Encoding.ASCII.GetBytes (withDataSet.ToString ())));
			string data;
			while ((data = reader.ReadLine ()) != string.Empty && data != null) {
				double[] RowData = data
					.Split(new char[] { ' ' }, StringSplitOptions.RemoveEmptyEntries)
					.Select(s => double.Parse(s))
					.ToArray();
				List<double> tempList = new List<double>();
				for (int i = 0; i < RowData.Count(); i++) {
					tempList.Add (RowData [i]);
				}
				_vectors.Add (new Vector(tempList));
			}
			reader.Close ();
		}

//				<remarks>
//				Method ReturnTopThreeComparisonResults - Returns an IEnumerable<KeyValuePair<String, double>>:
//				Uses LINQ to locate the three Vectors objects within the List of Vectors that have the best comparison results.
//				A list of Key Value Pair is returned, based on the comparison result and the index of the Vector
//				</remarks>
		public IEnumerable<KeyValuePair<String, double>> ReturnTopThreeComparisonResults(){
			Dictionary<string, double> resultMap = new Dictionary<string, double>();
			for (int i = 0; i < _vectors.Count (); i++) {
					resultMap.Add("[" + (i + 1).ToString () + "]", _vectors[i].ComparisonValue);
				}
			IEnumerable<KeyValuePair<String, double>> result = (from entry in resultMap orderby entry.Value descending select entry).ToDictionary(pair => pair.Key, pair => pair.Value).Take(3);
			return result;
		}
}