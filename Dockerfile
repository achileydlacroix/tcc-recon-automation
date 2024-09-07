FROM kalilinux/kali-rolling:latest

WORKDIR /data

RUN apt-get update

RUN apt-get upgrade -y

RUN apt-get dist-upgrade -y

RUN apt-get install git -y

RUN apt-get install golang -y

RUN apt-get install python3 -y

RUN apt-get install python3-pip -y

RUN go install -v github.com/projectdiscovery/subfinder/v2/cmd/subfinder@latest
RUN mv /root/go/bin/subfinder /usr/bin/

RUN go install -v github.com/projectdiscovery/nuclei/v2/cmd/nuclei@latest
RUN mv /root/go/bin/nuclei /usr/bin/
RUN nuclei -update-templates