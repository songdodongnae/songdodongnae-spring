# Build stage
FROM bellsoft/liberica-openjdk-alpine:17 AS builder
WORKDIR /app
COPY . .
RUN ./gradlew clean build -x test

# Run stage
FROM bellsoft/liberica-openjdk-alpine:17
WORKDIR /app

