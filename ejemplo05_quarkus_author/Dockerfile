#GENERAR LA IMAGEN
#   docker build -t app-authors .
#   docker tag app-authors jaimesalvador/app-authors
#
#   docker build -t maxximion/app-authors .
#
#   docker login
#   docker push jaimesalvador/app-authors .

FROM eclipse-temurin:17.0.11_9-jre-jammy

RUN mkdir /app
WORKDIR /app

COPY build/quarkus-app/app ./app
COPY build/quarkus-app/lib/ ./lib
COPY build/quarkus-app/quarkus/ ./quarkus
COPY build/quarkus-app/quarkus-run.jar app.jar

CMD ["java","-jar","app.jar"]