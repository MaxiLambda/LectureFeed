#Use-Case Specification: Joining a Session

# 1 Joining a Session

## 1.1 Brief Description
Every Viewer should be able to join an existing session by clicking on an invite-link or by entering the session-code. Viewers have to enter a nickname to join.

# 2 Flow of Events
## 2.1 Basic Flow
- Viewer clicks on the invite link or opens the homepage and enters the session code.
- Viewer enters his nickname
- Viewer clicks on the “join session” button

### 2.1.1 Activity Diagram
![Organization Application Activity Diagram](./activity_diagrams/join_session.svg)

### 2.1.2 Mock-up
![Join a Session Mockup](../image/mockup/LoginScreen.svg)

### 2.1.3 Narrative

```gherkin
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
```

## 2.2 Alternative Flows
(n/a)

# 3 Special Requirements
(n/a)

# 4 Preconditions
## 4.1 Presenter has already created the session and gave out the session-code/ invite-link.

# 5 Postconditions

# 6 Extension Points
(n/a)





