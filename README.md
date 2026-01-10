# h2m
h2 database start stop manager for plate


## mvn
### pg server start
```
mvn exec:java "-Dexec.mainClass=com.uchicom.h2m.PgServerMain"
```
### tcp server start
```
mvn exec:java "-Dexec.mainClass=com.uchicom.h2m.TcpServerMain"
```
### web server start
```
mvn exec:java "-Dexec.mainClass=com.uchicom.h2m.WebServerMain"
```
