@registration
Feature: edit email

  Scenario: user enters the correct details 
    Given Web context is setup 
      And following user exists
	  | name                  | email                         | password     |
	  | senthil               | senthil@gmail.com             | pwd54321     |
	When client requests POST '/api/user/email/edit/{emailID}' with JSON data 
	"""
	{"name":"senthil","email":"senthilnew@gmail.com","password":"pwd54321"}
	"""
	Then response code should be 200 
	  And user details should be stored in DB
	  And Response body should have a success message 'User updated successfully'
	  And Response header should have 'location' with value "http://localhost:8080/api/user/1" 

  Scenario: user enters the incorrect email 
    Given Web context is setup 
      And following user exists
	  | name                  | email                         | password     |
	  | senthil               | senthilnew@gmail.com          | pwd54321     |
	When client requests POST '/api/user/email/edit/{emailID}' with JSON data, incorrect email 
	"""
	{"name":"senthil","email":"senthilnew#gmail.com","password":"pwd54321"}
	"""
	Then response code should be 400
	  And Response body should have a failure message 'Incorrect email'

  Scenario: user enters an emailid which is already there in DB
    Given Web context is setup 
	  And following user exists
	  | name                  | email                         | password     |
	  | senthil               | senthilnew@gmail.com          | pwd54321     |
	When client requests POST '/api/user/email/edit/{emailID}' with JSON data, 
	"""
	{"name":"senthil","email":"natraj@gmail.com","password":"pwd54321"}
	"""
	Then response code should be 409
	  And Response body should have a failure message 'Email you entered is already exists'

