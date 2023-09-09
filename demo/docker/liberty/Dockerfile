FROM openliberty/open-liberty:23.0.0.6-full-java17-openj9


COPY --chown=1001:0  target/jax-rs.war /config/dropins/
COPY --chown=1001:0  target/server.xml /config

RUN configure.sh