# Demo of Sleuth & Zipkin

This is a demonstration of how to use Sleuth, Zipkin & an ELK stack for tracing requests.
## HOWTO
To start ELK (Elasticsearch, Logstash, Kibana) & the Zipkin Server, ensure that you have **Docker** & **Docker-compose** installed.
In the root you can run ELK & Zipkin with `docker-compose up`.

*The application itself consists of 5 (micro)services.
Ensure you have Maven installed on your computer.
Service A, B, C & D are REST services, while SOAP Service is a SOAP service.
To ensure that Sleuth will work with the SOAP client & server, there is a submodule added (sleuthsoapinterceptor).*

Go to the `sleuthsoapinterceptor` directory & perform a `mvn clean install`.
After this, perform a `mvn clean package` on the root of the project.
You can start the applications by `docker-compose -f docker-compose-services.yml up`. Or use any other way to start the applications you feel comfortable with.

Note: Ensure you have enough RAM assigned to Docker. See also [http://elk-docker.readthedocs.io/](http://elk-docker.readthedocs.io/), from which we
 are using the Dockerfile for our ELK environment.

## Using the application, Kibana & Zipkin
Go to http://localhost:8081/a/ to call service A.
 
 Go to http://localhost:5601 and go to management. Here you have to create a index pattern; for example `logstash-*`. After you configured this, you can discover your logs.

Add fields like `severity`, `facility`, `message` to view the logs of the services.
Add `X-B3-SpanId`, `X-B3-TraceId` or even `X-B3-CONVID` to view Sleuth related information.

Go to http://localhost:9411/zipkin/ to view the user interface of Zipkin. Push `Find Traces` to view the traces. You can click on one trace to view the service calls/spans.
Use http://localhost:8081/a/error/ or http://localhost:8081/a/performance to view different outcomes.