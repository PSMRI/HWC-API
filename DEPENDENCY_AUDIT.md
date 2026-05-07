# HWC-API Dependency Audit

This document tracks outdated dependencies in the HWC-API project and outlines an incremental upgrade path.

Ref: https://github.com/PSMRI/AMRIT/issues/152

## Summary

HWC-API currently runs on **Spring Boot 1.5.3** (EOL since August 2019) with **Java 8** (public updates ended March 2022). Several transitive and direct dependencies are significantly behind their latest stable releases.

## Dependency Status

### Framework & Runtime

| Dependency | Current | Latest Stable | Gap | Risk |
|-----------|---------|--------------|-----|------|
| Spring Boot | 1.5.3.RELEASE | 3.4.x | 6+ years, EOL | **High** — no security patches since 2019 |
| Java | 1.8 | 21 (LTS) | Multiple LTS versions behind | **High** — no free public updates since 2022 |

### Data & Caching

| Dependency | Current | Latest Stable | Gap | Notes |
|-----------|---------|--------------|-----|-------|
| spring-data-redis | 1.7.11.RELEASE | 3.4.x | Major version gap | Tied to Spring Boot version |
| lettuce (biz.paluch.redis) | 3.5.0.Final | — | Artifact relocated | **Moved to** `io.lettuce:lettuce-core`, old groupId abandoned |
| mysql-connector-java | (managed) | 9.x | Artifact renamed | **Renamed to** `com.mysql:mysql-connector-j` in 8.0.31+ |

### FHIR (Healthcare Interoperability)

| Dependency | Current | Latest Stable | Gap | Notes |
|-----------|---------|--------------|-----|-------|
| hapi-fhir-base | 3.8.0 | 7.6.x | 4 major versions | Significant API changes between major versions |
| hapi-fhir-structures-r4 | 3.8.0 | 7.6.x | Same | Should be upgraded together with hapi-fhir-base |
| hapi-fhir-server | 3.8.0 | 7.6.x | Same | Same as above |
| hapi-fhir-validation | 3.8.0 | 7.6.x | Same | Same as above |

### API Documentation

| Dependency | Current | Latest Stable | Gap | Notes |
|-----------|---------|--------------|-----|-------|
| springfox-swagger2 | 2.6.1 | — | Project abandoned | Last release July 2020. **Migrate to** `springdoc-openapi` |
| springfox-swagger-ui | 2.6.1 | — | Same | Same as above |

### Utilities

| Dependency | Current | Latest Stable | Gap | Notes |
|-----------|---------|--------------|-----|-------|
| guava | 21.0 | 33.x | 12+ major versions | Has known CVEs in older versions |
| thymeleaf | 3.0.14.RELEASE | 3.1.x | Minor version | CVE-2023-38286 affects versions before 3.1.1 |
| lombok | 1.16.18 | 1.18.x | Minor version | Low risk, but newer versions have better Java 11+ support |
| mapstruct | 1.2.0.Final | 1.6.x | Several minor versions | Low risk |
| quartz | 2.2.1 | 2.5.x | Minor version | Low risk |

### JPA

| Dependency | Current | Latest Stable | Notes |
|-----------|---------|--------------|-------|
| hibernate-jpa-2.0-api | 1.0.1.Final | — | JPA 2.0 is very old. Spring Boot 3.x uses Jakarta Persistence 3.1 |

## Incremental Upgrade Path

A direct jump from 1.5 to 3.x isn't practical. Here's a phased approach:

### Phase 1 — Prep work (no Spring Boot change)

- Update low-risk deps that don't depend on the Spring Boot version: guava, lombok, mapstruct, quartz, thymeleaf
- Replace `biz.paluch.redis:lettuce` with `io.lettuce:lettuce-core`
- Replace `springfox` with `springdoc-openapi-ui` (v1.x works with Spring Boot 2.x)
- Add a `.mvn/maven.config` or CI step that runs `mvn dependency:tree` to track transitive deps

### Phase 2 — Spring Boot 1.5 → 2.7

- Upgrade Java target from 8 to 11 (minimum for Boot 2.x)
- Migrate `pom.xml` parent to `spring-boot-starter-parent:2.7.x` (last 2.x release)
- Handle breaking changes: property name changes, actuator endpoint changes, Spring Security config migration
- Update `spring-data-redis` and the Redis connection setup
- Update `mysql-connector-java` to latest 8.x before the artifact rename

### Phase 3 — Spring Boot 2.7 → 3.x

- Upgrade Java to 17 (minimum for Boot 3.x)
- Migrate `javax.*` imports to `jakarta.*` (biggest change)
- Rename `mysql-connector-java` to `mysql-connector-j`
- Upgrade HAPI FHIR to 7.x (will require API changes)
- Update `hibernate-jpa-2.0-api` to Jakarta Persistence

## References

- [Spring Boot support timeline](https://spring.io/projects/spring-boot#support)
- [Spring Boot 2.0 migration guide](https://github.com/spring-projects/spring-boot/wiki/Spring-Boot-2.0-Migration-Guide)
- [Spring Boot 3.0 migration guide](https://github.com/spring-projects/spring-boot/wiki/Spring-Boot-3.0-Migration-Guide)
- [Springfox → SpringDoc migration](https://springdoc.org/migrating-from-springfox.html)
