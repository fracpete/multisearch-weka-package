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
 * ListSpaceDimension.java
 * Copyright (C) 2008-2018 University of Waikato, Hamilton, New Zealand
 */

package weka.core.setupgenerator;

import weka.core.Utils;

/**
 * Represents a single dimension in a multi-dimensional space that is based
 * on a list of values.
 *
 * @author  fracpete (fracpete at waikato dot ac dot nz)
 */
public class ListSpaceDimension
  extends SpaceDimension {

  /** for serialization. */
  private static final long serialVersionUID = -7709016830854739486L;

  /** the underlying list of values. */
  protected String[] m_List;

  /**
   * initializes the dimension (for list values).
   *
   * @param min 	the minimum index in the list (0-based index)
   * @param max 	the maximum index in the list (0-based index)
   * @param list 	the available values
   * @param label	the label for the axis
   */
  public ListSpaceDimension(int min, int max, String[] list, String label) {
    m_Min   = min;
    m_Max   = max;
    m_Step  = -1;
    m_Label = label;
    m_Width = max - min + 1;
    m_List  = list.clone();

    // min within range of list?
    if (m_Min >= m_List.length)
      throw new IllegalArgumentException(
        "Min must be smaller than list length (min=" + min + ", list=" + list.length + ")!");

    // max within range of list?
    if (m_Max >= m_List.length)
      throw new IllegalArgumentException(
        "Max must be smaller than list length (max=" + max + ", list=" + list.length + ")!");

    // is min <= max?
    if (m_Min > m_Max)
      throw new IllegalArgumentException(
        "Min must be at most Max (min=" + min + ", max=" + max + ")!");
  }

  /**
   * Tests itself against the provided dimension object.
   *
   * @param o		the dimension object to compare against
   * @return		if the two dimensions have the same setup
   */
  public boolean equals(Object o) {
    ListSpaceDimension 	dim;
    int			i;

    if (o == null)
      return false;

    if (!(o instanceof ListSpaceDimension))
      return false;

    dim = (ListSpaceDimension) o;
    if (getList().length != dim.getList().length)
      return false;

    for (i = 0; i < getList().length; i++) {
      if (!getList()[i].equals(dim.getList()[i]))
	return false;
    }

    return true;
  }

  /**
   * Returns the list of values, null in case of a numeric dimension that
   * is based on a mathematical function.
   *
   * @return		the list
   */
  public String[] getList() {
    return m_List;
  }

  /**
   * returns the value at the given point in the dimension.
   *
   * @param x		the x-th point on the axis
   * @return		the value at the given position
   */
  public Object getValue(int x) {
    if (x >= width())
      throw new IllegalArgumentException("Index out of scope on axis (" + x + " >= " + width() + ")!");

    return m_List[(int) m_Min + x];
  }

  /**
   * returns the closest index for the given value in the dimension.
   *
   * @param value	the value to get the index for
   * @return		the closest index in the dimension
   */
  public int getLocation(Object value) {
    int		result;
    int		i;
    String	valueStr;

    result = 0;

    valueStr = value.toString();
    for (i = 0; i < width(); i++) {
      if ((getValue(i)).equals(valueStr)) {
	result = ((int) m_Min) + i;
	break;
      }
    }

    return result;
  }

  /**
   * returns a sub-dimension with the same type/step/list, but different borders.
   *
   * @param left	the left index
   * @param right	the right index
   * @return 		the sub-dimension
   */
  public ListSpaceDimension subdimension(int left, int right) {
    return new ListSpaceDimension(left, right, getList(), getLabel());
  }

  /**
   * Returns a string representation of the dimension.
   *
   * @return		a string representation
   */
  public String toString() {
    return "dimension: " + getLabel() + ", min: " + getMin() + ", max: " + getMax() + ", list: " + Utils.arrayToString(getList());
  }
}