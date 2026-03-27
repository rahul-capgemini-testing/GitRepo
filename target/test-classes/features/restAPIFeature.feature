Feature: User Registration
 
Background:
Given the baseURI "https://demoqa.com"
When send the post request "/Account/v1/User" with user details
|username|password|
|Julius|Julius@123|
|Romeo|Romeo@123|
 
Scenario Outline: Creating New User
Then validate response status code with 201
 
 
Scenario: User creation Failed
Then validate response status code with 400