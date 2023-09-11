FROM open-liberty:23.0.0.6-full-java17-openj9


COPY --chown=1001:0  ./demo.war /config/dropins/
COPY --chown=1001:0  ./server.xml /config
COPY --chown=1001:0  ./sqlite-jdbc-3.43.0.0.jar /liberty/usr/shared/resources/
#COPY --chown=1001:0  ./key.p12 /liberty/usr/defaultServer/resources/security/
#COPY --chown=1001:0  ./ltpa.keys /liberty/usr/defaultServer/resources/security/
RUN configure.sh