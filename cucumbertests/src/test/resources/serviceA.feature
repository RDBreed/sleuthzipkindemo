Feature: get a model
  As a customer
  I want to get a model
  So that I can see the fancyness of the service and make the company happy :)

  Scenario:
    Given a customer
    When a customer calls service A
    Then a model is created

  Scenario:
    Given a customer
    And a conversation header value of blablabla
    When a customer calls service A
    Then a model is created