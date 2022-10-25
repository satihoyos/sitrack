# sitrack

# Getting Started

### Run enviroment
#### Starting up with docker mongoDB and maven
1. Run mongodb image.
```
   docker run --rm --name search-word-dev -d -p27017:27017 mongo:4-bionic
```
2. Download project from GitHub.
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

#### Starting up with docker-compose.
```
   docker-compose up
```

### Api
there are 5 methods in this api.
- #### Create Game
```
   Method:Post
   url: http://localhost:8084/alphabetSoup
   
   request:
   {
       "w":80,
       "h":80,
       "ltr":true,
       "rtl":true,
       "ttb":true,
       "btt":true,
       "d":true
   }
   
   response:
   {
       "id": "UUID"
   }
```
- #### Get view Game
```
   Method:Get
   url: http://localhost:8084/alphabetSoup/view/{UUID}
   
   response:
      data in txt 
```
  
- #### Get Words Game
```
   Method:Get
   url: http://localhost:8084/alphabetSoup/list/{UUID}
   
   response:
      [ "string", "string"....]
```

- #### Get Solution Game
```
   Method:Get
   url: http://localhost:8084/alphabetSoup/solution/{UUID}
   
   response:
      [  
            {
               "position" : {
                   "sr" : 6,
                   "sc" : 0,
                   "fr" : 6,
                   "fc" : 11
               },
               "content" : "refrigerador",
               "length" : 12,
               "type" : "HORIZONTAL"
           }, 
           {
               "position" : {
                   "sr" : 10,
                   "sc" : 0,
                   "fr" : 10,
                   "fc" : 6
               },
               "content" : "celular",
               "length" : 7,
               "type" : "HORIZONTAL_REVERTED"
           },
           ...,
           ...
        ]
```
- #### Find Word 
 Note: Rows and columns start since position zero (0)
```
   Method:Pur
   url: http://localhost:8084/alphabetSoup/{UUID}
   
   request:
     {
       "sr": 0,
       "sc": 75,
       "fr": 3,
       "fc": 75
     }
     
     response:
     
     { "mensaje": "Palabra encontrada" }
```
 

 
