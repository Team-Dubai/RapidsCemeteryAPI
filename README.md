# RapidsCemeteryAPI

## Prerequisites

* JDK 8
* Apache Tomcat 8
* PostgreSQL DB

## Frameworks and libraries

* Spring Boot 2.1.2
* Spring Security
* Spring
* Java Persistence API
* Hibernate 5
* Lombok

## Configuration

Configuration for the database connection and email server can be specified at “application.properties“ located at „/src/main/resources/application.properties“. This setting can be changed with each build and they can be overridden through server parameters such as ones Heroku offers. Database tables will be created at first run of the application.
Sample “application.properties” file can be found on the git repository.

## Run Guide

For running in IntelliJ Idea

Open the project.

Install all maven dependencies

Setup connection to the Postgres DB in application.properties

Run the app.

Api documentation and testing can be accessed at: *http://localhost:8080/swagger-ui.html#/*

## Architecture

Rapids Cemetery API was designed to be efficient and easy to maintain. To achieve this, we have focused on high separation of concerns and simple data structuring 

### Model

In model package we have classes that represent tables in the database. We use Java Persistence library for defining model which provides us with ORM capabilities and enables us to define and set properties of tables and columns in the database. Properties are set in the form of annotations such as “@Id, @Column(name = "NAME"), etc.”. Model is also enriched with Lombok library with is used for automatic generation of getters and setters without having them in the code explicitly. 

Documentation for Java Persistence can be found here : https://docs.oracle.com/javaee/7/api/javax/persistence/package-summary.html

Useful examples for Java Persistence and Lombok can be found here:
https://www.vogella.com/tutorials/JavaPersistenceAPI/article.html

### Data Access Layer(dao package)

Data access layer has a data access object for each of the classes from the model package. The scope of this layer is CRUD functionality. Interaction with the database is achieved with Java Persistence and Hibernate ORM-s. This enables us to interact with database through either native SQL code or using Hibernate and Java Persistence tools in Java. On this project most of the interaction with the database is done in the Java Code.

Documentation for Hibernate can be found here:
http://docs.jboss.org/hibernate/orm/5.4/javadocs/

### Service

Service package consists of set of services that can hold some business logic and communicate with the data access layer, and other components such as email server.
Service layer can hold some business logic as well as data validation and data sanitization.

### Controller

Controller is layer where we define our endpoints. For defining endpoints, we are using functionalities provided by Spring Boot Framework using Spring Boot annotations. Each Class represents an endpoint containing several methods. 

### Security

Security package serves for configuring security settings. Security is based on Spring Security library. JWT tokens are used for authentication and authorization on privileged API calls.
JWTAuthenticationFilter

This class defines how users get JWT token, how it’s generated, and how long will it last.

### JWTAutorizationFilter

This class checks is user authorized to make the API call in case API call requires privileges.	

### SecurityConstants

Class containing static parameters for security system as described in code. 

### SpringSecurityConfig

In this class we define which API calls will be available to everybody, and which will require authorization. 

You can find Spring Security documentation at:
https://docs.spring.io/spring-security/site/docs/current/reference/htmlsingle/


## Entering data into the database manually
To enter data into the database manually You will have to download a DBA tool. We suggest using DBeaver, as it’s free and open source solution supporting Linux, OsX and Windows environments. You can download DBeaver here: https://dbeaver.io/download/.
Instructions how to setup and use DBeaver can be found here: https://github.com/dbeaver/dbeaver/wiki/User-Guide

Database connection information will be available after application has been deployed to production environment.

Please note that changing anything in the database should only be performed by advanced user with prior database experience and good knowledge of the application. Please make a backup of the database prior to changing it, as changes may lead to data corruption, and cause application to stop working. During changes to the database application should not be publically available.

## Table description
rc_tag
id -> tags unique ID, auto generated
name-> name of tag

rc_item
id -> items unique ID, auto generated
name -> name of a item
category -> represents items category, can be one of following: GRAVE, TRAIL, PLANT
description -> description of the item
image -> image url
media -> media url
dob -> Date of Birth
dod -> Date of Death
notes -> notes
place_of_birth -> Place of birth
plot -> plot l
veteran_info -> veteran info
place_of_death -> place of death

rc_stop
id -> stops unique ID, auto generated
name -> name of a stop

rc_tour
id -> tours unique ID, auto generated
name -> name of a tour
category -> represents tours category
description -> description of the tour
image -> image url
media -> media url

rc_stop_items - Many to many table
item_id ->id of an item
stop_id ->id of a stop
rc_item_tags - Many to many table
item_id ->id of an item
tag_id -> id of a tag

rc_tour_stops - Many to many table
tour_id -> id of a tour
stop_id -> id of a stop
