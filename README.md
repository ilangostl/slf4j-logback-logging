# slf4j with Logback Logging


Logging using slf4j and Logback

# Technology
+ SBT 0.13
+ Scala 2.10.3
+ Java 1.7.0_25

## Run
With SBT on the path:
sbt run

## Explanation

Logging should be simple. Using a scala wrapper for SLF4J and logback seems to accomplish the mission.

[SLF4J](http://www.slf4j.org/) is a *simple logging facade for Java* that serves as a simple facade or abstraction for various logging frameworks (e.g. java.util.logging, logback, log4j) allowing the end user to plug in the desired logging framework at deployment time.

[Scalalogging](https://github.com/typesafehub/scalalogging) is a scala wrapper for SLF4J that improves on the performance of SLF4J by using standard scala features, and also provides a `Logging` trait that can be mixed in with any class to provide a logger object.

[Logback](http://logback.qos.ch/) is the actual logger, which I was recommened to use by the people at [Akka](http://akka.io/) (I'm basically copying the setup used by Akka). To have logback be used as the logger it just needs to be included in the classpath and SLF4J will use it.

All these means is that your `build.sbt` should contain:

```scala
libraryDependencies ++= Seq(
  "ch.qos.logback"      %  "logback-classic"      % "1.0.7",
  "com.typesafe"        %% "scalalogging-slf4j"   % "1.0.1"
)
```

And then to do some logging you need to extend the `Logging` class:

```scala
import com.typesafe.scalalogging.slf4j.Logging

object HelloLogging extends Logging {
  def main (args: Array[String]) {
    logger.debug("debugging here")
    logger.info("hello world")
    logger.error("opps")
  }
}
```

The output:

```
18:22:00.556 [main] DEBUG com.example.HelloLogging$ - debugging here
18:22:00.559 [main] INFO  com.example.HelloLogging$ - hello world
18:22:00.560 [main] ERROR com.example.HelloLogging$ - opps
```

To configure logback logger you need to add a config file `logback.xml` to the classpath. For example, to change the format and only log info and higher (taken from logback documention):

```xml
<configuration>
  <appender name="STDOUT" class="ch.qos.logback.core.ConsoleAppender">
    <encoder>
      <pattern>%d{HH:mm:ss} [%thread] %-5level %logger{36} - %msg%n</pattern>
    </encoder>
  </appender>

  <root level="info">
    <appender-ref ref="STDOUT" />
  </root>
</configuration>
```

Now the output is:

```
19:37:45 [main] INFO  com.example.HelloLogging$ - hello world
19:37:45 [main] ERROR com.example.HelloLogging$ - opps
```
