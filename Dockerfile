FROM java:8
EXPOSE 8080
ADD /target/server-analyze-audios-0.0.1-SNAPSHOT.jar server-analyze-audios.jar
ENTRYPOINT ["java", "-Dspring.profiles.active=container", "-jar","server-analyze-audios.jar", "container-entrypoint"]