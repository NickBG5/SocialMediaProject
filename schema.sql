-- USERS
CREATE TABLE users (
                       id BIGSERIAL PRIMARY KEY,
                       username VARCHAR(32) UNIQUE NOT NULL,
                       email VARCHAR(255) UNIQUE NOT NULL,
                       created_at TIMESTAMP NOT NULL DEFAULT NOW()
);

-- POSTS
CREATE TABLE posts (
                       id BIGSERIAL PRIMARY KEY,
                       user_id BIGINT NOT NULL REFERENCES users(id) ON DELETE CASCADE,
                       content VARCHAR(500) NOT NULL,
                       created_at TIMESTAMP NOT NULL DEFAULT NOW()
);

CREATE INDEX idx_posts_user_created ON posts(user_id, created_at);
CREATE INDEX idx_posts_created ON posts(created_at);

-- COMMENTS
CREATE TABLE comments (
                          id BIGSERIAL PRIMARY KEY,
                          user_id BIGINT NOT NULL REFERENCES users(id) ON DELETE CASCADE,
                          post_id BIGINT NOT NULL REFERENCES posts(id) ON DELETE CASCADE,
                          content VARCHAR(300) NOT NULL,
                          created_at TIMESTAMP NOT NULL DEFAULT NOW()
);

CREATE INDEX idx_comments_post_created ON comments(post_id, created_at);

-- FOLLOWS
CREATE TABLE follows (
                         id BIGSERIAL PRIMARY KEY,
                         follower_id BIGINT NOT NULL REFERENCES users(id) ON DELETE CASCADE,
                         followed_id BIGINT NOT NULL REFERENCES users(id) ON DELETE CASCADE,
                         created_at TIMESTAMP NOT NULL DEFAULT NOW(),
                         CONSTRAINT unique_follow UNIQUE (follower_id, followed_id)
);

-- LIKES
CREATE TABLE likes (
                       id BIGSERIAL PRIMARY KEY,
                       user_id BIGINT NOT NULL REFERENCES users(id) ON DELETE CASCADE,
                       post_id BIGINT NOT NULL REFERENCES posts(id) ON DELETE CASCADE,
                       created_at TIMESTAMP NOT NULL DEFAULT NOW(),
                       CONSTRAINT unique_like UNIQUE (user_id, post_id)
);
