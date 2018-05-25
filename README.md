# Test [![Build Status](https://travis-ci.org/ilyamoskalev/Test.svg?branch=dev)](https://travis-ci.org/ilyamoskalev/Test)

## Документация к API
Документацию можно читать как собственно в файле swagger.yml, так и через Swagger UI (там же есть возможность поиграться с запросами): [Ссылка](http://petstore.swagger.io/?url=https://raw.githubusercontent.com/ilyamoskalev/Test/master/swagger.yml)

## Сборка Docker контейнера
Контейнер будет собираться и запускаться командами вида:
```
docker build -t [USERNAME] https://github.com/ilyamoskalev/Test.git
docker run -p 8081:8081 --name [CONTAINER_NAME] -t [USERNAME]
```

## Сборка без использования Docker
Контейнер будет собираться и запускаться командами вида:
```
git clone https://github.com/ilyamoskalev/Test.git
cd Test
mvn package
export JDBC_DATABASE_URL=jdbc:postgresql://[your database url]
export DATABASE_USERNAME=[database username]
export DATABASE_PASSWORD=[database password]
java -jar target/Test-1.0-SNAPSHOT.jar
```
