#Download base image ubuntu 18.04
FROM openjdk:8-jre-alpine
MAINTAINER Daniel Pham <phamdaniel64000@gmail.com> Duarte Jeremi Bolonella Guillaume
RUN apk update
RUN apk add wget
RUN apk add --update bash && rm -rf /var/cache/apk/*
WORKDIR /
RUN wget http://www.cis.uni-muenchen.de/~schmid/tools/TreeTagger/data/tree-tagger-linux-3.2.2.tar.gz
RUN wget http://www.cis.uni-muenchen.de/~schmid/tools/TreeTagger/data/tagger-scripts.tar.gz
RUN wget http://www.cis.uni-muenchen.de/~schmid/tools/TreeTagger/data/install-tagger.sh
RUN wget http://www.cis.uni-muenchen.de/~schmid/tools/TreeTagger/data/french.par.gz
RUN sh install-tagger.sh
RUN tar xvf tree-tagger-linux-3.2.2.tar.gz
WORKDIR /
COPY tree-tagger /
COPY /parameter_Files /
COPY LemmePosTreeTagger-0.0.1-SNAPSHOT.war /
RUN apk add openjdk8
ENV JAVA_HOME /usr/lib/jvm/java-1.8-openjdk
ENV PATH $PATH:$JAVA_HOME/bin
CMD java -jar LemmePosTreeTagger-0.0.1-SNAPSHOT.war
