How to make a release
=====================

Preparation
-----------

* Change the artifact ID in `pom.xml` to today's date, e.g.:

  ```
  2016.6.6-SNAPSHOT
  ```

* Update the version, date and URL in `Description.props` to reflect new
  version, e.g.:

  ```
  Version=2016.6.6
  Date=2016-06-06
  PackageURL=https://github.com/fracpete/multisearch-weka-package/releases/download/v2016.6.6/multisearch-2016.6.6.zip
  ```

* Commit/push all changes


Weka package
------------

* Run the following command to generate the package archive for version
  `2016.6.6`:

  ```
  ant -f build_package.xml -Dpackage=multisearch-2016.6.6 clean make_package
  ```

* create a release tag on github (`v2016.6.6`)
* add release notes
* upload package archive from `dist`
* add link to this zip file in the `Releases` section of the `README.md` file
* notify Mark Hall about new release


Maven Central
-------------

* Run the following command to deploy the artifact:

  ```
  mvn release:clean release:prepare release:perform
  ```

  skip tests with:
  
  ```
  export MAVEN_OPTS="$MAVEN_OPTS -DskipTests=true"
  ```

* After successful deployment, push the changes out:

  ```
  git push
  ```

* After the artifacts show up on central, update the artifact version used
  in the dependency fragment of the `README.md` file

