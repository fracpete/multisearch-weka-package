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
 * ParameterTest.java
 * Copyright (C) 2008-2018 University of Waikato, Hamilton, New Zealand
 */
package weka.classifiers.meta.multisearch;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.Vector;

import junit.framework.TestCase;
import weka.classifiers.Classifier;
import weka.classifiers.functions.MultilayerPerceptron;
import weka.classifiers.meta.MultiSearch;
import weka.classifiers.trees.J48;
import weka.core.Attribute;
import weka.core.DenseInstance;
import weka.core.Instances;
import weka.core.Utils;
import weka.core.setupgenerator.AbstractParameter;
import weka.core.setupgenerator.ListParameter;
import weka.core.setupgenerator.MLPLayersParameter;

public class ParameterTest extends TestCase {

  private static Instances getDummyXORData() {
    List<String> values = new ArrayList<String>();
    values.add("0");
    values.add("1");
    Attribute x1 = new Attribute("x1", values);
    Attribute x2 = new Attribute("x2", values);
    Attribute y = new Attribute("y", values);
    ArrayList<Attribute> attributes = new ArrayList<Attribute>();
    attributes.add(x1);
    attributes.add(x2);
    attributes.add(y);

    Instances data = new Instances("xor", attributes, 4);

    for (int x1val = 0; x1val <= 1; ++x1val) {
      for (int x2val = 0; x2val <= 1; ++x2val) {
	for (int yval = 0; yval <= 1; ++yval) {
	  double[] attValues = { x1val, x2val, yval };
	  DenseInstance instance = new DenseInstance(1.0, attValues);
	  data.add(instance);
	}
      }
    }
    data.setClass(y);
    return data;
  }
  

  
  private Vector<Performance> searchValuesRandom(AbstractParameter parameter, Classifier classifier) throws Exception {
    AbstractParameter[] searchParameters = { parameter };
    
    RandomSearch searchAlgorithm = new RandomSearch();
    searchAlgorithm.setNumExecutionSlots(1);
    
    // first we need to run multisearch, to setup most variables OK
    MultiSearch multiSearch = new MultiSearch();
    multiSearch.setClassifier(classifier);
    multiSearch.setSearchParameters(searchParameters);
    multiSearch.setAlgorithm(searchAlgorithm);
    multiSearch.buildClassifier(getDummyXORData());
    
    // start new executor pool
    searchAlgorithm.startExecutorPool();
    Random random = new Random(0);
    searchAlgorithm.determineBestInSpace(searchAlgorithm.m_Space, getDummyXORData(), getDummyXORData(), 1, random, false);
    
    return searchAlgorithm.getPerformances();
  }
  
  private Vector<Performance> searchValuesAll(AbstractParameter parameter, Classifier classifier) throws Exception {
    AbstractParameter[] searchParameters = { parameter };
    
    DefaultSearch searchAlgorithm = new DefaultSearch();
    searchAlgorithm.setNumExecutionSlots(1);
    // first we need to run multisearch, to setup most variables OK
    MultiSearch multiSearch = new MultiSearch();
    multiSearch.setClassifier(classifier);
    multiSearch.setSearchParameters(searchParameters);
    multiSearch.setAlgorithm(searchAlgorithm);
    multiSearch.buildClassifier(getDummyXORData());
    
    // start new executor pool
    searchAlgorithm.startExecutorPool();
    searchAlgorithm.determineBestInSpace(searchAlgorithm.m_Space, getDummyXORData(), getDummyXORData(), 1, false);
    
    return searchAlgorithm.getPerformances();
  }
  
  private void verifyExpectedValues(Map<?, Boolean> expectedValues, int expectedTrues) {
    int trueCount = 0;
    for (Entry<?, Boolean> entry : expectedValues.entrySet()) {
      if (entry.getValue()) {
	trueCount += 1;
      }
    }
    assertEquals(expectedTrues, trueCount);
  }
  
  public void testMLPLayersParamGettersSetters() throws Exception {
    MLPLayersParameter mlplayers = new MLPLayersParameter();
    mlplayers.setMinLayers(5);
    assertEquals(mlplayers.getMinLayers(), 5);
    mlplayers.setMaxLayers(6);
    assertEquals(mlplayers.getMaxLayers(), 6);
    mlplayers.setMinLayerSize(7);
    assertEquals(mlplayers.getMinLayerSize(), 7);
    mlplayers.setMaxLayerSize(8);
    assertEquals(mlplayers.getMaxLayerSize(), 8);
  }

  public void testMLPLayersParamSmall() throws Exception {
    MLPLayersParameter mlplayers = new MLPLayersParameter();
    String options = "-minLayers 1 -maxLayers 2 -minLayerSize 4 -maxLayerSize 8";
    mlplayers.setOptions(Utils.splitOptions(options));

    assertTrue(mlplayers.getMinLayers() == 1);
    assertTrue(mlplayers.getMaxLayers() == 2);
    assertTrue(mlplayers.getMinLayerSize() == 4);
    assertTrue(mlplayers.getMaxLayerSize() == 8);
    String[] items = mlplayers.getItems();
    int expectedSize = 30; // 5^1 + 5^2
    assertTrue(expectedSize == mlplayers.calculateNumberOfCandidates());
    assertTrue(items.length == expectedSize);
  }

  public void testMLPLayersParamMedium() throws Exception {
    MLPLayersParameter mlplayers = new MLPLayersParameter();
    String options = "-minLayers 1 -maxLayers 4 -minLayerSize 9 -maxLayerSize 16";
    mlplayers.setOptions(Utils.splitOptions(options));

    assertTrue(mlplayers.getMinLayers() == 1);
    assertTrue(mlplayers.getMaxLayers() == 4);
    assertTrue(mlplayers.getMinLayerSize() == 9);
    assertTrue(mlplayers.getMaxLayerSize() == 16);
    String[] items = mlplayers.getItems();
    int expectedSize = 4680; // * 8^1 + 8^2 + 8^3 + 8^4
    assertTrue(expectedSize == mlplayers.calculateNumberOfCandidates());
    assertTrue(items.length == expectedSize);
  }

  public void testMLPLayersParamLarge() throws Exception {
    MLPLayersParameter mlplayers = new MLPLayersParameter();
    String options = "-minLayers 1 -maxLayers 32 -minLayerSize 128 -maxLayerSize 256";
    mlplayers.setOptions(Utils.splitOptions(options));

    assertTrue(mlplayers.getMinLayers() == 1);
    assertTrue(mlplayers.getMaxLayers() == 32);
    assertTrue(mlplayers.getMinLayerSize() == 128);
    assertTrue(mlplayers.getMaxLayerSize() == 256);
    String[] items = mlplayers.getItems();
    assertTrue(items.length == MLPLayersParameter.MAX_CANDIDATES_TO_GENERATE);
  }
  
  public void testMLPLayersParamLive() throws Exception {
    MLPLayersParameter parameter = new MLPLayersParameter();
    String options = "-minLayers 2 -maxLayers 2 -minLayerSize 2 -maxLayerSize 3 -property hiddenLayers";
    parameter.setOptions(Utils.splitOptions(options));
    Vector<Performance> results = searchValuesAll(parameter, new MultilayerPerceptron());
    
    Map<String, Boolean> expectedValues = new HashMap<String, Boolean>();
    expectedValues.put("2, 2", false);
    expectedValues.put("2, 3", false);
    expectedValues.put("3, 2", false);
    expectedValues.put("3, 3", false);
    for (Performance result : results) {
      MultilayerPerceptron current = (MultilayerPerceptron) result.m_Classifier;
      expectedValues.put(current.getHiddenLayers(), true);
    }
    verifyExpectedValues(expectedValues, 4);
    assertEquals(expectedValues.size(), 4);
  }
  
  public void testMLPLayersParamLiveRandom() throws Exception {
    MLPLayersParameter parameter = new MLPLayersParameter();
    String options = "-minLayers 2 -maxLayers 2 -minLayerSize 2 -maxLayerSize 3 -property hiddenLayers";
    parameter.setOptions(Utils.splitOptions(options));
    Vector<Performance> results = searchValuesRandom(parameter, new MultilayerPerceptron());
    
    Map<String, Boolean> expectedValues = new HashMap<String, Boolean>();
    expectedValues.put("2, 2", false);
    expectedValues.put("2, 3", false);
    expectedValues.put("3, 2", false);
    expectedValues.put("3, 3", false);
    for (Performance result : results) {
      MultilayerPerceptron current = (MultilayerPerceptron) result.m_Classifier;
      expectedValues.put(current.getHiddenLayers(), true);
    }
    verifyExpectedValues(expectedValues, 4);
    assertEquals(expectedValues.size(), 4);
  }

  public void testMLPLayersParamIllegalLayerSizeRaises() {
    MLPLayersParameter mlplayers = new MLPLayersParameter();
    String options = "-minLayerSize 4 -maxLayerSize 2";
    try {
      mlplayers.setOptions(Utils.splitOptions(options));
      fail("expected error was not raised");
    } catch (Exception e) {
      String expected = "minLayerSize should be smaller than or equal to maxLayerSize";
      assertEquals(expected, e.getMessage());
    }
  }

  public void testMLPLayersParamIllegalLayersRaises() {
    MLPLayersParameter mlplayers = new MLPLayersParameter();
    String options = "-minLayers 4 -maxLayers 2";
    try {
      mlplayers.setOptions(Utils.splitOptions(options));
      fail("expected error was not raised");
    } catch (Exception e) {
      String expected = "minLayers should be smaller than or equal to maxLayers";
      assertEquals(expected, e.getMessage());
    }
  }

  public void testMLPLayersParamIllegalBothRaises() {
    MLPLayersParameter mlplayers = new MLPLayersParameter();
    String options = "-minLayers 2 -maxLayers 2 -minLayerSize 4 -maxLayerSize 4";
    try {
      mlplayers.setOptions(Utils.splitOptions(options));
      fail("expected error was not raised");
    } catch (Exception e) {
      String expected = "no variation in layer structure possible";
      assertEquals(expected, e.getMessage());
    }
  }

  public void testListParameterFlag() throws Exception {
    ListParameter listparameter = new ListParameter();
    String options = "-list \"true false\" -property \"unpruned\"";
    listparameter.setOptions(Utils.splitOptions(options));
    Vector<Performance> results = searchValuesAll(listparameter, new J48());
    
    Map<Boolean, Boolean> expectedValues = new HashMap<Boolean, Boolean>();
    for (Performance result : results) {
      J48 current = (J48) result.m_Classifier;
      expectedValues.put(current.getUnpruned(), true);
    }
    verifyExpectedValues(expectedValues, 2);
    assertEquals(expectedValues.size(), 2);
  }
}
