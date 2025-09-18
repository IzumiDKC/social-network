-- V5__seed_data.sql (đã sửa)
-- Users demo (mật khẩu 123456 đã băm)
INSERT INTO users (username, email, password, created_at) VALUES
('alice','alice@example.com','{bcrypt}$2a$10$Z6NlXqSmzYgNzoDEo5kbA.y3rK5pMwhDY59u6x0oTjNydWBP7obF6', now()),
('bob',  'bob@example.com',  '{bcrypt}$2a$10$Z6NlXqSmzYgNzoDEo5kbA.y3rK5pMwhDY59u6x0oTjNydWBP7obF6', now());

-- Alice follow Bob
INSERT INTO follows (follower_id, followee_id, created_at)
VALUES ((SELECT id FROM users WHERE username='alice'),
        (SELECT id FROM users WHERE username='bob'),
        now());

-- Posts demo
INSERT INTO posts (author_id, content, created_at) VALUES
((SELECT id FROM users WHERE username='alice'),'Hello from Alice!', now()),
((SELECT id FROM users WHERE username='bob'),  'Hi, I am Bob 😎',  now());
