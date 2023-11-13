FROM artifactory.bankplus.ru:443/docker-registry/ext/adoptopenjdk/openjdk11:alpine

ARG JAR_FILE

COPY ${JAR_FILE} /home/app/static-content-backend.jar

RUN mkdir -p /home/app/config

WORKDIR /home/app

COPY config/ config/



ENTRYPOINT ["java", "-D_static-content-backend -Dfile.encoding=UTF-8 -Xms64m -Xmx256m", "-jar", "static-content-backend.jar" ]

# define arguments for labels
ARG VERSION
ARG COMMIT_HASH
ARG CREATION_DATE

# set labels
LABEL \
  org.opencontainers.image.title="static-content-backend" \
  org.opencontainers.image.description="Автоматический деплой статического контента" \
  org.opencontainers.image.version=${VERSION} \
  org.opencontainers.image.revision=${COMMIT_HASH} \
  org.opencontainers.image.created=${CREATION_DATE}
EXPOSE 9280