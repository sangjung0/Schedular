# Deployment Dockerfile

FROM ubuntu:22.04

ENV JAVA_VERSION=21
ENV JAVA_HOME=/usr/lib/jvm/java-${JAVA_VERSION}-oracle

RUN apt-get update && apt-get install -y wget
# if you want to use XLaunch remove '#'
#RUN apt-get install -y x11vnc xvfb
#ENV DISPLAY host.docker.internal:0.0

ADD https://download.oracle.com/java/${JAVA_VERSION}/latest/jdk-${JAVA_VERSION}_linux-x64_bin.tar.gz /tmp/jdk.tar.gz
RUN mkdir -p "${JAVA_HOME}" && \
    tar --extract --file /tmp/jdk.tar.gz --directory "${JAVA_HOME}" --strip-components=1 && \
    update-alternatives --install "/usr/bin/java" "java" "${JAVA_HOME}/bin/java" 1 && \
    update-alternatives --install "/usr/bin/javac" "javac" "${JAVA_HOME}/bin/javac" 1 && \
    rm /tmp/jdk.tar.gz

ENV PATH=${JAVA_HOME}/bin:${PATH}

WORKDIR /app
COPY . /app

RUN cp /app/example.txt /root

CMD ["java","-jar", "/app/Scheduler.jar"]
