ALTER TABLE client
    ADD CONSTRAINT unique_cpf UNIQUE (cpf);

ALTER TABLE client
    ADD CONSTRAINT unique_email UNIQUE (email);
