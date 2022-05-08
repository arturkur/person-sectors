FROM node:alpine AS build
WORKDIR /app

COPY package.json /app/package.json
RUN npm install
RUN npm install -g @angular/cli

EXPOSE 4200

COPY . /app

RUN npm run build -- --configuration production

FROM nginx:alpine

RUN apk add --no-cache bash

COPY nginx.conf /etc/nginx/nginx.conf
COPY --from=build /app/dist/frontend/ /usr/share/nginx/html

WORKDIR /etc/nginx

CMD ["nginx", "-g", "daemon off;"]
