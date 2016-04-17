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
 * AbstractMultiThreadedSearch.java
 * Copyright (C) 2016 University of Waikato, Hamilton, NZ
 */

package weka.classifiers.meta.multisearch;

import weka.classifiers.Classifier;
import weka.core.Instances;
import weka.core.Option;
import weka.core.Utils;

import java.util.Enumeration;
import java.util.Vector;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Ancestor for multi-threaded searches.
 *
 * @author FracPete (fracpete at waikato dot ac dot nz)
 * @version $Revision$
 */
public abstract class AbstractMultiThreadedSearch
  extends AbstractSearch {

  /** The number of threads to have executing at any one time. */
  protected int m_NumExecutionSlots = 1;

  /** Pool of threads to train models with. */
  protected transient ThreadPoolExecutor m_ExecutorPool;

  /**
   * Gets an enumeration describing the available options.
   *
   * @return an enumeration of all the available options.
   */
  @Override
  public Enumeration listOptions() {
    Vector 		result;
    Enumeration   	en;

    result = new Vector();

    result.addElement(new Option(
      "\tNumber of execution slots.\n"
	+ "\t(default 1 - i.e. no parallelism)",
      "num-slots", 1, "-num-slots <num>"));

    en = super.listOptions();
    while (en.hasMoreElements())
      result.addElement(en.nextElement());

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

    result.add("-num-slots");
    result.add("" + getNumExecutionSlots());

    options = super.getOptions();
    for (i = 0; i < options.length; i++)
      result.add(options[i]);

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
    String		tmpStr;

    tmpStr = Utils.getOption("num-slots", options);
    if (tmpStr.length() != 0)
      setNumExecutionSlots(Integer.parseInt(tmpStr));
    else
      setNumExecutionSlots(1);

    super.setOptions(options);
  }

  /**
   * Set the number of execution slots (threads) to use for building the
   * members of the ensemble.
   *
   * @param value 	the number of slots to use.
   */
  public void setNumExecutionSlots(int value) {
    if (value >= 1)
      m_NumExecutionSlots = value;
  }

  /**
   * Get the number of execution slots (threads) to use for building
   * the members of the ensemble.
   *
   * @return 		the number of slots to use
   */
  public int getNumExecutionSlots() {
    return m_NumExecutionSlots;
  }

  /**
   * Returns the tip text for this property.
   *
   * @return 		tip text for this property suitable for
   * 			displaying in the explorer/experimenter gui
   */
  public String numExecutionSlotsTipText() {
    return "The number of execution slots (threads) to use for " +
      "constructing the ensemble.";
  }

  /**
   * Start the pool of execution threads.
   */
  protected void startExecutorPool() {
    stopExecutorPool();

    log("Starting thread pool with " + m_NumExecutionSlots + " slots...");

    m_ExecutorPool = new ThreadPoolExecutor(
      m_NumExecutionSlots, m_NumExecutionSlots,
      120, TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>());
  }

  /**
   * Stops the ppol of execution threads.
   */
  protected void stopExecutorPool() {
    log("Shutting down thread pool...");

    if (m_ExecutorPool != null)
      m_ExecutorPool.shutdownNow();

    m_ExecutorPool = null;
  }

  /**
   * Helper method used for blocking.
   *
   * @param doBlock	whether to block or not
   */
  protected synchronized void block(boolean doBlock) {
    if (doBlock) {
      try {
	wait();
      }
      catch (InterruptedException ex) {
	// ignored
      }
    }
    else {
      notifyAll();
    }
  }

  /**
   * Records the completion of the training of a single classifier. Unblocks if
   * all classifiers have been trained.
   *
   * @param obj		the classifier or setup values that was attempted to train
   * @param success 	whether the classifier trained successfully
   */
  public synchronized void completedEvaluation(Object obj, boolean success) {
    if (!success) {
      m_Failed++;
      if (m_Debug) {
	if (obj instanceof Classifier)
	  System.err.println("Training failed: " + getCommandline(obj));
	else
	  System.err.println("Training failed: " + obj);
      }
    }
    else {
      m_Completed++;
    }

    if (m_Completed + m_Failed == m_NumSetups) {
      if (m_Failed > 0) {
	if (m_Debug)
	  System.err.println("Problem building classifiers - some failed to be trained.");
      }
      block(false);
    }
  }

  /**
   * Called before the search is executed.
   * <br>
   * Starts the thread pool.
   *
   * @param data	the dataset to use
   * @throws Exception	if search fails
   */
  @Override
  public void preSearch(Instances data) throws Exception {
    super.preSearch(data);
    startExecutorPool();
  }

  /**
   * Called after the search has been executed.
   * <br>
   * Stops the thread pool.
   *
   * @param data	the dataset to use
   * @param best	the best classifier setup
   * @return		the best classifier setup
   * @throws Exception	if search fails
   */
  @Override
  public Classifier postSearch(Instances data, Classifier best) throws Exception {
    Classifier	result;

    result = super.postSearch(data, best);
    stopExecutorPool();

    return result;
  }
}
