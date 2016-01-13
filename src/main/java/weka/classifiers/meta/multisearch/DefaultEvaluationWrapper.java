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
 * DefaultEvaluationWrapper.java
 * Copyright (C) 2015-2016 University of Waikato, Hamilton, NZ
 */

package weka.classifiers.meta.multisearch;

import weka.classifiers.Evaluation;

/**
 * Wrapper for the Evaluation class. Uses the first class label for class-label
 * dependent measures.
 *
 * @author FracPete (fracpete at waikato dot ac dot nz)
 * @version $Revision$
 */
public class DefaultEvaluationWrapper
  extends AbstractEvaluationWrapper<Evaluation, DefaultEvaluationMetrics> {

  private static final long serialVersionUID = 931329614934902835L;

  /** the evaluation object. */
  protected Evaluation m_Evaluation;

  /**
   * Initializes the wrapper.
   *
   * @param eval the evaluation to wrap
   */
  public DefaultEvaluationWrapper(Evaluation eval, DefaultEvaluationMetrics metrics) {
    super(eval, metrics);
  }

  /**
   * Sets the evaluation object to use.
   *
   * @param eval	the evaluation
   */
  @Override
  protected void setEvaluation(Evaluation eval) {
    m_Evaluation = eval;
  }

  /**
   * Returns the metric for the given ID.
   *
   * @param id		the id to get the metric for
   * @return		the metric
   */
  public double getMetric(int id) {
    try {
      switch (id) {
        case DefaultEvaluationMetrics.EVALUATION_CC:
          return m_Evaluation.correlationCoefficient();
        case DefaultEvaluationMetrics.EVALUATION_MATTHEWS_CC:
          return m_Evaluation.matthewsCorrelationCoefficient(0);
        case DefaultEvaluationMetrics.EVALUATION_RMSE:
          return m_Evaluation.rootMeanSquaredError();
        case DefaultEvaluationMetrics.EVALUATION_RRSE:
          return m_Evaluation.rootRelativeSquaredError();
        case DefaultEvaluationMetrics.EVALUATION_MAE:
          return m_Evaluation.meanAbsoluteError();
        case DefaultEvaluationMetrics.EVALUATION_RAE:
          return m_Evaluation.relativeAbsoluteError();
        case DefaultEvaluationMetrics.EVALUATION_COMBINED:
          return (1 - StrictMath.abs(m_Evaluation.correlationCoefficient()) + m_Evaluation.rootRelativeSquaredError() + m_Evaluation.relativeAbsoluteError());
        case DefaultEvaluationMetrics.EVALUATION_ACC:
          return m_Evaluation.pctCorrect();
        case DefaultEvaluationMetrics.EVALUATION_KAPPA:
          return m_Evaluation.kappa();
        case DefaultEvaluationMetrics.EVALUATION_PRECISION:
          return m_Evaluation.precision(0);
        case DefaultEvaluationMetrics.EVALUATION_WEIGHTED_PRECISION:
          return m_Evaluation.weightedPrecision();
        case DefaultEvaluationMetrics.EVALUATION_RECALL:
          return m_Evaluation.recall(0);
        case DefaultEvaluationMetrics.EVALUATION_WEIGHTED_RECALL:
          return m_Evaluation.weightedRecall();
        case DefaultEvaluationMetrics.EVALUATION_AUC:
          return m_Evaluation.areaUnderROC(0);
        case DefaultEvaluationMetrics.EVALUATION_WEIGHTED_AUC:
          return m_Evaluation.weightedAreaUnderROC();
        case DefaultEvaluationMetrics.EVALUATION_PRC:
          return m_Evaluation.areaUnderPRC(0);
        case DefaultEvaluationMetrics.EVALUATION_WEIGHTED_PRC:
          return m_Evaluation.weightedAreaUnderPRC();
        case DefaultEvaluationMetrics.EVALUATION_FMEASURE:
          return m_Evaluation.fMeasure(0);
        case DefaultEvaluationMetrics.EVALUATION_WEIGHTED_FMEASURE:
          return m_Evaluation.weightedFMeasure();
        case DefaultEvaluationMetrics.EVALUATION_TRUE_POSITIVE_RATE:
          return m_Evaluation.truePositiveRate(0);
        case DefaultEvaluationMetrics.EVALUATION_TRUE_NEGATIVE_RATE:
          return m_Evaluation.trueNegativeRate(0);
        case DefaultEvaluationMetrics.EVALUATION_FALSE_POSITIVE_RATE:
          return m_Evaluation.falsePositiveRate(0);
        case DefaultEvaluationMetrics.EVALUATION_FALSE_NEGATIVE_RATE:
          return m_Evaluation.falseNegativeRate(0);
        default:
          return Double.NaN;
      }
    }
    catch (Exception e) {
      return Double.NaN;
    }
  }
}
