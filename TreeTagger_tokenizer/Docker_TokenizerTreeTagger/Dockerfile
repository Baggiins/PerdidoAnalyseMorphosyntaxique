FROM openjdk:8-jre-alpine
MAINTAINER  Daniel PHAM <phamdaniel64000@gmail.com> Jeremi Duarte Guillaume Bolzonella
 
RUN apk update
RUN apk add perl
RUN apk add --update bash && rm -rf /var/cache/apk/*
WORKDIR /
COPY TokenizeTreeTagger-0.0.1-SNAPSHOT.war /
COPY utf8-tokenize.perl /
COPY /abbreviations /
RUN apk add openjdk8
ENV JAVA_HOME /usr/lib/jvm/java-1.8-openjdk
ENV PATH $PATH:$JAVA_HOME/bin
CMD java -jar TokenizeTreeTagger-0.0.1-SNAPSHOT.war
