@regression @ONT-3142
Feature: Validating etsy titles

  Scenario Outline: Validating etsy separate page title
    Given user navigates to Etsy application
    When user clicks on "<Section>" section
    Then  user validates that the title is "<Title>"
    #Jewelry & Accessories | Etsy
    Examples:
      | Title                                                | Section                                      |
      | Jewelry & Accessories \| Etsy                        | Jewelry and Accessories                      |
      | Valentine’s Day Gift Ideas for Everyone 2022 \| Etsy | Valentine’s Day Gift Ideas for Everyone 2022 |
      | Clothing & Shoes \| Etsy                             | Clothing & Shoes                             |
      | Home & Living \| Etsy                                | Home & Living                                |
      | Wedding & Party \| Etsy                              | Wedding & Party                              |
      | Toys & Entertainment \| Etsy                         | Toys & Entertainment                         |
      | Art & Collectibles \| Etsy                           | Art and Collectibles                         |
      | Craft Supplies & Tools \| Etsy                       | Craft Supplies and Tools                     |
      | The Etsy Gift Guide for 2022 \| Etsy                 | The Etsy Gift Guide for 2022                 |

