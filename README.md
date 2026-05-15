# ☕ Starbucks Project

A JavaFX Starbucks ordering system simulation that recreates core features of the Starbucks customer experience, including menu browsing, order creation, rewards tracking, gift cards, and store information.

This project was built using Java, JavaFX, FXML, Maven, and Object-Oriented Programming principles. It uses a controller-based structure with multiple scenes to simulate a desktop application experience.

---

## ✨ Features

- Browse a Starbucks-style menu
- View menu items and item details
- Create and manage customer orders
- Track rewards account information
- View gift card information
- Navigate between multiple JavaFX scenes
- Manage customer and account state across screens
- Display store-related information
- Use CSV-based data for menu integration
- Apply MVC-inspired organization with models, controllers, and FXML views

---

## 🖥️ Application Screens

The application includes multiple JavaFX/FXML screens:

- **Menu Screen** — main menu browsing page
- **Order View** — order summary and item management
- **Rewards Screen** — customer rewards account view
- **Gift Cards Screen** — gift card information page
- **Store Screen** — store/location-related information

---

## 🧱 Project Architecture

This project follows an MVC-inspired design.

### Models

These classes represent the main business objects used throughout the app:

- `Customer`
- `Customization`
- `GiftCard`
- `MenuItem`
- `Order`
- `OrderItem`
- `Reward`
- `RewardsAccount`
- `Store`
- `User`

### Controllers

These classes handle user interaction, scene logic, and application behavior:

- `BaseController`
- `GiftCardController`
- `HelloController`
- `MenuController`
- `OrderController`
- `RewardsController`
- `StoreController`
- `UserController`

### Application Support Classes

- `HelloApplication` — main JavaFX application entry point
- `Launcher` — launches the application
- `SceneNavigator` — handles scene sizing and navigation
- `AppState` — stores shared customer/account state across scenes

---

## 📁 Project Structure

```text
Starbucks-Project/
│
├── data/
│   └── starbucks_menu.csv
│
├── src/main/java/com/example/starbucksproject/
│   ├── AppState.java
│   ├── BaseController.java
│   ├── Customer.java
│   ├── Customization.java
│   ├── GiftCard.java
│   ├── GiftCardController.java
│   ├── HelloApplication.java
│   ├── HelloController.java
│   ├── Launcher.java
│   ├── MenuController.java
│   ├── MenuItem.java
│   ├── Order.java
│   ├── OrderController.java
│   ├── OrderItem.java
│   ├── Reward.java
│   ├── RewardsAccount.java
│   ├── RewardsController.java
│   ├── SceneNavigator.java
│   ├── Store.java
│   ├── StoreController.java
│   ├── User.java
│   ├── UserController.java
│   └── module-info.java
│
├── src/main/resources/com/example/starbucksproject/
│   ├── Gift Cards.fxml
│   ├── Menu Screen.fxml
│   ├── Order View.fxml
│   ├── RewardsScreen.fxml
│   ├── Store Screen.fxml
│   └── starbucks-logo.png
│
├── pom.xml
├── mvnw
├── mvnw.cmd
└── README.md
```

---

## 🛠️ Technologies Used

- Java
- JavaFX
- FXML
- Maven
- MVC-inspired design
- Object-Oriented Programming
- CSV file handling
- IntelliJ IDEA

---

## ▶️ How to Run

### 1. Clone the repository

```bash
git clone -b UpdatedScenes https://github.com/Isaac76896/Starbucks-Project.git
```

### 2. Open the project

Open the project folder in **IntelliJ IDEA** as a Maven project.

### 3. Build the project

```bash
mvn clean install
```

### 4. Run the application

You can run the app using Maven:

```bash
mvn javafx:run
```

Or run the main application class from IntelliJ:

```text
HelloApplication.java
```

---

## 🎯 Learning Goals

This project was created to practice and demonstrate:

- JavaFX application development
- FXML-based UI design
- Multi-scene navigation
- Object-Oriented Programming
- MVC-style project organization
- Controller-based application logic
- State management across screens
- Reading and organizing CSV data
- Building a desktop application with Maven

---

## 👥 Authors

### Isaac Lopez Salazar  
GitHub: https://github.com/Isaac76896

### Rositza Dineva  
GitHub: https://github.com/rxinn21

### Diego Rodriguez  
GitHub: https://github.com/Diegox4521

### Mark Guzman  
GitHub: https://github.com/markg1013

---

## ⭐ Repository Goal

This project is part of a software development portfolio and demonstrates experience with Java, JavaFX, FXML, Maven, object-oriented design, and multi-screen desktop application development.
