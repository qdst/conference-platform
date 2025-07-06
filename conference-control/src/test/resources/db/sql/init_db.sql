-- create c_control schema
CREATE SCHEMA IF NOT EXISTS c_control;
CREATE USER c_control_user WITH PASSWORD 'c_control_user';
GRANT ALL PRIVILEGES ON SCHEMA c_control TO c_control_user;

-- conference-control DB DDL
CREATE SEQUENCE conference_t_id_seq START 100 INCREMENT 1;

CREATE TABLE conference
(
    id              BIGINT PRIMARY KEY DEFAULT nextval('conference_t_id_seq'),
    conference_code CHAR(26)     NOT NULL UNIQUE,
    total_capacity  INT          NOT NULL,
    title           VARCHAR(255) NOT NULL,
    start_time      TIMESTAMP    NOT NULL,
    end_time        TIMESTAMP    NOT NULL,
    room_code       CHAR(26)     NOT NULL,
    status          VARCHAR(32)  NOT NULL
);

CREATE SEQUENCE participant_t_id_seq START 100 INCREMENT 1;

CREATE TABLE participant
(
    id                BIGINT PRIMARY KEY DEFAULT nextval('participant_t_id_seq'),
    conference_id     BIGINT       NOT NULL,
    registration_code CHAR(32)     NOT NULL UNIQUE,
    first_name        VARCHAR(128) NOT NULL,
    last_name         VARCHAR(128) NOT NULL,
    gender            VARCHAR(8)   NOT NULL,
    status            VARCHAR(16)  NOT NULL,
    email             VARCHAR(128) NOT NULL,
    date_of_birth     DATE         NOT NULL
);

ALTER TABLE participant
    ADD CONSTRAINT fk_participant_t_conference_t FOREIGN KEY (conference_id) REFERENCES conference (id);
