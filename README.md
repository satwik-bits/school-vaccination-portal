# school-vaccination-portal
 Building a full-stack web application to manage and track vaccination drives in a school.

To run the application follow these steps: 

1) The application will run on port 8080. We need to use the context path as /vaccine-drive before accessing the application. 

2) Swagger support provided. 

3) Install Java 17+ as well as Maven using Homebrew if using MacOS. Do it similarly for other Operating Systems.

4) Install MySQL Workbench for SQL support. 

5) Do a mvn clean install before running the application, which will install the libraries required to run the application. 

6) Execute the application.

7) Hit the URL: 
	http://localhost:8080/vaccine-drive/swagger-ui/index.html#/ : To access Swagger-UI
	http://localhost:8080/vaccine-drive/actuator/health : For Health Check
