FROM openjdk:20
LABEL maintainer="archfox"
EXPOSE 8080

ADD target/stampede.jar /app/app.jar

CMD ["java", "-jar", "/app/app.jar"]