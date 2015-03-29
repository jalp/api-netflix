# RUNNING
How to run and launch unit tests.

## Running service
    mvn spring-boot:run

## Running tests
    mvn test

# CURL 

## GET
    curl http://localhost:8080/account/1/saved_ads

## POST
    curl http://localhost:8080/account/1/saved_ads?list_id=1 -X POST -H "X-NGA-SOURCE: 90" -d @d.json -H "Content-type: application/json"

d.json has this format {"companyAd":true,"listId":2,"subject":null, "data":12313} for example.