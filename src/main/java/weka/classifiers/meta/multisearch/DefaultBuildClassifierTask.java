package weka.classifiers.meta.multisearch;

import java.util.concurrent.Callable;

import weka.classifiers.Classifier;
import weka.core.Instances;

public class DefaultBuildClassifierTask implements Callable {
  
  protected final Classifier m_Classifier;
  
  protected final Instances m_Data;
  
  public DefaultBuildClassifierTask(Classifier classifier, Instances data) {
	this.m_Classifier = classifier;
	this.m_Data = data;
  }
  
  public Boolean call() {
    try {
      m_Classifier.buildClassifier(m_Data);
	} catch (Exception e) {
	  return false;
	}
    return true;
  }
}
