FROM openjdk:12

#DockerFile config for tp-prog-comp images

ENV PROJECT_HOME /home/tp
ENV SRC_FILE ./target/progcomp-0.0.1-SNAPSHOT.jar
WORKDIR $PROJECT_HOME

COPY $SRC_FILE $PROJECT_HOME/tp.jar
COPY ./tp.sh $PROJECT_HOME/tp.sh
RUN chmod -R 777 $PROJECT_HOME
EXPOSE 8080
ENTRYPOINT $PROJECT_HOME/tp.sh