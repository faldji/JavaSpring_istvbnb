FROM openjdk:12

#DockerFile config for  hsqldb images
ENV HSQLDB_HOME /home/tp-prog-comp/hsqldb
ENV HSQLDB_FILE ./hsqldb

COPY $HSQLDB_FILE $HSQLDB_HOME
COPY ./tp-db.sh $HSQLDB_HOME
RUN chmod -R 777  $HSQLDB_HOME
EXPOSE 9001:9001
ENTRYPOINT $HSQLDB_HOME/tp-db.sh