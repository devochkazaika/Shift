CREATE TABLE IF NOT EXISTS history
(
    id SERIAL PRIMARY KEY NOT NULL,
    component_type varchar(30),
    operation_type varchar(30),
    bank varchar(30),
    platform varchar(30),
    status varchar(30),
    component_id BIGINT,
    day date,
    time time,
    user_name varchar(255)
);