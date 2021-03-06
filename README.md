# Multi Player Game

Game repo link : https://github.com/ysainath/Game-auto1/tree/master/Game

This project is command line based application which allows users to play multiple games.
User can create a character or sign in with already registered character.
User can take a fighting challenge and explore the game to collect the items.

### Prerequisites

1. Eclipse IDE with java 8 compatible
2. MySql installed in your local and should be running on port 3306
3. Maven installed

## Getting Started

Checkout this project to your local repo and build the application

### To Run the application from command line
 
mvn spring-boot:run

### To Run the application from eclipse

1. Import code into local eclipse
2. Run GameApplication.java

This application can also run H2 In-Memory database but it needs change in below file.
* Mysql datasource properties has to be removed and make use of h2 database config

https://github.com/ysainath/Game-auto1/blob/master/Game/src/main/resources/application.properties

### Integration tests

Built with [System Rules] (http://stefanbirkner.github.io/system-rules/#TextFromStandardInputStream) library for STD inputs

This is the link for Java class : https://github.com/ysainath/Game-auto1/blob/master/Game/src/test/java/com/auto1/group/game/service/GameHelperTest.java

### Unit tests

SpringRunner Junits are written for Services,Factory and repository classes .

### Demo 

   #### Welcome to Multi Player Game ###

  Select below options to proceed.
  
   * 1.Create a character.
   * 2.Do you already know your registered Character details?
   * 3.Exit

  ...........

## Built With

* [Spring boot - 1.5.10 RELEASE](https://docs.spring.io/spring-boot/docs/) - The Spring Boot framework
* [Maven](https://maven.apache.org/) - Dependency Management 
* [MySql](https://dev.mysql.com/doc/) - Database
* project tags https://github.com/ysainath/Game-auto1/tree/master/Game

## Authors

* **Sainath Yelamanchali** - https://github.com/ysainath/
