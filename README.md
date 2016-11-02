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

Reference configurations for logback and the application are located in `/server/src/main/resources/`. The reference configuration contains OAuth credentials which must be updated. To get up and running quickly, obtain your own OAuth keys and tokens (See the [Twitter Developer Platform](https://dev.twitter.com)), update the reference configuration, then run the app. A better, more permanent solution would be to provide your own `application.conf` and put it in the class path, or specify a configuration file as a VM option.

At a minimum, a configuration file must override the OAuth credentials:
 
    simple-twitter-stats {
      server {
        twitter {
          client {
            oauth {
              consumer {
                key = "..."
                secret = "..."
              }
              access {
                token = "..."
                secret = "..."
              }
            }
          }
        }
      }
    }
    
Of course, all other configuration properties can be updated, as well. 

Running
-------

To run the application from the command line using an `application.conf` or the reference configuration:

    java -jar [path/to/executable/jar.jar]

To provide your own configurations, create the appropriate configuration files and reference them as VM options when starting the app:

    java -jar -Dconfig.file=[path/to/application.conf] -Dlogback.configurationFile=path/to/logback.xml] 

Usage
-----

TODO
