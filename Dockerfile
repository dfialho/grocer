FROM eclipse-temurin:17-jre-alpine

ENV LANG='en_US.UTF-8' LANGUAGE='en_US:en'

COPY build/quarkus-app/lib/ /deployments/lib/
COPY build/quarkus-app/*.jar /deployments/
COPY build/quarkus-app/app/ /deployments/app/
COPY build/quarkus-app/quarkus/ /deployments/quarkus/

ENTRYPOINT java -jar /deployments/quarkus-run.jar -Djava.util.logging.manager=org.jboss.logmanager.LogManager
