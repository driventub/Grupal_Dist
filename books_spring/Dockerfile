#GENERAR LA IMAGEN
#   docker build -t app-authors .
#   docker tag app-authors jaimesalvador/app-books
#
#   docker build -t app-books-spring .
#
#   docker login
#   docker push jaimesalvador/app-books .


FROM eclipse-temurin:17.0.11_9-jre-jammy
RUN mkdir /app
WORKDIR /app
COPY build/libs/*.jar app.jar
CMD ["java","-jar","app.jar"]
