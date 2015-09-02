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
 * DefaultEvaluationMetrics.java
 * Copyright (C) 2015 University of Waikato, Hamilton, NZ
 */

package weka.classifiers.meta.multisearch;

import weka.core.Tag;

/**
 * Default metrics.
 *
 * @author FracPete (fracpete at waikato dot ac dot nz)
 * @version $Revision$
 */
public class DefaultEvaluationMetrics
  extends AbstractEvaluationMetrics {

  private static final long serialVersionUID = 8549253661958964524L;

  /** evaluation via: Correlation coefficient. */
  public static final int EVALUATION_CC = 0;

  /** evaluation via: Root mean squared error. */
  public static final int EVALUATION_RMSE = 1;

  /** evaluation via: Root relative squared error. */
  public static final int EVALUATION_RRSE = 2;

  /** evaluation via: Mean absolute error. */
  public static final int EVALUATION_MAE = 3;

  /** evaluation via: Relative absolute error. */
  public static final int EVALUATION_RAE = 4;

  /** evaluation via: Combined = (1-CC) + RRSE + RAE. */
  public static final int EVALUATION_COMBINED = 5;

  /** evaluation via: Accuracy. */
  public static final int EVALUATION_ACC = 6;

  /** evaluation via: Kappa statistic. */
  public static final int EVALUATION_KAPPA = 7;

  /** evaluation. */
  protected static final Tag[] TAGS_EVALUATION = {
    new Tag(EVALUATION_CC, "CC", "Correlation coefficient"),
    new Tag(EVALUATION_RMSE, "RMSE", "Root mean squared error"),
    new Tag(EVALUATION_RRSE, "RRSE", "Root relative squared error"),
    new Tag(EVALUATION_MAE, "MAE", "Mean absolute error"),
    new Tag(EVALUATION_RAE, "RAE", "Root absolute error"),
    new Tag(EVALUATION_COMBINED, "COMB", "Combined = (1-abs(CC)) + RRSE + RAE"),
    new Tag(EVALUATION_ACC, "ACC", "Accuracy"),
    new Tag(EVALUATION_KAPPA, "KAP", "Kappa")
  };

  /**
   * Returns the tags to used in the GUI.
   *
   * @return		the tags
   */
  @Override
  public Tag[] getTags() {
    return TAGS_EVALUATION;
  }

  /**
   * Returns the ID of default metric to use.
   *
   * @return		the default
   */
  @Override
  public int getDefaultMetric() {
    return EVALUATION_CC;
  }

  /**
   * Returns whether to negate the metric for sorting purposes.
   *
   * @param id		the metric id
   * @return		true if to invert
   */
  public boolean invert(int id) {
    switch (id) {
      case EVALUATION_CC:
        return true;
      case EVALUATION_ACC:
        return true;
      case EVALUATION_KAPPA:
        return true;
      default:
        return false;
    }
  }
}
