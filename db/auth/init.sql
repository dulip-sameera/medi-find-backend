-- 1. Create the Role table
CREATE TABLE IF NOT EXISTS roles (
    id SERIAL PRIMARY KEY,
    name VARCHAR(50) UNIQUE NOT NULL
);

-- 2. Create the Users table
CREATE TABLE IF NOT EXISTS users (
    id SERIAL PRIMARY KEY,
    username VARCHAR(100) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    role_id INTEGER NOT NULL,
    
    -- Defining the foreign key relationship
    CONSTRAINT fk_role
        FOREIGN KEY(role_id) 
        REFERENCES roles(id)
        ON DELETE RESTRICT
);

-- 3. Pre-seed basic roles for MediFind
INSERT INTO roles (name) 
VALUES ('ADMIN'), ('PHARMACY'), ('PATIENT') 
ON CONFLICT (name) DO NOTHING;