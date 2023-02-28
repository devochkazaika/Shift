FROM node:18-alpine
WORKDIR /frontend
COPY package.json /frontend/package.json
RUN npm install
COPY . .
RUN npm run build
ENV NODE_ENV production
EXPOSE 3000
CMD [ "npx", "serve", "build" ]