The **spring-runner** project provides some simple runner services that can bootstrap any Java service like process. It comes with a `SpringRunner` that takes any [Spring](http://www.springsource.org/about) XML configuration files and run it.

Example:
```
$ bin/run-spring config/simpleservice-beans.xml
INFO  SpringRunner   | Registered MBean: springrunner:type=service,name=SpringRunner
INFO  SpringRunner   | Starting Service[springrunner.SpringService@11546234]
INFO  FileSystemXmlApplicationContext| Refreshing
  org.springframework.context.support.FileSystemXmlApplicationContext@1194a4e: 
  startup date [Sat Mar 19 13:15:16 EDT 2011]; root of context hierarchy
INFO  XmlBeanDefinitionReader| Loading XML bean definitions from file 
  [C:\projects\spring-runner\config\simpleservice-beans.xml]
INFO  DefaultListableBeanFactory| Pre-instantiating singletons in
  org.springframework.beans.factory.support.DefaultListableBeanFactory@172e449: 
  defining beans [simpleService1,simpleService2,simpleService3,threadPool,serviceContainer]; 
  root of factory hierarchy
INFO  ServiceContainer| springrunner.ServiceContainer@7888229 started.
INFO  SpringRunner   | Service[springrunner.SpringService@11546234] started.
INFO  SpringRunner   | Server is ready.
```

You may shutdown the server by CTRL+C, or using a remote JMX Client, which also provided by this project.

See UserGuide for more.


---

If you have questions, try send emails to owner: <[Zemian Deng](http://code.google.com/p/zemiandeng)> saltnlight5 at gmail dot com.