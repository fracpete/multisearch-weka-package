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
 * AbstractParameter.java
 * Copyright (C) 2008 University of Waikato, Hamilton, New Zealand
 */

package weka.core.setupgenerator;

import weka.core.Option;
import weka.core.Utils;

import java.util.Enumeration;
import java.util.Vector;

/**
 * Abstract container class for search parameters.
 *
 * @author  fracpete (fracpete at waikato dot ac dot nz)
 * @version $Revision: 4521 $
 */
public abstract class AbstractPropertyParameter
  extends AbstractParameter {

  /** for serialization. */
  private static final long serialVersionUID = -941906920843843404L;

  /** the property to test. */
  protected String m_Property = "fill-in-property-path";

  /**
   * default constructor.
   */
  public AbstractPropertyParameter() {
    super();
  }
  
  /**
   * Returns a string describing the object.
   * 
   * @return 		a description suitable for displaying in the
   *         		explorer/experimenter gui
   */
  public abstract String globalInfo();

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
        "\tThe property path.\n"
        + "\t(default: '')",
        "property", 1, "-property <option>"));

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

    result.add("-property");
    result.add("" + getProperty());

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
    
    tmpStr = Utils.getOption("property", options);
    if (tmpStr.length() != 0)
      setProperty(tmpStr);
    else
      setProperty("");

    super.setOptions(options);
  }
  
  /**
   * Returns the tip text for this property.
   * 
   * @return 		tip text for this property suitable for
   * 			displaying in the explorer/experimenter gui
   */
  public String propertyTipText() {
    return "The property to test.";
  }

  /**
   * Get the property to update.
   *
   * @return 		the property.
   */
  public String getProperty() {
    return m_Property;
  }
  
  /**
   * Set the property to update.
   *
   * @param value 	the property.
   */
  public void setProperty(String value) {
    m_Property = value;
  }

  /**
   * Returns a string representation of the search parameter.
   * 
   * @return		a string representation
   */
  public String toString() {
    return "property: " + getProperty();
  }
}