###  FE html files located under the main/resources/templates (thymeleaf)
###  http://localhost:9000/accounts/open will render accounts.html
###  http://localhost:9000/customers/details will show the form. Submitting a valid customerId retrieves and displays the details.
###  Accounts and Transactions are different services extending the CustomerService abstraction
###  Attention to CI/CD. docker-compose file included and proper git flow
###  Testability will be also assessed. Unit tests included
###  API for managing transactions with Choreography-based Saga pattern making use of Kafka
###  Redis used for caching
###  The programming language by default is Java/Kotlin
###  Considered layers, abstractions, testability and enterprise-level architecture carefully.
### I prepared two separate branches including Java and Kotlin APIs for my assessment Since it was told me that it is needed to develop with Kotlin as well later on.
#### Java Branch: feature/Assessment   (default)
#### Kotlin : feature/AssessmentKotlin 

# Capgemini Assessment
## Initial conditions:
The assessment consists of an API to be used for opening a new “current account” of already existing
customers.
## Requirements
• The API will expose an endpoint which accepts the user information (customerID,
initialCredit).
• Once the endpoint is called, a new account will be opened connected to the user whose ID is
customerID.
• Also, if initialCredit is not 0, a transaction will be sent to the new account.
• Another Endpoint will output the user information showing Name, Surname, balance, and
transactions of the accounts.
## Bonuses
• Accounts and Transactions are different services.
• Frontend (simple one is OK).
• Attention to CI/CD
## Constraints
Feel free to use any open source tool or framework.
For storing the information, the data can be saved in memory and not actually persisted to an
external database, so that we can test the solution easier. However, remember this is a backend
assignment and consider layers, abstractions, testability and enterprise-level architecture carefully.
The programming language by default is Java / C# but we are flexible if you let us know which other
language you prefer.
## Expectations
The expected deliverable is the source code published on Github or Gitlab and instructions on how to
execute and test it.
We hope to see demonstration of good git practise and workflow; show us how you work in a team.
Testability will be also assessed.
Show your knowledge beyond boilerplate endpoints!
We'd love that you are rewarded for your effort by sharing your best work online as part of your
personal portfolio, but please be so kind as to remove references to Capgemini first!
