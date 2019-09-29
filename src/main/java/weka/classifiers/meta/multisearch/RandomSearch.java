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
 * RandomSearch.java
 * Copyright (C) 2016 Leiden University, NL
 * Copyright (C) 2018-2019 University of Waikato, Hamilton, NZ
 */

package weka.classifiers.meta.multisearch;

import weka.classifiers.Classifier;
import weka.core.Instances;
import weka.core.Option;
import weka.core.Utils;
import weka.core.converters.ConverterUtils.DataSource;
import weka.core.setupgenerator.Point;
import weka.core.setupgenerator.Space;
import weka.filters.Filter;
import weka.filters.unsupervised.instance.Resample;

import java.io.File;
import java.io.Serializable;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;
import java.util.Random;
import java.util.Vector;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;

/**
 <!-- globalinfo-start -->
 * Performs a search of an arbitrary number of parameters of a classifier and chooses the best pair found for the actual filtering and training.<br>
 * <br><br>
 <!-- globalinfo-end -->
 *
 <!-- options-start -->
 * Valid options are: <p>
 *
 * <pre> -sample-size &lt;num&gt;
 *  The size (in percent) of the sample to search the inital space with.
 *  (default: 100)</pre>
 *
 * <pre> -num-folds &lt;num&gt;
 *  The number of cross-validation folds for the search space.
 *  Numbers smaller than 2 turn off cross-validation and
 *  just perform evaluation on the training set.
 *  (default: 2)</pre>
 *
 * <pre> -test-set &lt;filename&gt;
 *  The (optional) test set to use for the search space.
 *  Gets ignored if pointing to a file. Overrides cross-validation.
 *  (default: .)</pre>
 *
 * <pre> -num-iterations &lt;num&gt;
 *  The number parameter settings that are tried (i.e., number of points in the search space are checked).
 *  (default: 100)</pre>
 *
 * <pre> -S &lt;num&gt;
 *  The random seed</pre>
 *
 * <pre> -num-slots &lt;num&gt;
 *  Number of execution slots.
 *  (default 1 - i.e. no parallelism)</pre>
 *
 * <pre> -D
 *  Whether to enable debugging output.
 *  (default off)</pre>
 *
 <!-- options-end -->
 *
 * General notes:
 * <ul>
 *   <li>Turn the <i>debug</i> flag on in order to see some progress output in the
 *       console</li>
 * </ul>
 *
 * @author FracPete (fracpete at waikato dot ac dot nz)
 */
public class RandomSearch
  extends AbstractMultiThreadedSearch {

  /** for serialization. */
  private static final long serialVersionUID = 2542453917013899104L;

  /** the sample size to search with. */
  protected double m_SampleSize = 100;

  /** number of cross-validation folds for each point in space. */
  protected int m_SearchSpaceNumFolds = 2;

  /**
   * the optional test set to use for the evaluation (overrides
   * cross-validation, ignored if dir).
   */
  protected File m_SearchSpaceTestSet = new File(".");

  /** the optional test set to use for the evaluation. */
  protected Instances m_SearchSpaceTestInst;

  /** maximum number of iterations to find optimum. */
  protected int m_NumIterations = 100;

  /** the random seed */
  protected int m_RandomSeed = 1;

  @Override
  public String globalInfo() {
    return "Performs a search of an arbitrary number of parameters of a classifier "
      + "and chooses the best pair found for the actual filtering and training.\n";
  }

  /**
   * Gets an enumeration describing the available options.
   *
   * @return an enumeration of all the available options.
   */
  @Override
  public Enumeration listOptions() {
    Vector result = new Vector();
    Enumeration en;

    result.addElement(new Option(
      "\tThe size (in percent) of the sample to search the inital space with.\n"
	+ "\t(default: 100)", "sample-size", 1,
      "-sample-size <num>"));

    result.addElement(new Option(
      "\tThe number of cross-validation folds for the search space.\n"
	+ "\tNumbers smaller than 2 turn off cross-validation and\n"
	+ "\tjust perform evaluation on the training set.\n"
	+ "\t(default: 2)", "num-folds", 1, "-num-folds <num>"));

    result.addElement(new Option(
      "\tThe (optional) test set to use for the search space.\n"
	+ "\tGets ignored if pointing to a file. Overrides cross-validation.\n"
	+ "\t(default: .)", "test-set", 1,
      "-test-set <filename>"));

    result.addElement(new Option(
      "\tThe number parameter settings that are tried "
	+ "(i.e., number of points in the search space are checked).\n"
	+ "\t(default: 100)", "num-iterations", 1,
      "-num-iterations <num>"));

    result.addElement(new Option("\tThe random seed", "seed", 1, "-S <num>"));

    en = super.listOptions();
    while (en.hasMoreElements())
      result.addElement(en.nextElement());

    return result.elements();
  }

  /**
   * returns the options of the current setup.
   *
   * @return the current options
   */
  @Override
  public String[] getOptions() {
    int i;
    Vector<String> result;
    String[] options;

    result = new Vector<String>();

    result.add("-sample-size");
    result.add("" + getSampleSizePercent());

    result.add("-num-folds");
    result.add("" + getSearchSpaceNumFolds());

    result.add("-test-set");
    result.add("" + getSearchSpaceTestSet());

    result.add("-num-iterations");
    result.add("" + getNumIterations());

    result.add("-S");
    result.add("" + getRandomSeed());

    options = super.getOptions();
    for (i = 0; i < options.length; i++)
      result.add(options[i]);

    return result.toArray(new String[result.size()]);
  }

  /**
   * Parses the options for this object.
   *
   * @param options
   *            the options to use
   * @throws Exception
   *             if setting of options fails
   */
  @Override
  public void setOptions(String[] options) throws Exception {
    String tmpStr;

    tmpStr = Utils.getOption("sample-size", options);
    if (tmpStr.length() != 0)
      setSampleSizePercent(Double.parseDouble(tmpStr));
    else
      setSampleSizePercent(100);

    tmpStr = Utils.getOption("num-folds", options);
    if (tmpStr.length() != 0)
      setSearchSpaceNumFolds(Integer.parseInt(tmpStr));
    else
      setSearchSpaceNumFolds(2);

    tmpStr = Utils.getOption("test-set", options);
    if (tmpStr.length() != 0)
      setSearchSpaceTestSet(new File(tmpStr));
    else
      setSearchSpaceTestSet(new File(System.getProperty("user.dir")));

    tmpStr = Utils.getOption("num-iterations", options);
    if (tmpStr.length() != 0)
      setNumIterations(Integer.parseInt(tmpStr));
    else
      setNumIterations(100);

    tmpStr = Utils.getOption("S", options);
    if (tmpStr.length() != 0)
      setRandomSeed(Integer.parseInt(tmpStr));
    else
      setRandomSeed(1);

    super.setOptions(options);
  }

  /**
   * Returns the tip text for this property.
   *
   * @return tip text for this property suitable for displaying in the
   *         explorer/experimenter gui
   */
  public String sampleSizePercentTipText() {
    return "The sample size (in percent) to use in the search.";
  }

  /**
   * Gets the sample size for the search space search.
   *
   * @return the sample size.
   */
  public double getSampleSizePercent() {
    return m_SampleSize;
  }

  /**
   * Sets the sample size for the search space search.
   *
   * @param value
   *            the sample size for the search space search.
   */
  public void setSampleSizePercent(double value) {
    m_SampleSize = value;
  }

  /**
   * Returns the tip text for this property.
   *
   * @return tip text for this property suitable for displaying in the
   *         explorer/experimenter gui
   */
  public String searchSpaceNumFoldsTipText() {
    return "The number of cross-validation folds when evaluating the search "
      + "space; values smaller than 2 turn cross-validation off and simple "
      + "evaluation on the training set is performed.";
  }

  /**
   * Gets the number of CV folds for the search space.
   *
   * @return the number of folds.
   */
  public int getSearchSpaceNumFolds() {
    return m_SearchSpaceNumFolds;
  }

  /**
   * Sets the number of CV folds for the search space.
   *
   * @param value
   *            the number of folds.
   */
  public void setSearchSpaceNumFolds(int value) {
    m_SearchSpaceNumFolds = value;
  }

  /**
   * Returns the tip text for this property.
   *
   * @return tip text for this property suitable for displaying in the
   *         explorer/experimenter gui
   */
  public String searchSpaceTestSetTipText() {
    return "The (optional) test set to use for evaluating the search space; "
      + "overrides cross-validation; gets ignored if pointing to a directory.";
  }

  /**
   * Gets the test set to use for the search space.
   *
   * @return the number of folds.
   */
  public File getSearchSpaceTestSet() {
    return m_SearchSpaceTestSet;
  }

  /**
   * Sets the test set to use folds for the search space.
   *
   * @param value
   *            the test set, ignored if dir.
   */
  public void setSearchSpaceTestSet(File value) {
    m_SearchSpaceTestSet = value;
  }

  /**
   * Returns the tip text for this property.
   *
   * @return tip text for this property suitable for displaying in the
   *         explorer/experimenter gui
   */
  public String numIterationsTipText() {
    return "The number parameter settings that are tried; ";
  }

  /**
   * Gets the number of iterations.
   *
   * @return the number of folds.
   */
  public int getNumIterations() {
    return m_NumIterations;
  }

  /**
   * Sets the number of iterations.
   *
   * @param value
   *            the test set, ignored if dir.
   */
  public void setNumIterations(int value) {
    m_NumIterations = value;
  }

  /**
   * Returns the tip text for this property.
   *
   * @return tip text for this property suitable for displaying in the
   *         explorer/experimenter gui
   */
  public String randomSeedTipText() {
    return "The seed used for randomization";
  }

  /**
   * Gets the number of iterations.
   *
   * @return the number of folds.
   */
  public int getRandomSeed() {
    return m_RandomSeed;
  }

  /**
   * Sets the random seed.
   *
   * @param value
   *            the random seed
   */
  public void setRandomSeed(int value) {
    m_RandomSeed = value;
  }

  /**
   * determines the best point for the given space, using CV with specified
   * number of folds.
   *
   * @param space
   *            the space to work on
   * @param train
   *            the training data to work with
   * @param test
   *            the test data to use, null if to use cross-validation
   * @param folds
   *            the number of folds for cross-validation, if &lt;2 then
   *            evaluation based on the training set is used
   * @param postClean
   * 		cleans performance vector in the end. Defaults to true,
   * 		only consider setting to false for testing purposes	
   * @return the best point (not actual parameters!)
   * @throws Exception
   *             if setup or training fails
   */
  protected Performance determineBestInSpace(Space space, Instances train,
					     Instances test, int folds, Random random, boolean postClean) throws Exception {
    Performance result;
    List<Point<Object>> enm;
    Performance performance;
    Point<Object> values;
    Performance p1;
    Performance p2;
    AbstractEvaluationTask newTask;
    List<Callable> tasks;
    int classLabel;

    m_Performances.clear();

    if (test != null)
      log("Determining best values using test set in space:\n" + space + "\n");
    else if (folds >= 2)
      log("Determining best values with " + folds
	+ "-fold CV in space:\n" + space + "\n");
    else
      log("Determining best values with evaluation on training set in space:\n"
	+ space + "\n");

    enm = Collections.list(space.values());
    m_NumSetups = Math.min(space.size(), m_NumIterations);
    Collections.shuffle(enm, random);
    if (train.classAttribute().isNominal())
      classLabel = m_Owner.getClassLabelIndex(train.classAttribute().numValues());
    else
      classLabel = -1;

    tasks = new ArrayList<Callable>();
    ArrayList<Future<Boolean>> results = new ArrayList<Future<Boolean>>();
    for (int i = 0; i < m_NumSetups; ++i) {
      values = enm.get(i);

      // already calculated?
      if (m_Cache.isCached(folds, values)) {
	performance = m_Cache.get(folds, values);
	m_Performances.add(performance);
	m_Trace.add(new AbstractMap.SimpleEntry<Integer, Performance>(
	  folds, performance));
	log(performance + ": cached=true");
      } else {
	newTask = m_Owner.getFactory().newTask(m_Owner, train, test,
	  m_Owner.getGenerator(), values, folds,
	  m_Owner.getEvaluation().getSelectedTag().getID(),
	  classLabel);
	results.add(m_ExecutorPool.submit(newTask));
      }
    }

    // wait for execution to finish
    try {
      for (int i = 0; i < results.size(); i++) {
	if (!results.get(i).get()) {
	  System.err.println("Execution of evaluation thread failed:\n" + tasks.get(i));
	  throw new IllegalStateException("Execution of evaluation thread failed:\n" + tasks.get(i));
	}
      }
    }
    catch (Exception e) {
      System.err.println("Thread-based execution of evaluation tasks failed!");
      e.printStackTrace();
      throw new IllegalStateException("Thread-based execution of evaluation tasks failed!", e);
    }

    // sort list
    Collections.sort(m_Performances,
      new PerformanceComparator(m_Owner.getEvaluation()
	.getSelectedTag().getID(), m_Owner.getMetrics()));

    result = m_Performances.firstElement();

    // check whether all performances are the same
    m_UniformPerformance = true;
    p1 = m_Performances.get(0);
    for (int i = 1; i < m_Performances.size(); i++) {
      p2 = m_Performances.get(i);
      if (p2.getPerformance(m_Owner.getEvaluation().getSelectedTag()
	.getID()) != p1.getPerformance(m_Owner.getEvaluation()
	.getSelectedTag().getID())) {
	m_UniformPerformance = false;
	break;
      }
    }
    if (m_UniformPerformance)
      log("All performances are the same!");

    logPerformances(space, m_Performances);
    log("\nBest performance:\n" + m_Performances.firstElement());
    
    if (postClean) {
      m_Performances.clear();
    }
    
    return result;
  }
  
  /**
   * Returns the performances of the last determineBestInSpace run, if
   * this was ran with postClean argument set to false (for testing)
   * @return vector of performances
   */
  public Vector<Performance> getPerformances() {
    return m_Performances;
  }

  /**
   * returns the best point in the space.
   *
   * @param inst
   *            the training data
   * @return the best point (not evaluated parameters!)
   * @throws Exception
   *             if something goes wrong
   */
  protected Performance findBest(Instances inst) throws Exception {
    Performance result;
    Point<Object> evals;
    Instances sample;
    Resample resample;
    Classifier cls;
    Random random = new Random(m_RandomSeed);

    log("Step 1:\n");

    // generate sample?
    if (getSampleSizePercent() == 100) {
      sample = inst;
    } else {
      log("Generating sample (" + getSampleSizePercent() + "%)");
      resample = new Resample();
      resample.setRandomSeed(retrieveOwner().getSeed());
      resample.setSampleSizePercent(getSampleSizePercent());
      resample.setInputFormat(inst);
      sample = Filter.useFilter(inst, resample);
    }

    m_UniformPerformance = false;

    // find first center
    log("\n=== Search space - Start ===");
    result = determineBestInSpace(m_Space, sample, m_SearchSpaceTestInst,
      m_SearchSpaceNumFolds, random, true);
    log("\nResult: " + result + "\n");
    log("=== Search space - End ===\n");

    evals = m_Owner.getGenerator().evaluate(result.getValues());
    cls = (Classifier) m_Owner.getGenerator().setup(
      (Serializable) m_Owner.getClassifier(), evals);
    log("Classifier: " + getCommandline(cls));

    return result;
  }

  /**
   * Loads test data, if required.
   *
   * @param data
   *            the current training data
   * @throws Exception
   *             if test sets are not compatible with training data
   */
  protected void loadTestData(Instances data) throws Exception {
    String msg;

    m_SearchSpaceTestInst = null;
    if (m_SearchSpaceTestSet.exists()
      && !m_SearchSpaceTestSet.isDirectory()) {
      m_SearchSpaceTestInst = DataSource.read(m_SearchSpaceTestSet
	.getAbsolutePath());
      m_SearchSpaceTestInst.setClassIndex(data.classIndex());
      msg = data.equalHeadersMsg(m_SearchSpaceTestInst);
      if (msg != null) {
	throw new IllegalArgumentException(
	  "Test set for search space not compatible with training dta:\n"
	    + msg);
      }
      m_SearchSpaceTestInst.deleteWithMissingClass();
      log("Using test set for search space: " + m_SearchSpaceTestSet);
    }
  }

  /**
   * Performs the actual search and returns the best setup.
   *
   * @param data	the dataset to use
   * @return		the best classifier setup
   * @throws Exception	if search fails
   */
  @Override
  public SearchResult doSearch(Instances data) throws Exception {
    SearchResult result;
    Point<Object> evals;
    Performance performance;

    loadTestData(data);

    performance = findBest(new Instances(data));
    evals = m_Owner.getGenerator().evaluate(performance.getValues());
    result = new SearchResult();
    result.classifier = (Classifier) m_Owner.getGenerator().setup(
      (Serializable) m_Owner.getClassifier(), evals);
    result.performance = performance;
    result.values = evals;

    return result;
  }
}
