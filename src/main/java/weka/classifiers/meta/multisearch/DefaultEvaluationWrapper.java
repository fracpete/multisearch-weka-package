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
 * Copyright (C) 2015 University of Waikato, Hamilton, NZ
 */

package weka.classifiers.meta.multisearch;

import weka.classifiers.Evaluation;

/**
 * Wrapper for the Evaluation class.
 *
 * @author FracPete (fracpete at waikato dot ac dot nz)
 * @version $Revision$
 */
public class DefaultEvaluationWrapper
  extends AbstractEvaluationWrapper<Evaluation> {

  private static final long serialVersionUID = 931329614934902835L;

  /** the evaluation object. */
  protected Evaluation m_Evaluation;

  /**
   * Initializes the wrapper.
   *
   * @param eval the evaluation to wrap
   */
  public DefaultEvaluationWrapper(Evaluation eval) {
    super(eval);
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
   * Returns the accuracy for nominal classes, NaN for numeric ones.
   *
   * @return		the accuracy
   */
  @Override
  public double accuracy() {
    try {
      return m_Evaluation.pctCorrect();
    }
    catch (Exception e) {
      return Double.NaN;
    }
  }

  /**
   * Returns the kappa for nominal classes, NaN for numeric ones.
   *
   * @return		the kappa
   */
  @Override
  public double kappa() {
    try {
      return m_Evaluation.kappa();
    }
    catch (Exception e) {
      return Double.NaN;
    }
  }

  /**
   * Returns the correlation coefficient for numeric classes, NaN for nominal ones.
   *
   * @return		the correlation coefficient
   */
  @Override
  public double correlationCoefficient() {
    try {
      return m_Evaluation.correlationCoefficient();
    }
    catch (Exception e) {
      return Double.NaN;
    }
  }

  /**
   * Returns the root mean squared error.
   *
   * @return		the error
   */
  @Override
  public double rootMeanSquaredError() {
    return m_Evaluation.rootMeanSquaredError();
  }

  /**
   * Returns the root relative squared error.
   *
   * @return		the error
   */
  @Override
  public double rootRelativeSquaredError() {
    return m_Evaluation.rootRelativeSquaredError();
  }

  /**
   * Returns the mean absolute error.
   *
   * @return		the error
   */
  @Override
  public double meanAbsoluteError() {
    return m_Evaluation.meanAbsoluteError();
  }

  /**
   * Returns the relative absolute error.
   *
   * @return		the error
   */
  @Override
  public double relativeAbsoluteError() {
    try {
      return m_Evaluation.relativeAbsoluteError();
    }
    catch (Exception e) {
      return Double.NaN;
    }
  }
}
