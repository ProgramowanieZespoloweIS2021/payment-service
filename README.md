![CI/CD](https://github.com/ProgramowanieZespoloweIS2021/payment-service/actions/workflows/ci.yml/badge.svg)

[![codecov](https://codecov.io/gh/ProgramowanieZespoloweIS2021/payment-service/branch/main/graph/badge.svg?token=72ef252b-f568-4b28-b4b1-88db3c91452f)](https://codecov.io/gh/ProgramowanieZespoloweIS2021/payment-service)


### USAGE
* Add new payment 
  
POST http://localhost:8085/payments

Example body: 
```
{
    "userId": 1,
    "price": 200.3,
    "status": "IN_PROGRESS"
}
```

* Update payment
  
POST http://localhost:8085/payments/<payment_id>

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
  
GET http://localhost:8085/payments/<payment_id>

Example response: 
```
{
    "id": 2,
    "userId": 1,
    "price": 200.30,
    "updateTime": "2021-04-30T21:37:21.477438",
    "createTime": "2021-04-30T21:37:21.477438",
    "status": "IN_PROGRESS",
    "_links": {
        "self": {
            "href": "http://localhost:8085/payments/2"
        }
    }
}
```
