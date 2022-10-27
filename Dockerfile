FROM maven:latest as builder
ARG build_folder

WORKDIR build
COPY . .
RUN mvn clean install -DskipTests


FROM openjdk:latest as deployer
WORKDIR app
#COPY --from=builder build/authentication-service/target/*.jar app.jar
RUN echo $build_folder
COPY --from=builder build/$build_folder/target/*.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]

#RUN echo ${build_folder}