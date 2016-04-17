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
 * AbstractSearch.java
 * Copyright (C) 2016 University of Waikato, Hamilton, NZ
 */

package weka.classifiers.meta.multisearch;

import weka.classifiers.Classifier;
import weka.classifiers.meta.MultiSearch;
import weka.core.Instances;
import weka.core.Option;
import weka.core.OptionHandler;
import weka.core.Tag;
import weka.core.Utils;
import weka.core.setupgenerator.Point;
import weka.core.setupgenerator.Space;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Map.Entry;
import java.util.Vector;

/**
 * Ancestor for search algorithms.
 *
 * @author FracPete (fracpete at waikato dot ac dot nz)
 * @version $Revision$
 */
public abstract class AbstractSearch
  implements Serializable, Cloneable, OptionHandler {

  protected List<Entry<Integer, Point<Object>>> m_Trace;

  /** the owner. */
  protected transient MultiSearch m_Owner;

  /** the debug flag. */
  protected boolean m_Debug;

  /** the parameter space. */
  protected Space m_Space;

  /** The number of setups completed so far. */
  protected int m_Completed;

  /** The number of setups that experienced a failure of some sort
   * during construction. */
  protected int m_Failed;

  /** the number of setups to evaluate. */
  protected int m_NumSetups;

  /** for storing the performances. */
  protected Vector<Performance> m_Performances;

  /** the cache for points in the space that got calculated
   * (raw points in space, not evaluated ones!). */
  protected PerformanceCache m_Cache;

  /** whether all performances in the space are the same. */
  protected boolean m_UniformPerformance = false;

  /** the best values. */
  protected Point<Object> m_Values = null;

  /**
   * Returns a string describing the object.
   *
   * @return 		a description suitable for displaying in the
   *         		explorer/experimenter gui
   */
  public abstract String globalInfo();

  /**
   * Sets the owning classifier.
   *
   * @param value	the owner
   */
  public void setOwner(MultiSearch value) {
    m_Owner = value;
  }

  /**
   * Retursn the current onwer.
   *
   * @return		the owner, null if none set
   */
  public MultiSearch getOwner() {
    return m_Owner;
  }

  /**
   * Returns a clone of itself.
   *
   * @return		a clone
   */
  public Object clone() {
    AbstractSearch result;

    try {
      result = (AbstractSearch) getClass().newInstance();
      result.setOptions(getOptions());
    }
    catch (Exception e) {
      result = null;
      e.printStackTrace();
    }

    return result;
  }

  /**
   * Gets an enumeration describing the available options.
   *
   * @return an enumeration of all the available options.
   */
  @Override
  public Enumeration listOptions() {
    Vector 		result;

    result = new Vector();

    result.addElement(new Option(
      "\tWhether to enable debugging output.\n"
	+ "\t(default off)",
      "D", 0, "-D"));

    return result.elements();
  }

  /**
   * returns the options of the current setup.
   *
   * @return		the current options
   */
  @Override
  public String[] getOptions() {
    int       		i;
    Vector<String>    	result;
    String[]  		options;

    result = new Vector<String>();

    if (getDebug())
      result.add("-D");

    return result.toArray(new String[result.size()]);
  }

  /**
   * Parses the options for this object.
   *
   * @param options	the options to use
   * @throws Exception	if setting of options fails
   */
  @Override
  public void setOptions(String[] options) throws Exception {
    setDebug(Utils.getFlag("D", options));
    Utils.checkForRemainingOptions(options);
  }

  /**
   * Set debugging mode.
   *
   * @param debug true if debug output should be printed
   */
  public void setDebug(boolean debug) {
    m_Debug = debug;
  }

  /**
   * Get whether debugging is turned on.
   *
   * @return true if debugging output is on
   */
  public boolean getDebug() {
    return m_Debug;
  }

  /**
   * Returns the tip text for this property
   *
   * @return tip text for this property suitable for displaying in the
   *         explorer/experimenter gui
   */
  public String debugTipText() {
    return "If set to true, search may output additional info to the console.";
  }

  /**
   * prints the specified message to stdout if debug is on and can also dump
   * the message to a log file.
   *
   * @param message	the message to print or store in a log file
   */
  public void log(String message) {
    m_Owner.log(message);
  }

  /**
   * prints the specified message to stdout if debug is on and can also dump
   * the message to a log file.
   *
   * @param message	the message to print or store in a log file
   * @param onlyLog	if true the message will only be put into the log file
   * 			but not to stdout
   */
  public void log(String message, boolean onlyLog) {
    m_Owner.log(message, onlyLog);
  }

  /**
   * generates a table string for all the performances in the space and returns
   * that.
   *
   * @param space		the current space to align the performances to
   * @param performances	the performances to align
   * @param type		the type of performance
   * @return			the table string
   */
  protected String logPerformances(Space space, Vector<Performance> performances, Tag type) {
    return m_Owner.logPerformances(space, performances, type);
  }

  /**
   * aligns all performances in the space and prints those tables to the log
   * file.
   *
   * @param space		the current space to align the performances to
   * @param performances	the performances to align
   */
  protected void logPerformances(Space space, Vector<Performance> performances) {
    m_Owner.logPerformances(space, performances);
  }

  /**
   * Adds the performance to the cache and the current list of performances.
   * Does nothing if at least one setup failed.
   *
   * @param performance	the performance to add
   * @param folds	the number of folds
   * @see		#m_Failed
   */
  public void addPerformance(Performance performance, int folds) {
    if (m_Failed > 0)
      return;

    m_Performances.add(performance);
    m_Cache.add(folds, performance);
  }

  /**
   * returns the parameter values that were found to work best.
   *
   * @return		the best parameter combination
   */
  public Point<Object> getValues() {
    return m_Values;
  }

  /**
   * Returns the size of m_Trace, which is technically the amount of
   * setups that where tested in order to find the best.
   */
  public int getTraceSize() {
    return m_Trace.size();
  }

  /**
   * Returns the CLI string of a given item in the trace.
   *
   * @param index the index of the trace item to obtain
   * @throws Exception
   */
  public String getTraceClassifierAsCli(int index) throws Exception {
    Classifier result = (Classifier) getOwner().getGenerator().setup(
      (Serializable) getOwner().getClassifier(), m_Trace.get(index).getValue());
    return getCommandline(result);
  }

  /**
   * Returns the performance score of a given item in the trace.
   *
   * @param index the index of the trace item to obtain
   * @throws Exception
   */
  public Double getTraceValue(int index) throws Exception {
    Entry<Integer, Point<Object>> currentItem = m_Trace.get(index);
    if (m_Cache.isCached(currentItem.getKey(), currentItem.getValue())) {
      Performance performance = m_Cache.get(currentItem.getKey(), currentItem.getValue());
      return performance.getPerformance();
    }
    else {
      throw new Exception("Setup not found in cache. ");
    }
  }

  /**
   * Returns the commandline of the given object.
   *
   * @param obj		the object to create the commandline for
   * @return		the commandline
   */
  public String getCommandline(Object obj) {
    return m_Owner.getCommandline(obj);
  }

  /**
   * Hook method for performing checks.
   * <br>
   * Default implementation only ensures that owner and data are present.
   *
   * @param data	the data to use
   * @throws Exception	if check fails
   */
  protected void check(Instances data) throws Exception {
    if (m_Owner == null)
      throw new IllegalStateException("No owner set!");
    if (data == null)
      throw new IllegalStateException("No data provided!");
  }

  /**
   * Called before the search is executed.
   * <br>
   * Default implementation only initializes the trace.
   *
   * @param data	the dataset to use
   * @throws Exception	if search fails
   */
  public void preSearch(Instances data) throws Exception {
    m_Cache        = new PerformanceCache();
    m_Performances = new Vector<Performance>();
    m_Trace        = new ArrayList<Entry<Integer,Point<Object>>>();

    m_Owner.getGenerator().reset();
    m_Space = m_Owner.getGenerator().getSpace();
  }

  /**
   * Performs the actual search and returns the best setup.
   *
   * @param data	the dataset to use
   * @return		the best classifier setup
   * @throws Exception	if search fails
   */
  public abstract Classifier doSearch(Instances data) throws Exception;

  /**
   * Called after the search has been executed.
   * <br>
   * Default implementation does nothing.
   *
   * @param data	the dataset to use
   * @param best	the best classifier setup
   * @return		the best classifier setup
   * @throws Exception	if search fails
   */
  public Classifier postSearch(Instances data, Classifier best) throws Exception {
    return best;
  }

  /**
   * Performs the search and returns the best setup.
   *
   * @param data	the dataset to use
   * @return		the best classifier setup
   * @throws Exception	if search fails
   */
  public Classifier search(Instances data) throws Exception {
    Classifier 	best;

    log("\n"
      + getClass().getName() + "\n"
      + getClass().getName().replaceAll(".", "=") + "\n"
      + "Options: " + Utils.joinOptions(getOptions()) + "\n");

    log("\n---> check");
    check(data);

    log("\n---> preSearch");
    preSearch(data);

    log("\n---> doSearch");
    best = doSearch(data);

    log("\n---> postSearch");
    return postSearch(data, best);
  }
}
