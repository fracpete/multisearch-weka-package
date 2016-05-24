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
 * TraceableOptimizer.java
 * Copyright (C) 2016 Leiden University, NL
 */

package weka.classifiers.meta.multisearch;

import java.util.List;
import java.util.Map.Entry;

public interface TraceableOptimizer {

  /**
   * Returns the size of m_Trace, which is technically the amount of setups
   * that where tested in order to find the best.
   *
   * @return		the number of setups tested
   */
  public int getTraceSize();

  /**
   * Returns the CLI string of a given item in the trace.
   *
   * @param index	the index of the trace item to obtain
   * @return		the commandline
   */
  public String getTraceClassifierAsCli(int index);

  /**
   * Returns the performance score of a given item in the trace.
   *
   * @param index	the index of the trace item to obtain
   * @return		the trace value
   */
  public Double getTraceValue(int index);

  /**
   * Returns the folds of a given item in the trace.
   *
   * @param index	the index of the trace item to obtain
   * @return		the number of folds
   */
  public Integer getTraceFolds(int index);

  /**
   * Returns the parameter settings in structured way
   *
   * @param index the index of the trace item to obtain
   */
  public List<Entry<String, String>> getTraceParamaterSettings(int index);

  /**
   * Returns the full trace.
   *
   * @return		the full trace
   */
  public List<Entry<Integer, Performance>> getTrace();
}
