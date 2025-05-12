FROM openjdk:21-jdk
MAINTAINER jnoble57@gmail.com
COPY target/store-purchase-0.0.1-SNAPSHOT.jar store-purchase-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","/store-purchase-0.0.1-SNAPSHOT.jar"]