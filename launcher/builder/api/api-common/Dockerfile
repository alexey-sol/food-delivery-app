# Stage 1.
# Building the module.
FROM maven:3.8.5-openjdk-17-slim AS module

ARG MODULE
ARG PROFILE

WORKDIR /app

COPY ./pom.xml ./pom.xml

WORKDIR ./$MODULE

COPY ./$MODULE/pom.xml ./pom.xml
RUN mvn -D $PROFILE dependency:go-offline -B

COPY ./$MODULE/src ./src
RUN mvn -D $PROFILE package

# Stage 2.
# Reducing the final image size. We only need the built target.
FROM scratch

ARG MODULE

COPY --from=module /app/$MODULE/target /
