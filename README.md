# find-me-a-buddy

## Setup

### Local database

1) Install [Docker](https://docs.docker.com/machine/install-machine/)
2) Pull MySQL image
```
docker pull mysql:8.0.11
```

3) Create and run your MySQL container
```
docker run -p 3306:3306 --name mysql_server -e MYSQL_ROOT_PASSWORD=root -e MYSQL_DATABASE=maindb -d mysql:8.0.11
```
