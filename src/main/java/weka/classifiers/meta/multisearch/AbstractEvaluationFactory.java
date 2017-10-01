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
 * AbstractEvaluationFactory.java
 * Copyright (C) 2015-2016 University of Waikato, Hamilton, NZ
 */

package weka.classifiers.meta.multisearch;

import weka.core.Instances;
import weka.core.SetupGenerator;
import weka.core.setupgenerator.Point;

import java.io.Serializable;

/**
 * Factory for creating evaluation-related objects.
 *
 * @author FracPete (fracpete at waikato dot ac dot nz)
 * @version $Revision$
 */
public abstract class AbstractEvaluationFactory<M extends AbstractEvaluationMetrics, W extends AbstractEvaluationWrapper, T extends AbstractEvaluationTask, E>
  implements Serializable {

  private static final long serialVersionUID = -2165415795400169938L;

  /**
   * Returns a new metrics instance.
   *
   * @return 		the metrics
   */
  public abstract M newMetrics();

  /**
   * Returns a new wrapper.
   *
   * @param eval	the evaluation to wrap
   * @return 		the wrapper
   */
  public abstract W newWrapper(E eval);

  /**
   * Returns a new task.
   *
   * @param owner	the owning search
   * @param train	the training data
   * @param test	the test data
   * @param generator	the generator
   * @param values	the values
   * @param folds	the number of folds
   * @param eval	the evaluation
   * @param classLabel	the class label index (0-based; if applicable)
   * @return		the task
   */
  public abstract T newTask(MultiSearchCapable owner, Instances train, Instances test, SetupGenerator generator, Point<Object> values, int folds, int eval, int classLabel);
}
