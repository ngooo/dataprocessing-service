FROM openjdk:8
COPY target/dataprocessing-1.0-SNAPSHOT.jar /app.jar
CMD ["java", "-jar", "/app.jar"]
