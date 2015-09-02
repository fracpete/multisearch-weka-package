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

/**
 * AbstractEvaluationMetrics.java
 * Copyright (C) 2015 University of Waikato, Hamilton, NZ
 */

package weka.classifiers.meta.multisearch;

import weka.core.Tag;

import java.io.Serializable;

/**
 * Ancestor for evaluation metrics (to be used in the GUI).
 *
 * @author FracPete (fracpete at waikato dot ac dot nz)
 * @version $Revision$
 */
public abstract class AbstractEvaluationMetrics
  implements Serializable {

  private static final long serialVersionUID = -2845628542266516187L;

  /**
   * Returns the tags to used in the GUI.
   *
   * @return		the tags
   */
  public abstract Tag[] getTags();

  /**
   * Returns the ID of default metric to use.
   *
   * @return		the default
   */
  public abstract int getDefaultMetric();

  /**
   * Returns whether to negate the metric for sorting purposes.
   *
   * @param tag		the metric
   * @return		true if to invert
   */
  public boolean invert(Tag tag) {
    return invert(tag.getID());
  }

  /**
   * Returns whether to negate the metric for sorting purposes.
   *
   * @param id		the metric id
   * @return		true if to invert
   */
  public abstract boolean invert(int id);

  /**
   * Returns whether the ID is valid.
   *
   * @param id		the ID to check
   * @return		true if valid
   */
  public boolean check(int id) {
    for (Tag tag: getTags()) {
      if (tag.getID() == id)
	return true;
    }
    return false;
  }
}
