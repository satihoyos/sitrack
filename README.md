# sitrack

# Getting Started

### Run enviroment
#### Starting up with docker mongoDB and maven
1. Run mongodb image.
```
   docker run --rm --name search-word-dev -d -p27017:27017 mongo:4-bionic
```
2. Download projecto from GitHub.
3. Compile project with maven.
   - if you have maven install.
```
   mvn clean package -DskipTests
   mvn spring-boot:run -Dspring-boot.run.arguments=--spring.data.mongodb.host=localhost
```
   - if you don't have maven install
```
   ./mvnw clean package -DskipTests
   ./mvnw spring-boot:run -Dspring-boot.run.arguments=--spring.data.mongodb.host=localhost
```
#### Starting up with commands docker.
1. Create image docker with spring search-word project.
```
   docker build -t search-words-game .
```
      
2. Create network for the images. 
```
   docker network create --driver bridge search-words-network
```
3. Run mongodb image.
```
   docker run --rm --name search-word-dev --network search-words-network -d -p27017:27017 mongo:4-bionic
```
4. Run search-word image.
```
   docker run --rm --name game --network search-words-network -p 8084:8084 search-words-game
```

docker rmi <image_ID> --force

 
 

 
