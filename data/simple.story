Feature: Launch a story sample for testing json object
  In order to test this feature
  As an explicit system actor
  I want to verify this behaviour
  Register system with 'org.jbehaviour.plugins.system.SystemSteps' plugin
  Declare ref002 as String "some string 002 to display"

  Scenario: Verify this sample
    Given print object $ref002
