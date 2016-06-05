package weka.classifiers.meta;/*
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
 * weka.classifiers.meta.MultiSearchTest.java
 * Copyright (C) 2016 University of Waikato, Hamilton, NZ
 */

import junit.framework.Test;
import junit.framework.TestSuite;
import junit.textui.TestRunner;
import weka.classifiers.AbstractClassifierTest;
import weka.classifiers.Classifier;

/**
 * Tests MultiSearch.
 *
 * @author FracPete (fracpete at waikato dot ac dot nz)
 * @version $Revision$
 */
public class MultiSearchTest
  extends AbstractClassifierTest {

  /**
   * Initializes the test.
   *
   * @param name	the name of the test
   */
  public MultiSearchTest(String name) {
    super(name);
    System.setProperty("weka.test.Regression.root", "src/test/resources");
  }

  /**
   * Returns the default classifier.
   *
   * @return		the classifier
   */
  @Override
  public Classifier getClassifier() {
    return new MultiSearch();
  }

  public static Test suite() {
    return new TestSuite(MultiSearchTest.class);
  }

  public static void main(String[] args){
    TestRunner.run(suite());
  }
}
