-- Step 1: Drop the Database (be careful with this command)
DROP DATABASE IF EXISTS test_db;
-- Step 2: Recreate the Database
CREATE DATABASE test_db;

CREATE TABLE TestEntity (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL
);

-- Step 4: Optional - Insert initial data into the "TestEntity" table
INSERT INTO TestEntity (name) VALUES ('test1');
INSERT INTO TestEntity (name) VALUES ('test2');