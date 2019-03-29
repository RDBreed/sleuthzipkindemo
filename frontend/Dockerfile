FROM node:8
ADD ./ /usr/apps/hello-docker/
#Override default index.html
COPY ./src/js/state-docker.js /usr/apps/hello-docker/src/js/state.js
WORKDIR /usr/apps/hello-docker/
RUN npm install
CMD [ "npm", "run-script", "start-docker" ]
EXPOSE 8080
