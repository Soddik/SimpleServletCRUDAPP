package util;

public class Queries {
    public static final String INIT_QUERY = """
            DROP TABLE IF EXISTS users;
            DROP TABLE IF EXISTS user_details;
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
                        """;
}
