FROM openjdk:8
EXPOSE 8092
ADD target/cold-storage-docker.jar spring-boot-docker.jar 
ENTRYPOINT ["java","-jar","/spring-boot-docker.jar"] 
