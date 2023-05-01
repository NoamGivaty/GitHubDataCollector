FROM pixelgroup/docker-java-git:11.0.4-jdk
COPY target/GitHubDataCollector-0.0.1-SNAPSHOT.jar /app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
