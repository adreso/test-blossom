FROM amazoncorretto:17-alpine as builder

WORKDIR application
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} ms-application.jar
RUN java -Djarmode=layertools -jar ms-application.jar extract

FROM amazoncorretto:17-alpine
WORKDIR application
COPY --from=builder application/dependencies/ ./
RUN true
COPY --from=builder application/snapshot-dependencies/ ./
RUN true
COPY --from=builder application/spring-boot-loader/ ./
RUN true
COPY --from=builder application/application/ ./

EXPOSE 8080
ENTRYPOINT ["java", "org.springframework.boot.loader.JarLauncher"]