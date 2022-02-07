@UI @smoke @regression
Feature: Validating WebOrders application login functionality\

  Scenario: Validating login functionality with valid credentials
    Given user navigates to the weborders application
    When user provides username "Tester" and password "test" clicks on login button
    Then user validates application is logged in

    Scenario: Validating login functionality with invalid credentials
      Given user navigates to the weborders application
      When user provides username "Teste" and password "testi" clicks on login button
      Then user validates error message "Invalid Login or Password."









