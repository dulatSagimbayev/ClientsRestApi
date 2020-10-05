## Table of contents
* [General info](#general-info)
* [Technologies](#technologies)
* [REST APIs Endpoints](#REST-APIs-Endpoints)
* [Database](#Database)

## General info
This is example of Clients information storage application providing REST API.
The entire application is contained within the ClientsApi file.
<br/>There are 2 files <code>ClientControllerTest.java</code> and <code>PhoneNumberControllerTest.java</code> which provides
simle unit tests using Mockito to <code>create</code>, <code>get</code>, <code>update</code> and <code>delete</code> operations.
<br/> Start to use Swagger - <code>/localhost:8080/swagger-ui/</code>
	
## Technologies
Project is created with:
* PostgreSQL version : 13
* Spring Boot version: 2.3.4
* Java version : 11
* Swagger version : 3.0.0
	
## REST APIs Endpoints

### Create a Client entity

```
POST /api/clients
Accept: application/json
Content-Type: application/json

{
    "name" : "Dulat",
    "lastName" : "Sagimbayev",
    "secondName" : "Nokmetzhanovich"
}

```

### Get all Clients information with their numbers

```
GET /api/clients
Accept: application/json
Content-Type: application/json
```

### Update Client 

```
PUT /api/clients/{clientId}/refresh
Accept: application/json
Content-Type: application/json

{
    "name" : "Dulat",
    "lastName" : "Sagimbayev",
    "secondName" : "Nokmetzhanovich"
}

```

### Delete Client 

```
DELETE /api/clients/{clientId}/remove
Accept: application/json
Content-Type: application/json
```

Expected output :
```
Client with id= {clientId} and his numbers are deleted successful
```

### Create Phone number entity 

```
POST /api/clients/{clientId}/numbers
Accept: application/json
Content-Type: application/json

{
    "type" : "Mobile",
    "phoneNumber" : "8-777-322-43-12"
}

```

The input for the "type" property is constrained by the generated <mark>**@RightType**</mark> annotation, 
which only allows the specified type ("Mobile", "Home" and "Office")

### Get Phone numbers by Client id

```
GET /api/clients/{clientId}/numbers
Accept: application/json
Content-Type: application/json
```

### Update numbers by Client id and Phone number id

```
PUT /api/clients/{clientId}/refresh/{numberId}
Accept: application/json
Content-Type: application/json

{
    "type" : "Mobile",
    "phoneNumber" : "8-777-322-43-12"
}

```

### Delete Phone number 

```
DELETE /api/clients/{clientId}/numbers/{numberId}/remove
Accept: application/json
Content-Type: application/json
```

Expected output :
```
Phone number with id= {numberId} is deleted successful
```

## Database

### Database configuration

```
spring.datasource.url=jdbc:postgresql://localhost:5432/clientsDB
spring.datasource.username=root
spring.datasource.password=postgreadmin1236
```

### Clients table view ("clients")

| id | name | last_name | second_name |
|----|------|-----------|-------------|
|    |      |           |             |

### Phone numbers table view ("numbers")

| id | type | phone_number | client_id |
|----|------|--------------|-----------|
|    |      |              |           |

The input for the "type" property is constrained by the generated <mark>**@RightType**</mark> annotation, 
which only allows the specified type ("Mobile", "Home" and "Office")