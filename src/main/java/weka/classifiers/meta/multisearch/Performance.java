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
 * Performance.java
 * Copyright (C) 2008-2015 University of Waikato, Hamilton, New Zealand
 */

package weka.classifiers.meta.multisearch;

import weka.core.Tag;
import weka.core.setupgenerator.Point;

import java.io.Serializable;
import java.util.HashMap;

/**
 * A helper class for storing the performance of values in the parameter
 * space. Can be sorted with the PerformanceComparator class.
 *
 * @author  fracpete (fracpete at waikato dot ac dot nz)
 * @version $Revision: 9515 $
 * @see PerformanceComparator
 */
public class Performance
  implements Serializable {

  /** for serialization. */
  private static final long serialVersionUID = -4374706475277588755L;

  /** the values the filter/classifier were built with. */
  protected Point<Object> m_Values;

  /** the evaluation type. */
  protected int m_Evaluation;

  /** the metrics. */
  protected AbstractEvaluationMetrics m_Metrics;

  /** stores the metric values. */
  protected HashMap<Integer,Double> m_MetricValues;

  /**
   * Initializes the performance container. If the Evaluation object is null,
   * then the worst possible values for the measures are assumed (in order to
   * assure a low ranking).
   *
   * @param values		the values
   * @param evaluation	the evaluation to extract the performance
   * 				measures from, can be null
   * @param evalType		the type of evaluation
   * @throws Exception	if retrieving of measures fails
   */
  public Performance(Point<Object> values, AbstractEvaluationWrapper evaluation, int evalType) throws Exception {
    super();

    m_Values       = values;
    m_Evaluation   = evalType;
    m_MetricValues = new HashMap<Integer, Double>();
    m_Metrics      = null;
    if (evaluation != null) {
      m_Metrics = evaluation.getMetrics();
      for (Tag tag : evaluation.getMetrics().getTags()) {
        m_MetricValues.put(tag.getID(), evaluation.getMetric(tag));
      }
    }
  }

  /**
   * Returns the evaluation type.
   *
   * @return		the type of evaluation
   */
  public int getEvaluation() {
    return m_Evaluation;
  }

  /**
   * returns the performance measure.
   *
   * @return 			the performance measure
   */
  public double getPerformance() {
    return getPerformance(m_Evaluation);
  }

  /**
   * returns the performance measure.
   *
   * @param evaluation	the type of evaluation to return
   * @param value 	the performance measure
   */
  public void setPerformance(int evaluation, double value) {
    if ((m_Metrics != null) && !m_Metrics.check(evaluation))
      return;
    m_MetricValues.put(evaluation, value);
  }

  /**
   * returns the performance measure.
   *
   * @param evaluation	the type of evaluation to return
   * @return 			the performance measure
   */
  public double getPerformance(int evaluation) {
    if ((m_Metrics != null) && !m_Metrics.check(evaluation))
      return Double.NaN;

    if (!m_MetricValues.containsKey(evaluation))
      return Double.NaN;

    return m_MetricValues.get(evaluation);
  }

  /**
   * returns the values for this performance.
   *
   * @return the values
   */
  public Point<Object> getValues() {
    return m_Values;
  }

  /**
   * returns a string representation of this performance object.
   *
   * @return a string representation
   */
  @Override
  public String toString() {
    String	result;
    String	evalStr;

    evalStr = null;
    if (m_Metrics != null) {
      for (Tag tag: m_Metrics.getTags()) {
	if (tag.getID() == m_Evaluation) {
	  evalStr = tag.getIDStr();
	  break;
	}
      }
    }
    if (evalStr == null)
      evalStr = "" + m_Evaluation;

    result = "Performance (" + getValues() + "): ";
    result +=   getPerformance()
              + " (" + evalStr + ")";

    return result;
  }
}