How to make a release
=====================

Preparation
-----------

* Change the artifact ID in `pom.xml` to today's date, e.g.:

  ```
  2014.12.31-SNAPSHOT
  ```

* Update the version, date and URL in `Description.props` to reflect new
  version, e.g.:

  ```
  Version=2014.12.31
  Date=2014-12-31
  PackageURL=https://github.com/fracpete/multisearch-weka-package/releases/download/v2014.12.31/multisearch-2014.12.31.zip
  ```

* Commit/push all changes


Weka package
------------

* Run the following command to generate the package archive for version
  `2014.12.31`:

  ```
  ant -f build_package.xml -Dpackage=multisearch-2014.12.31 clean make_package
  ```

* Create a release tag on github (`v2014.12.31`)
* add release notes
* upload package archive from `dist`
* add link to this zip file in the `Releases` section of the `README.md` file


Maven Central
-------------

* Run the following command to deploy the artifact:

  ```
  mvn release:clean release:prepare release:perform
  ```

* After successful deployment, push the changes out:

  ```
  git push
  ```

* After the artifacts show up on central, update the artifact version used
  in the dependency fragment of the `README.md` file
