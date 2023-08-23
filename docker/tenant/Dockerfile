# Use the official PostgreSQL image as the base image
FROM postgres:latest

# Environment variables to configure PostgreSQL
ENV POSTGRES_DB=tenant_test_db
ENV POSTGRES_USER=postgres
ENV POSTGRES_PASSWORD=password

# Expose the PostgreSQL port (default is 5432)
EXPOSE 5433

# Optionally, you can add any custom initialization scripts
# For example, if you have a custom SQL script named init.sql in the same directory as the Dockerfile:
COPY ./initDB.sql /docker-entrypoint-initdb.d/

# The official PostgreSQL image automatically runs any SQL scripts placed in /docker-entrypoint-initdb.d/

# The container will start the PostgreSQL server automatically
# CMD ["postgres"]