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
 * AbstractEvaluationTask.java
 * Copyright (C) 2015 University of Waikato, Hamilton, NZ
 */

package weka.classifiers.meta.multisearch;

import weka.classifiers.meta.MultiSearch;
import weka.core.Instances;
import weka.core.SetupGenerator;
import weka.core.setupgenerator.Point;

/**
 * Helper class for evaluating a setup.
 */
public abstract class AbstractEvaluationTask
  implements Runnable {

  /** the owner. */
  protected MultiSearch m_Owner;

  /** the data to use for training. */
  protected Instances m_Data;

  /** the setup generator to use. */
  protected SetupGenerator m_Generator;

  /** the setup. */
  protected Point<Object> m_Values;

  /** the number of folds for cross-validation. */
  protected int m_Folds;

  /** the type of evaluation. */
  protected int m_Evaluation;

  /**
   * Initializes the task.
   *
   * @param owner		the owning MultiSearch classifier
   * @param inst		the data
   * @param generator		the generator to use
   * @param values		the setup values
   * @param folds		the number of cross-validation folds
   * @param eval		the type of evaluation
   */
  public AbstractEvaluationTask(
    MultiSearch owner, Instances inst,
    SetupGenerator generator, Point<Object> values, int folds, int eval) {

    super();

    m_Owner      = owner;
    m_Data       = inst;
    m_Generator  = generator;
    m_Values     = values;
    m_Folds      = folds;
    m_Evaluation = eval;
  }

  /**
   * Performs the evaluation.
   *
   * @throws Exception	if evaluation fails
   */
  protected abstract void doRun() throws Exception;

  /**
   * Performs the evaluation.
   */
  public void run() {
    try {
      doRun();
    }
    catch (Exception e) {
      System.err.println("Encountered exception while evaluating classifier, skipping!");
      System.err.println("- Values: " + m_Values);
      e.printStackTrace();
      m_Owner.completedEvaluation(m_Values, false);
    }

    cleanUp();
  }

  /**
   * Cleans up after the task finishes.
   */
  public void cleanUp() {
    // clean up
    m_Owner     = null;
    m_Data      = null;
    m_Generator = null;
    m_Values    = null;
  }
}
