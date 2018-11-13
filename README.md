# Demo of Sleuth & Zipkin

This is a demonstration of how to use Sleuth, Zipkin & an ELK stack for tracing requests.
You may take a look at the presentation given on Devoxx by bit.ly/sleuth-zipkin-devoxx
## HOWTO
To start ELK (Elasticsearch, Logstash, Kibana) & the Zipkin Server, ensure that you have **Docker** & **Docker-compose** installed.
In the root you can run ELK & Zipkin with `docker-compose up monitoring`. 
Go to http://localhost:5601 & http://localhost:9411 to check if you can reach respectively Kibana & Zipkin.

*The application itself consists of 5 (micro)services.
Ensure you have Maven installed on your computer.
The Breakfast service, Machine service, Temperature service & Preference service are REST services, while the Energy Service is a SOAP service.
To ensure that Sleuth will work with the SOAP client & server, there is a submodule added (sleuthsoapinterceptor).*

Go to the `sleuthsoapinterceptor` directory & perform a `mvn clean install`.
After this, perform a `mvn clean package` on the root of the project.
You can start the applications by `docker-compose up services`. Or use any other way to start the applications you feel comfortable with.

Note: Ensure you have enough RAM assigned to Docker. See also [http://elk-docker.readthedocs.io/](http://elk-docker.readthedocs.io/) why; from 
which we are using the Dockerfile for our ELK environment.

## Using the application, Kibana & Zipkin
Go to http://localhost:8080 to go to the frontpage, to play with the toaster, eggs, bacon & coffee.
 
 Go to http://localhost:5601 and go to management. Here you have to create a index pattern; for example `logstash-*`. After you configured this, you can discover your logs.

Add fields like `severity`, `facility`, `message` to view the logs of the services.
Add `X-B3-SpanId`, `X-B3-TraceId` or even `X-B3-CONVID` to view Sleuth related information.

Go to http://localhost:9411/zipkin/ to view the user interface of Zipkin. Push `Find Traces` to view the traces. You can click on one trace to view the service calls/spans.
Use http://localhost:8081/a/error/ or http://localhost:8081/a/performance to view different outcomes.
