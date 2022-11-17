
#Notes




## get container id

docker ps

CONTAINER ID   IMAGE      COMMAND                  CREATED         STATUS         PORTS     NAMES
de914f21e120   uts-demo   "/bin/sh -c 'java -j…"   4 minutes ago   Up 4 minutes             sad_moore


## get ip address

docker inspect de914f21e120 | grep IPAddress

"SecondaryIPAddresses": null,
            "IPAddress": "172.17.0.2",
                    "IPAddress": "172.17.0.2",


default url http://172.17.0.2:8081/myApp/hello
