CREATE TABLE IF NOT EXISTS history
(
    id SERIAL PRIMARY KEY NOT NULL,
    component_type varchar(30),
    operation_type varchar(30),
    bank varchar(30),
    platform varchar(30),
    status varchar(30),
    main_component_id BIGINT,
    main_additional_uuid UUID,
    component_id BIGINT,
    additional_uuid UUID,
    rollback_able BOOLEAN,
    "day" date,
    time time,
    user_name varchar(255)
);