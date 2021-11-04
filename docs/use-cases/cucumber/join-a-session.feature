Feature: join session

  Scenario: join a session via invite-link
    Given I pressed an invite-link to a session
    And I am now on the homepage
    When I enter a nickname “NICKNAME”
    And press the “join session” button    
    Then I join the session with the nickname “NICKNAME”

  Scenario: join a session via session-code
    Given I am on the homepage
    When I enter the valid session-code “SESSION-CODE”
    And I enter a nickname “NICKNAME”
    And press the “join session” button 
    Then I join the session with the nickname “NICKNAME”

  Scenario: enter invalid session-code
    Given I am on the homepage
    When I enter the invalid session-code “BADSESSION-CODE”
    And I enter a nickname “NICKNAME”
    And press the “join session” button 
    Then I don’t join the session
    And I am redirected to the homepage
