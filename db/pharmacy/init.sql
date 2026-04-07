-- 1. Create the Pharmacy table
CREATE TABLE IF NOT EXISTS pharmacy (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    address TEXT NOT NULL,
    district VARCHAR(100) NOT NULL,
    owner_name VARCHAR(255) NOT NULL,
    pharmacist_name VARCHAR(255) NOT NULL,
    
    -- Sri Lankan Contact Validation (e.g., 0771234567 or 0112345678)
    contact CHAR(10) NOT NULL 
        CHECK (contact ~ '^0[0-9]{9}$'),
    
    longitude DECIMAL(9,6),
    latitude DECIMAL(8,6),
    
    -- SLMC Number: Only digits, exactly 4 or 5 characters
    slmc_no VARCHAR(5) UNIQUE NOT NULL
        CHECK (slmc_no ~ '^[0-9]{4,5}$'),
    
    -- Verify status restricted to PENDING, VERIFIED, REJECTED values
    verify VARCHAR(10) DEFAULT 'PENDING'
        CHECK (verify IN ('PENDING', 'VERIFIED', 'REJECTED')),
    
    -- Reference to the Auth Service users table ID
    user_id INTEGER NOT NULL,

    -- Lean Audit Columns
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- 2. Create the Inventory table
CREATE TABLE IF NOT EXISTS inventory (
    id SERIAL PRIMARY KEY,
    price DECIMAL(10,2) NOT NULL CHECK (price >= 0),
    available BOOLEAN DEFAULT TRUE,
    
    -- Foreign Key to the local pharmacy table
    pharmacy_id INTEGER NOT NULL,
    
    -- Reference to nmra_medicine ID in the Medicine Database
    nmra_medicine_id INTEGER NOT NULL,

    -- Audit Columns
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_pharmacy
        FOREIGN KEY(pharmacy_id) 
        REFERENCES pharmacy(id)
        ON DELETE CASCADE
);

-- 3. Pharmacy Indices for faster lookups
CREATE INDEX idx_pharmacy_district ON pharmacy(district);
CREATE INDEX idx_pharmacy_name ON pharmacy(name);
CREATE INDEX idx_pharmacy_user_id ON pharmacy(user_id);

-- 4. Inventory Indices for faster lookups
CREATE INDEX idx_inventory_pharmacy ON inventory(pharmacy_id);
CREATE INDEX idx_inventory_medicine ON inventory(nmra_medicine_id);