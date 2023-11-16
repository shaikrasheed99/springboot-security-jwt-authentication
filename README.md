# Spring Security JWT Authentication

## Gradle based spring boot application which provide below APIs of the users using test driven development.

## APIs of the Application

    - Signup
    - Login
    - Get all users
    - Get user by username

## APIs

### Signup - `/auth/signup`

* `NOTE` - this API can be accessed by everyone.

* Request

```
curl --location --request POST 'http://localhost:8080/auth/signup' \
--header 'Content-Type: application/json' \
--data-raw '{
    "username": "test_username",
    "password": "test_password",
    "role": "ROLE_test",
    "firstname": "test_firstname",
    "lastname": "test_lastname"
}'
```

* Response

```
{
    "status": "success",
    "code": "CREATED",
    "message": "user has created successfully",
    "data": {
        "token": "JWT_TOKEN"
    }
}
```

### Login - `/auth/login`

* Request

```
curl --location --request POST 'http://localhost:8080/auth/login' \
--header 'Content-Type: application/json' \
--data-raw '{
    "username": "test_username",
    "password": "test_password"
}'
```

* Response

```
{
    "status": "success",
    "code": "OK",
    "message": "user has logged in successfully",
    "data": {
        "token": "JWT_TOKEN"
    }
}
```

### Get all users - `/users`

* `NOTE` - this API can be accessed by authenticated users who are having admin role.

* Request

```
curl --location --request GET 'http://localhost:8080/users' \
--header 'Authorization: Bearer JWT_TOKEN' \
```

* Response

```
{
    "status": "success",
    "code": "OK",
    "message": "fetched users details successfully!!",
    "data": {
        "users": [
            {
                "username": "test_username",
                "password": "test_password",
                "firstname": "test_firstname",
                "lastname": "test_lastname",
                "role": "ROLE_test"
            }
        ]
    }
}
```

### Get User by username - `/users/{username}`

* `NOTE` - this API can be accessed by authenticated users who are having admin and user roles.

* Request

```
curl --location --request GET 'http://localhost:8080/users/test_username' \
--header 'Authorization: Bearer JWT_TOKEN' \
```

* Response

```
{
    "status": "success",
    "code": "OK",
    "message": "fetched user details successfully!!",
    "data": {
        "user": {
            "username": "test_username",
            "password": "test_password",
            "firstname": "test_firstname",
            "lastname": "test_lastname",
            "role": "ROLE_test"
        }
    }
}
```