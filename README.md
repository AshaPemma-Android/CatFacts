# 🐱 Cat Facts App

A simple Android app that fetches random cat facts using Clean Architecture principles. Built with Kotlin, Jetpack libraries, Hilt for DI, and Retrofit for networking.

---

## 🚀 Features

- Fetches random cat facts
- Clean Architecture: Domain, Data, UI layers
- Hilt for Dependency Injection
- Kotlin Coroutines + Flow for async operations
- ViewModel with StateFlow for UI state management
- Unit tests with MockK and Turbine

---

## 📂 Project Structure

com.example.catfacts
│
├── data # API & repository implementation
│ ├── remote
│ │ └── dto # CatFactDto for network layer
│ └── repository # CatFactRepositoryImpl
│
├── domain
│ ├── model # CatFact domain model
│ ├── repository # Repository interface
│ └── usecase # Business logic (GetCatFactUseCase)
│
├── ui # ViewModel + UIState
│
├── di # Hilt modules
│
└── util # DispatcherProvider for coroutine control

---

## 📦 Tech Stack

- **Kotlin**
- **Jetpack Compose** – Modern UI toolkit
- **Retrofit** – for network requests
- **Hilt** – for dependency injection
- **Coroutines + Flow** – for async & reactive code
- **StateFlow** – for UI state management
- **JUnit + MockK + Turbine** – for testing

---

## 🧪 Testing

Includes unit tests for:
- `CatFactViewModel`
- `toDomain()` mapping in DTO
- Coroutine handling with `DispatcherProvider`

---

## 🌐 API

- Base URL: `https://catfact.ninja/`
- Endpoint: `/fact`

Sample response:
```json
{
  "fact": "Cats sleep 70% of their lives.",
  "length": 31
}
