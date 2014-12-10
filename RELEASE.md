How to make a release
=====================

Preparation
-----------

* Change the artifact ID to today''s date, e.g.:

  <pre>
  2014.12.31-SNAPSHOT
  </pre>

* Commit/push all changes


Weka package
------------

* Run the following command to generate the package archive for version `1.0.0`:

  <pre>
  ant -f build_package.xml -Dpackage=multisearch-1.0.0 clean make_package
  </pre>

* Create a release tag on github (v1.0.0)
* add release notes
* upload package archive from `dist`


Maven Central
-------------

* Run the following command to deploy the artifact:

  <pre>
  mvn release:clean release:prepare release:perform
  </pre>

* After successful deployment, push the changes out:

  <pre>
  git push
  </pre>

