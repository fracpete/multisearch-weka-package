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
 * ParameterGroup.java
 * Copyright (C) 2016-2018 University of Waikato, Hamilton, NZ
 */

package weka.core.setupgenerator;

import weka.core.Option;
import weka.core.Utils;

import java.util.Enumeration;
import java.util.Vector;

/**
 * Groups dependent parameters.
 *
 * @author FracPete (fracpete at waikato dot ac dot nz)
 */
public class ParameterGroup
  extends AbstractParameter {

  private static final long serialVersionUID = -8833890695683415205L;

  /** the parameters. */
  protected AbstractParameter[] m_Parameters;

  /**
   * Initializes the object.
   */
  public ParameterGroup() {
    super();

    m_Parameters = new AbstractParameter[0];
  }

  /**
   * Returns a string describing the object.
   *
   * @return 		a description suitable for displaying in the
   *         		explorer/experimenter gui
   */
  @Override
  public String globalInfo() {
    return "Groups dependent parameters.";
  }

  /**
   * Gets an enumeration describing the available options.
   *
   * @return an enumeration of all the available options.
   */
  public Enumeration listOptions() {
    Vector result;
    Enumeration		en;

    result = new Vector();

    result.addElement(new Option(
	  "\tA parameter setup for generating the setups.\n"
	  + "\tCan be supplied multiple times.\n"
	  + "\t(default: " + AbstractParameter.class.getName() + ")",
	  "search", 1, "-search <classname options>"));

    result.addElement(new Option(
	  "",
	  "", 0, "\nOptions specific to search parameter class '"
	  + MathParameter.class.getName() + "' ('-search'):"));

    en = new MathParameter().listOptions();
    while (en.hasMoreElements())
      result.addElement(en.nextElement());

    result.addElement(new Option(
	  "",
	  "", 0, "\nOptions specific to search parameter class '"
	  + ListParameter.class.getName() + "' ('-search'):"));

    en = new ListParameter().listOptions();
    while (en.hasMoreElements())
      result.addElement(en.nextElement());

    return result.elements();
  }

  /**
   * returns the options of the current setup.
   *
   * @return		the current options
   */
  public String[] getOptions() {
    int       	i;
    Vector    	result;
    String	tmpStr;

    result = new Vector();

    for (i = 0; i < m_Parameters.length; i++) {
      result.add("-search");
      tmpStr =   m_Parameters[i].getClass().getName() + " "
               + Utils.joinOptions(m_Parameters[i].getOptions());
      result.add(tmpStr);
    }

    return (String[]) result.toArray(new String[result.size()]);
  }

  /**
   * Parses the options for this object.
   *
   * @param options	the options to use
   * @throws Exception	if setting of options fails
   */
  public void setOptions(String[] options) throws Exception {
    String		tmpStr;
    String[]		tmpOptions;
    Vector<String>	search;
    int			i;
    AbstractParameter[]	params;

    search = new Vector<String>();
    do {
      tmpStr = Utils.getOption("search", options);
      if (tmpStr.length() > 0)
	search.add(tmpStr);
    }
    while (tmpStr.length() > 0);
    if (search.size() == 0)
      throw new IllegalArgumentException("No search parameters provided!");
    params = new AbstractParameter[search.size()];
    for (i = 0; i < search.size(); i++) {
      tmpOptions    = Utils.splitOptions(search.get(i));
      tmpStr        = tmpOptions[0];
      tmpOptions[0] = "";
      params[i]     = (AbstractParameter) Utils.forName(AbstractParameter.class, tmpStr, tmpOptions);
    }
    setParameters(params);
  }

  /**
   * Returns the tip text for this property.
   *
   * @return 		tip text for this property suitable for
   * 			displaying in the explorer/experimenter gui
   */
  public String parametersFileTipText() {
    return "The parameter definitions.";
  }

  /**
   * Sets the parameters to use as basis for the setups.
   *
   * @param value	the parameters
   */
  public void setParameters(AbstractParameter[] value) {
    m_Parameters = value;
  }

  /**
   * Returns the current parameters.
   *
   * @return		the parameters
   */
  public AbstractParameter[] getParameters() {
    return m_Parameters;
  }

  /**
   * Returns the parameter as space dimensions.
   *
   * @return		always null
   * @throws Exception	if instantiation of dimension fails
   */
  public SpaceDimension spaceDimension() throws Exception {
    return null;
  }

  /**
   * Returns the evaluated value.
   *
   * @param point	the point to evaluate
   * @return		the input
   */
  public Object evaluate(Object point) {
    return point;
  }

  /**
   * Short description.
   *
   * @return		the description
   */
  public String toString() {
    return Utils.arrayToString(m_Parameters);
  }
}
