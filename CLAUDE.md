# CLAUDE.md - HWC-API

## Project Overview

HWC-API (Health and Wellness Centre API) is the backend service for Health and Wellness Centres in the AMRIT platform. It handles patient registration, nurse assessments, doctor consultations, lab tests, prescriptions, teleconsultation, and various clinical workflows including ANC, PNC, NCD screening, cancer screening, neonatal/childhood care, family planning, COVID-19, and general OPD. It also supports offline data sync for field-level workers and CHO (Community Health Officer) app sync.

## Tech Stack

- Java 17, Spring Boot 3.2.2, Maven
- Spring Data JPA / Hibernate, MySQL 8.0
- Redis for session management
- Lombok, MapStruct
- SpringDoc OpenAPI (Swagger UI at `/swagger-ui.html`)
- ECS logging (logback-ecs-encoder)
- JaCoCo for test coverage
- Packaged as WAR for Wildfly deployment

## Build & Run

```bash
mvn clean install -DENV_VAR=local          # Build
mvn spring-boot:run -DENV_VAR=local        # Run locally
mvn -B package --file pom.xml -P <profile> # Package WAR (dev, local, test, ci, uat)
mvn test                                    # Run tests
```

Environment config: `src/main/resources/common_<ENV_VAR>.properties` is copied to `application.properties` at build time.

## Key Packages (`com.iemr.hwc`)

- **controller/** - REST endpoints organized by clinical workflow:
  - `registrar/` - Patient registration (main + master data)
  - `anc/` - Antenatal Care
  - `pnc/` - Postnatal Care
  - `ncdscreening/` - NCD Screening (diabetes, hypertension, oral, breast, cervical cancer)
  - `ncdCare/` - NCD Care follow-up
  - `cancerscreening/` - Cancer screening workflows
  - `generalOPD/` - General OPD consultations
  - `quickconsult/` - Quick consultation
  - `neonatal/` - Neonatal and immunization services
  - `adolescent/` - Childhood and adolescent health
  - `familyPlanning/` - Family planning services
  - `covid19/` - COVID-19 screening and management
  - `labtechnician/` - Lab test ordering and results
  - `teleconsultation/` - Telemedicine specialist consultations
  - `videoconsultation/` - Video consultation (QuickBlox integration)
  - `foetalmonitor/` - Foetal monitor integration
  - `dataSyncActivity/` - Offline data sync (van/spoke to server)
  - `dataSyncLayerCentral/` - Central data sync layer
  - `choApp/` - CHO App sync endpoints
  - `patientApp/` - Patient mobile app endpoints
  - `common/` - Shared endpoints (worklist, master data)
  - `location/` - Location management
  - `masterVillage/` - Village master data
  - `report/` - CRM reports
  - `snomedct/` - SNOMED CT terminology
  - `spoke/` - Van/spoke management
  - `uptsu/` - UP Technical Support Unit integration
  - `diabetesAndHypertensionOutcome/` - Diabetes/hypertension outcome tracking
  - `wo/` - Location controller (alternate)
- **service/** - Business logic layer (mirrors controller structure)
- **repo/** - Spring Data JPA repositories
- **data/** - JPA entities organized by domain (doctor, nurse, anc, pnc, neonatal, labModule, etc.)
- **annotation/** - Custom annotations (SQL injection safe validation)
- **config/** - Application configuration
- **utils/** - Utilities (Redis, HTTP, validation, session, gateway, email, exception handling, mapper)

## Architecture Notes

- Comprehensive clinical workflow engine supporting 10+ visit types (ANC, PNC, NCD, cancer, general OPD, neonatal, adolescent, family planning, COVID-19, quick consult)
- Beneficiary flow status (`benFlowStatus/`) tracks patients through registration -> nurse -> doctor -> lab -> pharmacist pipeline
- Offline-first architecture: data sync layer enables van/spoke devices to work offline and sync to central server
- CHO App sync provides mobile-optimized endpoints for Community Health Officers
- Teleconsultation integration enables specialist remote consultations
- Video consultation via QuickBlox
- Foetal monitor device integration for ANC
- UPTSU integration for Uttar Pradesh government health system
- SNOMED CT for standardized clinical terminology
- SQL injection prevention via custom annotation validators
- Artifact ID: `hwc-api`, group: `com.iemr.hwc`, version: 3.2.1
