# Teacher App – Manage Students with Ease

The **Teacher App** is an Android application designed to assist teachers of IOE Purwanchal Campus in managing student-related activities efficiently. It provides features such as attendance tracking, internal mark assignments, subject organization, and communication via notices and notes.

## Features

### 1. Attendance Management
- View and manage attendance for students across various faculties (e.g., Computer, Civil).
- Organized structure based on year, semester, and section.
- Export attendance data in Excel format for offline access and record-keeping.

### 2. Subject and Student Management
- Add subjects by selecting the semester, subject, and section.
- Automatically retrieves student data from the backend based on selected section.

### 3. Notices and Notes
- Send academic notes and important notices to students.
- Enables effective communication between teachers and students.

### 4. Internal Marks Assignment
- Assign internal marks to students based on section and subject.
- Export internal marks to Excel files for documentation purposes.

---

## Tech Stack & Architecture

- **Frontend:** Kotlin with Jetpack Compose
- **Architecture:** MVVM (Model-View-ViewModel)
- **State Management:** LiveData, StateFlow
- **Data Storage:**
  - Cache memory to store usernames
  - CookieJar to manage session cookies
- **File Handling:** Supports Excel file generation and storage

## Backend Details

The backend for this project is built using:
- **Backend Framework:** Node.js with Express.js
- **Database:** PostgreSQL
- **Architecture:** Monolithic

Repository: [Campus Connect – Backend](https://github.com/Sangyog10/Campus-connect)

---

## How to Run the Project

### Prerequisites
- Android Studio (latest stable version)
- Internet connection
- Device running Android 13 (API 33) or higher (for download permission handling)

### Running the App
1. Clone this repository:
   ```bash
   git clone https://github.com/your-username/Teacher_App.git
   ```
2. Open the project in Android Studio.
3. Sync Gradle and build the project.
4. Run the app on a physical device or emulator.
5. Ensure the backend server is running locally or deployed before testing full functionality.

---

## Related Project

**Student App:** The companion app allows students to:
- View attendance records
- Access shared notes and notices
- View internal marks
- Get details about teachers

Repository: [Student App](https://github.com/Sanskar-Rijal/Student_App)

---

## App screenShots
<div align="start">
  <a href="https://imgur.com/mDSFOS9">
    <img src="https://i.imgur.com/mDSFOS9.gif" title="source: imgur.com" height="400" width="200" style="margin-right: 20px;" />
  </a>
  <a href="https://imgur.com/4gp9hX0">
    <img src="https://i.imgur.com/4gp9hX0.gif" title="source: imgur.com" height="400" width="200" style="margin-right: 20px;" />
  </a>
  <a href="https://imgur.com/oJYkmF1">
    <img src="https://i.imgur.com/oJYkmF1.gif" title="source: imgur.com" height="400" width="200" style="margin-right: 20px;" />
  </a>
  <a href="https://imgur.com/6CsiAmL">
    <img src="https://i.imgur.com/6CsiAmL.gif" title="source: imgur.com" height="400" width="200" style="margin-right: 20px;" />
  </a>
  <a href="https://imgur.com/no0yLqV">
    <img src="https://i.imgur.com/no0yLqV.gif" title="source: imgur.com" height="400" width="200" />
  </a>
  <a href="https://imgur.com/pt7I0Dk">
    <img src="https://i.imgur.com/pt7I0Dk.gif" title="source: imgur.com" height="400" width="200" />
  </a>
    <a href="https://imgur.com/x5y75Bd">
    <img src="https://i.imgur.com/x5y75Bd.gif" title="source: imgur.com" height="400" width="200" />
  </a>
    <a href="https://imgur.com/3gu7Qzx">
    <img src="https://i.imgur.com/3gu7Qzx.gif" title="source: imgur.com" height="400" width="200" />
  </a>
    <a href="https://imgur.com/VOHyaeQ">
    <img src="https://i.imgur.com/VOHyaeQ.gif" title="source: imgur.com" height="400" width="200" />
  </a>
   <a href="https://imgur.com/REORd5m">
    <img src="https://i.imgur.com/REORd5m.gif" title="source: imgur.com" height="400" width="200" />
  </a>
  <a href="https://imgur.com/ulFTo70">
    <img src="https://i.imgur.com/ulFTo70.gif" title="source: imgur.com" height="400" width="200" />
  </a>
  
  
</div>

---
