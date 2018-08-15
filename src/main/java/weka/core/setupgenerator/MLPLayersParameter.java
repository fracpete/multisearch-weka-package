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

	/** default value for minLayers **/
	protected final static int MIN_LAYERS_DEFAULT = 1;
	
	/** default value for maxLayers **/
	protected final static int MAX_LAYERS_DEFAULT = 2;

	/** default value for minLayerSize **/
	protected final static int MIN_LAYER_SIZE_DEFAULT = 8;

	/** default value for maxLayerSize **/
	protected final static int MAX_LAYER_SIZE_DEFAULT = 128;

	/** the minimum numbers of layers. */
	protected int m_MinLayers = MIN_LAYERS_DEFAULT;
	
	/** the maximum number of layers. */
	protected int m_MaxLayers = MAX_LAYERS_DEFAULT;
	
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
				+ "Given minLayers, maxLayers, minLayerSize and maxLayerSize, "
				+ "it generates a comma-separated string s integers, such that "
				+ "minLayers <= s <= maxLayers, and for every integer n "
				+ "minLayerSize <= n <= maxLayerSize. "
				+ "NOTE: This is an experimental design. Due to the nature of"
				+ "the API, all posibilities will be generated. Using many "
				+ "layers (>2) or many nodes per layer is currently discouraged.";
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
				"\tThe minimum number of layers.\n" + "\t(default: " + MIN_LAYERS_DEFAULT + ")",
				"minLayers", MIN_LAYERS_DEFAULT, "-minLayers <value>"));
		
		result.addElement(new Option(
				"\tThe maximal number of layers.\n" + "\t(default: " + MAX_LAYERS_DEFAULT + ")",
				"maxLayers", MAX_LAYERS_DEFAULT, "-maxLayers <value>"));

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

		result.add("-minLayers");
		result.add("" + getMinLayers());
		
		result.add("-maxLayers");
		result.add("" + getMaxLayers());

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

		tmpStr = Utils.getOption("minLayers", options);
		if (tmpStr.length() != 0)
			setMinLayers(Integer.parseInt(tmpStr));
		else
			setMinLayers(MIN_LAYERS_DEFAULT);
		
		tmpStr = Utils.getOption("maxLayers", options);
		if (tmpStr.length() != 0)
			setMaxLayers(Integer.parseInt(tmpStr));
		else
			setMaxLayers(MAX_LAYERS_DEFAULT);

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
		
		checkStructureParams();
		
		super.setOptions(options);
	}

	/**
	 * Returns the tip text for this property.
	 * 
	 * @return tip text for this property suitable for displaying in the
	 *         explorer/experimenter gui
	 */
	public String minLayersTipText() {
		return "The minimal number of layers to generate";
	}

	/**
	 * Returns the value for this property.
	 * 
	 * @return the number of layers to generate
	 */
	public int getMinLayers() {
		return m_MinLayers;
	}

	/**
	 * Sets the value for this property.
	 * 
	 * @param the
	 *          minimal number of layers to generate
	 */
	public void setMinLayers(int minLayers) {
		this.m_MinLayers = minLayers;
	}
	

	/**
	 * Returns the tip text for this property.
	 * 
	 * @return tip text for this property suitable for displaying in the
	 *         explorer/experimenter gui
	 */
	public String maxLayersTipText() {
		return "The maximal number of layers to generate";
	}

	/**
	 * Returns the value for this property.
	 * 
	 * @return the number of layers to generate
	 */
	public int getMaxLayers() {
		return m_MaxLayers;
	}

	/**
	 * Sets the value for this property.
	 * 
	 * @param the
	 *          maximal number of layers to generate
	 */
	public void setMaxLayers(int maxLayers) {
		this.m_MaxLayers = maxLayers;
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
	
	private void checkStructureParams() throws Exception {
		if (getMaxLayerSize() < getMinLayerSize()) {
			throw new Exception(
					"minLayerSize should be smaller than or equal to maxLayerSize");
		}
		if (getMaxLayers() < getMinLayers()) {
			throw new Exception(
					"minLayers should be smaller than or equal to maxLayers");
		}
		if (getMaxLayers() == getMinLayers() && getMaxLayerSize() == getMinLayerSize()) {
			throw new Exception(
					"no variation in layer structure possible");
		}
	}
	
	private String implodeList(List<Integer> list, String delimiter) {
		StringBuilder sb = new StringBuilder();
		for (Integer i : list) {
			sb.append(delimiter + i);
		}
		return sb.toString().substring(delimiter.length());
	}

	private List<String> generateLayers(List<Integer> currentLayers) {
		if (currentLayers.size() >= getMaxLayers()) {
			List<String> result = new LinkedList<String>();
			result.add(implodeList(currentLayers, ","));
			return result;
		} else {
			List<String> result = new LinkedList<String>();
			// add current result
			if (currentLayers.size() >= getMinLayers()) {
				result.add(implodeList(currentLayers, ","));
			}
			// generate layers of higher size
			for (int i = getMinLayerSize(); i <= getMaxLayerSize(); ++i) {
				currentLayers.add(i);
				result.addAll(generateLayers(currentLayers));
				currentLayers.remove(currentLayers.size()-1);
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
		checkStructureParams();
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
		result += ", minLayers: " + m_MinLayers;
		result += ", maxLayers: " + m_MaxLayers;
		result += ", minLayerSize: " + m_MinLayerSize;
		result += ", maxLayerSize: " + m_MaxLayerSize;
		return result;
	}
}