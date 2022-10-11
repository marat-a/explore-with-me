FROM amazoncorretto:11-alpine-jdk
COPY target/*.jar app.jar
#ENV TZ="Europe/Moscow"
ENTRYPOINT ["java","-jar","/app.jar"]
ENV POSTGRES_PASSWORD=test
ENV POSTGRES_USER=test