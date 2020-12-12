FROM ubuntu

LABEL version="1.0.0" description="SPARK docker" maintainer="Matheus dos Santos Silva <matheus.santos.silva@ufms.br>"

RUN apt-get -y update
RUN apt-get -y install openjdk-8-jdk
RUN apt-get -y install scala
RUN apt-get -y update
RUN /bin/echo debconf shared/accepted-oracle-license-v1-1 select true | /usr/bin/debconf-set-selections

RUN apt-get -y install curl
RUN curl -s http://d3kbcqa49mib13.cloudfront.net/spark-2.2.0-bin-hadoop2.6.tgz | tar -xz -C /usr/local/
RUN cd usr/local && ln -s spark-2.2.0-bin-hadoop2.6 spark


ENV JAVA_HOME /usr/lib/jvm/java-8-openjdk-amd64/jre
ENV SPARK_HOME /usr/local/spark
ENV PATH $PATH:/usr/local/spark/bin


ENTRYPOINT ["bash"]