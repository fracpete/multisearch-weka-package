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
 * DefaultEvaluationFactory.java
 * Copyright (C) 2015 University of Waikato, Hamilton, NZ
 */

package weka.classifiers.meta.multisearch;

import weka.classifiers.Evaluation;
import weka.classifiers.meta.MultiSearch;
import weka.core.Instances;
import weka.core.SetupGenerator;
import weka.core.setupgenerator.Point;

/**
 * Default factory.
 *
 * @author FracPete (fracpete at waikato dot ac dot nz)
 * @version $Revision$
 */
public class DefaultEvaluationFactory
  extends AbstractEvaluationFactory<DefaultEvaluationMetrics, DefaultEvaluationWrapper, DefaultEvaluationTask, Evaluation> {

  private static final long serialVersionUID = -7535032839072532838L;

  /**
   * Returns a new metrics instance.
   *
   * @return 		the metrics
   */
  @Override
  public DefaultEvaluationMetrics newMetrics() {
    return new DefaultEvaluationMetrics();
  }

  /**
   * Returns a new wrapper.
   *
   * @param eval	the evaluation to wrap
   * @return 		the wrapper
   */
  @Override
  public DefaultEvaluationWrapper newWrapper(Evaluation eval) {
    return new DefaultEvaluationWrapper(eval, newMetrics());
  }

  /**
   * Returns a new task.
   *
   * @param owner	the owning search
   * @param inst	the data
   * @param generator	the generator
   * @param values	the values
   * @param folds	the number of folds
   * @param eval	the evaluation
   * @return		the task
   */
  @Override
  public DefaultEvaluationTask newTask(MultiSearch owner, Instances inst, SetupGenerator generator, Point<Object> values, int folds, int eval) {
    return new DefaultEvaluationTask(owner, inst, generator, values, folds, eval);
  }
}
