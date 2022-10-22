FROM maven:3-amazoncorretto-17 AS temp
RUN mkdir "notification-service"
COPY /src /notification-service/src
COPY pom.xml /notification-service/pom.xml
WORKDIR /notification-service
RUN mvn package

FROM amazoncorretto:17
RUN mkdir "notification-service"
EXPOSE 9092
COPY --from=temp /notification-service/target/notification-service.jar  /notification-service/notification-service.jar
ENTRYPOINT java -jar /notification-service/notification-service.jar
