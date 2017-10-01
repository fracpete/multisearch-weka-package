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
 * MultiSearchCapable.java
 * Copyright (C) 2017 University of Waikato, Hamilton, NZ
 */

package weka.classifiers.meta.multisearch;

import weka.classifiers.Classifier;
import weka.core.SelectedTag;
import weka.core.SetupGenerator;
import weka.core.Tag;
import weka.core.setupgenerator.Point;
import weka.core.setupgenerator.Space;

import java.util.Vector;

/**
 * Interface for multi-search capable classifiers..
 *
 * @author FracPete (fracpete at waikato dot ac dot nz)
 */
public interface MultiSearchCapable
  extends Classifier {

  /**
   * prints the specified message to stdout if debug is on and can also dump
   * the message to a log file.
   *
   * @param message the message to print or store in a log file
   */
  public void log(String message);

  /**
   * prints the specified message to stdout if debug is on and can also dump
   * the message to a log file.
   *
   * @param message the message to print or store in a log file
   * @param onlyLog if true the message will only be put into the log file
   *                but not to stdout
   */
  public void log(String message, boolean onlyLog);

  /**
   * generates a table string for all the performances in the space and returns
   * that.
   *
   * @param space        the current space to align the performances to
   * @param performances the performances to align
   * @param type         the type of performance
   * @return the table string
   */
  public String logPerformances(Space space, Vector<Performance> performances, Tag type);

  /**
   * aligns all performances in the space and prints those tables to the log
   * file.
   *
   * @param space        the current space to align the performances to
   * @param performances the performances to align
   */
  public void logPerformances(Space space, Vector<Performance> performances);

  /**
   * Returns the commandline of the given object.
   *
   * @param obj the object to create the commandline for
   * @return the commandline
   */
  public String getCommandline(Object obj);

  /**
   * Returns the factory instance.
   *
   * @return the factory
   */
  public AbstractEvaluationFactory getFactory();

  /**
   * Returns the evaluation metrics.
   *
   * @return the metrics
   */
  public AbstractEvaluationMetrics getMetrics();

  /**
   * Returns the search algorithm.
   *
   * @return		the algorithm
   */
  public AbstractSearch getAlgorithm();

  /**
   * returns the parameter values that were found to work best.
   *
   * @return the best parameter combination
   */
  public Point<Object> getBestValues();

  /**
   * returns the points that were found to work best.
   *
   * @return the best points
   */
  public Point<Object> getBestCoordinates();

  /**
   * returns the best Classifier setup.
   *
   * @return the best Classifier setup
   */
  public Classifier getBestClassifier();

  /**
   * returns the Classifier setup.
   *
   * @return the Classifier setup
   */
  public Classifier getClassifier();

  /**
   * Returns the setup generator.
   *
   * @return the generator
   */
  public SetupGenerator getGenerator();

  /**
   * Returns the integer index.
   *
   * @param upper the maximum to use
   * @return the index (0-based)
   */
  public int getClassLabelIndex(int upper);

  /**
   * Gets the criterion used for evaluating the classifier performance.
   *
   * @return 		the current evaluation criterion.
   */
  public SelectedTag getEvaluation();

  /**
   * Gets the seed for the random number generations
   *
   * @return the seed for the random number generation
   */
  public int getSeed();
}
