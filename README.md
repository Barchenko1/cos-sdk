Custom Object Storage

lib to process dao layer default implementation and db migration tools

setup test docker db on postgres
docker build -t my-postgres-image .
docker run --rm --name docker-postgres -e POSTGRES_PASSWORD=sa -e POSTGRES_USER=sa -p 127.0.0.1:5432:5432 -d my-postgres-image

created 4 connection pools:
Hikari
c3p0
dbcp2
vibur