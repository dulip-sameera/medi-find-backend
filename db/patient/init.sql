-- 1. Create the Patient Medicine List table
CREATE TABLE IF NOT EXISTS patient_medicine_list (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL, 
    
    -- Reference to the Auth Service User ID (Logical reference)
    user_id INTEGER NOT NULL,

    -- Lean Audit Columns
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- 2. Create the Patient Medicine List Item table
-- This is the 'Many-to-Many' link between a List and the Medicines
CREATE TABLE IF NOT EXISTS patient_medicine_list_item (
    id SERIAL PRIMARY KEY,
    
    -- Foreign Key to the local List table
    patient_medicine_list_id INTEGER NOT NULL,
    
    -- Reference to nmra_medicine ID in the Medicine Database (Logical reference)
    nmra_medicine_id INTEGER NOT NULL,

    -- Lean Audit Columns
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    -- Foreign Key constraint within the same database
    CONSTRAINT fk_medicine_list
        FOREIGN KEY(patient_medicine_list_id) 
        REFERENCES patient_medicine_list(id)
        ON DELETE CASCADE
);

-- 3. Performance Indices
CREATE INDEX idx_list_user_id ON patient_medicine_list(user_id);
CREATE INDEX idx_item_list_id ON patient_medicine_list_item(patient_medicine_list_id);
CREATE INDEX idx_item_medicine_id ON patient_medicine_list_item(nmra_medicine_id);