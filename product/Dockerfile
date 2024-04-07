# Use a more recent Maven base image
FROM openjdk:21-ea-jdk-bookworm AS build
RUN apt-get update && apt-get install -y maven

# Copy common-utils source code into Docker image
COPY ../common-utils /app/common-utils

# Compile common-utils in Docker image
RUN mvn -f /app/common-utils/pom.xml clean install
RUN ls /root/.m2/repository/com/example/common-utils/0.0.1-SNAPSHOT/common-utils-0.0.1-SNAPSHOT || echo "common-utils package does not exist in Maven directory"
# Copy your service source code into Docker image
COPY ../product /app/product

# Compile your service in Docker image
RUN mvn -f /app/product/pom.xml clean package

FROM openjdk:21-ea-jdk-bookworm
# Copy compiled jar file into Docker image
COPY --from=build /app/product/target/*.jar app.jar

# Set Docker image startup command
ENTRYPOINT ["java","-jar","/app.jar"]