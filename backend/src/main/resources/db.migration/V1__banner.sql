CREATE TABLE test_banner (
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