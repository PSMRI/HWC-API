# Health and Wellness Centre (HWC) API Module
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

### Prerequisites 
* JDK 1.8
* Maven 

$ ./mvn clean install

## Installation
This service has been tested on Wildfly as the application server.

### Prerequisites 
* Wildfly (or any compatible app server)
* Redis
* MySQL Database

## Integrations
* Video Consultation

## Usage
All features have been exposed as REST endpoints. Refer to the SWAGGER API specification for details.

