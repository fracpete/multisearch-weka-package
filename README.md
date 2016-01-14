multisearch-weka-package
========================

Weka package for parameter optimization, similar to GridSearch.
Can be used for optimizing an arbitrary number of parameters, in contrast to
GridSearch which always requires you to optimize two parameters. However, it
does not offer automatic search space extensions like GridSearch.


How to use packages
-------------------

For more information on how to install the package, see:

http://weka.wikispaces.com/How+do+I+use+the+package+manager%3F


Maven
-----

Add the following dependency in your `pom.xml` to include the package:

```xml
    <dependency>
      <groupId>com.github.fracpete</groupId>
      <artifactId>multisearch-weka-package</artifactId>
      <version>2016.1.14</version>
      <type>jar</type>
      <exclusions>
        <exclusion>
          <groupId>nz.ac.waikato.cms.weka</groupId>
          <artifactId>weka-dev</artifactId>
        </exclusion>
      </exclusions>
    </dependency>
```
