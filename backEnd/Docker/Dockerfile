services:
  web:
    image:
    #build:
    #  context: .
    #  dockerfile: Dockerfile
    ports:
      - 8443:8443
    environment:
      - SPRING_DATASOURCE_URL=jdbc:mysql://localhost:3306/healthcarer?serverTimezone=UTC
      - SPRING_DATASOURCE_USERNAME=root
      - SPRING_DATASOURCE_PASSWORD=root
    restart: on-failure
  db:
    image: mysql:8.0.36
    restart: always
    environment:
      - MYSQL_ROOT_PASSWORD=root
      - MYSQL_DATABASE=healthcarer
    volumes:
      - ./mysql:/var/lib/mysql


