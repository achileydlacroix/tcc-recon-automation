FROM kalilinux/kali-rolling:latest
MAINTAINER Achiley D. Lacroix

WORKDIR /scripts

WORKDIR /data

RUN apt-get update

RUN apt-get upgrade -y

RUN apt-get dist-upgrade -y

RUN apt-get install locate -y

RUN apt-get install wget -y

RUN apt-get install git -y

RUN apt-get install vim -y

RUN apt-get install golang -y

RUN apt-get install python3 -y

RUN apt-get install python3-pip -y

RUN pip3 install uuid

RUN go install -v github.com/projectdiscovery/subfinder/v2/cmd/subfinder@latest
RUN mv /root/go/bin/subfinder /usr/bin/

RUN go install -v github.com/projectdiscovery/nuclei/v2/cmd/nuclei@latest
RUN mv /root/go/bin/nuclei /usr/bin/
RUN nuclei -update-templates

RUN wget -q https://download.java.net/java/GA/jdk17.0.10/aa891ca53a004bd6aeca05bca5f1182f/36/GPL/openjdk-17.0.10_linux-x64_bin.tar.gz -O /tmp/openjdk-17.0.10.tar.gz && \
    tar xzf /tmp/openjdk-17.0.10.tar.gz -C /usr/lib/jvm && \
    update-alternatives --install /usr/bin/java java /usr/lib/jvm/jdk-17.0.10/bin/java 100 && \
    update-alternatives --install /usr/bin/javac javac /usr/lib/jvm/jdk-17.0.10/bin/javac 100
ENV JAVA_HOME /usr/lib/jvm/jdk-17.0.10

ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java", "-jar", "/app.jar"]