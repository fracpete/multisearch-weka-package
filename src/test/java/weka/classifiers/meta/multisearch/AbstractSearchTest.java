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
 * Copyright (C) 2002-2016 University of Waikato
 */

package weka.classifiers.meta.multisearch;

import junit.framework.TestCase;
import weka.core.CheckGOE;
import weka.core.CheckOptionHandler;
import weka.core.SerializationHelper;

/**
 * Abstract Test class for search algorithms.
 * 
 * @author FracPete (fracpete at waikato dot ac dot nz)
 * @version $Revision: 10222 $
 */
public abstract class AbstractSearchTest extends TestCase {

  /** The search to be tested */
  protected AbstractSearch m_Search;

  /** the OptionHandler tester */
  protected CheckOptionHandler m_OptionTester;

  /** for testing GOE stuff */
  protected CheckGOE m_GOETester;

  /**
   * Constructs the <code>AbstractSearchTest</code>. Called by subclasses.
   * 
   * @param name the name of the test class
   */
  public AbstractSearchTest(String name) {
    super(name);
  }

  /**
   * Configures the CheckOptionHandler uses for testing the optionhandling. Sets
   * the search return from the getSearch() method.
   * 
   * @return the fully configured CheckOptionHandler
   * @see #getSearch()
   */
  protected CheckOptionHandler getOptionTester() {
    CheckOptionHandler result;

    result = new CheckOptionHandler();
    result.setOptionHandler(getSearch());
    result.setUserOptions(new String[0]);
    result.setSilent(true);

    return result;
  }

  /**
   * Configures the CheckGOE used for testing GOE stuff. Sets the AbstractSearch
   * returned from the getAbstractSearch() method.
   * 
   * @return the fully configured CheckGOE
   * @see #getSearch()
   */
  protected CheckGOE getGOETester() {
    CheckGOE result;

    result = new CheckGOE();
    result.setObject(getSearch());
    result.setSilent(true);

    return result;
  }

  /**
   * Called by JUnit before each test method. This implementation creates the
   * default classifier to test and loads a test set of Instances.
   * 
   * @exception Exception if an error occurs reading the example instances.
   */
  @Override
  protected void setUp() throws Exception {
    m_Search = getSearch();
    m_OptionTester = getOptionTester();
    m_GOETester = getGOETester();
  }

  /** Called by JUnit after each test method */
  @Override
  protected void tearDown() {
    m_Search = null;
    m_OptionTester = null;
    m_GOETester = null;
  }

  /**
   * Used to create an instance of a specific search algorithm.
   * 
   * @return a suitably configured <code>AbstractSearch</code> value
   */
  public abstract AbstractSearch getSearch();

  /**
   * Checks whether the scheme's toString() method works even though the
   * classifies hasn't been built yet.
   * 
   * @return index 0 is true if the toString() method works fine
   */
  protected boolean[] testToString() {
    boolean[] result = new boolean[2];

    try {
      AbstractSearch copy = m_Search.getClass().newInstance();
      copy.toString();
    } catch (Exception e) {
      fail("toString method failed: " + e);
    }

    return result;
  }

  /**
   * tests for a serialVersionUID. Fails in case the scheme doesn't declare a
   * UID.
   */
  public void testSerialVersionUID() {
    boolean[] result = new boolean[2];

    result[0] = !SerializationHelper.needsUID(m_Search.getClass());

    if (!result[0])
      fail("Does not declare serialVersionUID!");
  }

  /**
   * tests the listing of the options
   */
  public void testListOptions() {
    if (!m_OptionTester.checkListOptions()) {
      m_OptionTester.setSilent(false);
      m_OptionTester.checkListOptions();
      fail("Options cannot be listed via listOptions.");
    }
  }

  /**
   * tests the setting of the options
   */
  public void testSetOptions() {
    if (!m_OptionTester.checkSetOptions()) {
      m_OptionTester.setSilent(false);
      m_OptionTester.checkSetOptions();
      fail("setOptions method failed.");
    }
  }

  /**
   * tests whether the default settings are processed correctly
   */
  public void testDefaultOptions() {
    if (!m_OptionTester.checkDefaultOptions()) {
      m_OptionTester.setSilent(false);
      m_OptionTester.checkDefaultOptions();
      fail("Default options were not processed correctly.");
    }
  }

  /**
   * tests whether there are any remaining options
   */
  public void testRemainingOptions() {
    if (!m_OptionTester.checkRemainingOptions()) {
      m_OptionTester.setSilent(false);
      m_OptionTester.checkRemainingOptions();
      fail("There were 'left-over' options.");
    }
  }

  /**
   * tests the whether the user-supplied options stay the same after setting.
   * getting, and re-setting again.
   * 
   * @see #getOptionTester()
   */
  public void testCanonicalUserOptions() {
    if (!m_OptionTester.checkCanonicalUserOptions()) {
      m_OptionTester.setSilent(false);
      m_OptionTester.checkCanonicalUserOptions();
      fail("setOptions method failed");
    }
  }

  /**
   * tests the resetting of the options to the default ones
   */
  public void testResettingOptions() {
    if (!m_OptionTester.checkSetOptions()) {
      m_OptionTester.setSilent(false);
      m_OptionTester.checkSetOptions();
      fail("Resetting of options failed");
    }
  }

  /**
   * tests for a globalInfo method
   */
  public void testGlobalInfo() {
    if (!m_GOETester.checkGlobalInfo()) {
      m_GOETester.setSilent(false);
      m_GOETester.checkGlobalInfo();
      fail("No globalInfo method");
    }
  }

  /**
   * tests the tool tips
   */
  public void testToolTips() {
    if (!m_GOETester.checkToolTips()) {
      m_GOETester.setSilent(false);
      m_GOETester.checkToolTips();
      fail("Tool tips inconsistent");
    }
  }
}
