# Stage 1. Build react app
FROM artifactory.bankplus.ru:443/registry/ext/node:lts-18.12.1-alpine3.16 AS build-stage

# app directory
ARG APP_DIR=/site/app

ENV HTTP_PROXY http://proxy.ftc.ru:3128
ENV HTTPS_PROXY http://proxy.ftc.ru:3128
ENV NO_PROXY ".ftc.ru,.bankplus.ru,127.0.0.0"

ENV OWNER=$OWNER

WORKDIR ${APP_DIR}

COPY package*.json ./
RUN npm config set @faktura:registry https://artifactory.bankplus.ru/artifactory/api/npm/react-lib-local
RUN npm config set @fbalance:registry https://artifactory.bankplus.ru/artifactory/api/npm/react-lib-local
RUN npm install --production

COPY . .

RUN npm run build

LABEL \
  org.opencontainers.image.title="static-content-front" \
  org.opencontainers.image.description="Автоматический деплой статического контента frontend"

###### DEPLOYMENT IMAGE ######
#FROM nginx:1.17.2-alpine
FROM artifactory.bankplus.ru/registry/ext/nginx:1.17.2-alpine

RUN mkdir /usr/share/nginx/html/static-content

COPY --from=build-stage /site/app/build /usr/share/nginx/html/static-content

COPY --from=build-stage /site/app/nginx.conf /etc/nginx/nginx.conf

LABEL container_owner=${OWNER}

EXPOSE 80
ENTRYPOINT ["nginx", "-g", "daemon off;"]
