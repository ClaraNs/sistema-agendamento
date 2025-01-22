CREATE TABLE appointment (
    id SERIAL PRIMARY KEY,
    data DATE NOT NULL,
    confirmada BOOLEAN DEFAULT false,
    client_id BIGINT NOT NULL,
    FOREIGN KEY (client_id) REFERENCES client(id) ON DELETE CASCADE
);
