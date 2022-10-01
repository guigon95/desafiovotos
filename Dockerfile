FROM openjdk

WORKDIR /app

COPY target/desafiovotos-0.0.1-SNAPSHOT.jar /app/desafiovotos.jar

ENTRYPOINT ["java", "-jar", "desafiovotos.jar"]