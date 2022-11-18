
#Notes

## Add command line processing

usage: MyServer
 -b,--bind <arg>          bind to address
 -d,--debug               Turn on debugging
 -h,--help                show help
 -k,--keyfile <arg>       pcs12/rsa keyfile
 -p,--port <arg>          Set the port
 -s,--ssl                 Turn on ssl
 -w,--keypassword <arg>   keyfile password



## get container id

docker ps

CONTAINER ID   IMAGE      COMMAND                  CREATED         STATUS         PORTS     NAMES
de914f21e120   uts-demo   "/bin/sh -c 'java -j…"   4 minutes ago   Up 4 minutes             sad_moore


## get ip address

docker inspect de914f21e120 | grep IPAddress

"SecondaryIPAddresses": null,
            "IPAddress": "172.17.0.2",
                    "IPAddress": "172.17.0.2",


sample default
- http://172.17.0.2:8081/myApp/hello

sample resteasy url
- https://127.0.0.1:8443/myApp/rest/uts

## create key

$JAVA_HOME/bin/keytool -genkeypair -alias uts-tls -keyalg RSA -dname "cn=uts, ou=apps, o=jcd-dev.net, c=US" -validity 365 -keystore keystore.pfx -keypass mypa55wd -storepass myspa55wd -v

