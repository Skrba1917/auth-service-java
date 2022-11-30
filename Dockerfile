

FROM adoptopenjdk/maven-openjdk11
COPY ./ ./
RUN mvn clean package
ENTRYPOINT ["java","-jar","target/AuthService-0.0.1-SNAPSHOT.jar"]
EXPOSE 8080