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

import java.util.Enumeration;
import java.util.Vector;

/**
 * Container class for search parameters.
 *
 * @author  fracpete (fracpete at waikato dot ac dot nz)
 * @version $Revision: 4521 $
 */
public class ListParameter
  extends AbstractPropertyParameter {
  
  /** for serialization. */
  private static final long serialVersionUID = 1415901739037349037L;

  /** the custom list delimiter to use. */
  protected String m_CustomDelimiter = "";

  /** the list string of values. */
  protected String m_ListStr = "";

  /**
   * Returns a string describing the object.
   * 
   * @return 		a description suitable for displaying in the
   *         		explorer/experimenter gui
   */
  public String globalInfo() {
    return 
        "Container class defining the search parameters for a particular "
      + "property.\n"
      + "Only the specified list values are used.\n"
      + "A custom list delimiter can be defined as well. By default, "
      + "a blank-separated list is used.";
  }
  
  /**
   * Gets an enumeration describing the available options.
   *
   * @return an enumeration of all the available options.
   */
  public Enumeration listOptions() {
    Vector	result;
    Enumeration	enm;

    result = new Vector();

    enm = super.listOptions();
    while (enm.hasMoreElements())
      result.add(enm);

    result.addElement(new Option(
        "\tThe custom list delimiter.\n"
        + "\t(default: none)",
        "custom-delimiter", 1, "-custom-delimiter <values>"));

    result.addElement(new Option(
        "\tThe list of explicit values to use.\n"
        + "\t(default: none)",
        "list", 1, "-list <values>"));

    return result.elements();
  }

  /**
   * returns the options of the current setup.
   *
   * @return		the current options
   */
  public String[] getOptions() {
    Vector<String>	result;
    String[]		options;
    int			i;

    result = new Vector<String>();

    options = super.getOptions();
    for (i = 0; i < options.length; i++)
      result.add(options[i]);

    if (!getCustomDelimiter().isEmpty()) {
      result.add("-custom-delimiter");
      result.add("" + getCustomDelimiter());
    }

    result.add("-list");
    result.add("" + getList());

    return result.toArray(new String[result.size()]);	  
  }

  /**
   * Parses the options for this object.
   *
   * @param options	the options to use
   * @throws Exception	if setting of options fails
   */
  public void setOptions(String[] options) throws Exception {
    String	tmpStr;

    tmpStr = Utils.getOption("custom-delimiter", options);
    if (tmpStr.length() != 0)
      setCustomDelimiter(tmpStr);
    else
      setCustomDelimiter("");

    tmpStr = Utils.getOption("list", options);
    if (tmpStr.length() != 0)
      setList(tmpStr);
    else
      setList("");

    super.setOptions(options);
  }
  
  /**
   * Returns the tip text for this property.
   * 
   * @return 		tip text for this property suitable for
   * 			displaying in the explorer/experimenter gui
   */
  public String customerDelimiterTipText() {
    return "The custom list delimiter to use - blank-separated list by default.";
  }

  /**
   * Get the custom delimiter to use.
   *
   * @return 		the delimiter.
   */
  public String getCustomDelimiter() {
    return m_CustomDelimiter;
  }
  
  /**
   * Set the custom delimiter to use.
   *
   * @param value 	the delimiter.
   */
  public void setCustomDelimiter(String value) {
    m_CustomDelimiter = value;
  }

  /**
   * Returns the tip text for this property.
   *
   * @return 		tip text for this property suitable for
   * 			displaying in the explorer/experimenter gui
   */
  public String listTipText() {
    return "The blank-separated list of values to use.";
  }

  /**
   * Get the list of values.
   *
   * @return 		the list.
   */
  public String getList() {
    return m_ListStr;
  }

  /**
   * Set the list of values.
   *
   * @param value 	the list of values.
   */
  public void setList(String value) {
    m_ListStr = value;
  }

  /**
   * Splits the list string using the appropriate delimiter.
   *
   * @return		the list items
   * @throws Exception	if the splitting fails
   */
  public String[] getItems() throws Exception {
    String[] 	result;

    if (getCustomDelimiter().isEmpty())
      result = Utils.splitOptions(getList());
    else
      result = getList().split(getCustomDelimiter());

    return result;
  }

  /**
   * Returns a string representation of the search parameter.
   * 
   * @return		a string representation
   */
  public String toString() {
    String	result;

    result = super.toString();
    result += ", list: " + getList();
    if (!getCustomDelimiter().isEmpty())
      result += ", delimiter: '" + getCustomDelimiter() + "'";
    
    return result;
  }
}