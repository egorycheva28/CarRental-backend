CREATE TABLE users(
       id UUID DEFAULT gen_random_uuid() PRIMARY KEY,
       create_date TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
       edit_date TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
       last_name VARCHAR(100) NOT NULL,
       first_name VARCHAR(100) NOT NULL,
       middle_name VARCHAR(100),
       age BIGINT NOT NULL,
       phone VARCHAR(11) NOT NULL UNIQUE,
       email VARCHAR NOT NULL UNIQUE,
       password VARCHAR NOT NULL CHECK(LENGTH(password) >= 6 AND LENGTH(password) <= 66)
);

CREATE TABLE roles(
       id UUID DEFAULT gen_random_uuid() PRIMARY KEY,
       user_id UUID NOT NULL,
       role VARCHAR NOT NULL,
       FOREIGN KEY (user_id) REFERENCES users(id)
);