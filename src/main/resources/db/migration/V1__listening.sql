-- V1: users + Listening module (topics, lessons, sentences, audio, attempts, progress)
-- Modules (Spring Modulith): user = users | content = topics, lessons, sentences
--                            audio = audio_assets | learning = attempts, lesson_progress

CREATE TABLE users (
    id            BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    email         VARCHAR(255) NOT NULL UNIQUE,
    password_hash VARCHAR(255) NOT NULL,
    display_name  VARCHAR(100) NOT NULL,
    role          VARCHAR(20)  NOT NULL DEFAULT 'LEARNER'
        CHECK (role IN ('LEARNER', 'ADMIN')),
    created_at    TIMESTAMPTZ  NOT NULL DEFAULT now()
);

CREATE TABLE topics (
    id            BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    level         VARCHAR(2)   NOT NULL
        CHECK (level IN ('A1', 'A2', 'B1', 'B2', 'C1', 'C2')),
    slug          VARCHAR(100) NOT NULL,
    title         VARCHAR(200) NOT NULL,
    display_order INT          NOT NULL DEFAULT 0,
    UNIQUE (level, slug)
);

CREATE TABLE lessons (
    id            BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    topic_id      BIGINT       NOT NULL REFERENCES topics (id),
    title         VARCHAR(200) NOT NULL,
    status        VARCHAR(20)  NOT NULL DEFAULT 'DRAFT'
        CHECK (status IN ('DRAFT', 'PUBLISHED')),
    display_order INT          NOT NULL DEFAULT 0
);
CREATE INDEX idx_lessons_topic ON lessons (topic_id, display_order);

CREATE TABLE sentences (
    id         BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    lesson_id  BIGINT NOT NULL REFERENCES lessons (id),
    position   INT    NOT NULL,
    text_en    TEXT   NOT NULL,
    meaning_vi TEXT,
    UNIQUE (lesson_id, position)
);

CREATE TABLE audio_assets (
    id           BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    sentence_id  BIGINT       NOT NULL REFERENCES sentences (id),
    speed        VARCHAR(10)  NOT NULL CHECK (speed IN ('SLOW', 'NORMAL')),
    voice_name   VARCHAR(100) NOT NULL,
    storage_path VARCHAR(500) NOT NULL,
    char_count   INT          NOT NULL CHECK (char_count > 0),
    created_at   TIMESTAMPTZ  NOT NULL DEFAULT now(),
    UNIQUE (sentence_id, speed, voice_name)
);

-- Every submission is kept (learners may redo the same sentence many times).
CREATE TABLE attempts (
    id              BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    user_id         BIGINT      NOT NULL REFERENCES users (id),
    sentence_id     BIGINT      NOT NULL REFERENCES sentences (id),
    exercise_type   VARCHAR(20) NOT NULL
        CHECK (exercise_type IN ('DICTATION', 'FILL_BLANK')),
    audio_speed     VARCHAR(10) NOT NULL CHECK (audio_speed IN ('SLOW', 'NORMAL')),
    blank_positions INT[],
    answer_text     TEXT        NOT NULL,
    score           SMALLINT    NOT NULL CHECK (score BETWEEN 0 AND 100),
    is_correct      BOOLEAN     NOT NULL,
    created_at      TIMESTAMPTZ NOT NULL DEFAULT now(),
    -- blank_positions is required for FILL_BLANK and must be NULL for DICTATION
        CHECK ((exercise_type = 'FILL_BLANK') = (blank_positions IS NOT NULL))
);
CREATE INDEX idx_attempts_user_sentence ON attempts (user_id, sentence_id, created_at DESC);

-- One summary row per (user, lesson). Recomputed from attempts, never incremented.
CREATE TABLE lesson_progress (
    id               BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    user_id          BIGINT      NOT NULL REFERENCES users (id),
    lesson_id        BIGINT      NOT NULL REFERENCES lessons (id),
    status           VARCHAR(20) NOT NULL DEFAULT 'IN_PROGRESS'
        CHECK (status IN ('IN_PROGRESS', 'COMPLETED')),
    done_sentences   INT         NOT NULL DEFAULT 0 CHECK (done_sentences >= 0),
    last_activity_at TIMESTAMPTZ NOT NULL DEFAULT now(),
    UNIQUE (user_id, lesson_id)
);
