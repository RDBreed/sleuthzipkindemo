FROM sebp/elk

ADD ./03-gelf-input.conf /etc/logstash/conf.d/03-gelf-input.conf
ADD ./30-output.conf /etc/logstash/conf.d/30-output.conf

# expose GELF's default UDP port (12201)
EXPOSE 12201/udp