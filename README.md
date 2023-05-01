# GitHub Data Collector

The GitHub Data Collector is a Java-based application that collects information on GitHub users and their repositories. The application performs scraping and regex operations to gather relevant repository URLs and raw data, then clones the repositories to collect additional data on specific languages, such as Java, Python, Node.js, Angular, React, and .NET. It also reads the repository files to obtain data on the number of lines and matches for specified keywords.

## Running the Application

To run the application, set up the project with an IDE and run it. Access the application via http://localhost:8080/swagger-ui.html. Enter one or more usernames separated by commas and without spaces, as well as any relevant keywords separated by commas.

## Technologies Used

The GitHub Data Collector uses the following technologies:
- Java
- Regex
- Web Scraping
- Docker

## Data Collected

The GitHub Data Collector collects the following data for each user:
- User's Name
- User's URL
- Number of Public Repositories
- Number of Followers
- Number of People the User is Following
- Number of Java Repositories
- Number of Python Repositories
- Number of Node.js Repositories
- Number of Angular Repositories
- Number of React Repositories
- Number of .NET Repositories
- Number of Forks
- Number of Commits
- Number of Stars

## Docker Image

To run the application using Docker, build the Dockerfile and run the image.

