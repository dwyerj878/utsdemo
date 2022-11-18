FROM amazoncorretto:17

RUN mkdir uts

COPY target/uts-demo-0.0.4-SNAPSHOT.jar uts/uts-demo.jar
COPY target/dependency/*.jar uts/
COPY keystore.pfx uts/keystore.pfx

CMD java -cp uts/*:uts/uts-demo.jar net.dev.jcd.uts.MyServer  --keyfile uts/keystore.pfx --keypassword myspa55wd --port 8443 --ssl 

