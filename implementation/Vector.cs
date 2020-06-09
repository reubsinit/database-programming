//<summary>
//Class: Vector
//Author: BitNak - Reuben Wilson
//Date Authored: 04.08.14
//Last Modified: 10.08.14
//Description: The Vector class is used to catalog the required data for each vector that appears
//Within abstract dataset.
//</summary>

using System;
using System.Text;
using System.IO;
using System.Linq;
using System.Collections.Generic;

	public class Vector
	{
//				<remarks>
//				Three fields declared:
//				_vectorValues as a List of double - used to store the values that represent a vector
//				_standardisedValues as a List of double - used to store the standardized values for the vector
//				_comparionValue as a double - comparison result used to compare against other vector entries
//				</remarks>
		private List<double> _vectorValues;
		private List<double> _standardisedValues;
		private double _comparisonValue;

//				<remarks>
//				Constructor for class, Vector
//				Takes one argument:
//				1. List of double
//				</remarks>
		public Vector (List<double> values)
		{
			_vectorValues = values;
			_standardisedValues = new List<double> ();
			_comparisonValue = 0;
		}

//				<remarks>
//				Getter VectorValues for field _vectorValues
//				</remarks>
		public List<double> VectorValues
		{
			get {return _vectorValues;}
		}

//				<remarks>
//				Getter StandardisedValues for field _standardisedValues
//				</remarks>
		public List<double> StandardisedValues
		{
			get {return _standardisedValues;}
			set { _standardisedValues = value;}
		}

//				<remarks>
//				Getter/Setter ComparisonValue for field _comparisonValue
//				</remarks>
		public double ComparisonValue
		{
			get {return _comparisonValue;}
			set { _comparisonValue = value;}
		}
}

