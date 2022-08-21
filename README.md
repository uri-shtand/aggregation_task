# aggregation_task

a backend service which will be used internally by engineers to manage CRM cases. 

we are dealing with two different CRM systems, managed separately and independently, called “Banana” and “Strawberry”.


## Notes about the solution:

* The object examples include both "Customer_ID" and "Customer ID". Used "Customer ID".
* Unitests are minimal due to time constraints.
* No implementation of negative/bad flow and no tests for that.
* Used a single model object for banana and strawberry cases. 
  * In real life would probably split to two objects with shared interface even if identical.
* 
