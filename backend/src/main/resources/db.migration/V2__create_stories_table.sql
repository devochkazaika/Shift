CREATE TABLE IF NOT EXISTS stories (
     id Serial PRIMARY KEY NOT NULL,
     bank_id VARCHAR(255),
     platform VARCHAR(255),
     font_size VARCHAR(255),
     preview_title VARCHAR(255),
     preview_title_color VARCHAR(255),
     preview_url VARCHAR(255),
     preview_gradient VARCHAR(255),
     approved VARCHAR(15)
);

CREATE TABLE IF NOT EXISTS frames (
    id UUID PRIMARY KEY,
    story_id BIGINT,
    title VARCHAR(255),
    text TEXT,
    text_color VARCHAR(255),
    picture_url VARCHAR(255),
    visible_button_or_none VARCHAR(255),
    button_text VARCHAR(255),
    button_text_color VARCHAR(255),
    button_background_color VARCHAR(255),
    button_url VARCHAR(255),
    gradient VARCHAR(255),
    approved BOOLEAN,
    CONSTRAINT fk_story FOREIGN KEY (story_id) REFERENCES stories(id)
);

CREATE TABLE IF NOT EXISTS test_banner (
     id Serial PRIMARY KEY NOT NULL,
     code VARCHAR(255) UNIQUE,
     name VARCHAR(255),
     picture VARCHAR(255),
     bank_id VARCHAR(255),
     main_banner BIGINT,
     icon VARCHAR(255),
     text TEXT,
     url VARCHAR(255),
     url_text VARCHAR(255),
     priority INTEGER,
     color VARCHAR(255),
     available BOOLEAN,
     platform VARCHAR(255),
     CONSTRAINT fk_main_banner FOREIGN KEY (main_banner) REFERENCES test_banner(id)
);

