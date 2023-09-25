ARG DOCKER_NAMESPACE
ARG DOCKER_API_COMMON_REPOSITORY
ARG API_COMMON_VERSION

# Stage 1.
# Get the common library from Docker Registry. It has a JAR which we're going to put in the local
# repository in stage 2.
FROM $DOCKER_NAMESPACE/$DOCKER_API_COMMON_REPOSITORY:$API_COMMON_VERSION AS lib

# Stage 2.
# Building the application. We then pass the result JAR to the stage 3.
FROM maven:3.8.5-openjdk-17-slim AS maven

ARG MODULE
ARG PROFILE
ARG API_COMMON_VERSION

ARG API_COMMON_ARTIFACT_ID=api-common
ARG MAVEN_REPOSITORY=/usr/share/maven/ref/repository
ARG PROJECT=$MAVEN_REPOSITORY/com/github/alexeysol/fooddeliveryapp

ARG FROM=/$API_COMMON_ARTIFACT_ID-$API_COMMON_VERSION.jar
ARG TO=$PROJECT/$API_COMMON_ARTIFACT_ID/$API_COMMON_VERSION/$API_COMMON_ARTIFACT_ID-$API_COMMON_VERSION.jar

# Before downloading dependencies and building the application, first we need to put the library JAR
# in the local repository.
COPY --from=lib $FROM $TO

WORKDIR /app

COPY ./pom.xml ./pom.xml

WORKDIR ./$MODULE

# Include POM files and download the dependencies. Since we usually don't update POM files as
# often as source code, the cache should be more persistent (so we need to download dependencies
# less often when building) [1].
COPY ./$MODULE/pom.xml ./pom.xml
RUN mvn -D $PROFILE -D maven.repo.local=$MAVEN_REPOSITORY dependency:go-offline -B

# Build the result JAR file.
COPY ./$MODULE/src ./src
RUN mvn -D $PROFILE -D maven.test.skip=true -D maven.repo.local=$MAVEN_REPOSITORY package

# Stage 3.
# Reducing the result size of the image by using this relatively slim JDK image. We'll be able to
# run the application via regular "java" command [1].
# The only worrying issue here is that the image uses MUSL which would potentially cause
# problems [2].
FROM bellsoft/liberica-openjdk-alpine-musl:17.0.8.1

ARG MODULE

WORKDIR /app

COPY --from=maven /app/$MODULE/target/$MODULE.jar ./

# [1]. https://whitfin.io/blog/speeding-up-maven-docker-builds/
# [2]. https://www.reddit.com/r/voidlinux/comments/muoqis/what_are_the_advantages_of_using_musl_in_place_of/
