FROM node:8
RUN npm install -g http-server
ADD src/ /usr/apps/hello-docker/
#Override default index.html
COPY src/index-docker.html /usr/apps/hello-docker/index.html
WORKDIR /usr/apps/hello-docker/

CMD ["http-server", "-s"]