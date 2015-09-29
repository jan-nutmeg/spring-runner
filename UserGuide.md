# Introduction #
The **spring-runner** project provides some simple runner services that can bootstrap a server process with any Spring XML configuration files.

Many times we just want a simple but functional server process to bootstrap a Spring application context and let it run in background. That's what this project gives you.

For those who are not familiar what [Spring Framework](http://www.springsource.org) is, then please visit their site and learn more. Spring comes with many things, but in large it is a framework that provide inversion of control (IoC) container management. It has a very user and developer friendly xml configuration model to manage any Plain Old Java Objects (POJOs). Developers can write these POJOs for any business logic & services, and then use Spring to glue (config) them together declaratively instead of hardcoding the service dependencies.

Though Spring is flexible and easy to use, but it doesn't comes with any server bootstrapper classes ready for you to run. Typical usage of Spring is to start it as part of Web Container such as Tomcat, and use the Servlet's `ApplicationListener`'s lifecycle to boostrap the Spring container.

What this project offer is just a very simple, thin Java server that take one or more Spring XML configuration files and run it. This is very useful in a standalone environment, or even for testing. You provide all the needed jars of your applications, wire the your services in an xml file, then just run it.

Our server is simple, but yet provide most functional items that any typical background process would need:
  * Any logging library that supported by SLF4J. We provide `log4j` as default.
  * A `config` directory that already in classpath and you can provide any XML config file there.
  * A system property `app.home` is preset in the location of your server.
  * A pre-configured daily rolling logfile under `log` directory.
  * The server expose itself to local JMX platform mbeanserver, so it can be managed remotely by any standard JMX client (eg: jconsole).
  * The spring-context, apache commons-lang & commons-io jars are already in classpath for easy development extension.

Few examples of Spring xml configuration files already provided in the `config` directory of this project. You can give it a try now and run it like this:
```
cd spring-runner
bin/run-spring config/filepoller-beans.xml

# or you can run any xml config file already in your jar/classpath
bin/run-spring classpath:com/fanncy/company/my-custom-services-beans.xml
```

You simply add your jars into the `lib` directory and then any of our `run-*` scripts will auto picks it up and set the classpath for you.

NOTE: This project is built and tested on Java 6. It should work with Java 5 as well, but you need to run the tests on your own.

## When not to use spring-runner ##
Well, we provide a very simple server for you to do anything in a standalone application; and Spring framework is flexible and comprehensive enough to let you write any enterprise scale level application. Then what's spring-runner not for? Well spring-runner is not an full blown JEE server, it's not even Web container. So if you need those features, by all mean use those servers instead.

The spring-runner has no classloader, or OSGI facilities. It's just a simple server that loads everything you set in the classpath and run. Simple and works for most use cases. If your application is so large and unwilling to break into separated Java processes, then by all means use other server such as Apache Virgo, which is a official Spring powered OSGI server. Also Learning curve might be a big difference. You could browse and understand all spring-runner code in couple days, but it might take you few months to learn other larger servers.

# Running Spring Server #
We provided a Bash shell script `bin/run-spring` that will run one or more Spring xml config files. This should work on any Linux/Unix system that has Bash shell terminal. To run this in Windows, you would need the [Cygwin](http://www.cygwin.com) terminal. If you insist on running this on Windows without Bash shell, then you can simply try java invocation on a DOS (cmd.exe) prompt like this:
```
java springrunner.SpringRunner config/simpleservice-beans.xml
```

Our `bin/run-spring`, which inherit from `run-java` script that provide much more features. See "Running Java Program" section below for more.

# Running a custom Service Runner #
You usually want all the features Spring provide to write your business service classes. But in case if you do not need Spring, and still want a simple service with `start()` and `stop()` lifecycle to be bootstrapped as a server, then you can use our `ServiceRunner` service.

In fact our `SpringRunner` service is actually just an extension to the `ServiceRunner`. There is a `bin/run-service` script that invoke this. It will run with any classname that implements `springrunner.Service` and any arguments you want to pass for it. This gives you a place to extend our server service with your own.

Example:
```
bin/run-service springrunner.SimpleService
```

# Running any Java Program #
Our project comes with a `bin/run-java` shell script that you can use to run any java program. This script has few enhanced features that makes it easy to use.

  * It can be invoked from any directory in your system.
  * It auto setup `config` directory in classpath.
  * It auto setup any jar files in lib directory in classpath.
  * It provides additional `-D` or `-cp` Java options all in one line.
  * It let you debug the script with RUN\_JAVA\_DEBUG=1

See the script itself for more comments and usage.

Here are some example usages
```
# A simple java program
bin/run-java springrunner.tool.SysProps

# Use Java -D option all in oneline!
bin/run-java springrunner.tool.JmxClient shutdown -DjmxUser=tester -DjmxPassword=tester123
```