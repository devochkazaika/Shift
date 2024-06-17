CREATE TABLE test_bank (
  id UUID PRIMARY KEY,
  name VARCHAR(255) UNIQUE,
  full_name VARCHAR(255) UNIQUE
);

CREATE TABLE test_banner (
    id Serial PRIMARY KEY NOT NULL,
    code VARCHAR(255) UNIQUE,
    name VARCHAR(255),
    picture VARCHAR(255),
    bank UUID,
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

INSERT INTO test_bank (id, name, full_name) VALUES
        ('ab4f35d1-7e6f-4f4b-9000-52d441c4e4de', 'absolutbank', 'АКБ "Абсолют Банк" (ПАО)'),
        ('b6f8a6de-2c8f-4fcd-b97f-52d92ab8e34c', 'agroros', 'АО "БАНК "АГРОРОС"'),
        ('c1f14a5a-3d67-4952-93e1-11204b3f0f52', 'akcept', 'АО "БАНК АКЦЕПТ"'),
        ('d27b3bb9-0e5d-4ff3-9c8b-89a9171d7e53', 'alexbank', 'ПАО БАНК "АЛЕКСАНДРОВСКИЙ"'),
        ('e38d56f2-9a77-4d5c-8a97-8f34e2d4e5d6', 'apkbank', 'АО КБ "АГРОПРОМКРЕДИТ"'),
        ('f494b62a-8b73-4747-878f-2c8e434d785f', 'bankdolinsk', 'КБ "Долинск" (АО)'),
        ('a51a6714-6585-4a2e-94a0-9f2c5b02e9d5', 'chelindbank', 'ПАО "ЧЕЛИНДБАНК"'),
        ('b62b3e9a-7d86-4b99-9734-2a82f1236e74', 'expobank', 'АО "Экспобанк"'),
        ('c73c7b98-8e77-4e7d-9c62-6a92d1bcd1b5', 'gaztransbank', 'ООО КБ "ГТ БАНК"'),
        ('d84d08f4-3f88-4b6c-8d73-2e82e1c4f1c6', 'iturupbank', 'Банк "ИТУРУП" (ООО)'),
        ('e95e14d2-4f99-4e4b-9d84-5a93c2e5f2d7', 'jtbank', 'Джей энд Ти Банк (АО)'),
        ('fa6f21b0-5faa-4f5b-9e95-3a82d3c6e3e8', 'lanta', 'АКБ "Ланта-Банк" (АО)'),
        ('0b7f28a8-6eab-4f6b-9fa6-4b72d4d7f3e9', 'novobank', 'ПАО УКБ "НОВОБАНК"'),
        ('1c802dc6-7e3b-4f7c-9db7-1b82d5e8f4ea', 'nskbl', 'БАНК "ЛЕВОБЕРЕЖНЫЙ" (ПАО)'),
        ('2d9137f4-8e4f-4f8d-9ec8-7a93e6f9f5eb', 'rostfinance', 'ООО КБ "РОСТФИНАНС"'),
        ('3e104dfa-9f61-4f9e-9f39-8a94e7f0e6fc', 'sdm', 'СДМ-Банк (ПАО)'),
        ('4f2152b8-af72-4fa0-900a-9b95e8f1f7fd', 'siab', 'ПАО БАНК "СИАБ"'),
        ('5f3260d6-bf83-4fb1-90ab-2b06e9f2f8fe', 'solidbank', 'АО "Солид Банк"'),
        ('603d76c4-cf94-4fc2-91bc-3b17f4f3f9ff', 'tavrich', 'Таврический Банк (АО)'),
        ('713e82b2-dfa5-4fd3-92cd-4c28f5e4f1e0', 'tkbbank', 'ТКБ БАНК ПАО'),
        ('824f98a0-efa6-4fe4-93de-5d39f6e5f2e1', 'tpsb', 'ПАО "ТОМСКПРОМСТРОЙБАНК"'),
        ('9350a496-00b7-4ff5-94ef-6e4af7e6f3e2', 'venets-bank', 'АО БАНК "ВЕНЕЦ"'),
        ('a461b8e4-11c8-4ff6-95f0-7f5bf8e7f4e3', 'vlbb', 'АО "ВЛАДБИЗНЕСБАНК"'),
        ('b572c0f2-22d9-4ff7-96f1-8f6cf9e8f5e4', 'wildberries', 'ООО "Вайлдберриз Банк"');