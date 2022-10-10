Feature: LoginFeature
  This feature deals with the login functionality of the application

  Scenario: Check if right category is shown for a product
    Given user logins to ABC web application
    When user search for club and member
    Then member validation is done