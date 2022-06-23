CREATE TABLE IF NOT EXISTS user_details
(
    id           UUID PRIMARY KEY,
    first_name   VARCHAR(20),
    last_name    VARCHAR(20) NOT NULL,
    passport_num INTEGER     NOT NULL,
    email        VARCHAR(50) NOT NULL,
    UNIQUE (email)
);

CREATE TABLE IF NOT EXISTS users
(
    id       UUID PRIMARY KEY,
    login    VARCHAR(20) UNIQUE NOT NULL,
    password VARCHAR(20),
    details  UUID               NOT NULL,
    UNIQUE (details),
    FOREIGN KEY (details) REFERENCES user_details (id)
);

DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS user_details;

SELECT *
FROM user_details;

INSERT INTO user_details (id, first_name, last_name, passport_num, email)
VALUES (uuid_generate_v4(), (?), (?), (?), (?))
RETURNING id;