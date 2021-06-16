![CI/CD](https://github.com/ProgramowanieZespoloweIS2021/payment-service/actions/workflows/ci.yml/badge.svg)

[![codecov](https://codecov.io/gh/ProgramowanieZespoloweIS2021/payment-service/branch/develop/graph/badge.svg?token=AQT3LMTG5F)](https://codecov.io/gh/ProgramowanieZespoloweIS2021/payment-service)


### USAGE
* Add new payment 
  
POST http://localhost:8080/payments

Example body: 
```
{
    "userId": 1,
    "price": 123.0,
    "status": "IN_PROGRESS",
    "offerTitles": [
        "example title 1",
        "example title 2"
    ]
}
```

* Update payment
  
POST http://localhost:8080/payments/<payment_id>

Possible statuses:
- IN_PROGRESS,
- FINISHED,
- CANCELLED

Example body: 
```
{
    "status": "CANCELLED"
}
```


* Get payment
  
GET http://localhost:8080/payments/<payment_id>

Example response: 
```
{
    "id": 4,
    "userId": 1,
    "price": 2000.3,
    "updateTime": "2021-05-06T19:19:57.9145519",
    "createTime": "2021-05-06T19:19:57.9145519",
    "status": "IN_PROGRESS",
    "offerTitles": [
      "Example offer 1",
      "Example offer 2"
    ],
    "_links": {
        "self": {
            "href": "http://localhost:8080/payments/4"
        },
        "pay": {
            "href": "http://localhost:8080/payments/4/pay"
        }
    }
}
```

* Get payments for user
  
GET http://localhost:8080/payments?user_id=<user_id>

Example response: 
```
[
    {
        "id": 1,
        "userId": 1,
        "price": 123.00,
        "updateTime": "2021-06-15T21:18:05.26846",
        "createTime": "2021-06-15T21:18:05.26796",
        "status": "IN_PROGRESS",
        "offerTitles": [
            "example title 1",
            "example title 2"
        ],
        "links": [
            {
                "rel": "self",
                "href": "http://localhost:8080/payments/1"
            },
            {
                "rel": "pay",
                "href": "http://localhost:8080/payments/1/pay"
            }
        ]
    }
]
```

* Finalize payment 
  
POST http://localhost:8080/payments/<payment_id>/pay

Example body: 
```
{
    "email": "abc@gmail.com",
    "name": "Jan",
    "surname": "Kowalski",
    "cartNumber": "1234567890123456",
    "expirationDate": "21/05",
    "codeCvv": "111"
}
```

Example response:
* payment has been successful 
```
{
    "message": "Balance was reduced by <price>"
}
```

* not enough funds on existing account:
```
{
    "message": "Insufficient funds"
}
```

* account data is invalid
```
{
    "message": "account data is invalid"
}
```

* If payment status is FINISHED or CANCELED you cannot pay multiple times
```
{
    "message": "Payment has been finished or canceled"
}
```

