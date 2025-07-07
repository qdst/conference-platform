# Conference Platform

The platform allows to create rooms, register or cancel conferences and participants, and leave feedback about
conferences.

---

## Table of Contents

1. [Getting Started](#getting-started)
    1. [Stack](#stack)
2. [Running the Platform](#running-the-platform)
    1. [Build & Run Scripts](#build--run-scripts)
3. [Architecture Overview](#architecture-overview)
    1. [Architecture Diagram](#architecture-diagram)
4. [Database](#database)
    1. [Database Structure](#database-structure)
    2. [Database Credentials](#database-credentials)
    3. [Core Services to Schemas Mapping](#core-services-to-schemas-mapping)
5. [Services & Navigation](#services--navigation)
    1. [Core Services](#core-services)
    2. [Gateway](#gateways)
    4. [User Interfaces](#user-interfaces)
    5. [Docker Profile](#docker-profile)
    6. [Application Navigation](#application-navigation)
6. [Domain Logic & Validation](#domain-logic--validation)

---

## Getting Started

### Stack

- **Java 21**
- **Gradle Wrapper 8.14.2** (supports Java 21)
- **Spring Boot 3.5.3** & **Spring Data JPA**
- **PostgreSQL 17.5**
- **Liquibase 4.31.1**
- **Spring MVC** & **Spring Cloud Gateway WebFlux**
- **Docker 28.1.1** & **Docker Compose v2.35.1-desktop.1**
- **Thymeleaf Template Engine**

---

## Running the Platform

### Build & Run Scripts

| Script                         | Description                                                                                                                     |
|--------------------------------|---------------------------------------------------------------------------------------------------------------------------------|
| `./run_conference_platform.sh` | This is the main script which will clea, build, create images and run all services at once. It consists from another 3 actions. |
| `./clean_build_all.sh`         | Clean and Build all 7 sub-projects.                                                                                             |
| `./build_images.sh`            | Creates Docker images from the built JAR files of each service.                                                                 |
| `./docker-copmpose.yml`        | Configuration to orchestrate run of Docker Containers for Conference Platform.                                                  |

**Run main script:**

```bash
./run_conference_platform.sh
```

## Architecture Overview

### Architecture Diagram

For the demo I used third approach of the architecture design:

![Microservice Architecture v3](docs/img.png)

This approach is best suited to the separation of concerns, as it promotes modularity, maintainability, and scalability
by clearly dividing responsibilities among distinct modules.
Besides that, from a scalability perspective, this approach will scale better.

## Database

### Database Structure

Each microservice has its own schema. For the purposes of this demo, we run a single database container but partition it
into three isolated schemas - `c_control`, `c_room`, and `c_feedback` - to simulate separate databases.
This approach was chosen for simplicity and minimal resource usage. In production, these schemas would be typically
deployed a separate database instances, providing stronger isolation and fault tolerance.

The SQL script that creates the schemas, roles, and credentials is here:  
[db-init/init_db_container.sql](db-init/init_db_container.sql)

The superuser has access to all three schemas.

### Database Credentials

| user type/schema | username                    | password        |
|------------------|-----------------------------|-----------------|
| superuser        | conference_platform_db_user | changeme        |
| c_room           | c_room_user                 | c_room_user     |
| c_control        | c_control_user              | c_control_user  |
| c_feedback       | c_feedback_user             | c_feedback_user |

### Core Services to Schemas Mapping

- **Conference Room Module** (`c_room`)
- **Conference Control Module** (`c_control`)
- **Conference Feedback Module** (`c_feedback`)

## Services & Navigation

### Core Services

Here is three core services:

- Conference Room Module (conference-room)
- Conference Control Module (conference-control)
- Conference Feedback Module (conference-feedback)

### Gateways

Both gateway modules are built with Spring Cloud WebFlux Gateway and configured declaratively:

- **Back Office Gateway (internal)**: `back-office-gateway`
- **Conference Gateway (external)**: `conference-gateway`

I included the prefixes `external` and `internal` in the controller names of the core services to distinguish them
between the gateways, according to their respective roles.
For the external services, the URI paths of their endpoints also include the `/external` prefix, which is removed by the
external gateway (conference-gateway).

### User Interfaces

The UI services use Spring MVC with the Thymeleaf template engine for a lightweight, server-rendered interface.

- **Back Office User Interface (internal)**: `back-office-ui`
- **Conference User Interface (external)**: `external-ui`

### Platform module to port mapping

| Module              | Port  |
|---------------------|-------|
| conference-room     | 11001 |
| conference-control  | 11002 |
| conference-feedback | 11003 |
| back-office-gateway | 12001 |
| conference-gateway  | 12002 |
| back-office-ui      | 8888  |
| external-ui         | 9999  |

The internal Docker ports for the modules correspond to the exposed ports on the local host.

### Docker Profile

Each service includes a profile configuration in `application-docker.properties` to support running in a Docker
environment.

### Application Navigation

Both the external and internal UI modules have their starting page at the path /main.
You can navigate to http://localhost:9999/main for the external UI and http://localhost:8888/main for the internal UI.

## Domain Logic & Validation

- Cancelled conferences and participants are not deleted to allow better tracking of history.
As a result, when a conference or participant is cancelled, only the status is changed. In the case of a conference, its cancellation will also update the status of its participants from REGISTERED to CANCELLED.
- The goal is to have, in the end, an error handler similar to the one implemented in the conference-control module, as well as to properly represent errors in the UI.
- The room for a conference can be changed if:
  - the room is in AVAILABLE status
  - there are no overlapping conferences for the new room
  - the capacity of the new room is greater than the number of registered, active (not cancelled) participants
  - if any of these conditions are not met, an exception will be thrown.
- The same applies to room status and capacity:
  - The room status cannot be changed if the room has any future conferences. 
  - The room capacity cannot be set lower than the number of registered, active (not cancelled) participants for a conference.
  - if any of these conditions are not met, an exception will be thrown.
- A conference can be cancelled from its page at http://localhost:8888/conferences/{conferenceCode} (where {conferenceCode} is the code of the conference of interest),
or by navigating from the main page at http://localhost:8888/main to the "Navigate by code" section. There, you can enter the conference code and click the "Open Conference" button.
