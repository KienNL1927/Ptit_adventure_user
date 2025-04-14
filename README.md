# PTIT Adventure

PTIT Adventure is a gamified campus exploration app designed for students of the Posts and Telecommunications Institute of Technology (PTIT). The app encourages students to discover and learn about different locations on campus through interactive quests and challenges.

## Features

### For Students
- **Interactive Campus Map**: Navigate through different buildings and floors of the PTIT campus
- **Quest System**: Complete location-based challenges and tasks
- **NFC Integration**: Scan NFC tags at physical locations to verify quest completion
- **Quiz Challenges**: Answer questions about PTIT to earn points
- **Leaderboard**: Compete with other students and see your ranking
- **Personal Profile**: Track your progress, level, and achievements
- **Task Management**: View completed and pending quests

### For Staff/Administrators
- **Student Management**: View all students and their progress
- **Student Statistics**: Analyze student performance and engagement
- **Profile Management**: Reset student progress or remove student data
- **Leaderboard Monitoring**: View the leaderboard from an admin perspective

## Technical Overview

### Architecture
- Built with Java for Android
- Uses Retrofit for API communication
- Implements Model-View-Controller (MVC) pattern
- Utilizes Android Navigation Component for fragment management
- Features a dual-role system (student/staff) with different UI flows

### Backend Communication
- RESTful API integration through Retrofit
- JWT-based authentication system
- Real-time progress synchronization

### Key Components
- **Data Models**: User, Student, Staff, Quest, Quiz, Question, etc.
- **API Services**: Authentication, quest management, leaderboard, statistics
- **Repositories**: User, Quest, Student data management
- **Adapters**: List adapters for various RecyclerViews (quests, students, questions)
- **Fragments**: Map, Checklist, Leaderboard, Profile, etc.

## Screens & Flows

### Authentication Flow
- Login screen with username/password
- Forgot password functionality

### Student Flow
- Main navigation: Map, Checklist, Leaderboard, Profile
- Location exploration and quest completion
- NFC scanning for physical verification
- Quiz participation
- Achievement tracking

### Staff Flow
- Student statistics dashboard
- Student list with search functionality
- Individual student profile access
- Administration actions (reset progress, delete student)

## NFC Integration

The app features NFC (Near Field Communication) technology to verify student presence at specific locations:

1. Students navigate to a physical location on campus
2. App prompts to scan an NFC tag at the location
3. Upon successful scan, the quest is marked as completed
4. Points are awarded and progress is updated

## Gamification Elements

- **Points System**: Earn points by completing quests and quizzes
- **Levels**: Progress through levels based on accumulated points
- **Leaderboard**: Compete with other students
- **Achievement Tracking**: View completed quests and challenges

## Getting Started

### Prerequisites
- Android Studio 
- Android SDK (minimum API level 21)
- Java Development Kit (JDK) 8 or higher

### Installation & Setup
1. Clone the repository
2. Open the project in Android Studio
3. Configure the backend server URL in `Client.java` (`BASE_URL` constant)
4. Build and run the application on an Android device or emulator

