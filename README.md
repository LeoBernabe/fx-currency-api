# Instructions
## Introduction 
* The Rest API is written in Java Spring Boot Framework, It designs to calculate the currency rate and to get the latest rates.
## Clone the public repository
`git clone https://github.com/LeoBernabe/fx-currency-api.git`
## Run the project
`mvn spring-boot:run`
## Run the unit test
`mvn test`
### The unit test code coverage for the service layer
![image](https://user-images.githubusercontent.com/46875288/152323375-663f6edf-c690-4915-b9c6-094be6a2ca2d.png)
## Access the swagger link
`http://localhost:8080/swagger-ui.html`
## List of endpoints 
* `/get-all-latest-rate`
* `/get-currency-rate-result`
### Sample request for get all latest rate endpoint
* Request URL
`http://localhost:8080/get-all-latest-rate?base=EUR`
* Response Body
```json
{
  "success": "true",
  "base": "EUR",
  "date": "2022-02-03T00:00:00.000+00:00",
  "rates": {
    "AED": 4.144894,
    "AFN": 112.44014,
    "ALL": 121.25402,
    "AMD": 545.9726,
    "ANG": 2.036712,
    "AOA": 592.4631,
    "ARS": 118.66358,
    "AUD": 1.586041,
    "AWG": 2.031202,
    "AZN": 1.89278,
    "BAM": 1.953156,
    "BBD": 2.281777,
    "BDT": 97.1589,
    "BGN": 1.95548,
    ....
  }
}
```

note: `base` param only accepts EUR currency due to the subscription plan
### Sample request for get currency rate result endpoint
* Request URL
`http://localhost:8080/get-currency-rate-result?buyAmount=10&buyCurrency=EUR&sellAmount=0&sellCurrency=USD`
* Response Body
```json
{
  "buyCurrency": "EUR",
  "buyAmount": 10,
  "sellCurrency": "USD",
  "sellAmount": 11.284451,
  "conversionRate": 1.128445
}
```

note: `buyCurrency` param only accepts EUR currency due to the subscription plan
