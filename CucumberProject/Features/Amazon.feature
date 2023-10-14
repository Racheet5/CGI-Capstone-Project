Feature: feature to test Amazon Website

  Scenario: checking login procedure
    Given launch the browser
    And Navigate to the URL
    And User selects the login option
    And the user enters the "<email>"
    And User clicks continue
    And the user enters the "<password>" and login
    Then User closes the Browser
    
     Examples: 
    | email                | password   |
    | rp5.thegoalpost@gmail.com | password |
    
  