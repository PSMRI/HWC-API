# Health and Wellness Centre (HWC) API Module 
[![License: GPL v3](https://img.shields.io/badge/License-GPLv3-blue.svg)](https://www.gnu.org/licenses/gpl-3.0)  ![branch parameter](https://github.com/PSMRI/HWC-API/actions/workflows/sast-and-package.yml/badge.svg)

Health and Wellness centre (HWC) is one of the comprehensive applications of AMRIT designed to capture details of 7 Service packages as per guidelines which should be available at Health and Wellness centre.

### Primary Features
* Provide medical advice and services to beneficiaries
* Out patient service 
* Video consultation with specialists doctors
* Drug dispense and laboratory facility available at centre
* More than 20 lab tests can be performed using IOT devices and data flows directly to AMRIT
* Compliance with all 3 milestones of ABDM 
* SnomedCT, LOINC, ICD -10, FHIR compatible

### Patient Visit Category
* Ante natal care (ANC)
* Post natal care (PNC)
* Neonatal and infant health care services
* Childhood and adolescent health care services including immunisation
* Family planning, contraceptive services and other reproductive health care
* Care for acute simple illnesses and minor ailments 
* Management of communicable diseases
* National health programs (General OPD)
* Prevention, screening and management of non communicable diseases (NCD)

## Building From Source
This microservice is built on Java, Spring boot framework and MySQL DB.

### Pre-requisites 
* JDK 1.8
* Spring Tool Suite 3 / Eclipse(2023-03)
* Maven (if not pre-installed with the editor)
* Redis-x64-3.0.504
* MySQL Workbench 8.0

### Creating a build configuration in STS / Eclipse

* You can copy `common_example.properties` to `common_local.properties` and edit the file accordingly. The file is under `src/main/environment` folder.
* In your editor, click on Run -> Run configuration.
* Double-click on Maven build and give a suitable name for the new configuration.
* Populate the base directory by clicking on workspace and selecting HWC-API module.
* Set goals to clean install -DENV_VAR=local(your choice of desired environment) and click on Apply. 
* It is advisable have a personal environment properties file under src/main/environment filling out all the placeholders to avoid repetitive manual work each time you run locally.
* Click Run to run the build configuration.

### Creating a run configuration in STS / Eclipse

* In your editor, click on Run -> Run configuration.
* Double click on Spring Boot App(in STS) / Java Application(in Eclipse) and give a suitable name for the new configuration.
* Select the project and main class and click on Apply.
* Click Run to run the configuration. Keep the Redis server open during this run.
* Once the run is complete, load http://localhost:8080/swagger-ui.html#!/

### How to Import, Build, and Run Maven Code in Visual Studio Code

1. **Install Visual Studio Code**: Download and install [Visual Studio Code](https://code.visualstudio.com/) from the official website.

2. **Install Java**: Ensure you have the Java Development Kit (JDK 1.8) installed on your computer. You can download it from the [Oracle website](https://www.oracle.com/java/technologies/javase-downloads.html) or use an open-source JDK like [OpenJDK](https://adoptopenjdk.net/).

3. **Install Maven**: Make sure you have Apache Maven installed on your system. Download it from the [Apache Maven website](https://maven.apache.org/download.cgi).

4. **Install Visual Studio Code Extensions**:
   - **Java Extension Pack**: Open Visual Studio Code, go to the Extensions view by clicking on the Extensions icon in the Activity Bar, and search for "Java Extension Pack." Install it to get Java support and Maven integration.

5. **Open the Project in Visual Studio Code**:
   - Launch Visual Studio Code.
   - Use the **File > Open Folder** option to open your Maven project folder.

6. **Configure Java and Maven**:
   - If not already configured, set up your Java Home and Maven Home in Visual Studio Code.
   - Go to **File > Preferences > Settings**, search for "Java Home" and "Maven Home," and specify the paths to your JDK and Maven installations.

7. **Build and Run**:
   - To build and run your Maven project, open the integrated terminal in Visual Studio Code (**Terminal > New Terminal**).
   - Navigate to your project directory using the `cd` command.
   - Use Maven commands like `mvn clean install` to build your project.
   - To run your Java application, use mvn spring-boot:run. Ensure the redis server is open during run.
   
##DB restoration
[blank_db_zip](https://psmri.github.io/PSMRI/developer-guides/technical-overview/#db-restoration)   

## Integrations
* Video Consultation

## Usage
All features have been exposed as REST endpoints. Refer to the SWAGGER API specification for details.

