# Проект "Статистический контент Faktura"

### Как запустить?

Для того чтобы запустить приложение с использованием [**Docker**](https://www.docker.com/), необходимо:
1. собрать образы, используя в терминале команды **docker build -t backend .** в модуле "backend" и **docker build . -t frontend** в модуле "frontend";
2. проверить, что каждый из образов корректно собрался, используя команды **docker run -p 8080:8080 backend** и **docker run -p 3000:3000 -d frontend**;
3. перейти в общую папку репозитория, которая называется "content-maker" с помощью команды **cd /content-maker**;
4. запустить docker-compose.yml, используя команду **docker-compose up**