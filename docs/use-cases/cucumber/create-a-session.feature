Feature: create session

  Scenario: create a session 
    Given I started the LectureFeed application
    And I opened a browser on a different device 
    When I open the moderator-page
    And I enter a valid Admin-token
    And I press the “create session” button    
    Then create a new session invite-link, session-code, QR-Code
