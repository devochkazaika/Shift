CREATE TABLE IF NOT EXISTS history
(
    id SERIAL PRIMARY KEY NOT NULL,
    component_type varchar(30),
    operation_type varchar(30),
    status varchar(30),
    component_id BIGINT,
    time date,
    user_name varchar(255),
    CONSTRAINT fk_story FOREIGN KEY (component_id) REFERENCES stories(id)
);