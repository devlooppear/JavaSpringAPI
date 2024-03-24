CREATE TABLE personal_access_token (
    id SERIAL PRIMARY KEY,
    user_id BIGINT NOT NULL,
    active BOOLEAN NOT NULL,
    token TEXT NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users(id)
);
