FROM public.ecr.aws/ews-network/amazoncorretto:8
EXPOSE 8080
ARG JAR_FILE=target/coveragesvc-0.0.1-SNAPSHOT.jar
ADD ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]