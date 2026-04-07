-- 1. Create the NMRA Medicine Master Table
CREATE TABLE IF NOT EXISTS nmra_medicine (
    id SERIAL PRIMARY KEY,
    -- Enforce: Starts with 'M', followed by exactly 6 digits, total 7 chars
    reg_no CHAR(7) UNIQUE NOT NULL 
        CHECK (reg_no ~ '^M[0-9]{6}$'), 
        
    generic_name VARCHAR(255) NOT NULL, 
    brand_name VARCHAR(255) NOT NULL,   
    
    -- Lean Audit Columns
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
);

-- 2. Indexing for high-performance searching
CREATE INDEX idx_generic_name ON nmra_medicine(generic_name);
CREATE INDEX idx_brand_name ON nmra_medicine(brand_name);

-- 3. Pre-seed with valid data
INSERT INTO nmra_medicine (reg_no, generic_name, brand_name) 
VALUES 
('M017589', 'Amoxicillin', 'Amoxyl'),
('M102455', 'Paracetamol', 'Panadol'),
('M309112', 'Metformin', 'Glucophage')
ON CONFLICT (reg_no) DO NOTHING;