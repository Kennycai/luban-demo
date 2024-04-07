# 使用Maven基础镜像
FROM maven:3.6.3-openjdk-8-slim AS build

WORKDIR /app

# 将您的服务源代码复制到Docker镜像中
COPY . /app/eureka

# 在Docker镜像中编译您的服务
RUN cd /app/eureka && mvn clean install

# 使用Java基础镜像
FROM openjdk:21-ea-jdk-bookworm

# 将编译好的jar文件复制到Docker镜像中
COPY --from=build /app/eureka/target/*.jar app.jar

# 设置Docker镜像的启动命令
ENTRYPOINT ["java","-jar","/app.jar"]

