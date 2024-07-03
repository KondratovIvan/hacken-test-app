# hacken-test-app

To run this application you must

**1. Docker**
* Download Docker [Here](https://docs.docker.com/get-docker/). Hint: Enable Hyper-V feature on windows and restart;
* Then open powershell and check:
```bash
docker info
```
or check docker version
```bash
docker -v
```
or docker compose version
```bash
docker-compose -v
```

**2. Spring boot app**
* Clone the repository:
```bash
git clone https://github.com/KondratovIvan/hacken-test-app
```
* Build the maven project:
```bash
mvn clean install
```
* Running the containers:
This command will build docker image.
```bash
docker build -t hacken-test-app .
```  
This command will build the docker containers and start them.
```bash
docker-compose up
```

**Note**

All commands should be run from project root (where docker-compose.yml locates)

* If you have to want to see running containers. Checklist docker containers
```bash
docker container list -a
```
or
```bash
docker-compose ps
```

**3. Swagger**

* After application start visit http://localhost:8081/swagger-ui/index.html to find dynamic documentation for app endpoints

**4. Postman**

* Download and install the Postman request client to send HTTP requests

**5. Metrics**

* After trying some endpoints visit http://localhost:8081/actuator/prometheus to find general application metrics and metrics of app methods callings (csv_parseMethod_calls_total, csv_searchMethod_calls_total)

**6. Stop app**

*  Stop containers:
```bash
docker-compose down
```
