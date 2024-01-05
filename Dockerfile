FROM eclipse-temurin:17-jdk-jammy

COPY target/filesbkp-0.0.1-SNAPSHOT.jar /tmp

CMD ["echo", "Hello"]
