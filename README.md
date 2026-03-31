# ☕ Starbucks Project

A **Java Maven-based Starbucks ordering system simulation** that models core Starbucks operations such as menu browsing, customer orders, rewards, gift cards, and store management.

This project demonstrates **Object-Oriented Programming (OOP), MVC principles, controller-based architecture, and CSV data handling** in Java.

---

## 📌 Features

- 📋 Browse Starbucks menu items from CSV data
- 👤 Customer account management
- 🛒 Create and manage orders
- 🎁 Gift card support
- ⭐ Rewards account system
- 🏬 Store location management
- ⚙️ Controller-based business logic
- 📦 Maven project structure
- 📁 CSV-based menu dataset integration

---

## 🏗️ Project Architecture

This project follows an **MVC-inspired structure**:

### **Models**
- `Customer`
- `Customization`
- `GiftCard`
- `MenuItem`
- `Order`
- `OrderItem`
- `RewardsAccount`
- `Store`
- `User`

### **Controllers**
- `MenuController`
- `OrderController`
- `RewardsController`
- `StoreController`
- `UserController`

This separation improves:

- scalability
- maintainability
- readability
- easier testing

---

## 📂 Project Structure

```text
Starbucks-Project/
│
├── data/
│   └── starbucks_menu.csv
│
├── src/main/java/com/example/starbucksproject/
│   ├── Customer.java
│   ├── Customization.java
│   ├── GiftCard.java
│   ├── MenuController.java
│   ├── MenuItem.java
│   ├── Order.java
│   ├── OrderController.java
│   ├── OrderItem.java
│   ├── RewardsAccount.java
│   ├── RewardsController.java
│   ├── Store.java
│   ├── StoreController.java
│   ├── User.java
│   └── UserController.java
│
├── pom.xml
└── README.md
```

---

## 🚀 Technologies Used

- **Java**
- **Maven**
- **IntelliJ IDEA**
- **CSV Data Files**
- **MVC Design Pattern**
- **Object-Oriented Programming**

---

## ▶️ How to Run

### 1) Clone the repository
```bash
git clone https://github.com/Isaac76896/Starbucks-Project.git
```

### 2) Open in IntelliJ
Open the folder as a **Maven project**.

### 3) Build the project
```bash
mvn clean install
```

### 4) Run the application
Run the main Java class from IntelliJ.

---

## 📊 Learning Goals

This project was designed to practice:

- Java class design
- controller-based architecture
- file input with CSV
- modular project organization
- real-world business object modeling
- clean Maven project setup


## 👨‍💻 Authors

**Isaac Lopez Salazar**
- GitHub: https://github.com/Isaac76896

**Rositza Dineva**
- GitHub: https://github.com/rxinn21

**Diego Rodriguez**
- GitHub: https://github.com/Diegox4521

**Mark Guzman**
- GitHub: https://github.com/markg1013




---

## ⭐ Repository Goal

This project is part of our software development portfolio to demonstrate:

- Java development
- MVC design
- OOP principles
- scalable project organization