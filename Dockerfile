FROM amazoncorretto:17

COPY target/uts-demo-0.0.4-SNAPSHOT.jar uts-demo.jar
COPY keystore.pfx keystore.pfx

CMD java -jar uts-demo.jar --keyfile keystore.pfx --keypassword myspa55wd --port 8443 --ssl

