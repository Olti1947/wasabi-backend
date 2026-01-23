CREATE TABLE chat_messages(
id BIGSERIAL PRIMARY KEY,

from_user VARCHAR(255) NOT NULL,
to_user VARCHAR(255) NOT NULL,

content TEXT NOT NULL,

timestamp TIMESTAMP NOT NULL,

message_type VARCHAR(50) NOT NULL DEFAULT 'TEXT',

is_read BOOLEAN NOT NULL DEFAULT FALSE

);

CREATE INDEX idx_chat_messages_from_user ON chat_messages(from_user);
CREATE INDEX idx_chat_messages_to_user ON chat_messages(to_user);
CREATE INDEX idx_chat_messages_timestamp ON chat_messages(timestamp);
