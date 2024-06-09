FROM quay.io/keycloak/keycloak:latest

# Копируем файл realm-export.json в контейнер
COPY ./* /opt/keycloak/data/import/realm-export

# Установка переменных окружения
ENV KC_DB=postgres
ENV KC_DB_URL_HOST=postgres
ENV KC_DB_URL_DATABASE=keycloak
ENV KC_DB_PASSWORD=123456
ENV KC_DB_USERNAME=keycloak
ENV KC_DB_SCHEMA=public
ENV KEYCLOAK_ADMIN=admin
ENV KEYCLOAK_ADMIN_PASSWORD=password

# Открытие порта 8080
EXPOSE 8080

# Установка правильной команды запуска
ENTRYPOINT ["/opt/keycloak/bin/kc.sh", "start-dev", "--import-realm"]
