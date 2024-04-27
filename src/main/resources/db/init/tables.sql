DROP TABLE IF EXISTS images CASCADE;
DROP TABLE IF EXISTS users CASCADE;

CREATE TABLE users
(
    id       SERIAL PRIMARY KEY,
    username VARCHAR(64) UNIQUE NOT NULL,
    password VARCHAR(2048)      NOT NULL,
    active   BOOLEAN            NOT NULL DEFAULT FALSE,
    created  TIMESTAMP,
    role     VARCHAR(32)        NOT NULL
);

CREATE TABLE images
(
    id            SERIAL PRIMARY KEY,
    user_id       INT          NOT NULL,
    name          VARCHAR(255) NULL,
    data          bytea        NOT NULL,
    uploaded_date TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users (id)
);

