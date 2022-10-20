DROP TABLE IF EXISTS users, categories, events, requests, compilations, events_compilation;

CREATE TABLE IF NOT EXISTS users (
                                     id BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
                                     name VARCHAR(255) NOT NULL,
                                     email VARCHAR(512) NOT NULL,
                                     activated BOOLEAN DEFAULT FALSE NOT NULL,
                                     CONSTRAINT pk_user PRIMARY KEY (id),
                                     CONSTRAINT UQ_USER_EMAIL UNIQUE (email)
);

CREATE TABLE IF NOT EXISTS categories (
                                          id BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
                                          name VARCHAR(255) NOT NULL UNIQUE,
                                          CONSTRAINT pk_category PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS locations (
                                         id BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
                                         name VARCHAR(255),
                                         coordinates geography(POINT, 4326),
                                         radius DECIMAL,
                                         CONSTRAINT pk_name PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS events (
                                      id BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
                                      annotation VARCHAR(4000) NOT NULL,
                                      category_id BIGINT NOT NULL,
                                      created_on TIMESTAMP WITHOUT TIME ZONE NOT NULL,
                                      description VARCHAR(4000) NOT NULL,
                                      event_date TIMESTAMP WITHOUT TIME ZONE NOT NULL,
                                      initiator_id BIGINT NOT NULL,
                                      paid BOOLEAN NOT NULL,
                                      participant_limit INTEGER DEFAULT 0 NOT NULL,
                                      published_on TIMESTAMP WITHOUT TIME ZONE,
                                      request_moderation BOOLEAN DEFAULT FALSE NOT NULL,
                                      state VARCHAR(10) DEFAULT 'PENDING' NOT NULL,
                                      title VARCHAR(4000) NOT NULL,
                                      confirmed_requests  BIGINT,
                                      coordinates geography(POINT, 4326),
                                      CONSTRAINT pk_event PRIMARY KEY (id),
                                      CONSTRAINT FK_EVENT_ON_CATEGORY FOREIGN KEY (category_id) REFERENCES categories (id),
                                      CONSTRAINT FK_EVENT_ON_USER FOREIGN KEY (initiator_id) REFERENCES users (id)
);

CREATE TABLE IF NOT EXISTS requests (
                                        id BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
                                        event_id BIGINT NOT NULL,
                                        requestor_id BIGINT NOT NULL,
                                        status VARCHAR(100) NOT NULL,
                                        created TIMESTAMP WITHOUT TIME ZONE,
                                        CONSTRAINT pk_request PRIMARY KEY (id),
                                        CONSTRAINT FK_REQUEST_ON_EVENT FOREIGN KEY (event_id) REFERENCES events (id),
                                        CONSTRAINT FK_REQUEST_ON_USER FOREIGN KEY (requestor_id) REFERENCES users (id)
);

CREATE TABLE IF NOT EXISTS compilations (
                                            id BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
                                            title VARCHAR(127) NOT NULL,
                                            pinned BOOLEAN DEFAULT FALSE NOT NULL,
                                            CONSTRAINT pk_compilations PRIMARY KEY(id)
);

CREATE TABLE IF NOT EXISTS events_compilation (
                                                  events_id BIGINT NOT NULL,
                                                  compilation_id BIGINT NOT NULL,
                                                  CONSTRAINT FK_EVENTS_COMPILATION_ON_EVENT FOREIGN KEY (events_id) REFERENCES events (id) ON DELETE CASCADE,
                                                  CONSTRAINT FK_EVENTS_COMPILATION_ON_COMPILATION FOREIGN KEY (compilation_id) REFERENCES compilations (id) ON DELETE CASCADE
);