# for production
FROM node:17.9.0-alpine3.15
WORKDIR /app
COPY package*.json ./
RUN npm install --only=production
COPY bin /app/bin
COPY routes /app/routes
COPY ./app.js /app
EXPOSE 3000
ENTRYPOINT ["npm","start"]