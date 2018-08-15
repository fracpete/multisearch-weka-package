/*
 *   This program is free software: you can redistribute it and/or modify
 *   it under the terms of the GNU General Public License as published by
 *   the Free Software Foundation, either version 3 of the License, or
 *   (at your option) any later version.
 *
 *   This program is distributed in the hope that it will be useful,
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *   GNU General Public License for more details.
 *
 *   You should have received a copy of the GNU General Public License
 *   along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

/*
 * ListParameter.java
 * Copyright (C) 2008-2016 University of Waikato, Hamilton, New Zealand
 */

package weka.core.setupgenerator;

import weka.core.Option;
import weka.core.Utils;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.LinkedList;
import java.util.List;
import java.util.Vector;

/**
 * Container specialized class for the hiddenLayers search parameter of the
 * MultiLayerPerceptron.
 *
 * @author janvanrijn (janvanrijn at gmail dot com)
 * @version $Revision: 4521 $
 */
public class MLPLayersParameter extends AbstractPropertyParameter {

	/** for serialization. */
	private static final long serialVersionUID = -5119694776105238138L;

	/** default value for numLayers **/
	protected final static int NUM_LAYERS_DEFAULT = 1;

	/** default value for minLayerSize **/
	protected final static int MIN_LAYER_SIZE_DEFAULT = 8;

	/** default value for maxLayerSize **/
	protected final static int MAX_LAYER_SIZE_DEFAULT = 128;

	/** the number of layers to generate. */
	protected int m_NumLayers = NUM_LAYERS_DEFAULT;

	/** the minimum size of each layer **/
	protected int m_MinLayerSize = MIN_LAYER_SIZE_DEFAULT;

	/** the maximum size of each layer **/
	protected int m_MaxLayerSize = MAX_LAYER_SIZE_DEFAULT;

	/**
	 * Returns a string describing the object.
	 * 
	 * @return a description suitable for displaying in the explorer/experimenter
	 *         gui
	 */
	public String globalInfo() {
		return "Container class defining the hiddenLayer search parameter "
				+ "of the MultiLayerPerceptron.\n"
				+ "Given numLayers, minLayerSize and maxLayerSize, it returns "
				+ "a comma-separated string of numLayers numbers, such that"
				+ "minLayerSize <= n <= maxLayerSize (for each layer n). ";
	}

	/**
	 * Gets an enumeration describing the available options.
	 *
	 * @return an enumeration of all the available options.
	 */
	public Enumeration listOptions() {
		Vector result;
		Enumeration enm;

		result = new Vector();

		enm = super.listOptions();
		while (enm.hasMoreElements())
			result.add(enm);

		result.addElement(new Option(
				"\tThe number of layers.\n" + "\t(default: " + NUM_LAYERS_DEFAULT + ")",
				"numLayers", NUM_LAYERS_DEFAULT, "-numLayers <value>"));

		result.addElement(new Option(
				"\tTThe minimum size for each layer.\n" + "\t(default: "
						+ MIN_LAYER_SIZE_DEFAULT + ")",
				"minLayerSize", MIN_LAYER_SIZE_DEFAULT, "-minLayerSize <value>"));

		result.addElement(new Option(
				"\tTThe maximal size for each layer.\n" + "\t(default: "
						+ MAX_LAYER_SIZE_DEFAULT + ")",
				"maxLayerSize", MAX_LAYER_SIZE_DEFAULT, "-maxLayerSize <value>"));

		return result.elements();
	}

	/**
	 * returns the options of the current setup.
	 *
	 * @return the current options
	 */
	public String[] getOptions() {
		Vector<String> result;
		String[] options;
		int i;

		result = new Vector<String>();

		options = super.getOptions();
		for (i = 0; i < options.length; i++)
			result.add(options[i]);

		result.add("-numLayers");
		result.add("" + getNumLayers());

		result.add("-minLayerSize");
		result.add("" + getMinLayerSize());

		result.add("-maxLayerSize");
		result.add("" + getMaxLayerSize());

		return result.toArray(new String[result.size()]);
	}

	/**
	 * Parses the options for this object.
	 *
	 * @param options
	 *          the options to use
	 * @throws Exception
	 *           if setting of options fails
	 */
	public void setOptions(String[] options) throws Exception {
		String tmpStr;

		tmpStr = Utils.getOption("numLayers", options);
		if (tmpStr.length() != 0)
			setNumLayers(Integer.parseInt(tmpStr));
		else
			setNumLayers(NUM_LAYERS_DEFAULT);

		tmpStr = Utils.getOption("minLayerSize", options);
		if (tmpStr.length() != 0)
			setMinLayerSize(Integer.parseInt(tmpStr));
		else
			setMinLayerSize(MIN_LAYER_SIZE_DEFAULT);

		tmpStr = Utils.getOption("maxLayerSize", options);
		if (tmpStr.length() != 0)
			setMaxLayerSize(Integer.parseInt(tmpStr));
		else
			setMaxLayerSize(MIN_LAYER_SIZE_DEFAULT);

		super.setOptions(options);
	}

	/**
	 * Returns the tip text for this property.
	 * 
	 * @return tip text for this property suitable for displaying in the
	 *         explorer/experimenter gui
	 */
	public String numLayersTipText() {
		return "The number of layers to generate";
	}

	/**
	 * Returns the value for this property.
	 * 
	 * @return the number of layers to generate
	 */
	public int getNumLayers() {
		return m_NumLayers;
	}

	/**
	 * Sets the value for this property.
	 * 
	 * @param the
	 *          number of layers to generate
	 */
	public void setNumLayers(int numLayers) {
		this.m_NumLayers = numLayers;
	}

	/**
	 * Returns the tip text for this property.
	 * 
	 * @return tip text for this property suitable for displaying in the
	 *         explorer/experimenter gui
	 */
	public String minLayerSizeTipText() {
		return "The minimum size of each layer";
	}

	/**
	 * Returns the value for this property.
	 * 
	 * @return the minimum layer size
	 */
	public int getMinLayerSize() {
		return m_MinLayerSize;
	}

	/**
	 * Sets the value for this property.
	 * 
	 * @param the
	 *          minimum layer size
	 */
	public void setMinLayerSize(int minLayerSize) {
		this.m_MinLayerSize = minLayerSize;
	}

	/**
	 * Returns the tip text for this property.
	 * 
	 * @return tip text for this property suitable for displaying in the
	 *         explorer/experimenter gui
	 */
	public String maxLayerSizeTipText() {
		return "The maximum size of each layer";
	}

	/**
	 * Returns the value for this property.
	 * 
	 * @return the maximum layer size
	 */
	public int getMaxLayerSize() {
		return m_MaxLayerSize;
	}

	/**
	 * Sets the value for this property.
	 * 
	 * @param the
	 *          maximum layer size
	 */
	public void setMaxLayerSize(int maxLayerSize) {
		this.m_MaxLayerSize = maxLayerSize;
	}

	private String implodeList(List<Integer> list, String delimiter) {
		StringBuilder sb = new StringBuilder();
		for (Integer i : list) {
			sb.append(delimiter + i);
		}
		return sb.toString().substring(delimiter.length());
	}

	private List<String> generateLayers(List<Integer> currentLayers) {
		if (currentLayers.size() >= getNumLayers()) {
			List<String> result = new LinkedList<String>();
			result.add(implodeList(currentLayers, ","));
			return result;
		} else {
			List<String> result = new LinkedList<String>();
			for (int i = getMinLayerSize(); i <= getMaxLayerSize(); ++i) {
				currentLayers.add(i);
				result.addAll(generateLayers(currentLayers));
				currentLayers.remove(i);
			}
			return result;
		}
	}

	/**
	 * Generates all possible number of layer combinations
	 *
	 * @return a list of all possible items
	 * @throws Exception
	 *           if the splitting fails
	 */
	public String[] getItems() throws Exception {
		if (getMaxLayerSize() < getMinLayerSize()) {
			throw new Exception(
					"minLayerSize should be smaller than or equal to " + " maxLayerSize");
		}
		List<String> result = generateLayers(new ArrayList<Integer>());
		return result.toArray(new String[result.size()]);
	}

	/**
	 * Returns a string representation of the search parameter.
	 * 
	 * @return a string representation
	 */
	public String toString() {
		String result;

		result = super.toString();
		result += ", numLayers: " + m_NumLayers;
		result += ", minLayerSize: " + m_MinLayerSize;
		result += ", maxLayerSize: " + m_MaxLayerSize;
		return result;
	}
}