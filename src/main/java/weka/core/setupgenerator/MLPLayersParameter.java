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
 * MLPLayersParameter.java
 * Copyright (C) 2008-2018 University of Waikato, Hamilton, New Zealand
 */

package weka.core.setupgenerator;

import weka.core.Option;
import weka.core.Utils;

import java.util.AbstractCollection;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
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

  /** restricts the number of candidates that are being generated exhaustively **/
  public final static int MAX_CANDIDATES_TO_GENERATE = 65536;

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
      + "minLayerSize <= n <= maxLayerSize. ";
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
   * @param minLayers
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
   * @param maxLayers
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
   * @param minLayerSize
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
   * @param maxLayerSize
   *          maximum layer size
   */
  public void setMaxLayerSize(int maxLayerSize) {
    this.m_MaxLayerSize = maxLayerSize;
  }

  /**
   * Calculates the number of possible candidate structures
   *
   * @return number of possible candidate structures
   */
  public long calculateNumberOfCandidates() {
    long result = 0L;
    for (int i = getMinLayers(); i <= getMaxLayers(); ++i) {
      result += Math.pow(getMaxLayerSize() - getMinLayerSize() + 1, i);
    }
    return result;
  }

  /**
   * Checks the given parameters for simple constraint violations
   */
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

  /**
   * Converts a List of integers into a delimited string
   *
   * @param list: the list to convert
   * @param delimiter: the separation string, typically a comma
   * @return the imploded list
   */
  private String implodeList(List<Integer> list, String delimiter) {
    StringBuilder sb = new StringBuilder();
    for (Integer i : list) {
      sb.append(delimiter + i);
    }
    return sb.toString().substring(delimiter.length());
  }

  /**
   * Generates MLP layer structures randomly
   *
   * @param numCandidates: the number of candidates to generate
   * @param seed: the random seed to use for the RNG
   * @return the layer structures
   */
  private HashSet<String> generateCandicatesRandom(int numCandidates, long seed) {
    Random random = new Random(seed);
    HashSet<String> result = new HashSet<String>();
    while (result.size() < numCandidates) {
      List<Integer> current = new ArrayList<Integer>();
      while (current.size() < getMaxLayers()) {
	final int range = getMaxLayerSize() - getMinLayerSize() + 1;
	final int currentLayerSize = random.nextInt(range) + getMinLayerSize();
	current.add(currentLayerSize);
	if (current.size() > getMinLayers()) {
	  String currentAsStr = implodeList(current, ",");
	  result.add(currentAsStr);
	  if (result.size() >= numCandidates) {
	    break;
	  }
	}
      }
    }
    return result;
  }

  /**
   * Generates recursively all possible MLP layer structures within the
   * constraints
   *
   * @param currentLayers: partial generated layer
   * @return the layer structures
   */
  private ArrayList<String> generateCandidatesExhaustive(ArrayList<Integer> currentLayers) {
    if (currentLayers.size() >= getMaxLayers()) {
      ArrayList<String> result = new ArrayList<String>();
      result.add(implodeList(currentLayers, ","));
      return result;
    } else {
      ArrayList<String> result = new ArrayList<String>();
      // add current result
      if (currentLayers.size() >= getMinLayers()) {
	result.add(implodeList(currentLayers, ","));
      }
      // generate layers of higher size
      for (int i = getMinLayerSize(); i <= getMaxLayerSize(); ++i) {
	currentLayers.add(i);
	result.addAll(generateCandidatesExhaustive(currentLayers));
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

    AbstractCollection<String> result;
    if (calculateNumberOfCandidates() >= MAX_CANDIDATES_TO_GENERATE) {
      // Random seed does not need to be parameterized, as it will barely
      // influence results
      result = generateCandicatesRandom(MAX_CANDIDATES_TO_GENERATE, 0L);
    } else {
      result = generateCandidatesExhaustive(new ArrayList<Integer>());
    }
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