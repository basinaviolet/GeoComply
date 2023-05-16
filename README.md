# GeoComply 
### Case study for the position of Junior QA Automation

---
Pages:
- BasePage contains configurations and wrappers for methods with web elements; 
- RegistrationPage for launching Gmail mailbox; 
- MainPage - the main page for organizing mail

---
Utils src\main:
- ReadProperties - reads all properties from config.properties
- Waiter - wrapper for waiting for action

Utils src\java:
- MailUtils contains methods for working with javaMail
- YmlReader reads data from *.yml files

MailDTO - class for storing message data

---
Resources src\main:
- config.properties contains important parameters for launching pages and creating mail 
_(url, e-mail, gmail password, 2StepVerification password)_
- log4j2.properties contains parameters of logging

Resources src\java:
- fileWithData.yml - file with addition data for tests _(subject and body of letter)_

---
Tests:
- BaseTest contains configurations for tests starting and finishing; 

UI-tests
- StartPageTest - testing way to MainPage with Gmail registration
- SendMailTest - functional tests for creating and sending message
- ReceivedMailTest - functional tests for received message checking

---
The link to the Allure report is in allureReport.txt

---
### Addition information 
test "ReceivedMailTest\checkReceivedMessageByJavaMail" requirements of using 2-step verification. 

The default settings are for windows.

For using MAC: 
- open the config.properties file 
- assign the password2StepVerification field a value from password2StepVerificationForMac
