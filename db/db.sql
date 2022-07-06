CREATE TABLE IF NOT EXISTS user_details
(
    id           UUID PRIMARY KEY,
    first_name   VARCHAR(20),
    last_name    VARCHAR(20),
    passport_num INTEGER     NOT NULL,
    email        VARCHAR(50) NOT NULL,
    created_at TIMESTAMP DEFAULT NOW(),
    modified_at TIMESTAMP,
    UNIQUE (passport_num),
    UNIQUE (email)
);

CREATE TABLE IF NOT EXISTS users
(
    id       UUID PRIMARY KEY,
    login    VARCHAR(20) UNIQUE NOT NULL,
    password VARCHAR(20),
    user_details_id  UUID               NOT NULL,
    UNIQUE (user_details_id),
    FOREIGN KEY (user_details_id) REFERENCES user_details (id)
);

DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS user_details;

SELECT *
FROM user_details;

INSERT INTO user_details (id, first_name, last_name, passport_num, email)
VALUES (uuid_generate_v4(), (?), (?), (?), (?))
RETURNING id;

CREATE OR REPLACE FUNCTION trigger_set_timestamp()
    RETURNS TRIGGER AS $$
BEGIN
    NEW.modified_at = NOW();
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER set_timestamp
    BEFORE UPDATE ON user_details
    FOR EACH ROW
EXECUTE PROCEDURE trigger_set_timestamp();