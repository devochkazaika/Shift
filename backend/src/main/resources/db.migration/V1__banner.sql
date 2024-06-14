CREATE TABLE test_bank (
      id BIGINT PRIMARY KEY,
      C_NAME VARCHAR(255)
);

CREATE TABLE test_banner (
    id BIGINT PRIMARY KEY,
    code VARCHAR(255),
    name VARCHAR(255),
    picture VARCHAR(255),
    bank BIGINT,
    main_banner BIGINT,
    icon VARCHAR(255),
    text TEXT,
    url VARCHAR(255),
    url_text VARCHAR(255),
    priority INTEGER,
    color VARCHAR(255),
    available BOOLEAN,
    platform VARCHAR(255),
    CONSTRAINT fk_bank FOREIGN KEY (bank) REFERENCES test_bank(id),
    CONSTRAINT fk_main_banner FOREIGN KEY (main_banner) REFERENCES test_banner(id)
);