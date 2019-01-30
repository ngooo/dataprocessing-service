# Data processing service

The purpose of the project is to demonstrate concepts such as REST and Websockets.

## Getting Started

### Prerequisites

Docker

### Testing

To test the application:

```
./mvnw clean test
```

There are three test classes: MessageControllerTest, MessageRepositoryTest and MessageValidationTest. To test them separately:

```
./mvnw clean test -Dtest=<Test Class>
```

### Installing

To generate the jar file:

```
./mvnw clean package
```

To run the project:
```
docker-compose up
```

### Run the application

To run the application, enter the following in the web browser:
```
localhost:8080
```

## REST API

### Send message

Request
```
POST /message
```

```
curl -v localhost:8080/message -H 'Content-type:application/json' -d '{"content": "abrakadabra", "timestamp": "2018-10-09 00:12:12+0100"}'
```

Response
```
{"content":"abrakadabra","timestamp":"2018-10-08 23:12:12+0000"}
```

### Get messages

Request
```
GET /messages
```

```
curl -v localhost:8080/messages -H 'Content-type:application/json'
```

Response
```
{"_embedded":{"palindromeList":[{"content":"aabrakadabra","timestamp":"2018-10-08 23:12:12+0000","longest_palindrome_size":3}]},"_links":{"self":{"href":"http://localhost:8080/messages?page=0&size=5"}},"page":{"size":5,"totalElements":1,"totalPages":1,"number":0}}
```
