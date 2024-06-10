# Metamerce API Server

## Description
This is the API server for the Metamerce project, a 3D and interactive e-commerce website!
It is a RESTful API server that is built using the Java Spring Boot 3.0 framework with SQL Server as database.
[![My Skills](https://skillicons.dev/icons?i=spring,java,docker)](https://skillicons.dev)

### Live Demo

The Frontend and API server is hosted on azure using Web Service. Both apps are containerized using Docker.

Frontend Url: [https://metamerce.netlify.app/](https://metamerce.netlify.app/)

Backend Url for Swagger API Documentation: [https://metamerce-be.azurewebsites.net/swagger-ui.html](https://metamerce-be.azurewebsites.net/swagger-ui.html)

## Installation
You can choose to run the project in one of the following way:
### Local
1. Clone the repository
2. Open the project in Intellj IDEA
3. Set up a SQL server database
4. Create a database in SQL server called `metamerce`
5. Set the environment variables
6. Run the project in Intellj IDEA

### Docker
Docker image is hosted on Docker hub, you can pull the image using the following command
```
docker pull lwwongleon/metamerce-be:6.0
```

To run the docker image, use the following command
```
docker run -p 5454:5454 -e db_username=your_username -e db_password=your_password -e db_url=your_url lwwongleon/metamerce-be:6.0
```

## Swagger and OpenAPI Documentation
The API documentation is available at `http://localhost:5454/swagger-ui.html`

## Environment Variables
```env
# Your frontend url
CORS_ALLOWED_ORIGINS=http://localhost:8080

# Database connection
db_username=your_username
db_password=your_password
db_url=your_url
```