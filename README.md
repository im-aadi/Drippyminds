# Freelancing App for College Students and Companies

This project is a freelancing platform designed to connect college students with companies seeking talent for various projects, assignments, research papers, and more. The app provides a streamlined way for students to find freelance work and for companies to access a pool of skilled students.

## Features
- Project, assignment, and research paper management
- Video and feedback integration
- Booking system for services
- Built with Kotlin (backend) and modern Android (frontend)
- Uses SQLite for data storage

## Getting Started

### Prerequisites
- Java 11 or higher
- Gradle
- Android Studio (for frontend development)

### Backend Setup
1. Navigate to the `backend` directory:
   ```sh
   cd backend
   ```
2. Build and run the backend:
   ```sh
   ./gradlew run
   ```
   This will start the backend server and initialize the SQLite database (`drippy_minds.db`).

### Frontend Setup
1. Open the `app` directory in Android Studio.
2. Build and run the app on an emulator or physical device.

## Database
- The backend uses SQLite and will automatically create the required tables on first run.

## Contributing
Pull requests are welcome. For major changes, please open an issue first to discuss what you would like to change.

## License
This project is licensed under the MIT License. 