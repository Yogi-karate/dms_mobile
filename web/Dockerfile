FROM mhart/alpine-node:latest
MAINTAINER tramm

RUN mkdir -p /var/www/app
WORKDIR /var/www/app
COPY ./package.json ./yarn.lock /var/www/app/
# Try installing python 
RUN apk --no-cache add --virtual builds-deps build-base python
# RUN apk --update --no-cache --virtual dev-dependencies add git python make g++
# RUN yarn install --frozen-lockfile --production && yarn cache clean
RUN npm install

COPY . /var/www/app
RUN npm run build

# RUN apk del dev-dependencies

EXPOSE 8000
CMD ["npm", "start"]