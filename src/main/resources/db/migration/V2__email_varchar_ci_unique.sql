
ALTER TABLE users ALTER COLUMN email TYPE varchar(255);
DROP INDEX IF EXISTS ux_users_email_ci;
CREATE UNIQUE INDEX ux_users_email_ci ON users (lower(email));
