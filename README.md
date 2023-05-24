# Filmorate
Сервис, предоставляющий информацию о кинофильмах и телесериалах.
Посетители могут регистрироваться в сервисе, ставить оценки фильмам и сериалам,
добавлять в друзья других пользователей сервиса.

## База данных

- [Схема БД](#схема-бд)
- [Примеры запросов из БД](#примеры-запросов)

### Схема БД

![](https://github.com/pymanceva/java-filmorate/blob/main/src/main/resources/schema.png?raw=true)

### Примеры запросов

<details>
    <summary><h3>Работа с фильмами:</h3></summary>

* Запрос фильма по id:

```SQL
SELECT f.film_id,
       f.name,
       f.description,
       f.release_date,
       f.duration,
       g.name  AS genre,
       mp.name AS mpa_rating
FROM films f
         JOIN mpa_rating mp ON f.mpa_rating_id = mp.mpa_rating_id
         JOIN film_genres fg ON f.film_id = fg.film_id
         JOIN genres g ON fg.genre_id = g.genre_id
WHERE f.film_id = ?;
```   

* Запрос всех фильмов:

```SQL
SELECT f.film_id,
       f.name,
       f.description,
       f.release_date,
       f.duration,
       mp.name              AS mpa_rating,
       GROUP_CONCAT(g.name) AS genres
FROM films f
         JOIN mpa_rating mp ON f.mpa_rating_id = mp.mpa_rating_id
         JOIN film_genres fg ON f.film_id = fg.film_id
         JOIN genres g ON fg.genre_id = g.genre_id
GROUP BY f.film_id;
```

* Запрос топ-N фильмов по количеству лайков:
```SQL
SELECT f.film_id,
       f.name,
       f.description,
       f.release_date,
       f.duration,
       mp.name           AS mpa_rating,
       g.name            AS genre,
       COUNT(fl.user_id) AS like_count
FROM films f
         JOIN mpa_rating mp ON f.mpa_rating_id = mp.mpa_rating_id
         JOIN film_genres fg ON f.film_id = fg.film_id
         JOIN genres g ON fg.genre_id = g.genre_id
         LEFT JOIN film_likes fl ON f.film_id = fl.film_id
GROUP BY f.film_id,
         mp.name,
         g.name
ORDER BY like_count DESC 
LIMIT N;
```
</details>

<details>
    <summary><h3>Работа с пользователями:</h3></summary>

* Запрос пользователя по id:

```SQL
SELECT *
FROM users
WHERE user_id = ?
```   

* Запрос всех пользователей:

```SQL
SELECT *
FROM users
``` 

</details>

<details>
    <summary><h3>Работа с жанрами:</h3></summary>

* Запрос жанра по id:

```SQL
SELECT *
FROM genres
WHERE genre_id = ?
``` 

* Запрос всех жанров:

```SQL
SELECT *
FROM genres
```   
</details>

<details>
    <summary><h3>Работа с рейтингами MPA:</h3></summary>

* Запрос рейтинга MPA по id:

```SQL
SELECT *
FROM mpa_rating
WHERE mpa_rating_id = ?
``` 

* Запрос всех рейтингов MPA:

```SQL
SELECT *
FROM mpa_rating
```   
</details>


## Инструкция по установке

- [Требования](#требования)
- [Установка](#установка)
- [Запуск](#запуск)

### Требования

- Apache Maven 3.6.0 и позднее
- JDK 11 и позднее

### Установка

1. Клонировать репозиторий:
```bash
git clone https://github.com/pymanceva/java-filmorate.git
```

2. Перейти в корневую директорию проекта:
```bash
cd java-filmorate
```

3. Собрать проект, используя Maven:
```bash
mvn clean install
```

### Запуск

После установки запустить приложение:
```bash
mvn spring-boot:run
```

