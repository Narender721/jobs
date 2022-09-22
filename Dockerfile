FROM node:16-alpine as build-stage

ARG APP_HOME=/app
ARG PUBLIC_URL
ENV PUBLIC_URL=${PUBLIC_URL:-}

WORKDIR ${APP_HOME}
COPY . ${APP_HOME}

ENV PATH ${APP_HOME}/node_modules/.bin:$PATH

RUN npm install --legacy-peer-deps
RUN PUBLIC_URL=${PUBLIC_URL} npm run build
