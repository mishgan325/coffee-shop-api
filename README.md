# Coffee Shop API

Серверная часть Telegram Mini App для заказа кофе. Реализована на **Spring Boot** с использованием **Spring Security** и **Spring Data JPA**.

[Клиентская часть](https://github.com/mishgan325/coffee-shop-api)

## 🧱 Стек технологий

- Java 17+
- Spring Boot
- Spring Security (Basic Auth)
- Spring Data JPA (Hibernate)
- PostgreSQL

## 🚀 Разворачивание

### Клонирование проекта

```bash
git clone https://github.com/mishgan325/coffee-shop-api.git
cd coffee-shop-api
```
### Настройка `application.yml`

```yaml
telegram:
  bot:
    token: <telegram_bot_токен>

server:
  port: <внутренний_порт> 

admin:
  password: <пароль_администратора> 

spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/coffee_db
    username: <пользователь_postgres>
    password: <пароль_postgres>
  jpa:
    hibernate:
      ddl-auto: update
    show-sql: true
   database-platform: org.hibernate.dialect.PostgreSQLDialect

```

### Запуск контейнера

#### Создание образа
```bash
docker build <название_образа> .
```
#### Запуск контейнера
```bash
docker run -d -p <внешний_порт>:<внутренний_порт> <название_образа>
```
## 🔐 Авторизация

- **Пользователи:** авторизация через Telegram `initData` (HMAC-SHA256).
    
- **Админы:** Basic Auth (логин/пароль).
    

## REST API


| Метод | Endpoint               | Описание                            |
| ----- | ---------------------- | ----------------------------------- |
| POST  | `/api/users/login`     | Авторизация через Telegram initData |
| GET   | `/api/coffees`         | Список кофе                         |
| GET   | `/api/addons`          | Список добавок                      |
| POST  | `/api/orders`          | Оформить заказ                      |
| GET   | `/api/users/me/orders` | История заказов                     |
### Admin API (Basic Auth)

| Метод  | Endpoint              | Описание         |
| ------ | --------------------- | ---------------- |
| GET    | `/admin/orders`       | Все заказы       |
| POST   | `/admin/coffees`      | Добавить кофе    |
| GET    | `/admin/coffees`      | Список кофе      |
| PUT    | `/admin/coffees/{id}` | Поменять кофе    |
| DELETE | `/admin/coffees/{id}` | Удалить кофе     |
| POST   | `/admin/addons`       | Добавить добавку |
| GET    | `/admin/addons`       | Список добавок   |
| PUT    | `/admin/addons/{id}`  | Изменить добавку |
| DELETE | `/admin/addons/{id}`  | Удалить добавку  |

## 🛡️ Безопасность

- Telegram initData проверяется с использованием HMAC-подписи.
    
- Admin-панель защищена Basic Auth.
    
- CORS/защиты от CSRF отключены для Mini App.
