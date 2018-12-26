FROM openjdk:8-jdk-alpine
VOLUME /tmp
ARG JAR_FILE=target/projeto-conclusao-curso-0.0.1.jar
ADD ${JAR_FILE} tcc.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/tcc.jar"]
EXPOSE 8000