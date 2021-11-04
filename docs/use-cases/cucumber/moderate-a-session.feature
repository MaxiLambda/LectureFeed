Feature: Moderate a session

  Scenario: answer a valid question
    Given I created a session
    And joined it as an administrator
    And a viewer has asked a question
    And I received the question
    Then the question is shown in the list of questions
    When I click the remove-button
    Then the question is removed from the list of questions
