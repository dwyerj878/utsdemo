FROM amazoncorretto:17

COPY target/uts-demo-0.0.4-SNAPSHOT.jar uts-demo.jar


CMD java -jar uts-demo.jar

