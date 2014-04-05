Otavio's Apache Camel Examples: TCS Persistence
============

This project implements a simple Cassandra persistence layer used by the TCS
module in the set of Apache Camel examples.

Usage
----

Once you have an Apache Cassandra instance up and running, you can setup the persistence
using these commands:


- Create the keyspace:

```
CREATE KEYSPACE tcs WITH REPLICATION = { 'class' : 'SimpleStrategy', 'replication_factor' : 1 };
```


- Create the tables in the keyspace:

```
USE tcs;

CREATE TABLE references (
  hash text,
  domain text,
  reference_date timestamp,
  inclusion_date timestamp,
  reference_text text,
  PRIMARY KEY (hash)
) WITH COMPACT STORAGE;


CREATE TABLE csp (
  name text,
  domain text,
  PRIMARY KEY (domain)
) WITH COMPACT STORAGE ;

```

Obs.: the compact storage is required so that it works w/ pre CQL3 applications.

- After running you can clean it up with:

```
DROP KEYSPACE tcs;
```

References
----

* [Main Site](http://orpiske.net/)
* [Apache Maven](http://maven.apache.org/)
* [Apache Cassandra](http://cassandra.apache.org/)
* [Hector](http://1and1.github.io/hector/build/html/index.html)

