FROM openjdk:8-jre-alpine
MAINTAINER Daniel Pham <phamdaniel64000@gmail.com> Duarte Jeremi <jrmduarte29@gmail.com> Bolonella Guillaume
WORKDIR /
COPY Detecteur.war /

RUN apk add openjdk8
ENV JAVA_HOME /usr/lib/jvm/java-1.8-openjdk
ENV PATH $PATH:$JAVA_HOME/bin

CMD java -jar Detecteur.war
