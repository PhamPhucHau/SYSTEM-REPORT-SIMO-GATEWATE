-- 01-init.sql
-- Runs in the 'shbown' database (specified in POSTGRES_DB)

-- Create the vector extension (for full-text search)
CREATE EXTENSION IF NOT EXISTS vector;

-- Create additional user
CREATE USER shbvn WITH ENCRYPTED PASSWORD '1111';

-- Grant privileges to shbvn
GRANT ALL PRIVILEGES ON DATABASE shbown TO shbvn;

-- Create SIMO_USER table
CREATE TABLE SIMO_USER (
    id SERIAL PRIMARY KEY,
    username VARCHAR(50) UNIQUE NOT NULL,
    password_hash TEXT NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    fist_login_yn VARCHAR(1) DEFAULT 'Y',
    status VARCHAR(1) DEFAULT 'A',
    pw_wrong_cnt INTEGER DEFAULT 0,
    lch_password_dt VARCHAR(14) DEFAULT TO_CHAR(CURRENT_TIMESTAMP, 'YYYYMMDDHH24MISS'),
    regis_inf_dt VARCHAR(14) DEFAULT TO_CHAR(CURRENT_TIMESTAMP, 'YYYYMMDDHH24MISS'),
    lchg_inf_dt VARCHAR(14) DEFAULT TO_CHAR(CURRENT_TIMESTAMP, 'YYYYMMDDHH24MISS')
);

-- Create index on commonly queried columns
CREATE INDEX idx_simo_user_1 ON SIMO_USER(username, email);

-- Create SIMO_ROLE table
CREATE TABLE SIMO_ROLE (
    id SERIAL PRIMARY KEY,
    role_id VARCHAR(50) UNIQUE NOT NULL,
    status VARCHAR(1) DEFAULT 'A',
    configuration TEXT,
    regis_inf_dt VARCHAR(14) DEFAULT TO_CHAR(CURRENT_TIMESTAMP, 'YYYYMMDDHH24MISS'),
    lchg_inf_dt VARCHAR(14) DEFAULT TO_CHAR(CURRENT_TIMESTAMP, 'YYYYMMDDHH24MISS')
);

-- Create SIMO_USER_ROLE table
CREATE TABLE SIMO_USER_ROLE (
    id SERIAL PRIMARY KEY,
    user_id VARCHAR(50)  NOT NULL , --REFERENCES SIMO_USER(id),
    role_id VARCHAR(50)  NOT NULL , --REFERENCES SIMO_ROLE(id),
    status VARCHAR(1) DEFAULT 'A',
    regis_inf_dt VARCHAR(14) DEFAULT TO_CHAR(CURRENT_TIMESTAMP, 'YYYYMMDDHH24MISS'),
    lchg_inf_dt VARCHAR(14) DEFAULT TO_CHAR(CURRENT_TIMESTAMP, 'YYYYMMDDHH24MISS')
);

-- Create SIMO_AUTH_MENU table
CREATE TABLE SIMO_AUTH_MENU (
    id SERIAL PRIMARY KEY,
    menu_id VARCHAR(50) NOT NULL,
    menu_name VARCHAR(100) NOT NULL,
    menu_url VARCHAR(200) NOT NULL,
    mode VARCHAR(4) default 'AUTH',
    description TEXT,
    status VARCHAR(1) DEFAULT 'A',
    method VARCHAR(10),
    role_id VARCHAR(50)  NOT NULL,
    configuration TEXT,
    regis_inf_dt VARCHAR(14) DEFAULT TO_CHAR(CURRENT_TIMESTAMP, 'YYYYMMDDHH24MISS'),
    lchg_inf_dt VARCHAR(14) DEFAULT TO_CHAR(CURRENT_TIMESTAMP, 'YYYYMMDDHH24MISS')
);
create unique index idx_simo_auth_menu_1 on SIMO_AUTH_MENU(menu_id, role_id);

-- Create SIMO_METADATA table
CREATE TABLE SIMO_METADATA (
    ID SERIAL PRIMARY KEY,
    LOOKUPCODE VARCHAR(100) NOT NULL,
    LOOKUPCODEID VARCHAR(100) NOT NULL,
    LANGUAGE VARCHAR(10) NOT NULL,
    VALUE VARCHAR(255) NOT NULL,
    ORDERBY VARCHAR(10),
    SERVICENAME VARCHAR(50),
    SHOW_YN VARCHAR(1) DEFAULT 'Y',
    REGIS_INF_DT VARCHAR(14) DEFAULT TO_CHAR(CURRENT_TIMESTAMP, 'YYYYMMDDHH24MISS'),
    LCHG_INF_DT VARCHAR(14) DEFAULT TO_CHAR(CURRENT_TIMESTAMP, 'YYYYMMDDHH24MISS')
);

-- Create SIMO_FILE_MAS table
CREATE TABLE SIMO_FILE_MAS (
    id SERIAL PRIMARY KEY,
    template_code VARCHAR(50) NOT NULL,
    seq_no INTEGER NOT NULL,
    file_name VARCHAR(255) NOT NULL,
    process_status VARCHAR(1) NOT NULL,
    file_status VARCHAR(1) NOT NULL ,
    total_count INTEGER NOT NULL,
    success_count INTEGER NOT NULL,
    file_valid_count INTEGER NOT NULL,
    remark VARCHAR(500),
    file_type VARCHAR(1) NOT NULL,
    regis_user VARCHAR(50) NOT NULL,
    regis_inf_dt VARCHAR(14) DEFAULT TO_CHAR(CURRENT_TIMESTAMP, 'YYYYMMDDHH24MISS'),
    lchg_user VARCHAR(50),
    lchg_inf_dt VARCHAR(14) DEFAULT TO_CHAR(CURRENT_TIMESTAMP, 'YYYYMMDDHH24MISS')
);


-- create configuration table
CREATE TABLE SIMO_CONFIG (
    id SERIAL PRIMARY KEY,
    config_key VARCHAR(100) NOT NULL,
    config_value VARCHAR(100) NOT NULL,
    config_desc TEXT,
    regis_inf_dt VARCHAR(14) DEFAULT TO_CHAR(CURRENT_TIMESTAMP, 'YYYYMMDDHH24MISS'),
    lchg_inf_dt VARCHAR(14) DEFAULT TO_CHAR(CURRENT_TIMESTAMP, 'YYYYMMDDHH24MISS')
);


create table SIMO_FILE_HIS (
    id SERIAL PRIMARY KEY,
    template_code VARCHAR(50) NOT NULL,
    file_name VARCHAR(255) NOT NULL,
    file_upload_dt VARCHAR(8) NOT NULL,
    process_status VARCHAR(1) NOT NULL default 'I',
    file_status VARCHAR(4) NOT NULL default 'UPLD',
    total_count INTEGER NOT NULL default 0,
    success_count INTEGER NOT NULL default 0,
    file_valid_count INTEGER NOT NULL default 0,
    remark VARCHAR(500),
    file_type VARCHAR(1) NOT NULL,
    regis_user VARCHAR(50) NOT NULL,
    regis_inf_dt VARCHAR(14) DEFAULT TO_CHAR(CURRENT_TIMESTAMP, 'YYYYMMDDHH24MISS'),
    lchg_user VARCHAR(50),
    lchg_inf_dt VARCHAR(14) DEFAULT TO_CHAR(CURRENT_TIMESTAMP, 'YYYYMMDDHH24MISS')
);


create table SIMO_FILE_DETAIL_HIS (
    id SERIAL PRIMARY KEY,
    template_code VARCHAR(50) NOT NULL,
    file_name VARCHAR(255) NOT NULL,
    file_upload_dt VARCHAR(8) NOT NULL,
    process_status VARCHAR(1) NOT NULL default 'I',
    row_number VARCHAR(50) NOT NULL,
    cif VARCHAR(50) NOT NULL,
    acount_no VARCHAR(50) ,
    account_name VARCHAR(100) ,
    personal_id VARCHAR(50) ,
    remark VARCHAR(500),
    file_type VARCHAR(1) NOT NULL,
    file_data TEXT NOT NULL,
    regis_user VARCHAR(50) NOT NULL,
    regis_inf_dt VARCHAR(14) DEFAULT TO_CHAR(CURRENT_TIMESTAMP, 'YYYYMMDDHH24MISS'),
    lchg_user VARCHAR(50),
    lchg_inf_dt VARCHAR(14) DEFAULT TO_CHAR(CURRENT_TIMESTAMP, 'YYYYMMDDHH24MISS')
);


-- Create index on commonly queried columns
CREATE INDEX idx_metadata_lookup ON SIMO_METADATA(LOOKUPCODE, LOOKUPCODEID);
CREATE INDEX idx_metadata_service ON SIMO_METADATA(SERVICENAME);
CREATE INDEX idx_file_mas_lookup ON SIMO_FILE_MAS(template_code);


create unique index idx_file_detail_his_1 on SIMO_FILE_DETAIL_HIS(file_name, file_upload_dt, row_number);
create unique index idx_file_his_1 on SIMO_FILE_HIS(file_name, file_upload_dt);


-- Grant table privileges to shbvn
GRANT ALL PRIVILEGES ON ALL TABLES IN SCHEMA public TO shbvn;
GRANT ALL PRIVILEGES ON ALL SEQUENCES IN SCHEMA public TO shbvn;