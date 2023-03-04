###### BUILD IMAGE ######
FROM node:18.14.2-alpine3.17 AS build
WORKDIR /app

COPY package.json /app/package.json
COPY package-lock.json /app/package-lock.json

RUN npm install
COPY . /app

RUN npm run build


###### DEPLOYMENT IMAGE ######
FROM nginx:1.23.3-alpine-slim
COPY --from=build /app/.nginx.conf /etc/nginx/conf.d/default.conf
WORKDIR /usr/share/nginx/html
RUN rm -rf ./*
COPY --from=build /app/build .
ENTRYPOINT ["nginx", "-g", "daemon off;"]
