# Genealogy IA

An intelligent genealogy application featuring a Java 25 backend and Angular 21 frontend.

## 🚀 Getting Started

Follow these instructions to get the project up and running on your local machine.

### Prerequisites
- Java 25
- Node.js (Latest LTS)
- Maven

### Installation

#### Backend
```bash
cd backend
mvn clean install
```

#### Frontend
```bash
cd frontend
npm install
npm start
```

## 📚 Documentation

Detailed documentation for this project:

-   [Functional Documentation](./doc/functional/index.md) - Business rules and logic.
-   [Technical Documentation](./doc/technical/index.md) - Architecture, schema, and dev guides.
-   [Architecture Decision Records (ADR)](./doc/adr/) - Significant design decisions.

## 🏗️ Architecture

This project follows Clean Architecture principles.

```
genealogy-ia/
├── backend/          # Java Multi-module Maven Project
│   ├── domain/       # Enterprise Business Rules (Entities)
│   ├── application/  # Application Business Rules (Use Cases)
│   └── infrastructure/ # Frameworks & Drivers
├── frontend/         # Angular Application
└── doc/              # Documentation
```

## 🧪 Testing

#### Backend
```bash
cd backend
mvn test
```

#### Frontend
```bash
cd frontend
npm test
```
