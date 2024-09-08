FROM openjdk:17
LABEL authors="debarshisaha"
# This is working directory where files will be copied
WORKDIR /usr/src/mytestapp
# "." means current working directory, here copying jar file from target folder to current working directory with name demo.jar
COPY target/dropbookmarks-0.0.1-SNAPSHOT.jar /demo.jar
COPY ./config.yml /usr/src/mytestapp/
CMD ["java","-jar","/demo.jar","server","config.yml"]