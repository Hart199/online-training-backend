FROM openjdk:8-jre

ADD ./target/online-training-0.1.0.jar online-training.jar
ENTRYPOINT exec java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -Dspring.profiles.active=prod -jar /online-training.jar