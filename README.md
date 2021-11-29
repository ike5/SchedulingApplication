# Scheduling Application

## Purpose
> This application allows users to create, view, maintain, and delete customer appointments. Additionally,
users can generate reports on the number of appointments, contact-to-customer appointments, and number of customers.

## Author Info
Author: Ike Maldonado  
Email: imald14@wgu.edu  
Phone: +1 (415) 760-7438  

--- 

## Application Info
- Date: 11/24/2021  
- Application Version: 1.0  

### IDE: Intellij

IntelliJ IDEA 2021.2.2 (Ultimate Edition)  
Build #IU-212.5284.40, built on September 13, 2021  
Runtime version: 11.0.12+7-b1504.28 aarch64  
VM: OpenJDK 64-Bit Server VM by JetBrains s.r.o.  
macOS 12.0.1  


## Getting started

- Setting timezones
  - Make sure to set machine timezone in order for LocalTime ComboBoxes to populate correctly.

- Changing default locale
  - Uncomment static `Test.changeLocale()` method in Main method
  

## Additional Report description
- This report counts the total number of Appointments, Customers, Contacts, and logins (successful and unsuccessful). 
  - Location: `Reports Button > Additional Report Tab`


## Additional info
- Location of lambda expressions
  - `LoginController.java > textfieldLogin() > changeScreen()`
  - `LoginController.java > onLoginAction() > changeScreen()`

## MySQL info
MySQL version: 8.0.25
Driver: mysql-connector-java-8.0.25
