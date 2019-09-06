FROM openjdk:8-jdk-alpine
VOLUME /tmp
ARG JAR_FILE=target/DockerWebApp-1.0.jar
ARG PORT=8080
EXPOSE ${PORT}

COPY ${JAR_FILE} app.jar
ENTRYPOINT ["sh", "-c", "java -Djava.security.egd=file:/dev/./urandom -Dserver.port=$PORT -jar /app.jar"]
