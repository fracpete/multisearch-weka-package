package weka.classifiers.meta.multisearch;

import java.util.List;
import java.util.Map.Entry;

public interface TraceableOptimizer {

	/**
	 * Returns the size of m_Trace, which is technically the amount of setups
	 * that where tested in order to find the best.
	 */
	public int getTraceSize();

	/**
	 * Returns the CLI string of a given item in the trace.
	 * 
	 * @param index
	 *            the index of the trace item to obtain
	 */
	public String getTraceClassifierAsCli(int index);

	/**
	 * Returns the performance score of a given item in the trace.
	 * 
	 * @param index
	 *            the index of the trace item to obtain
	 */
	public Double getTraceValue(int index);

	/**
	 * Returns the folds of a given item in the trace.
	 *
	 * @param index
	 *            the index of the trace item to obtain
	 */
	public Integer getTraceFolds(int index);

	/**
	 * Returns the full trace.
	 */
	public List<Entry<Integer, Performance>> getTrace();
}
