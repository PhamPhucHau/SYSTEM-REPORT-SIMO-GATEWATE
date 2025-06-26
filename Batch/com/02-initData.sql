-- 02-infoInit.sql
-- Add initial users to the 'users' table

-- Enable pgcrypto extension for password hashing (if not already enabled)
CREATE EXTENSION IF NOT EXISTS pgcrypto;

-- Insert sample users
INSERT INTO SIMO_USER (username, password_hash, email) VALUES
    ('admin', '$2a$10$x/N/RfoZSa1cLeMzfEIWmOUMwm3OdivMr1/cHsOKagimzyiO4LCIK', 'admin@shinhan.com');
INSERT INTO SIMO_USER (username, password_hash, email) VALUES
    ('anonymous', '1111', 'anonymous@shinhan.com');

-- Insert sample roles
INSERT INTO SIMO_ROLE (role_id, configuration) VALUES
    ('admin', '[]');
INSERT INTO SIMO_ROLE (role_id, configuration) VALUES
    ('anonymous', '[]');

-- Insert sample user roles
INSERT INTO SIMO_USER_ROLE (user_id, role_id) VALUES
    ('admin', 'admin');
INSERT INTO SIMO_USER_ROLE (user_id, role_id) VALUES
    ('anonymous', 'anonymous');

-- Insert sample metadata
INSERT INTO SIMO_METADATA (LOOKUPCODE, LOOKUPCODEID, LANGUAGE, VALUE, SERVICENAME) VALUES
    ('SIMO_USER_ROLE', 'admin', 'en', 'Admin', 'OMS'),
    ('SIMO_USER_ROLE', 'anonymous', 'en', 'Anonymous', 'OMS'),
    ('SIMO_USER_FEATURE', 'recon', 'en', 'Reconciliation', 'OMS'),
    ('FILE_TEMPLATE_CODE', 'INST_API1_6_TKTT', 'en', 'Reconciliation', 'OMS'),
    ('FILE_TEMPLATE_CODE', 'INST_API1_7_NGGL', 'en', 'Reconciliation', 'OMS'),
    ('FILE_TEMPLATE_CODE', 'INST_API1_8_NGGL', 'en', 'Reconciliation', 'OMS'),
    ('FILE_TEMPLATE_CODE', 'INST_API1_9_TKTT', 'en', 'Reconciliation', 'OMS'),
    ('FILE_TEMPLATE_CODE', 'INST_API1_22_WDR', 'en', 'Reconciliation', 'OMS');

-- insert sample auth menu
INSERT INTO SIMO_AUTH_MENU (menu_id, menu_name, menu_url, role_id, mode, method) VALUES
    ('AUTH_GEN_TOKEN', 'Generate Token', '/v1/auth/generateToken', 'admin', 'NONE', 'POST');
INSERT INTO SIMO_AUTH_MENU (menu_id, menu_name, menu_url, role_id, mode, method) VALUES
    ('AUTH_GEN_HASHING_VALUE', 'Generate Hashing Value', '/v1/auth/generateHashingValue', 'admin', 'NONE', 'GET');
INSERT INTO SIMO_AUTH_MENU (menu_id, menu_name, menu_url, role_id, mode, method) VALUES
    ('AUTH_GEN_HASHING_VALUE', 'Generate Hashing Value', '/v1/auth/generateHashingValue', 'anonymous', 'NONE', 'GET');
