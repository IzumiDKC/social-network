CREATE EXTENSION IF NOT EXISTS citext;

CREATE TABLE users (
  id          BIGSERIAL PRIMARY KEY,
  username    VARCHAR(30) UNIQUE NOT NULL,
  email       CITEXT UNIQUE NOT NULL,
  password    TEXT NOT NULL,
  full_name   VARCHAR(100),
  bio         TEXT,
  avatar_url  TEXT,
  created_at  TIMESTAMPTZ NOT NULL DEFAULT now()
);

CREATE TABLE posts (
  id          BIGSERIAL PRIMARY KEY,
  author_id   BIGINT NOT NULL REFERENCES users(id) ON DELETE CASCADE,
  content     TEXT NOT NULL,
  media_url   TEXT,
  privacy     SMALLINT NOT NULL DEFAULT 0,
  created_at  TIMESTAMPTZ NOT NULL DEFAULT now()
);
