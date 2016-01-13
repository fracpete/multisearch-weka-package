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
 * Copyright (C) 2015-2016 University of Waikato, Hamilton, NZ
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

  /** evaluation via: AUC. */
  public static final int EVALUATION_AUC = 8;

  /** evaluation via: weighted AUC. */
  public static final int EVALUATION_WEIGHTED_AUC = 9;

  /** evaluation via: PRC. */
  public static final int EVALUATION_PRC = 10;

  /** evaluation via: weighted PRC. */
  public static final int EVALUATION_WEIGHTED_PRC = 11;

  /** evaluation via: FMeasure. */
  public static final int EVALUATION_FMEASURE = 12;

  /** evaluation via: weighted FMeasure. */
  public static final int EVALUATION_WEIGHTED_FMEASURE = 13;

  /** evaluation via: Matthews Correlation coefficient. */
  public static final int EVALUATION_MATTHEWS_CC = 14;

  /** evaluation via: precision. */
  public static final int EVALUATION_PRECISION = 15;

  /** evaluation via: weighted precision. */
  public static final int EVALUATION_WEIGHTED_PRECISION = 16;

  /** evaluation via: recall. */
  public static final int EVALUATION_RECALL = 17;

  /** evaluation via: weighted recall. */
  public static final int EVALUATION_WEIGHTED_RECALL = 18;

  /** evaluation via: true positive rate. */
  public static final int EVALUATION_TRUE_POSITIVE_RATE = 19;

  /** evaluation via: true negative rate. */
  public static final int EVALUATION_TRUE_NEGATIVE_RATE = 20;

  /** evaluation via: false positive rate. */
  public static final int EVALUATION_FALSE_POSITIVE_RATE = 21;

  /** evaluation via: false negative rate. */
  public static final int EVALUATION_FALSE_NEGATIVE_RATE = 22;

  /** evaluation. */
  protected static final Tag[] TAGS_EVALUATION = {
    new Tag(EVALUATION_CC, "CC", "Correlation coefficient"),
    new Tag(EVALUATION_MATTHEWS_CC, "MCC", "Matthews correlation coefficient"),
    new Tag(EVALUATION_RMSE, "RMSE", "Root mean squared error"),
    new Tag(EVALUATION_RRSE, "RRSE", "Root relative squared error"),
    new Tag(EVALUATION_MAE, "MAE", "Mean absolute error"),
    new Tag(EVALUATION_RAE, "RAE", "Root absolute error"),
    new Tag(EVALUATION_COMBINED, "COMB", "Combined = (1-abs(CC)) + RRSE + RAE"),
    new Tag(EVALUATION_ACC, "ACC", "Accuracy"),
    new Tag(EVALUATION_KAPPA, "KAP", "Kappa"),
    new Tag(EVALUATION_PRECISION, "PREC", "Precision"),
    new Tag(EVALUATION_WEIGHTED_PRECISION, "WPREC", "Weighted precision"),
    new Tag(EVALUATION_RECALL, "REC", "Recall"),
    new Tag(EVALUATION_WEIGHTED_RECALL, "WREC", "Weighted recall"),
    new Tag(EVALUATION_AUC, "AUC", "Area under ROC"),
    new Tag(EVALUATION_WEIGHTED_AUC, "WAUC", "Weighted area under ROC"),
    new Tag(EVALUATION_PRC, "PRC", "Area under PRC"),
    new Tag(EVALUATION_WEIGHTED_PRC, "WPRC", "Weighted area under PRC"),
    new Tag(EVALUATION_FMEASURE, "FM", "F-Measure"),
    new Tag(EVALUATION_WEIGHTED_FMEASURE, "WFM", "Weighted F-Measure"),
    new Tag(EVALUATION_TRUE_POSITIVE_RATE, "TPR", "True positive rate"),
    new Tag(EVALUATION_TRUE_NEGATIVE_RATE, "TNR", "True negative rate"),
    new Tag(EVALUATION_FALSE_POSITIVE_RATE, "FPR", "False positive rate"),
    new Tag(EVALUATION_FALSE_NEGATIVE_RATE, "FNR", "False negative rate"),
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
      case EVALUATION_ACC:
      case EVALUATION_KAPPA:
      case EVALUATION_MATTHEWS_CC:
      case EVALUATION_PRECISION:
      case EVALUATION_WEIGHTED_PRECISION:
      case EVALUATION_RECALL:
      case EVALUATION_WEIGHTED_RECALL:
      case EVALUATION_AUC:
      case EVALUATION_WEIGHTED_AUC:
      case EVALUATION_PRC:
      case EVALUATION_WEIGHTED_PRC:
      case EVALUATION_FMEASURE:
      case EVALUATION_WEIGHTED_FMEASURE:
      case EVALUATION_TRUE_POSITIVE_RATE:
      case EVALUATION_TRUE_NEGATIVE_RATE:
        return true;
      default:
        return false;
    }
  }
}
