FROM openjdk:8-jre-alpine
MAINTAINER  Daniel PHAM Jeremi Duarte Guillaume Bolzonella
 
RUN apk update
RUN apk add --update bash && rm -rf /var/cache/apk/*
WORKDIR /
COPY /Talismane /
RUN apk add openjdk8
ENV JAVA_HOME /usr/lib/jvm/java-1.8-openjdk
ENV PATH $PATH:$JAVA_HOME/bin
CMD java -Xmx1G -Dconfig.file=talismane-en-5.2.0.conf -jar talismane-core-5.2.0.jar --analyse --startModule=tokeniser --endModule=posTagger --sessionId=en --mode=server --encoding=UTF8 
