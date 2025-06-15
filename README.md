# OTP Authentication Service

## Описание
Это сервис для генерации и валидации одноразовых паролей (OTP), а также управления пользователями и их аутентификацией.


## Требования

- Java 17 или выше
- PostgreSQL
- Локальный SMPP сервер
- Docker и Docker Compose (опционально)

## Установка и запуск

2. Запустите базу данных через Docker Compose:
```bash
docker-compose up -d postgres
```

3. Настройте Telegram бота:
- Перейдите по ссылке: [@lkjhggtyujnbvcBot](https://t.me/@lkjhggtyujnbvcBot)
- Напишите команду `/start` боту, чтобы активировать его
- Получите chatId из ответа бота

## Как пользоваться API

Базовый URL:
http://localhost:8080/api/v1/

---

### OTP Config

Для обновления конфигурации OTP отправьте POST запрос на /otp/config с JSON телом, например:

POST /api/v1/otp/config  
Content-Type: application/json  
Authorization: Bearer {{token}} // должен отправлять админ  

```json
{
    "length": 6,
    "duration": 120
}
```

Для получения текущей конфигурации выполните GET запрос на /otp/config.

GET /api/v1/otp/config  
Content-Type: application/json  
Authorization: Bearer {{token}} // должен отправлять админ  

---

### OTP Token

Для генерации OTP отправьте POST запрос на /otp/token с JSON телом:

POST /api/v1/otp/token  
Content-Type: application/json  
Authorization: Bearer {{token}}  

```json
{
  "operationId": "b08cece7-3347-4d0c-bbd3-4cd5a4f9a2c4"
}
```

Для проверки OTP отправьте POST запрос на /otp/token/validate с JSON телом:

POST /api/v1/otp/token/validate  
Content-Type: application/json  
Authorization: Bearer {{token}}  

```json
{
  "code": "123456",
  "operationId": "b08cece7-3347-4d0c-bbd3-4cd5a4f9a2c4"
}
```
---

### User

Для регистрации нового пользователя отправьте POST запрос на /user/register с JSON телом, например:

POST /api/v1/user/register  
Content-Type: application/json  

```json
{
  "username": "1",
  "role": "ADMIN",
  "email": "1@email.ru",
  "phoneNumber": "1",
  "tgId": "1",
  "passwordHash": "1"
}
```

Для входа в систему отправьте POST запрос на /user/login с JSON телом:

POST /api/v1/user/login  
Content-Type: application/json  

```json
{
  "email": "1@email.ru",
  "password": "1"
}
```

Для получения списка всех пользователей выполните GET запрос на /user.

Для удаления пользователя выполните DELETE запрос на /user/{id}.