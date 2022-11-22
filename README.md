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

|                               | Admin | Customer |    All |
|-------------------------------|:-----:|---------:|-------:|
| /user/createAdmin             |  []   |       [] |    [x] |
| /customer/createCustomer      |  []   |       [] |    [x] |
| /customer/getCustomerOrders   |  []   |      [x] |     [] |
| /book/saveBook                |  [x]  |       [] |     [] |
| /book/addBookToStock          |  [x]  |       [] |     [] |
| /order/createOrder            |  []   |      [x] |     [] |
| /order/list                   |  [x]  |       [] |     [] |
| /customer/getCustomerOrders   |  [x]  |       [] |     [] |
| /statistics/getMonthlyReport  |  [x]  |       [] |     [] |

