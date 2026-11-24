-- V2: Grammar module (category tree, packs, questions, options, attempts)

CREATE TABLE grammar_categories (
    id            BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    parent_id     BIGINT REFERENCES grammar_categories (id),
    title         VARCHAR(200) NOT NULL,
    display_order INT NOT NULL DEFAULT 0
);
CREATE INDEX idx_grammar_categories_parent ON grammar_categories (parent_id, display_order);

CREATE TABLE grammar_packs (
    id             BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    category_id    BIGINT       NOT NULL REFERENCES grammar_categories (id),
    title          VARCHAR(200) NOT NULL,
    theory_content TEXT         NOT NULL,
    status         VARCHAR(20)  NOT NULL DEFAULT 'DRAFT'
        CHECK (status IN ('DRAFT', 'PUBLISHED')),
    display_order  INT          NOT NULL DEFAULT 0
);
CREATE INDEX idx_grammar_packs_category ON grammar_packs (category_id, display_order);

CREATE TABLE grammar_questions (
    id          BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    pack_id     BIGINT NOT NULL REFERENCES grammar_packs (id),
    position    INT    NOT NULL,
    stem_en     TEXT   NOT NULL,
    stem_vi     TEXT,
    explanation TEXT   NOT NULL,
    UNIQUE (pack_id, position)
);

CREATE TABLE question_options (
    id          BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    question_id BIGINT  NOT NULL REFERENCES grammar_questions (id),
    label       CHAR(1) NOT NULL CHECK (label IN ('A', 'B', 'C', 'D')),
    text_en     TEXT    NOT NULL,
    text_vi     TEXT,
    is_correct  BOOLEAN NOT NULL DEFAULT FALSE,
    UNIQUE (question_id, label),
    -- lets grammar_attempts prove the chosen option belongs to the question
    UNIQUE (id, question_id)
);
-- At most one correct option per question (the "at least one" rule is checked in the service).
CREATE UNIQUE INDEX uq_one_correct_option ON question_options (question_id) WHERE is_correct;

CREATE TABLE grammar_attempts (
    id                 BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    user_id            BIGINT      NOT NULL REFERENCES users (id),
    question_id        BIGINT      NOT NULL REFERENCES grammar_questions (id),
    selected_option_id BIGINT      NOT NULL,
    is_correct         BOOLEAN     NOT NULL,
    created_at         TIMESTAMPTZ NOT NULL DEFAULT now(),
    FOREIGN KEY (selected_option_id, question_id)
        REFERENCES question_options (id, question_id)
);
CREATE INDEX idx_grammar_attempts_user_question
    ON grammar_attempts (user_id, question_id, created_at DESC);
