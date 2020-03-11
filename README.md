# APIAutomation
This repo implements API automation framework using RestAssured, TestNG, Maven and ExtentReports

## Scenario
Create one test to get weather using Open Weather API in the city of London: 
http://openweathermap.org/current 

We suggest using Rest Assured.


## Framework Details

Framework contains following type of classes arranged in various packages.

1. Test package contains WeatherAPITest - which implements given scenario. 
It also contains BaseTest class which contains methods to setup test environment before starting test process as well as methods 
to actually perform API calls which can be used by any child test class

2. Helper package contains two sub-packages - constants and util. 
Constants package contains static values for authentication, file paths, URL parameters and some of the test data used in the project. 
In util package, there are utility classes to implement methods for logging, reporting and excel data extraction as well methods specific 
for dealing with API requests and responses

Apart from these packages, there are following configuration files in the framework: 
 - /src/main/resources/log4j.properties - Provides logging framework configuration using log4j 
 - testng.xml - Contains configuration for running the project as TestNG Suite 
 - pom.xml - Contains maven configuration to manage dependencies, and maintain maven runtime profiles
