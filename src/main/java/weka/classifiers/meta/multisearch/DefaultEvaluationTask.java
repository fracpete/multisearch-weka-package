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
 * DefaultEvaluationTask.java
 * Copyright (C) 2015-2016 University of Waikato, Hamilton, NZ
 */

package weka.classifiers.meta.multisearch;

import weka.classifiers.Classifier;
import weka.classifiers.Evaluation;
import weka.classifiers.meta.MultiSearch;
import weka.core.Instances;
import weka.core.SetupGenerator;
import weka.core.setupgenerator.Point;

import java.io.Serializable;
import java.util.Random;

/**
 * Default Evaluation task.
 */
public class DefaultEvaluationTask
  extends AbstractEvaluationTask {

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
   */
  public DefaultEvaluationTask(
    MultiSearch owner, Instances train, Instances test,
    SetupGenerator generator, Point<Object> values, int folds, int eval) {
    super(owner, train, test, generator, values, folds, eval);
  }

  /**
   * Returns whether predictions can be discarded (depends on selected measure).
   */
  protected boolean canDiscardPredictions() {
    switch (m_Owner.getEvaluation().getSelectedTag().getID()) {
      case DefaultEvaluationMetrics.EVALUATION_AUC:
      case DefaultEvaluationMetrics.EVALUATION_PRC:
        return false;
      default:
        return true;
    }
  }

  /**
   * Performs the evaluation.
   *
   * @throws Exception	if evaluation fails
   */
  protected void doRun() throws Exception {
    Point<Object>	evals;
    Evaluation 		eval;
    Classifier 		classifier;
    Performance		performance;
    boolean		completed;

    // setup
    evals      = m_Generator.evaluate(m_Values);
    classifier = (Classifier) m_Generator.setup((Serializable) m_Owner.getClassifier(), evals);

    // evaluate
    try {
      eval = new Evaluation(m_Train);
      eval.setDiscardPredictions(canDiscardPredictions());
      if (m_Test == null) {
        if (m_Folds >= 2) {
          eval.crossValidateModel(classifier, m_Train, m_Folds, new Random(m_Owner.getSeed()));
        }
        else {
          classifier.buildClassifier(m_Train);
          eval.evaluateModel(classifier, m_Train);
        }
      }
      else {
        classifier.buildClassifier(m_Train);
        eval.evaluateModel(classifier, m_Test);
      }
      completed = true;
    }
    catch (Exception e) {
      eval = null;
      System.err.println("Encountered exception while evaluating classifier, skipping!");
      System.err.println("- Classifier: " + m_Owner.getCommandline(classifier));
      e.printStackTrace();
      completed = false;
    }

    // store performance
    performance = new Performance(
      m_Values,
      m_Owner.getFactory().newWrapper(eval),
      m_Evaluation,
      (Classifier) m_Generator.setup((Serializable) m_Owner.getClassifier(), evals));
    m_Owner.getAlgorithm().addPerformance(performance, m_Folds);

    // log
    m_Owner.log(performance + ": cached=false");

    // release slot
    if (m_Owner.getAlgorithm() instanceof AbstractMultiThreadedSearch)
      ((AbstractMultiThreadedSearch) m_Owner.getAlgorithm()).completedEvaluation(classifier, completed);
  }
}
