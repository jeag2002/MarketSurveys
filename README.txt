PROJECTS:

Java project created in Eclipse Mars 2 Release (4.5.2); Windows 10

Technologies
Java jdk1.8.0_101 (64 bits) ==> Mandatory. 
Maven 3.3
JUnit 4.9
Mockito
SpringBoot 1.5.3
Hibernate/JPA/H2
JAX-RS
HATEOAS
Swagger
Spring Zuul (Netflix Zuul)

Three Projects:

1)CaravelloTestServer:

Server part of the project.

1.1)Bussines Layer:
Four functionalities:

1.1.1) /getmarketsurveylist (POST)

Get a list of market surveys from the input criteria contained into RequestWrapper Object

1.1.2) /asyncgetmarketsurveylist (POST)

Same functionality of 1.1) but doing the query asynchonically.

1.1.3) /getmarketsurveyslistcache (GET)

Get the information of previous query by uuid

1.1.4) /getmarketsurvey (GET)

Get the information of a market survey.

For more information, launch the SpringBoot server; and access to http://localhost:8090/swagger-ui.html

1.2)Integration Layer:

One table MarketData.

create table MarketData(
marketid int not null primary key auto_increment,
providerid varchar(10),
gender varchar(2),
age integer,
currency varchar(3),
income integer,
country varchar(3),
frecuency varchar(10),
channel varchar(10)
);

2) CaravelloZuulGatewayProxy

Spring Boot Gateway/Proxy server

Redirect calls http://<host>:8080/getmarketsurveylist to

http://<host>:8090/getmarketsurveylist or
http://<host>:8090/getmarketsurveylistcache

depending on method mode (GET OR POST)

3) CaravelloTestClient:

Simple java project testing getmarketsurvey (GET); getmarketsurveylist (POST); asyncgetmarketsurveylist (POST)