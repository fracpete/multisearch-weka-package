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
 * AbstractEvaluationWrapper.java
 * Copyright (C) 2015 University of Waikato, Hamilton, NZ
 */

package weka.classifiers.meta.multisearch;

import weka.core.Tag;

import java.io.Serializable;

/**
 * Wrapper for evaluation classes.
 *
 * @author FracPete (fracpete at waikato dot ac dot nz)
 * @version $Revision$
 * @param <T> the type of evaluation to wrap
 * @param <M> the associated metrics
 */
public abstract class AbstractEvaluationWrapper<T, M extends AbstractEvaluationMetrics>
  implements Serializable {

  private static final long serialVersionUID = -4712561735709150591L;

  /** the metrics in use. */
  protected M m_Metrics;

  /**
   * Initializes the wrapper.
   *
   * @param eval	the evaluation to wrap
   * @param metrics	the metrics to use
   */
  public AbstractEvaluationWrapper(T eval, M metrics) {
    m_Metrics = metrics;
    setEvaluation(eval);
  }

  /**
   * Sets the evaluation object to use.
   *
   * @param eval	the evaluation
   */
  protected abstract void setEvaluation(T eval);

  /**
   * Returns the metrics.
   *
   * @return		the metrics
   */
  public M getMetrics() {
    return m_Metrics;
  }

  /**
   * Returns the metric for the given tag.
   *
   * @param tag		the tag to get the metric for
   * @return		the metric
   */
  public double getMetric(Tag tag) {
    return getMetric(tag.getID());
  }

  /**
   * Returns the metric for the given ID.
   *
   * @param id		the id to get the metric for
   * @return		the metric
   */
  public abstract double getMetric(int id);
}
