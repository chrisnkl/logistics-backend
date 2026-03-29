### Logistics Backend

An advanced rest api for logistics management.

#### Features

- Order creation, tracking and asynchronous processing.
- Dynamic cost calculation based on the delivery.
- Vehicle management and assignment.
- Analytics with AI summarization of real time data.

#### Tech
- Java 25
- Spring Boot 3.5.8
- Spring Web
- Spring Data JPA
- JUnit 5 & Mockito

#### Run Locally
1. Run `mvn clean install -DskipTests` to install dependencies.
2. Configure your database settings in `application-local.properties`.
3. Run `mvn spring-boot:run -D"spring-boot.run.profiles"=local` to start the application.


#### Run Locally Through Module
1. Make sure to set the active profile to `local` in your IDE run configuration.
2. Run the `LogisticsBackendApplication` class to start the application.
3. Make sure you have skipped tests during the build process.
