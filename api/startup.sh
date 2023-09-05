#! /bin/bash
mvn -pl app spring-boot:run -D spring-boot.run.profiles=prod &
mvn -pl gateway spring-boot:run -D spring-boot.run.profiles=prod
