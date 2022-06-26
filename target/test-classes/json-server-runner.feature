Feature: Json-server usage
  Write in terminal code below
  npm install -g json-server
  json-server --watch db.json

  Scenario: Run json-server and get all users
    Given I have run json server
    When I get all posts
    Then I should see that list of posts is more than 5

  Scenario: Create different posts in json-server
    Given I have run json server
    When I set parameters for posts
      | name    | valueOf    |
      | title   | TitleOne   |
      | author  | newAuthor  |
      | content | newContent |
      | year    | 2022       |
    And I create specs for posts
    And I create a post
    Then I should see the post

  Scenario: Delete posts by id
    Given I have run json server
    When I delete 16 from server
    Then I should see success message

  Scenario: Update post by Put request
    Given I have run json server
    When I set parameters for posts
      | title   | veryNewTitle  |
      | author  | veryNewAuthor |
      | edition | third         |
      | year    | 2022          |
    And I create specs for posts
    Then Post 18 should be updated
    Then I should see the post


  Scenario: Update post by Patch request
    Given I have run json server
    When I set "title1" and "jsonPPP" for 19 post
    Then I should see the post



