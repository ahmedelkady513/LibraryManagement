
# Library Management System
Library managment system assesment allow crud operation for books , patrons and keep tracking of borrowing and returning of books.



## Features

- JWT-based authorization to protect the API endpoints
- Controllers MVC Tests using JUNIT and Mockito
- CRUD & Validation For Both patrons and books
- Track And Validate Borrowing books
- Track And Validate Returning books
- Exception Handling & Error Logging
- Service Methods Calls Logging & Timing




## Run Using Docker

Clone the project

```bash
  git clone https://github.com/ahmedelkady513/LibraryManagement
```

Go to the project directory


Run Docker compose

```bash
  Docker compose up
```



## Run Using IDE

To run the project using IDE 

Before runing the application postgres needed with the default config can be changed from application.yaml file

Default Config

    -Url : localhost
    -Schema Name : library
    -Username : postgres
    -Password : 12345678
    
Application port : 8080
## Usage

After running the project commandLineRunner will create a demo account is created with username admin and password admin.

You will find post man collection included with the project named "Library Management System.postman_collection" contains all end points with their request data if needed.

Start by logging in with default credentials a token will be returned Dont forget to send it with the request go to authorization tab and select Bearer token and past the token.

Example Login response

```JSON
{
    "username": "admin",
    "token": "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiIsImV4cCI6MTcyMzEyMjEwMiwiaWF0IjoxNzIzMTE4NTAyfQ.YoRLbva4CUZnYVVuDAaQPJt01CbYgRF-fKlN8MpNb4OI-Dp-O7BuB12VUah6BTFInCsb1Yi3XwJTIKOku_wn2g"
}
```



