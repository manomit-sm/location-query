FROM eclipse-temurin:17-jdk-alpine
ENV HOME=/usr/app
ARG db_url
ENV DB_URL=$db_url
ARG db_user
ENV DB_USER=$db_user
ARG db_password
ENV DB_PASSWORD=$db_password
ARG keystore_password
ENV KEYSTORE_PASSWORD=$keystore_password
RUN mkdir -p $HOME
WORKDIR $HOME
ADD . $HOME
RUN chmod +x mvnw
RUN --mount=type=cache,target=/root/.m2 ./mvnw -f $HOME/pom.xml clean install
ARG JAR_FILE=$HOME/target/LOCATION_QUERY.jar
COPY $JAR_FILE /app/runner.jar
EXPOSE 8083
ENTRYPOINT java -jar /app/runner.jar