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
 * AbstractEvaluationTask.java
 * Copyright (C) 2015-2017 University of Waikato, Hamilton, NZ
 */

package weka.classifiers.meta.multisearch;

import weka.core.Instances;
import weka.core.SetupGenerator;
import weka.core.setupgenerator.Point;

import java.util.concurrent.Callable;

/**
 * Helper class for evaluating a setup.
 */
public abstract class AbstractEvaluationTask
  implements Callable {

  /** the owner. */
  protected MultiSearchCapable m_Owner;

  /** the data to use for training. */
  protected Instances m_Train;

  /** the test set to use (cross-validation if null). */
  protected Instances m_Test;

  /** the setup generator to use. */
  protected SetupGenerator m_Generator;

  /** the setup. */
  protected Point<Object> m_Values;

  /** the number of folds for cross-validation. */
  protected int m_Folds;

  /** the type of evaluation. */
  protected int m_Evaluation;

  /** the class label index (0-based). */
  protected int m_ClassLabel;

  /**
   * Initializes the task.
   *
   * @param owner		the owning MultiSearch classifier
   * @param train		the training data
   * @param test		the test data, can be null
   * @param generator		the generator to use
   * @param values		the setup values
   * @param folds		the number of cross-validation folds
   * @param eval		the type of evaluation
   * @param classLabel		the class label index (0-based; if applicable)
   */
  public AbstractEvaluationTask(
    MultiSearchCapable owner, Instances train, Instances test,
    SetupGenerator generator, Point<Object> values, int folds, int eval, int classLabel) {

    super();

    m_Owner      = owner;
    m_Train      = train;
    m_Test       = test;
    m_Generator  = generator;
    m_Values     = values;
    m_Folds      = folds;
    m_Evaluation = eval;
    m_ClassLabel = classLabel;

    if (m_Test != null) {
      String msg = m_Train.equalHeadersMsg(m_Test);
      if (msg != null)
	throw new IllegalArgumentException(msg);
    }
  }

  /**
   * Performs the evaluation.
   *
   * @return false if evaluation fails
   */
  protected abstract Boolean doRun() throws Exception;

  /**
   * Performs the evaluation.
   */
  public Boolean call() throws Exception {

    Boolean result = doRun();
    cleanUp();
    return result;
  }

  /**
   * Cleans up after the task finishes.
   */
  public void cleanUp() {
    // clean up
    m_Owner     = null;
    m_Train     = null;
    m_Test      = null;
    m_Generator = null;
    m_Values    = null;
  }
}
