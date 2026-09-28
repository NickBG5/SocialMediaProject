CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE TABLE users (
                       id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
                       username VARCHAR(32) UNIQUE NOT NULL,
                       email VARCHAR(255) UNIQUE NOT NULL,
                       display_name VARCHAR(64),
                       bio TEXT,
                       avatar_url TEXT,
                       created_at TIMESTAMP NOT NULL DEFAULT NOW(),
                       updated_at TIMESTAMP NOT NULL DEFAULT NOW()
);

CREATE INDEX idx_users_username ON users(username);

CREATE TABLE auth_credentials (
                                  id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
                                  user_id UUID NOT NULL REFERENCES users(id) ON DELETE CASCADE,
                                  password_hash TEXT,
                                  provider VARCHAR(32) NOT NULL DEFAULT 'local',
                                  provider_user_id TEXT,
                                  created_at TIMESTAMP NOT NULL DEFAULT NOW()
);

CREATE INDEX idx_auth_user_id ON auth_credentials(user_id);

CREATE TABLE posts (
                       id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
                       user_id UUID NOT NULL REFERENCES users(id) ON DELETE CASCADE,
                       content TEXT NOT NULL,
                       created_at TIMESTAMP NOT NULL DEFAULT NOW(),
                       updated_at TIMESTAMP NOT NULL DEFAULT NOW()
);

CREATE INDEX idx_posts_user_id ON posts(user_id);
CREATE INDEX idx_posts_created_at ON posts(created_at);

CREATE TABLE media (
                       id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
                       post_id UUID NOT NULL REFERENCES posts(id) ON DELETE CASCADE,
                       url TEXT NOT NULL,
                       media_type VARCHAR(16) NOT NULL,
                       created_at TIMESTAMP NOT NULL DEFAULT NOW()
);

CREATE INDEX idx_media_post_id ON media(post_id);

CREATE TABLE comments (
                          id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
                          post_id UUID NOT NULL REFERENCES posts(id) ON DELETE CASCADE,
                          user_id UUID NOT NULL REFERENCES users(id) ON DELETE CASCADE,
                          parent_comment_id UUID REFERENCES comments(id) ON DELETE CASCADE,
                          content TEXT NOT NULL,
                          created_at TIMESTAMP NOT NULL DEFAULT NOW()
);

CREATE INDEX idx_comments_post_id ON comments(post_id);
CREATE INDEX idx_comments_user_id ON comments(user_id);
CREATE INDEX idx_comments_parent_id ON comments(parent_comment_id);

CREATE TABLE likes (
                       id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
                       user_id UUID NOT NULL REFERENCES users(id) ON DELETE CASCADE,
                       post_id UUID REFERENCES posts(id) ON DELETE CASCADE,
                       comment_id UUID REFERENCES comments(id) ON DELETE CASCADE,
                       created_at TIMESTAMP NOT NULL DEFAULT NOW(),
                       CHECK (
                           (post_id IS NOT NULL AND comment_id IS NULL) OR
                           (post_id IS NULL AND comment_id IS NOT NULL)
                           )
);

CREATE INDEX idx_likes_user_id ON likes(user_id);
CREATE INDEX idx_likes_post_id ON likes(post_id);
CREATE INDEX idx_likes_comment_id ON likes(comment_id);

CREATE TABLE follows (
                         follower_id UUID NOT NULL REFERENCES users(id) ON DELETE CASCADE,
                         following_id UUID NOT NULL REFERENCES users(id) ON DELETE CASCADE,
                         created_at TIMESTAMP NOT NULL DEFAULT NOW(),
                         PRIMARY KEY (follower_id, following_id)
);

CREATE INDEX idx_follows_follower ON follows(follower_id);
CREATE INDEX idx_follows_following ON follows(following_id);