CREATE TABLE IF NOT EXISTS stories (
     id SERIAL PRIMARY KEY NOT NULL,
     bank_id VARCHAR(255),
     platform VARCHAR(255),
     font_size VARCHAR(255),
     preview_title VARCHAR(255),
     preview_title_color VARCHAR(255),
     preview_url VARCHAR(255),
     preview_gradient VARCHAR(255),
     approved BOOLEAN
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


