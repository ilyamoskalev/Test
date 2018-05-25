FROM ubuntu:16.04

MAINTAINER Ilya Moskalev

RUN apt-get -y update

ENV PGVER 9.5
RUN apt-get update
RUN apt-get install -y postgresql-$PGVER

USER postgres

RUN /etc/init.d/postgresql start &&\
        psql --command "CREATE USER docker WITH SUPERUSER PASSWORD 'docker';" &&\
        createdb -E UTF8 -T template0 -O docker docker &&\
        /etc/init.d/postgresql stop


RUN echo "host all  all    0.0.0.0/0  md5" >> /etc/postgresql/$PGVER/main/pg_hba.conf

EXPOSE 5432

USER root

RUN apt-get update
RUN apt-get install -y openjdk-8-jdk-headless

RUN apt-get install -y maven

ENV WORK /opt/Test
ADD src/ $WORK/src/
ADD pom.xml $WORK/

WORKDIR $WORK

RUN mvn package

EXPOSE 8081

CMD export JDBC_DATABASE_URL=jdbc:postgresql://localhost:5432/docker &&\
    export DATABASE_USERNAME=docker &&\
    export DATABASE_PASSWORD=docker &&\
    service postgresql start &&\
    java -jar target/Test-1.0-SNAPSHOT.jar
