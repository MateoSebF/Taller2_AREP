# Web Server

This is a laboratory where you can test various aspects of network connections, providing the foundation for understanding how URLs work, how sockets function to establish a basic server-client connection, and ultimately enabling you to create a web server that runs on port 35000. This server can respond to requests for HTML, CSS, JS, and image files, as well as have a REST API to create objects based on query parameters—all using Java's networking library.

## Getting Started

This project is built in Java using Maven. No additional dependencies are required, except for the JUnit Jupiter dependency to run the project tests.


### Prerequisites

Before running this project, ensure you have the following installed on your system:

* Java Development Kit (JDK) 23
    * Download and install from: [Oracle JDK or OpenJDK](https://www.oracle.com/co/java/technologies/downloads/)
    * Verify installation with:
        ```
        java -version
        ```
* Apache Maven (for dependency management and build automation)
    * Install from: [Maven Downloads](https://maven.apache.org/download.cgi#Installation)
    * Verify installation with:
        ```
        mvn -version
        ```
* Git (to clone the repository)
    * Install from: [Git Downloads](https://git-scm.com/downloads)
    * Verify installation with:
        ```
        git --version
        ```

### Installing

Follow these steps to set up and run the project in your local development environment:

Clone the Repository:

```
git clone https://github.com/MateoSebF/Taller1_AREP.git

```

Navigate to the Project Directory:

```
cd Taller1_AREP
```

Build the Project with Maven:

```
mvn clean install
```

Run the Web Server:

```
mvn clean compile exec:java
```
### Test the API
Try sending a request to the REST API endpoint:

http://localhost:35000/app/getObjectFromQuery?name=John&age=15

It should return a JSON object like:

```
{"name" : "John" , "age" : "15"}
```

## Running the tests

To run the automated tests for this system, you can use Maven, which integrates well with JUnit for running tests. These tests help ensure that the application is working as expected and validate the behavior of the system.

You can run all tests in the system using the following Maven command:

```
mvn test
```

### Break down into end to end tests

The following tests verify the functionality of the server by simulating HTTP requests for different types of files (HTML, CSS, JS, images) and checking the server's responses. These tests ensure that the server correctly handles the requests, serves the expected content, and returns appropriate status codes.

For example, the test **testMakeConnectionHTML()** checks if the server successfully responds to a request for index.html with a 200 OK status. This ensures that the server is running and can handle requests for HTML pages correctly.

### And coding style tests

These tests ensure that the code follows standard Java coding conventions and best practices, including naming conventions, documentation, and formatting. These tests help maintain consistency, readability, and clarity across the project.

For example, the **testMethodNamingConvention()** test verifies that all method names follow the camelCase convention, a widely accepted naming style in Java. This helps maintain a consistent coding style across the projec

## Deployment

To deploy this project to a cloud environment, the application needs to be configured to dynamically listen on the appropriate port, typically provided as an environment variable. Additionally, you must ensure that all static resources (HTML, CSS, JS, and images) are correctly placed in accessible directories or bundled with the deployment package. The project should also be updated to handle logging properly, as cloud platforms do not typically display console output by default. Finally, ensure that the application is packaged correctly, often as a .jar file, and that it is set up to run continuously in the cloud environment.

## Built With

* [Java](https://www.oracle.com/co/java/technologies/downloads/) - The programming language used
* [Maven](https://maven.apache.org/) - Dependency Management
* [JUnit](https://junit.org/junit5/) - Testing Framework for unit tests

## Versioning

We use [Conventional Commits](https://www.conventionalcommits.org/en/v1.0.0/) for versioning.  

## Authors

**Mateo Forero** - *Initial work* - [MateoSebF](https://github.com/MateoSebF)

## Acknowledgments

* Inspiration from various resources and tutorials on building simple HTTP servers.