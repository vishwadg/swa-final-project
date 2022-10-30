FROM maven:latest as builder
WORKDIR build
COPY . .
RUN mvn clean install -DskipTests


FROM openjdk:latest as deployer
ARG build_folder
WORKDIR app
RUN echo $build_folder
COPY --from=builder build/$build_folder/target/*.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]