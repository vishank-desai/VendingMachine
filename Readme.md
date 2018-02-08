The VendingMachine Application is Spring Boot Application with Maven dependencies and Rest API is used. 
Features of Vending Machine Application
1. Add Coins/Currency by default. Coins and Currency Notes are taken into Consideration.
2. Add Product with name,price,quantity.
3. Select the Product to check the price
4. Check if the Product is available and make sure enough change is available when higher amount is inserted in vending machine
5. Reduce the quantity of the product and add the currency in the vending machine
6.JUnit Test case is written. 

Assumption :
1. The currency is taken into denomination , Example 1 dollar note is considered 100 Cents. 
2. The application supports coins and currency till Ten Dollars
3. Different Products are added in vending machine

Rest API :
To add a product 
POST : http://localhost:8080/vendingmachine/v1/addProduct {
    "name":"Lays",
    "price":"15"
}

To get price of product 
GET :http://localhost:8080/vendingmachine/v1/getProduct/Lays

To get the change as list of currency 
GET:
http://localhost:8080/vendingmachine/v1/getChange/250
Result :
[
    "TWODOLLAR",
    "QUARTER",
    "QUARTER"
]