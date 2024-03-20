FROM eclipse-temurin:17-jdk-focal
ARG db_url
ENV DB_URL=$db_url
ARG db_user
ENV DB_USER=$db_user
ARG db_password
ENV DB_PASSWORD=$db_password
ARG keystore_password
ENV KEYSTORE_PASSWORD=$keystore_password
WORKDIR /app
ADD . .
RUN chmod +x mvnw
RUN ./mvnw dependency:go-offline
EXPOSE 8081
CMD ["./mvnw", "spring-boot:run"]