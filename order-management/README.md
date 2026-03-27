# Order Management Microservice

A simple Order Management REST API built with Spring Boot.

---

## Approach

Orders are stored in a `ConcurrentHashMap` keyed by UUID for thread-safe in-memory storage without a database. 
The service layer is defined as an interface with a separate `OrderServiceImpl` to follow the dependency inversion principle and keep the controller decoupled from the implementation. 
Bean Validation on DTOs handles all 400 Bad Request cases declaratively, and status transition rules (`NEW → PROCESSING → COMPLETED`) are enforced in the service layer. 
All error responses including invalid enum values are centralised in a `GlobalExceptionHandler` to keep the controller and service clean.
