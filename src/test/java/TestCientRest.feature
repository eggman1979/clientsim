Feature: CreateUser

    Scenario: test
      Given John Doe, with cpr number 1000 has an Account in Fast Money Bank
      When the Client wants to create an Account on DTU Pay using his smartphone
      Then  DTU Pay should create an account for John Doe
