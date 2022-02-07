@regression @MB2P-120
Feature: Validating calculate and order creation functionalities

  Scenario Outline: Validating calculate functionality
    Given user navigates to the weborders application
    When user provides username "Tester" and password "test" clicks on login button
    And user clicks on Order module
    And user selects "<product>" product with <quantity> quantity
    Then  user validates total is calculated correctly for quantity <quantity>
    Examples:
      | product     | quantity |
      | MyMoney     | 1        |
      | FamilyAlbum | 100      |
      | ScreenSaver | 55       |
      | MyMoney     | 20       |

  @MB-413
  Scenario Outline: Validating create order functionality
    Given user navigates to the weborders application
    When user provides username "Tester" and password "test" clicks on login button
    And user counts number of orders in table
    And user clicks on Order module
    And user creates order with data
      | order   | quantity   | name   | street   | city   | state   | zip   | cc   | expire date   |
      | <order> | <quantity> | <name> | <street> | <city> | <state> | <zip> | <cc> | <expire date> |
    Then user validates success message "New order has been successfully added."
    And user validates order added to List of Orders

    Examples:
      | order       | quantity | name     | street      | city        | state    | zip   | cc               | expire date |
      | MyMoney     | 1        | John Doe | 123 MY Road | Chicago     | Illinois | 12345 | 1234567891234567 | 12/21       |
      | FamilyAlbum | 5        | Patel    | 123 MY Road | New York    | NY       | 00000 | 9876543212345678 | 12/22       |
      | ScreenSaver | 50       | Kim      | 123 MY Road | Los Angeles | CA       | 17654 | 987654321356677  | 12/22       |

