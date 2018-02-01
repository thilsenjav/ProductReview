@registration
Feature: edit personal details

  Scenario: user enters the correct details 
    Given Web context is setup 
      And following user exists
	  | id | name                  | email                         | password     |
	  | 1  | senthil               | senthil@gmail.com             | pwd12345     |
	When client requests POST '/api/user/edit/{1}' with JSON data 
	"""
	{"name":"senthil","email":"senthil@gmail.com","password":"pwd54321"}
	"""
	Then response code should be 200 
	  And user should be stored in DB with id 1
	  And Response body should have a success message 'User updated successfully'
	  And Response header should have 'location' with value "http://localhost:8080/api/user/1" 

  Scenario: user enters the incorrect email 
    Given Web context is setup 
      And following user exists
	  | id  | name                  | email                         | password     |
	  | 1   | senthil               | senthil@gmail.com             | pwd54321     |
	When client requests POST '/api/user/edit/{1}' with JSON data, incorrect email 
	"""
	{"name":"senthil","email":"senthil#.gmail.com","password":"pwd54321"}
	"""
	Then response code should be 400
	  And Response body should have a failure message 'Incorrect email'

  Scenario: user enters the incorrect password 
    Given Web context is setup 
    And following user exists
	  | id | name                  | email                         | password     |
	  | 1  | senthil               | senthil@gmail.com             | pwd54321     |
	When client requests POST '/api/user/edit/{1}' with JSON data, incorrect pwd pattern 
	"""
	{"name":"senthil","email":"senthil@gmail.com","password":"pwd01"}
	"""
	Then response code should be 400
	  And Response body should have a failure message 'Incorrect password, password should have 8 characters'

  Scenario: user enters an emailid which is already there in DB
    Given Web context is setup 
	  And following users exist
	  | id | name                  | email                         | password     |
	  | 1  | senthil               | senthil@gmail.com             | pwd54321     |
	  | 2  | natraj                | natraj@gmail.com              | pwd54321     |
	When client requests POST '/api/user/edit/{1}' with JSON data, 
	"""
	{"name":"senthil","email":"natraj@gmail.com","password":"pwd54321"}
	"""
	Then response code should be 409
	  And Response body should have a failure message 'Email you entered is already exists'

