FROM openjdk:8-jre

ADD ./target/online-training-0.0.1-SNAPSHOT.jar online-training.jar
RUN mkdir -p /var/www/storages
RUN chown 33:33 /var/www/storages
RUN chmod 755 /var/www/storages
ENTRYPOINT exec java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -Dmaven.test.skip=true -Dspring.profiles.active=prod -jar /online-training.jar