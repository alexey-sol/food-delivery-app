#! /bin/bash
mvn -pl app spring-boot:run -D spring-boot.run.profiles=dev &
mvn -pl gateway spring-boot:run -D spring-boot.run.profiles=dev
