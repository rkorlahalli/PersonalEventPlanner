# Personal Event Planner App

## Overview

This project is an Android Studio application developed using **Java**, **XML**, and the **Room Persistence Library**. The app allows users to manage upcoming events, appointments, and travel plans through a structured interface with local data persistence.

Users can:

- Add new events
- View all saved events
- Update existing events
- Delete events

The application stores event data locally on the device using **Room**, so the data remains available even after the app is closed or the device is restarted.

---

## Features

- Add a new event with:
  - Title
  - Category
  - Location
  - Date and Time
- View all upcoming events in a list
- Automatically sort events by date
- Edit existing event details
- Delete events no longer needed
- Store event data locally using Room Database
- Navigate between screens using the Jetpack Navigation Component
- Bottom Navigation Bar for switching between Event List and Add Event screens
- Input validation for required fields
- Validation to prevent selecting a past date for new events
- Snackbar feedback for save, update, delete, and validation actions

---

## Event Details Stored

Each event includes the following fields:

- Title
- Category
- Location
- Date and Time

### Example Categories

- Work
- Social
- Travel
- Study
- Other

---

## Project Structure

- `MainActivity.java` — hosts the fragments and bottom navigation
- `activity_main.xml` — defines the main user interface layout
- `EventListFragment.java` — displays all saved events in a RecyclerView
- `fragment_event_list.xml` — layout for the event list screen
- `AddEventFragment.java` — handles adding a new event
- `fragment_add_event.xml` — layout for the add event screen
- `EditEventFragment.java` — handles editing and deleting an existing event
- `fragment_edit_event.xml` — layout for the edit event screen
- `EventAdapter.java` — connects event data to the RecyclerView
- `item_event.xml` — layout for a single event item in the list
- `EventEntity.java` — defines the Room database table structure
- `EventDao.java` — contains CRUD database operations
- `EventDatabase.java` — provides the Room database instance
- `EventRepository.java` — manages data operations between Room and ViewModel
- `EventViewModel.java` — connects UI components to the data layer
- `nav_graph.xml` — defines fragment navigation actions
- `bottom_nav_menu.xml` — defines bottom navigation menu items
- `strings.xml` — stores text values used in the interface

---

## How the App Works

The user opens the application and is presented with the **Event List** screen, where all saved events are displayed in date order.

Using the bottom navigation bar, the user can move to the **Add Event** screen and enter:

- event title
- category
- location
- date
- time

When the user presses the save button, the application validates the input and stores the event in the Room database.

When an event is selected from the list, the app opens the **Edit Event** screen, where the user can update the event details or delete the event.

All event data is stored locally on the device, allowing the app to keep data between sessions.

---

## Technologies Used

- Java
- XML
- Room Persistence Library
- RecyclerView
- Fragments
- Jetpack Navigation Component
- ViewModel
- Material Design Components

---

## How to Run the Project

1. Open the project in Android Studio
2. Allow Gradle to sync completely
3. Build the project
4. Run the app on an emulator or Android device

---

## Author

Rohan Korlahalli
