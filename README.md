# 🎟️ Ticket Booking System (Java + Gradle)

A **console-based Train Ticket Booking System** built with **Java** and **Gradle**.  
The project simulates a real-world ticket reservation system where users can register, log in, search for trains, and book or cancel tickets.  
All data is stored in **JSON files**, making the system lightweight and database-independent.

---

## 📌 Features

- 👤 **User Management**  
  - Register new users with **BCrypt-hashed passwords**  
  - Log in securely and manage user profiles  

- 🚆 **Train Search**  
  - Search trains by **source**, **destination**, and **travel date**  

- 🎟 **Ticket Booking**  
  - Book tickets instantly with a **unique ticket ID**  

- ❌ **Ticket Cancellation**  
  - Cancel booked tickets with validation checks  

- 💾 **Persistent Storage**  
  - Data stored in **JSON files** (Users, Trains, Tickets)  

---

## 🛠 Tech Stack

- **Language:** Java  
- **Build Tool:** Gradle  
- **Libraries:**  
  - [Jackson](https://github.com/FasterXML/jackson) → for JSON handling  
  - [BCrypt](https://github.com/jeremyh/jBCrypt) → for secure password hashing  
- **Storage:** JSON file-based persistence  

---

## 🚀 Getting Started

### ✅ Prerequisites
- Java **17+**  
- Gradle installed (or use the provided Gradle Wrapper)  
- Any Java IDE (IntelliJ IDEA recommended)

### ⚡ Installation
1. Clone the repository:
   ```bash
   git clone https://github.com/myselfavaneesh/Ticket-Booking-Java-Project.git
   ```
2. Navigate into the project directory:
   ```bash
   cd Ticket-Booking-Java-Project
   ```
3. Build the project with Gradle:
   ```bash
   ./gradlew build
   ```
4. Run the project:
   ```bash
   ./gradlew run
   ```

---

## 🎮 Usage

- Run the application in terminal/console  
- Menu-driven options will appear (Register, Login, Search Train, Book Ticket, Cancel Ticket)  
- Example flow:  
  1. Register a new user  
  2. Login with credentials  
  3. Search train from **Delhi → Mumbai**  
  4. Book ticket for travel date  
  5. Cancel ticket if required  

---

## 📂 Project Structure

```
Ticket-Booking-Java-Project
 ┣ 📂 src/main/java/org/example
 ┃ ┣ 📂 entities      # User, Train, Ticket classes
 ┃ ┣ 📂 service       # Booking, Train services
 ┃ ┣ 📂 util          # Utility classes (e.g., BCrypt hashing)
 ┃ ┗ Main.java        # Entry point
 ┣ 📂 data            # JSON storage files
 ┣ build.gradle
 ┣ settings.gradle
 ┗ README.md
```

---

## 🌟 Future Enhancements
- Web-based UI with **Spring Boot**  
- Database integration (**MySQL/PostgreSQL**)  
- Seat selection system  
- Admin dashboard for train/ticket management  

---

## 👨‍💻 Author
**Avaneesh Jaiswal (AJ)**  
🔗 [GitHub Profile](https://github.com/myselfavaneesh)
