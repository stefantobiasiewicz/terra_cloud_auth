FROM amazoncorretto:18
EXPOSE 10000
ADD /target/auth-0.0.1.jar auth.jar
ENTRYPOINT ["java","-jar","auth.jar"]