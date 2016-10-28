simple-twitter-stats
====================

Overview
--------

An exploratory application that uses the Twitter Streaming API to get tweets and gather some basic statistics.

Packaging
---------

simple-twitter-stats is packaged as a fat executable JAR. To build the JAR:

    mvn clean package

The packaged JAR will be located at `server/target/simple-twitter-stats-server-[version].jar`.

Running
-------

To run the application from the command line:

    java -Dconfig.file=[path/to/application.conf] -Dlogback.configurationFile=path/to/logback.xml] -jar [path/to/executable/jar.jar]

There are two properties that should be specified for non-local environments:

* _config.file_: The path to the application configuration file.
* _logback.configurationFile_: The path to the logging configuration file.

If either of these properties are not defined, configurations suitable for local development (`server/src/main/resources/{application.conf, logback.xml}`) will be loaded by default.

Usage
-----

TODO
