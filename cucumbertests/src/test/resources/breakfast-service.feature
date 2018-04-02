Feature: get a breakfast
  As a customer
  I want to get a breakfast
  So that I will start the day well

  Scenario:
    Given a customer
    When a customer want a toasted bread
    And a fried egg
    And some bacon
    And a good cup of coffee
    Then he gets a wonderful breakfast

  Scenario:
    Given a customer
    And a conversation header value of blablabla
    When a customer want a toasted bread
    And a fried egg
    And some bacon
    And a good cup of coffee
    Then he gets a wonderful breakfast