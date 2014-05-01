Otavio's Examples: TCS Persistence
============

This project implements a simple REST webservice that receives CSP references and adds them to an
Apache Cassandra database. It runs on Apache Tomcat.

URLs
----


Get all CSPs stored:

Method: GET
```
tcs.solar.info/domain/
```

Create a new CSP

POST:
```
tcs.solar.info/domain/{domain}
```

JSON Request:
```
    {
        "name": "HomeMadeCSP"
    }
```

Obtain all words associated associated with all the CSPs (in progress)

Method: GET
```
tcs.solar.info/tagcloud/
```

Get the tag cloud for CSP (in progress):

Method: POST
```
tcs.solar.info/tagcloud/domain
```



Add a new reference to the database

POST:
```
tcs.solar.info/references/{domain}
```


References
----

* [Main Site](http://orpiske.net/)
* [Apache Maven](http://maven.apache.org/)
* [Spring REST Tutorial](https://github.com/spring-guides/tut-rest)
* [Netflix Astyanax](https://github.com/Netflix/astyanax/wiki)


