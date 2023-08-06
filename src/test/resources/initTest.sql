INSERT INTO TestEntity (name) VALUES ('test1');
INSERT INTO TestEntity (name) VALUES ('test2');

INSERT INTO TestEmployee (name) VALUES ('John Doe');
INSERT INTO TestEmployee (name) VALUES ('Jane Smith');

INSERT INTO TestDependent (name, status, testEmployee_id) VALUES ('Dependent 1', 'Active', 1);
INSERT INTO TestDependent (name, status, testEmployee_id) VALUES ('Dependent 2', 'Inactive', 1);
INSERT INTO TestDependent (name, status, testEmployee_id) VALUES ('Dependent 3', 'Active', 2);