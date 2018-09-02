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
 * Copyright (C) 2008-2018 University of Waikato, Hamilton, New Zealand
 */

package weka.core.setupgenerator;

import java.io.Serializable;

/**
 * Represents a single dimension in a multi-dimensional space.
 *
 * @author  fracpete (fracpete at waikato dot ac dot nz)
 */
public abstract class SpaceDimension
  implements Serializable {

  /** for serialization. */
  private static final long serialVersionUID = -7709016830854739486L;

  /** the minimum on the axis. */
  protected double m_Min;

  /** the maximum on the axis. */
  protected double m_Max;

  /** the step size for the axis. */
  protected double m_Step;

  /** the label for the axis. */
  protected String m_Label;

  /** the number of points on the axis. */
  protected int m_Width;

  /**
   * Tests itself against the provided dimension object.
   *
   * @param o		the dimension object to compare against
   * @return		if the two dimensions have the same setup
   */
  public abstract boolean equals(Object o);

  /**
   * returns the left border.
   *
   * @return 		the left border
   */
  public double getMin() {
    return m_Min;
  }

  /**
   * returns the right border.
   *
   * @return 		the right border
   */
  public double getMax() {
    return m_Max;
  }

  /**
   * returns the step size on the axis.
   *
   * @return 		the step size
   */
  public double getStep() {
    return m_Step;
  }

  /**
   * returns the label for the axis.
   *
   * @return		the label
   */
  public String getLabel() {
    return m_Label;
  }

  /**
   * returns the number of points on the axis (incl. borders)
   *
   * @return 		the number of points on the axis
   */
  public int width() {
    return m_Width;
  }

  /**
   * returns the value at the given point in the dimension.
   *
   * @param x		the x-th point on the axis
   * @return		the value at the given position
   */
  public abstract Object getValue(int x);

  /**
   * returns the closest index for the given value in the dimension.
   *
   * @param value	the value to get the index for
   * @return		the closest index in the dimension
   */
  public abstract int getLocation(Object value);

  /**
   * checks whether the given value is on the border of the dimension.
   *
   * @param value		the value to check
   * @return			true if the the value is on the border
   */
  public boolean isOnBorder(double value) {
    return isOnBorder(getLocation(value));
  }

  /**
   * checks whether the given location is on the border of the dimension.
   *
   * @param location 		the location to check
   * @return			true if the the location is on the border
   */
  public boolean isOnBorder(int location) {
    if (location == 0)
      return true;
    else
      return (location == width() - 1);
  }

  /**
   * returns a sub-dimension with the same type/step/list, but different borders.
   *
   * @param left	the left index
   * @param right	the right index
   * @return 		the sub-dimension
   */
  public abstract SpaceDimension subdimension(int left, int right);

  /**
   * Returns a string representation of the dimension.
   *
   * @return		a string representation
   */
  public abstract String toString();
}