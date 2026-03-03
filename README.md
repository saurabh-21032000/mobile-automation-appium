# 📱 Mobile Automation Framework – Appium + Java + TestNG

![Build](https://img.shields.io/github/actions/workflow/status/saurabh-21032000/mobile-automation-appium/maven.yml)
![Java](https://img.shields.io/badge/Java-17-blue)
![Appium](https://img.shields.io/badge/Appium-2.x-green)
![Maven](https://img.shields.io/badge/Maven-Build-red)
![Platform](https://img.shields.io/badge/Platform-Android-brightgreen)

---

## 🚀 Overview

This is a scalable, production-ready Mobile Automation Framework built using:

* Appium 2.x
* Java
* TestNG
* Maven
* Page Object Model (POM)
* Extent Reports
* GitHub Actions CI

The framework follows clean architecture principles and is designed for maintainability, scalability, and enterprise-level automation.

---

## 🏗 Framework Architecture

Test Layer (TestNG)
↓
BaseTest
↓
DriverManager
↓
CapabilityManager
↓
Appium Server
↓
Android Device / Emulator

---

## 📂 Project Structure

src
├── main
│   ├── java
│   │   ├── base
│   │   ├── core
│   │   ├── enums
│   │   ├── helpers
│   │   ├── pages
│   │   └── utils
│   └── resources
│       ├── config.properties
│       └── log4j2.xml
│
└── test
├── java
│   └── tests/android
└── resources
├── capabilities.json
└── testdata

---

## 🧠 Design Patterns Used

* Page Object Model (POM)
* Singleton Pattern (DriverManager)
* Factory Pattern (CapabilityManager)
* Listener Pattern (TestListener)
* Utility Abstraction Layer

---

## ⚙️ How To Run Tests

### 1️⃣ Start Appium Server

appium

### 2️⃣ Run via Maven

mvnd clean test

### 3️⃣ Run Specific Test

mvntest -Dtest=SignupTest

---

## 📊 Reporting

* Extent Reports integration
* Screenshot on failure
* Logging via Log4j2
* TestNG Listeners

---

## 🔁 Continuous Integration

This project includes GitHub Actions CI pipeline:

* Automatically triggers on push
* Performs Maven clean install
* Validates build
* Ensures no broken commits

---

## 📈 Scalability Features

* Environment-based configuration
* JSON-based capabilities
* CSV-based test data
* Centralized driver management
* Modular utilities

---

## 🧪 Sample Test Coverage

* Signup Flow
* Recharge Flow
* Call Details Validation

---

## 🛠 Tech Stack

* Java 17
* Appium 2.x
* Selenium
* TestNG
* Maven
* GitHub Actions
* Log4j2

---

## 👨‍💻 Author

Saurabh Yadav
QA Automation Engineer | Mobile Automation | Appium | Java | TestNG

---

## 🔥 Future Enhancements

* iOS support
* Parallel execution
* Dockerized Appium
* API Automation integration
* Allure Reporting
* Cloud execution (BrowserStack / SauceLabs)

---

⭐ If you find this framework useful, feel free to fork and enhance it.
