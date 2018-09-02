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
 * SpaceDimension.java
 * Copyright (C) 2008-2016 University of Waikato, Hamilton, New Zealand
 */

package weka.core.setupgenerator;

import weka.core.Utils;

/**
 * Represents a single dimension in a multi-dimensional space.
 *
 * @author  fracpete (fracpete at waikato dot ac dot nz)
 */
public class FunctionSpaceDimension
  extends SpaceDimension {

  /** for serialization. */
  private static final long serialVersionUID = -7709016830854739486L;

  /**
   * initializes the dimension (for numeric values).
   *
   * @param min 	the minimum on the axis
   * @param max 	the maximum on the axis
   * @param step 	the step size for the axis
   * @param label	the label for the axis
   */
  public FunctionSpaceDimension(double min, double max, double step, String label) {
    m_Min   = min;
    m_Max   = max;
    m_Step  = step;
    m_Label = label;
    m_Width = (int) StrictMath.round((m_Max - m_Min) / m_Step) + 1;

    // is min < max?
    if (m_Min >= m_Max)
      throw new IllegalArgumentException("Min must be smaller than Max!");

    // steps positive?
    if (m_Step <= 0)
      throw new IllegalArgumentException("Step must be a positive number!");

    // check borders
    if (!Utils.eq(m_Min + (m_Width-1)*m_Step, m_Max))
      throw new IllegalArgumentException(
        "Axis doesn't match! Provided max: " + m_Max
          + ", calculated max via min and step size: "
          + (m_Min + (m_Width-1)*m_Step));
  }

  /**
   * Tests itself against the provided dimension object.
   *
   * @param o		the dimension object to compare against
   * @return		if the two dimensions have the same setup
   */
  public boolean equals(Object o) {
    FunctionSpaceDimension dim;
    int		i;

    if (o == null)
      return false;

    if (!(o instanceof FunctionSpaceDimension))
      return false;

    dim = (FunctionSpaceDimension) o;

    if (width() != dim.width())
      return false;

    if (getMin() != dim.getMin())
      return false;

    if (getStep() != dim.getStep())
      return false;

    if (!getLabel().equals(dim.getLabel()))
      return false;

    return true;
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

    return m_Min + m_Step*x;
  }

  /**
   * returns the closest index for the given value in the dimension.
   *
   * @param value	the value to get the index for
   * @return		the closest index in the dimension
   */
  public int getLocation(Object value) {
    int		result;
    double	distance;
    double	currDistance;
    int		i;

    result = 0;

    // determine x
    distance = m_Step;
    for (i = 0; i < width(); i++) {
      currDistance = StrictMath.abs(((Double) value) - ((Double) getValue(i)));
      if (Utils.sm(currDistance, distance)) {
	distance = currDistance;
	result   = i;
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
  public FunctionSpaceDimension subdimension(int left, int right) {
    return new FunctionSpaceDimension((Double) getValue(left), (Double) getValue(right), getStep(), getLabel());
  }

  /**
   * Returns a string representation of the dimension.
   *
   * @return		a string representation
   */
  public String toString() {
    return "dimension: " + getLabel() + ", min: " + getMin() + ", max: " + getMax() + ", step: " + getStep();
  }
}