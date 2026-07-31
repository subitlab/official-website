FROM gradle:8.8-jdk17 AS build
WORKDIR /workspace
COPY . .
RUN gradle shadowJar --no-daemon

FROM eclipse-temurin:17-jre
RUN useradd --system --uid 10001 --create-home subit-website
WORKDIR /app
COPY --from=build /workspace/build/libs/SubITWebsiteContentService.jar /app/service.jar
USER subit-website
EXPOSE 8091
ENTRYPOINT ["java", "-jar", "/app/service.jar"]
