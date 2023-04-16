drop objects if exist;

create table TestEntity (
    id bigint AUTO_INCREMENT primary key,
    name varchar(255)
)

INSERT INTO TestEntity (name) VALUES ('test1');
INSERT INTO TestEntity (name) VALUES ('test2');