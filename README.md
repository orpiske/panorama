Otavio's Apache Camel Examples: Sas Service
============

This example project shows a simple request/response service in Apache Camel using
Apache Active MQ.

Concepts
----

It is implemented on the simplest way possible. Some of the key concepts, approaches
used in this example:
* [Request and Reply](http://camel.apache.org/request-reply.html)
* [Data Format](http://camel.apache.org/data-format.html)
** [JaxbDataFormat](http://camel.apache.org/jaxb.html)
* [Exception Clause](http://camel.apache.org/exception-clause)


Usage
----

To compile the server code, you'll need Apache Maven:

```
mvn -P Delivery clean package
```

Note: you will need to have installed the Sas Types before the aforementioned command.

To install, just unpack it to your preferred location. For example:

```
tar -xvf /path/to/project/target/sas-service-1.0.0-SNAPSHOT-bin.tar.gz
```

To run, go to the directory where you extracted it and run the following command to add the
startup script to the PATH:

```
export PATH=$PATH:`pwd`/bin
```

After that, you can run it with:

```
sas-service run
```

You can tweak configuration (including Apache ActiveMQ) in the config directory. Logs will be
created in the logs directory within the installation directory.

You need to have an Apache Active MQ instance up and running with two queues: sas.request and
sas.reply to run this example.

References
----

* [Main Site](http://orpiske.net/)
* [Apache Maven](http://maven.apache.org/)
* [Apache Camel](http://camel.apache.org/)
* [Apache Active MQ](http://activemq.apache.org/)

