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
 * DefaultSearchTest.java
 * Copyright (C) 2016 University of Waikato, Hamilton, NZ
 */

package weka.classifiers.meta.multisearch;

import junit.framework.Test;
import junit.framework.TestSuite;
import junit.textui.TestRunner;

/**
 * Tests the {@link DefaultSearch} class.
 *
 * @author FracPete (fracpete at waikato dot ac dot nz)
 * @version $Revision$
 */
public class DefaultSearchTest
  extends AbstractSearchTest {

  /**
   * Constructs the <code>AbstractSearchTest</code>. Called by subclasses.
   *
   * @param name the name of the test class
   */
  public DefaultSearchTest(String name) {
    super(name);
  }

  /**
   * Used to create an instance of a specific search algorithm.
   *
   * @return a suitably configured <code>AbstractSearch</code> value
   */
  @Override
  public AbstractSearch getSearch() {
    return new DefaultSearch();
  }

  public static Test suite() {
    return new TestSuite(DefaultSearchTest.class);
  }

  public static void main(String[] args){
    TestRunner.run(suite());
  }
}
