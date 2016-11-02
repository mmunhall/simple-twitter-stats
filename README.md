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

Configuration
-------------

Reference configurations for logback and the application are located in `/server/src/main/resources/`.

TODO: Include info about Twitter OAuth configuration.

Running
-------

To run the application from the command line:

    java -jar [path/to/executable/jar.jar]

To provide your own configurations, create the appropriate configuration files and reference them as VM options when starting the app:

    java -jar -Dconfig.file=[path/to/application.conf] -Dlogback.configurationFile=path/to/logback.xml] 

Usage
-----

TODO
