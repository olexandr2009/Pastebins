CREATE TABLE IF NOT EXISTS user_credential(
    id UUID PRIMARY KEY,
    username VARCHAR UNIQUE,
    email VARCHAR UNIQUE,
    password VARCHAR
);

