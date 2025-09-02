
DROP TABLE IF EXISTS message_attachments CASCADE;
DROP TABLE IF EXISTS read_statuses CASCADE;
DROP TABLE IF EXISTS user_statuses CASCADE;
DROP TABLE IF EXISTS messages CASCADE;
DROP TABLE IF EXISTS channels CASCADE;
DROP TABLE IF EXISTS users CASCADE;
DROP TABLE IF EXISTS binary_contents CASCADE;

CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

-- ===== 테이블 생성 =====
CREATE TABLE binary_contents (
  id            uuid PRIMARY KEY DEFAULT uuid_generate_v4(),
  created_at    timestamptz NOT NULL,
  file_name     varchar(255) NOT NULL,
  size          bigint NOT NULL,
  content_type  varchar(100) NOT NULL,
  bytes         bytea NOT NULL
);

CREATE TABLE users (
  id           uuid PRIMARY KEY DEFAULT uuid_generate_v4(),
  created_at   timestamptz NOT NULL,
  updated_at   timestamptz,
  username     varchar(50)  NOT NULL,
  email        varchar(100) NOT NULL,
  password     varchar(60)  NOT NULL,
  profile_id   uuid NULL,
  CONSTRAINT uk_users_username UNIQUE (username),
  CONSTRAINT uk_users_email    UNIQUE (email),
  CONSTRAINT fk_users_profile
    FOREIGN KEY (profile_id) REFERENCES binary_contents(id)
    ON DELETE SET NULL
);

CREATE TABLE channels (
  id           uuid PRIMARY KEY DEFAULT uuid_generate_v4(),
  created_at   timestamptz NOT NULL,
  updated_at   timestamptz,
  name         varchar(100),
  description  varchar(500),
  type         varchar(10) NOT NULL CHECK (type IN ('PUBLIC', 'PRIVATE'))
);

CREATE TABLE messages (
  id           uuid PRIMARY KEY DEFAULT uuid_generate_v4(),
  created_at   timestamptz NOT NULL,
  updated_at   timestamptz,
  content      text,
  channel_id   uuid NOT NULL,
  author_id    uuid,
  CONSTRAINT fk_messages_channel
    FOREIGN KEY (channel_id) REFERENCES channels(id)
    ON DELETE CASCADE,
  CONSTRAINT fk_messages_author
    FOREIGN KEY (author_id) REFERENCES users(id)
    ON DELETE SET NULL
);

CREATE TABLE read_statuses (
  id            uuid PRIMARY KEY DEFAULT uuid_generate_v4(),
  created_at    timestamptz NOT NULL,
  updated_at    timestamptz,
  user_id       uuid,
  channel_id    uuid,
  last_read_at  timestamptz NOT NULL,
  CONSTRAINT fk_read_user
    FOREIGN KEY (user_id)    REFERENCES users(id)
        ON DELETE CASCADE,
  CONSTRAINT fk_read_channel
    FOREIGN KEY (channel_id) REFERENCES channels(id)
        ON DELETE CASCADE,
  CONSTRAINT uk_read_user_channel UNIQUE (user_id, channel_id)
);

CREATE TABLE user_statuses (
  id             uuid PRIMARY KEY DEFAULT uuid_generate_v4(),
  created_at     timestamptz NOT NULL,
  updated_at     timestamptz,
  user_id        uuid NOT NULL,
  last_active_at timestamptz NOT NULL,
  CONSTRAINT fk_status_user
    FOREIGN KEY (user_id) REFERENCES users(id)
        ON DELETE CASCADE,
  CONSTRAINT uk_status_user UNIQUE (user_id)
);

CREATE TABLE message_attachments (
  message_id    uuid,
  attachment_id uuid,
  CONSTRAINT fk_ma_message
    FOREIGN KEY (message_id)    REFERENCES messages(id)
        ON DELETE CASCADE,
  CONSTRAINT fk_ma_attachment
    FOREIGN KEY (attachment_id) REFERENCES binary_contents(id)
        ON DELETE CASCADE
);

-- ===== 인덱스 =====
-- CREATE INDEX IF NOT EXISTS idx_messages_channel_created  ON messages (channel_id, created_at DESC);
-- CREATE INDEX IF NOT EXISTS idx_messages_author           ON messages (author_id);
-- CREATE INDEX IF NOT EXISTS idx_read_statuses_user        ON read_statuses (user_id);
-- CREATE INDEX IF NOT EXISTS idx_read_statuses_channel     ON read_statuses (channel_id);
-- CREATE INDEX IF NOT EXISTS idx_users_profile             ON users (profile_id);
