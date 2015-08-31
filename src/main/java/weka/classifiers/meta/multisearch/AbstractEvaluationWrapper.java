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

import java.io.Serializable;

/**
 * Wrapper for evaluation classes.
 *
 * @author FracPete (fracpete at waikato dot ac dot nz)
 * @version $Revision$
 * @param <T>	the type of evaluation to wrap
 */
public abstract class AbstractEvaluationWrapper<T>
  implements Serializable {

  private static final long serialVersionUID = -4712561735709150591L;

  /**
   * Initializes the wrapper.
   *
   * @param eval	the evaluation to wrap
   */
  public AbstractEvaluationWrapper(T eval) {
    setEvaluation(eval);
  }

  /**
   * Sets the evaluation object to use.
   *
   * @param eval	the evaluation
   */
  protected abstract void setEvaluation(T eval);

  /**
   * Returns the accuracy for nominal classes, NaN for numeric ones.
   *
   * @return		the accuracy
   */
  public abstract double accuracy();

  /**
   * Returns the kappa for nominal classes, NaN for numeric ones.
   *
   * @return		the kappa
   */
  public abstract double kappa();

  /**
   * Returns the correlation coefficient for numeric classes, NaN for nominal ones.
   *
   * @return		the correlation coefficient
   */
  public abstract double correlationCoefficient();

  /**
   * Returns the root mean squared error.
   *
   * @return		the error
   */
  public abstract double rootMeanSquaredError();

  /**
   * Returns the root relative squared error.
   *
   * @return		the error
   */
  public abstract double rootRelativeSquaredError();

  /**
   * Returns the mean absolute error.
   *
   * @return		the error
   */
  public abstract double meanAbsoluteError();

  /**
   * Returns the relative absolute error.
   *
   * @return		the error
   */
  public abstract double relativeAbsoluteError();
}
