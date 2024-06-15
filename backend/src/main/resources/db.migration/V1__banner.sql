CREATE TABLE test_bank (
      id VARCHAR(255) PRIMARY KEY,
      NAME VARCHAR(255) UNIQUE
);

CREATE TABLE test_banner (
    id BIGINT PRIMARY KEY NOT NULL AUTO_INCREMENT,
    code VARCHAR(255) UNIQUE,
    name VARCHAR(255),
    picture VARCHAR(255),
    bank VARCHAR(255),
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

INSERT INTO test_bank (id, NAME) VALUES
     ('absolutbank', 'АКБ "Абсолют Банк" (ПАО)'),
     ('agroros', 'АО "БАНК "АГРОРОС"'),
     ('akcept', 'АО "БАНК АКЦЕПТ"'),
     ('alexbank', 'ПАО БАНК "АЛЕКСАНДРОВСКИЙ"'),
     ('apkbank', 'АО КБ "АГРОПРОМКРЕДИТ"'),
     ('bankdolinsk', 'КБ "Долинск" (АО)'),
     ('chelindbank', 'ПАО "ЧЕЛИНДБАНК"'),
     ('expobank', 'АО "Экспобанк"'),
     ('gaztransbank', 'ООО КБ "ГТ БАНК"'),
     ('iturupbank', 'Банк "ИТУРУП" (ООО)'),
     ('jtbank', 'Джей энд Ти Банк (АО)'),
     ('lanta', 'АКБ "Ланта-Банк" (АО)'),
     ('novobank', 'ПАО УКБ "НОВОБАНК"'),
     ('nskbl', 'БАНК "ЛЕВОБЕРЕЖНЫЙ" (ПАО)'),
     ('rostfinance', 'ООО КБ "РОСТФИНАНС"'),
     ('sdm', 'СДМ-Банк (ПАО)'),
     ('siab', 'ПАО БАНК "СИАБ"'),
     ('solidbank', 'АО "Солид Банк"'),
     ('tavrich', 'Таврический Банк (АО)'),
     ('tkbbank', 'ТКБ БАНК ПАО'),
     ('tpsb', 'ПАО "ТОМСКПРОМСТРОЙБАНК"'),
     ('venets-bank', 'АО БАНК "ВЕНЕЦ"'),
     ('vlbb', 'АО "ВЛАДБИЗНЕСБАНК"'),
     ('wildberries', 'ООО "Вайлдберриз Банк"');