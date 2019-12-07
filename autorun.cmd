docker network create tp-network & docker build -f hsqldb.dockerFile -t tp-database . & docker build -f tp-abdoulaye.dockerFile -t tp-abdoulaye .
start docker run -p 9001:9001 --name tp-database --network tp-network tp-database
timeout 10
start docker run -p 9192:8080 --name tp-abdoulaye --network tp-network tp-abdoulaye
