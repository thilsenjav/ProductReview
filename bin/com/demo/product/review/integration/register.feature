@registration
Feature: register directly here

  Scenario: user enters the correct details 
    Given Web context is setup 
      And DB is empty
	When client requests POST /api/user/ with JSON data 
	"""
	{"name":"senthil","email":"senthil@gmail.com","password":"pwd12345"}
	"""
	Then response code should be 200 
	  And user should be stored in DB with id 1
	  And Response body should be 'User registration successful'
	  And Response header should have 'location' with value 'http://localhost:8080/api/user/1' 
	  