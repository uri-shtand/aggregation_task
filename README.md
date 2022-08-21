# aggregation_service

a backend service which will be used internally by engineers to manage CRM cases. 

we are dealing with two different CRM systems, managed separately and independently, called “Banana” and “Strawberry”.

The aggregation service refreshes the CRM state every 4 hours.

## Running the service

The service requires MongoDB to operate. In its current state - only embedded MONGO is supported.

It also requires JAVA 11 at least 

In order to use an external MongoDB, start by changing the de.flapdoodle.embed.mongo maven artifact to "test" artifact and add relevant application properties.
(Did not test due to lack of time)

You can check the application properties for the things which can be configured 

This is a spring boot application. 
### build using maven 
    mvnw clean install

### run using mvnw spring boot plugin 
    mvnw spring-boot:run

## URLs

GET http://serverUrl/caseagg/aggregation/all

You can also use the optional request params providerName and caseStatus to filter results.

POST http://serverUrl/caseagg/aggregation/refresh

## Notes about the implementation:

* The object examples include both "Customer_ID" and "Customer ID". Used "Customer ID".
* Unitests are minimal due to time constraints.
* No implementation of negative/bad flow and no tests for that.
* Used a single model object for banana and strawberry cases. 
  * In real life would probably split to two objects with shared interface even if identical.
* Aggregation logic is to unify all cases with same error code , provider id and case status
* Implemented partial filtering due to time constraints.
* Did not implement sort (which I would in real life) due to time constraints

