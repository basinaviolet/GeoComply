# GeoComply 
Case study for the position of Junior QA Automation

Pages:
BasePage contains configurations and wrappers for methods with web elements; 
RegistrationPage for launching Gmail mailbox; 
MainPage - the main page for organizing mail

Utils src\main:
ReadProperties - reads all properties from config.properties
Waiter - wrapper for waiting for action

Utils src\java:
MailUtils contains methods for working with javaMail
YmlReader reads data from *.yml files

MailDTO - class for storing message data

Resources src\main:
config.properties contains important parameters for launching pages and creating mail 
(url, e-mail, gmail password, 2StepVerification password)
log4j2.properties contains parameters of logging

Resources src\java:
fileWithData.yml - file with addition data for tests (subject and body of letter)

Tests:
BaseTest contains configurations for tests starting and finishing; 
UI-tests
StartPageTest - testing way to MainPage with Gmail registration
SendMailTest - functional tests for creating and sendind message
ReceivedMailTest - functional tests for received message checking

Addition information
test "ReceivedMailTest\checkReceivedMessageByJavaMail" is disabled due to the requirements of using 2-step verification. 
Using this type of verification requires my phone during registration. But unfortunately this is not possible. 