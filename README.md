# ReadingIsGood
ReadingIsGood is an online books retail firm which operates only on the Internet. Developed with springboot and used postgres as database.

#### Firstly, you need to create admin with ```/user/createAdmin``` url and create customer with ```/customer/createCustomer``` url. After that you can access endpoints using Basic Token. All requests should have basic token parameter that contains saved email and password. Otherwise, you can't access the system.


There is 5 controller in application.
* Customer Controller
    * Persist New Customer,
    * Query All Orders Of The Customer
* Book Controller
    * Persist New Book,
    * Update Book's Stock
* Order Controller
    * Persist New Order,
    * Query Order By Id,
    * List Orders By Date Interval
* Statics Controller
    * Monthly Sales Statistics
* User Controller
    * Create Admin

System developed with Spring Security. There is two user type as admin and customer.
Admin can save a book, add book to stock, list orders and monitor monthly statistics. But cannot create order.
Customer can create order and get their own orders detail.

|                               | Admin | Customer |  All |
|-------------------------------|:-----:|---------:|-----:|
| /user/createAdmin             |   ✔  |       ✔ |    ✔ |
| /customer/createCustomer      |   ✔  |        ✔|    ✔ |
| /customer/getCustomerOrders   |   ❌  |        ✔|    ❌ |
| /book/saveBook                |   ✔  |        ❌|    ❌ |
| /book/addBookToStock          |   ✔  |        ❌|    ❌ |
| /order/createOrder            |   ❌  |        ✔|    ❌ |
| /order/list                   |   ✔  |        ❌|    ❌ |
| /customer/getCustomerOrders   |   ✔  |        ❌|    ❌ |
| /statistics/getMonthlyReport  |   ✔  |        ❌|    ❌ |

You can see request and response sample in swagger definition (http://localhost:8081/swagger-ui/index.html#/).
Also postman collection is added to repository.

**_NOTE:_**  Postman collection is created with basic token auth parameters. If you create admin and create customer with given credentials, you don't need to change other requests basic token parameters. If you want to create admin and customer different credentials, you have to add the credentials to basic token.

### User
#### 1. createAdmin
Request:
  ````http request
  POST localhost:8081/user/createAdmin
  ````

  ```` json
    {
      "email" : "admin@getir.com",
      "password" : "1"
    }
  ````
Response:
  ```` json
  {
    "data": {
        "email": "admin@getir.com",
        "role": "ROLE_ADMIN"
    },
    "error": null,
    "time": "2022-11-23T07:56:29.908770200Z"
  }
  ````

### Customer
#### 1. createCustomer
Request:
  ````http request
  POST localhost:8081/customer/createCustomer
  ````

  ```` json
    {
      "email" : "customer@getir.com",
      "password" : "1"
    }
  ````
Response:
  ```` json
  {
    "data": {
        "email": "customer@getir.com"
    },
    "error": null,
    "time": "2022-11-23T07:57:43.806085300Z"
  }
  ````
#### 2. getCustomerOrders
Request:
  ````http request
  GET localhost:8081/customer/getCustomerOrders?page=1&pageSize=10
  ````
Auth:
```` json
  {
      "type": "basic",
      "basic": [
          {
              "key": "username",
              "value": "customer@getir.com",
              "type": "string"
          },
          {
              "key": "password",
              "value": "1",
              "type": "string"
          }
      ]
  }
  ````
Response:
  ```` json
  {
    "data": {
        "orderResponses": [
            {
                "orderNumber": 41225261,
                "totalAmount": 550.0,
                "orderLines": [
                    {
                        "code": "KTP-1",
                        "quantity": 5,
                        "amount": 150.0
                    },
                    {
                        "code": "KTP-2",
                        "quantity": 10,
                        "amount": 400.0
                    }
                ]
            }
        ],
        "page": 1,
        "pageSize": 10
    },
    "error": null,
    "time": "2022-11-23T08:21:02.057111500Z"
  }
  ````

### Book
#### 1. saveBook
Request:
  ````http request
  POST localhost:8081/book/saveBook
  ````
Auth:
```` json
  {
      "type": "basic",
      "basic": [
          {
              "key": "username",
              "value": "admin@getir.com",
              "type": "string"
          },
          {
              "key": "password",
              "value": "1",
              "type": "string"
          }
      ]
  }
  ````
  ```` json
    {
        "code": "KTP-1",
        "price": 30.0
    }
  ````
Response:
  ```` json
  {
    "data": {
        "code": "KTP-1",
        "price": 30.0
    },
    "error": null,
    "time": "2022-11-23T08:06:29.790865200Z"
  }
  ````
#### 2. addBookToStock
Request:
  ````http request
  POST localhost:8081/book/addBookToStock
  ````
Auth:
```` json
  {
      "type": "basic",
      "basic": [
          {
              "key": "username",
              "value": "admin@getir.com",
              "type": "string"
          },
          {
              "key": "password",
              "value": "1",
              "type": "string"
          }
      ]
  }
  ````
```` json
  {
    "code": "KTP-1",
    "quantity": 20
  }
  ````
Response:
  ```` json
  {
    "data": {
        "code": "KTP-1",
        "quantity": 20
    },
    "error": null,
    "time": "2022-11-23T08:08:24.927049700Z"
}
  ````

### Order
#### 1. createOrder
Request:
  ````http request
  POST localhost:8081/order/createOrder
  ````
Auth:
```` json
  {
      "type": "basic",
      "basic": [
          {
              "key": "username",
              "value": "customer@getir.com",
              "type": "string"
          },
          {
              "key": "password",
              "value": "1",
              "type": "string"
          }
      ]
  }
  ````
  ```` json
    {
        "orderLines": [
            {
                "code" : "KTP-1",
                "quantity" : 5
            },
            {
                "code" : "KTP-2",
                "quantity" : 10
            }
        ]
    }
  ````
Response:
  ```` json
  {
    "data": {
        "orderNumber": 41225261,
        "totalAmount": 550.0,
        "orderLines": [
            {
                "code": "KTP-1",
                "quantity": 5,
                "amount": 150.0
            },
            {
                "code": "KTP-2",
                "quantity": 10,
                "amount": 400.0
            }
        ]
    },
    "error": null,
    "time": "2022-11-23T08:12:39.920381500Z"
}
  ````
#### 2. getByOrderNumber
Request:
  ````http request
  GET localhost:8081/order/getByOrderNumber?orderNumber=41225261
  ````
Auth:
```` json
  {
      "type": "basic",
      "basic": [
          {
              "key": "username",
              "value": "admin@getir.com",
              "type": "string"
          },
          {
              "key": "password",
              "value": "1",
              "type": "string"
          }
      ]
  }
  ````
Response:
  ```` json
  {
    "data": {
        "orderNumber": 41225261,
        "totalAmount": 550.0,
        "orderLines": [
            {
                "code": "KTP-1",
                "quantity": 5,
                "amount": 150.0
            },
            {
                "code": "KTP-2",
                "quantity": 10,
                "amount": 400.0
            }
        ]
    },
    "error": null,
    "time": "2022-11-23T08:13:31.077609400Z"
  }
  ````
#### 3. listOrder
Request:
  ````http request
  POST localhost:8081/order/list
  ````
Auth:
```` json
  {
      "type": "basic",
      "basic": [
          {
              "key": "username",
              "value": "admin@getir.com",
              "type": "string"
          },
          {
              "key": "password",
              "value": "1",
              "type": "string"
          }
      ]
  }
  ````
  ```` json
    {
      "startDate" : "2022-11-01",
      "endDate" : "2022-11-30"
    }
  ````
Response:
  ```` json
  {
    "data": [
        {
            "orderNumber": 41225261,
            "totalAmount": 550.0,
            "orderLines": [
                {
                    "code": "KTP-1",
                    "quantity": 5,
                    "amount": 150.0
                },
                {
                    "code": "KTP-2",
                    "quantity": 10,
                    "amount": 400.0
                }
            ]
        }
    ],
    "error": null,
    "time": "2022-11-23T08:16:22.860924400Z"
  }
  ````
### Customer
#### 1. getMonthlyReport
Request:
  ````http request
  GET localhost:8081/statistics/getMonthlyReport
  ````
Auth:
```` json
  {
      "type": "basic",
      "basic": [
          {
              "key": "username",
              "value": "admin@getir.com",
              "type": "string"
          },
          {
              "key": "password",
              "value": "1",
              "type": "string"
          }
      ]
  }
  ````
Response:
  ```` json
  {
    "data": [
        {
            "monthName": "November",
            "orderCount": 2,
            "totalAmount": 550.0,
            "totalQuantity": 15
        }
    ],
    "error": null,
    "time": "2022-11-23T08:19:24.251648800Z"
  }
  ````

## Unit And Integration Tests
You could check test directories. Test Coverage is below. Also integration tests were written.

![alt text](https://github.com/onurozerdal/reading-is-good/blob/master/testCoverage.JPG)


## Technologies
* Java 17
* SpringBoot 2.7.5
* Spring Data JPA
* Spring Security
* Postgres
* Maven
* Mockito
* JUnit
* Swagger

## Run Commands
You can run ```docker-compose up``` in root directory after clone repository from github.
