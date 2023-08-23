Custom Object Storage

lib to process dao layer default implementation and db migration tools

setup test docker db on postgres
docker build -t cos-sdk-db-image .

docker run --rm --name cos-sdk-test-db -p 127.0.0.1:5432:5432 -d cos-sdk-db-image

created 4 connection pools:
Hikari
c3p0
dbcp2
Vibur