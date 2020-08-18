FROM openjdk:8-jre-alpine
MAINTAINER  Daniel PHAM <phamdaniel64000@gmail.com> Jeremi Duarte Guillaume Bolzonella
 
RUN apk update
RUN apk add --update bash && rm -rf /var/cache/apk/*
WORKDIR /
COPY /Talismane /
COPY LemmePosTalismane-0.0.1-SNAPSHOT.war /
RUN apk add openjdk8
ENV JAVA_HOME /usr/lib/jvm/java-1.8-openjdk
ENV PATH $PATH:$JAVA_HOME/bin
CMD java -jar LemmePosTalismane-0.0.1-SNAPSHOT.war
