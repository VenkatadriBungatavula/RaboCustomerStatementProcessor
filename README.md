#RaboCustomerStatementProcessor is spring boot application REST service which receives the customer statement JSON as a POST data, Perform the below validations

All transaction references should be unique
The end balance needs to be validated ( Start Balance +/- Mutation = End Balance )
URL: http://localhost:8080/validateStatement Method: POST

Design used:
Uses Java 8 Maintain layered structure as below Controller -- RaboStatementProcessController: to hadle the requrests and map to methods Util -- ValidationUtil: to validate the transaction statement 1. Method to check duplication transaction reference 2. Method to validate End Balance in transactions Model -- ErrorRecord, Request, ResponseStatus, Transaction: model classes Exception -- Gloabal exception handling

Provided Unit test cases, Integration testing, logging
