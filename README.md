# Meeting Calendar Assistant

The Meeting Calendar Assistant is a Java Spring Boot application that assists users in booking meetings, finding free slots for meetings, and identifying meeting conflicts.

## Features

- **Book Meeting:** Allows users to book meetings with the calendar owner.
- **Find Free Slots:** Given the calendars of two employees, finds free slots for a meeting of a fixed duration.
- **Find Conflicts:** Given a meeting request, identifies all participants with meeting conflicts.

## Technologies Used

- Java
- Spring Boot
- MySQL
- JUnit
- Mockito
- Postman (for testing)
- RESTful APIs

## Setup Instructions

1. Clone the repository.
2. Configure the database connection in `application.properties`.
3. Run the application using `mvn spring-boot:run`.

## Endpoints

- **POST /meetings/employees:** Create a new employee.
- **GET /meetings/conflicts:** Find conflicting meetings for a given meeting.
- **GET /meetings/freeslots:** Find free slots for a meeting between two employees.
- **POST /meetings/book:** Book a meeting.

## Usage

1. Create employees using the `/meetings/employees` endpoint.
2. Use the `/meetings/book` endpoint to book a meeting.
3. Use the `/meetings/conflicts` endpoint to find conflicting meetings.
4. Use the `/meetings/freeslots` endpoint to find free slots for a meeting.

## Testing

The application can be tested using Postman. Import the provided Postman collection and run the requests to test the endpoints.

## Contributors

- [NehaV](https://github.com/NV2480)


