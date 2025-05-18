# Aether Alarm

A modern, minimalist alarm app for Android with a clean design and intuitive interface.

## Features

- Create and manage multiple alarms
- Enable/disable alarms with a single tap
- Custom alarm names for better organization
- Precise scheduling using Android's exact alarm API
- Clean, modern UI with edge-to-edge design
- Splash screen for a polished app launch experience

## Technical Details

- Minimum SDK: Android 14 (API 34)
- Target SDK: Android 14 (API 34)
- Architecture: MVVM with Clean Architecture principles
- Language: Kotlin
- Database: Room for local storage
- Coroutines & Flow for asynchronous operations
- ViewBinding for type-safe view access
- Dependency Injection pattern (custom implementation)
- Edge-to-edge UI design

## Project Structure

```
com.jmoreira7.aetheralarm/
├── data/           # Data layer (repositories, database)
├── di/             # Dependency injection
├── domain/         # Domain layer (entities, use cases)
├── receiver/       # Broadcast receivers
└── ui/             # UI layer
    ├── alarmsettings/  # Alarm creation/editing
    ├── alarmtrigger/   # Alarm trigger screen
    ├── main/           # Main alarm list screen
    └── vo/             # UI value objects
```

## Setup & Installation

1. Clone the repository
2. Open the project in Android Studio
3. Build and run on a device or emulator running Android 14+

## Permissions

The app requires the following permissions:
- `SCHEDULE_EXACT_ALARM` - For precise alarm scheduling

## Author

Jonas Moreira