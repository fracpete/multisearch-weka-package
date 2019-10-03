multisearch-weka-package
========================

Weka package for parameter optimization, similar to GridSearch.
Can be used for optimizing an arbitrary number of parameters, in contrast to
GridSearch which always requires you to optimize two parameters. However, it
does not offer automatic search space extensions like GridSearch.


Parameters
----------

* `weka.core.setupgenerator.MathParameter`

  uses mathematical expression to compute the (numeric) parameters
  
* `weka.core.setupgenerator.ListParameter`

  list of values; default is blank-separated, but a custom delimiter
  can be supplied
  
* `weka.core.setupgenerator.MLPLayersParameter`

  generates hidden layer definitions for the MultiLayerPerceptron
  classifier  (contributed by [Jan van Rijn](https://github.com/janvanrijn))
  
* `weka.core.setupgenerator.ParameterGroup`

  allows grouping of dependent parameters, e.g., setting on group sets the kernel 
  of SMO to RFBKernel and explores the gamma option, another group sets the
  kernel to PolyKernel and explores the exponent option.

**Note:** array elements, e.g., the filters inside a `weka.filters.MultiFilter`
can be accessed using `[n]` with `n` being the 0-based index. E.g., if the
third filter inside a `MultiFilter` is a `PLSFilter`, then its `numComponents`
property can be accessed with `filters[2].numComponents`.


Supported parameter types
-------------------------

* char, string
* float, double
* int, long
* boolean
* `weka.core.SelectedTag`
* Java classname (and possible options for classes implementing `weka.core.OptionHandler`)


Search space exploration
------------------------

The search space of setups can be explored with different strategies, derived
from `weka.classifiers.meta.multisearch.AbstractSearch`. The following 
strategies are available:

* `weka.classifiers.meta.multisearch.DefaultSearch`
  
  Exhaustive search of parameter space

* `weka.classifiers.meta.multisearch.RandomSearch`

  Random search of parameter space (contributed by [Jan van Rijn](https://github.com/janvanrijn))


Example
-------

With the following classifier setup:

```
weka.classifiers.meta.FilteredClassifier
|
+- weka.filters.supervised.attribute.PLSFilter
|
+- weka.classifiers.functions.LinearRegression
```

You can explore the filter's *PLS components* and classifier's *ridge* parameters
by referencing these properties as follows (the MultiSearch's classifier is
used as the base for the property paths):

* components: `filter.numComponents`
* ridge: `classifier.ridge`

For more examples, please see the following repository:

https://github.com/fracpete/multisearch-weka-package-examples


Releases
--------

Click on one of the following links to download the corresponding Weka package:

* [2019.10.4](https://github.com/fracpete/multisearch-weka-package/releases/download/v2019.10.4/multisearch-2019.10.4.zip)
* [2019.9.30](https://github.com/fracpete/multisearch-weka-package/releases/download/v2019.9.30/multisearch-2019.9.30.zip)
* [2018.9.2](https://github.com/fracpete/multisearch-weka-package/releases/download/v2018.9.2/multisearch-2018.9.2.zip)
* [2018.8.16](https://github.com/fracpete/multisearch-weka-package/releases/download/v2018.8.16/multisearch-2018.8.16.zip)
* [2018.8.11](https://github.com/fracpete/multisearch-weka-package/releases/download/v2018.8.11/multisearch-2018.8.11.zip)
* [2017.10.1](https://github.com/fracpete/multisearch-weka-package/releases/download/v2017.10.1/multisearch-2017.10.1.zip)
* [2017.3.28](https://github.com/fracpete/multisearch-weka-package/releases/download/v2017.3.28/multisearch-2017.3.28.zip)
* old releases: [2016.6.6](https://github.com/fracpete/multisearch-weka-package/releases/download/v2016.6.6/multisearch-2016.6.6.zip), 
  [2016.5.31](https://github.com/fracpete/multisearch-weka-package/releases/download/v2016.5.31/multisearch-2016.5.31.zip),
  [2016.5.25](https://github.com/fracpete/multisearch-weka-package/releases/download/v2016.5.25/multisearch-2016.5.25.zip),
  [2016.5.15](https://github.com/fracpete/multisearch-weka-package/releases/download/v2016.5.15/multisearch-2016.5.15.zip),
  [2016.4.30](https://github.com/fracpete/multisearch-weka-package/releases/download/v2016.4.30/multisearch-2016.4.30.zip),
  [2016.4.25](https://github.com/fracpete/multisearch-weka-package/releases/download/v2016.4.25/multisearch-2016.4.25.zip),
  [2016.4.14](https://github.com/fracpete/multisearch-weka-package/releases/download/v2016.4.14/multisearch-2016.4.14.zip),
  [2016.1.30](https://github.com/fracpete/multisearch-weka-package/releases/download/v2016.1.30/multisearch-2016.1.30.zip),
  [2016.1.15](https://github.com/fracpete/multisearch-weka-package/releases/download/v2016.1.15/multisearch-2016.1.15.zip),
  [2016.1.14](https://github.com/fracpete/multisearch-weka-package/releases/download/v2016.1.14/multisearch-2016.1.14.zip),
  [2016.1.13](https://github.com/fracpete/multisearch-weka-package/releases/download/v2016.1.13/multisearch-2016.1.13.zip),
  [2015.10.15](https://github.com/fracpete/multisearch-weka-package/releases/download/v2015.10.15/multisearch-2015.10.15.zip),
  [2015.9.2](https://github.com/fracpete/multisearch-weka-package/releases/download/v2015.9.2/multisearch-2015.9.2.zip),
  [2014.12.10](https://github.com/fracpete/multisearch-weka-package/releases/download/v2014.12.10/multisearch-2014.12.10.zip)


How to use packages
-------------------

For more information on how to install the package, see:

https://waikato.github.io/weka-wiki/packages/manager/


Maven
-----

Add the following dependency in your `pom.xml` to include the package:

```xml
    <dependency>
      <groupId>com.github.fracpete</groupId>
      <artifactId>multisearch-weka-package</artifactId>
      <version>2019.10.4</version>
      <type>jar</type>
      <exclusions>
        <exclusion>
          <groupId>nz.ac.waikato.cms.weka</groupId>
          <artifactId>weka-dev</artifactId>
        </exclusion>
      </exclusions>
    </dependency>
```
