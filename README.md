# Dragons of Mugloar

Purpose is to win the game by creating dragon strong enough for knight to beat
Application runs the game number of times and logs result to text file in csv format.

CSV Format: 
* gameId
* Knight stats
* Weather report
* Dragon stats
* Game Result

### How to run
Run as simple spring boot application:
>`mvn package && java -jar target\mugloar-0.0.1-SNAPSHOT.jar 5`

where 5 how many times to play the game (by default 1).
Or by simply using maven spring boot run plugin: 
>`mvn spring-boot:run -Dspring-boot.run.arguments=5`


### Tech stack:
* Maven - dependency management
* Spring Boot - for dependency injection
* RxJava - for good api and managing callbacks
* Retrofit - wrapping rest api calls in RxJava way
* Lombok - removing setters/getters boilerplate 