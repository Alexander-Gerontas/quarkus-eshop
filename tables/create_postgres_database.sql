SELECT 'CREATE DATABASE quarkus-db'
WHERE NOT EXISTS (SELECT FROM pg_database WHERE datname = 'quarkus-db')\gexec
